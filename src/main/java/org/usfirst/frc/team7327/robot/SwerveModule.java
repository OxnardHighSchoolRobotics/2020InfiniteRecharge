package org.usfirst.frc.team7327.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveModule{
    private CANSparkMax m_motor;
    private CANSparkMax mSteering;
    private Notifier pidLoop;          
    private volatile double currentError, lastError, pidOutput;
    private double setpoint, lastAngle;
    private static final double dt = 0.05;  
    private Potentiometer steeringEncoder;
    private final PIDController m_drivePIDController = new PIDController(.1, 0, 0);
    private Boolean isFlipped; 
    private CANEncoder encoder; 
    /**
     * @param kSteeringID   the ID of the steering motor
     * @param kDriveID      the ID of the drive motor
     * @param SteeringEncoder the AbsoluteEncoder for SwerveDrive
     * @param kP            the steering kP gain
     */
    public SwerveModule(int kSteeringID, int kDriveID, Potentiometer steeringEncoder, double kP, double kD, boolean isFlipped){
        m_motor = new CANSparkMax(kDriveID, MotorType.kBrushless);
        mSteering = new CANSparkMax(kSteeringID, MotorType.kBrushless);
        encoder = new CANEncoder(m_motor);
        lastAngle = 0;
        this.steeringEncoder = steeringEncoder;
        
        lastError = getModifiedError();
        pidLoop = new Notifier(() -> {
            currentError = getModifiedError();  
            pidOutput = kP * currentError + kD * (lastError - currentError);
            pidOutput = Math.min(pidOutput, .8);
            pidOutput = Math.max(pidOutput, -.8); 
            mSteering.set(-pidOutput); //Flipped turning value for Spark, if you use +pidOutput it will converge to error = 1 instead of 0
            SmartDashboard.putNumber("PidOutput: ", pidOutput);
            SmartDashboard.putNumber("CurrentError: ", currentError);
            lastError = currentError;
        });

        pidLoop.startPeriodic(dt);
        this.isFlipped = isFlipped; 
    }
    public double getError(){return setpoint - getSteeringEncoder();}
    public double getModifiedError(){return (boundHalfDegrees(getError()))/180;}
    public void setDrivePower(double power){
    	if(isFlipped) m_motor.set(-power);
        else          m_motor.set(power);
    }
    public void setSteeringDegrees(double deg){setpoint = boundHalfDegrees(deg);}
    public double getSetpointDegrees(){return setpoint;}
    public void set(double degrees, double power){
        double supplement = degrees > 0 ? degrees-180 : 180+degrees;
        if(Math.abs(supplement-lastAngle) <= 90){
            setSteeringDegrees(supplement); setDrivePower(-power);
            lastAngle = supplement;
        }else {setSteeringDegrees(degrees); setDrivePower(power); 
            lastAngle = degrees;
        }
    }
    public static double boundHalfDegrees(double angle){while(angle>=180)angle-=360;while(angle<-180)angle+=360; return angle;}
    public double getSteeringEncoder(){
        double angle=steeringEncoder.get();while(angle>360)angle-=360;while(angle<0)angle+=360;return angle; 
    }
    public double getDriveEncoder(){ return encoder.getPosition(); }
}
