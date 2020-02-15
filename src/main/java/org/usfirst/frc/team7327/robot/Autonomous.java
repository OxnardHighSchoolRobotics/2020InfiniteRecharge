package org.usfirst.frc.team7327.robot;

public class Autonomous {    

    public static void Auto(){
        //x,y,angle
        Robot.MoveTo(0, 0.25,0);
        Robot.SleepFor(2);
        Robot.MoveTo(.25, 0, 90);
        Robot.SleepFor(2);
        Robot.MoveTo(0, -0.25, 180);
        Robot.SleepFor(2);
        Robot.MoveTo(-.25, 0, -90);
        Robot.SleepFor(2);
        Robot.MoveTo(0, 0, 0);
        Robot.LimeAlign();
        
    }   
//use -180 to 180; ie, 270 = -90
    public static void Auto2(){
        Robot.MoveTo(0,0.30,0);
        Robot.MoveTo(0,.20,90);
        Robot.MoveTo(0,0.25,180);
        Robot.MoveTo(-.25,0,-90);
        Robot.LimeAlign();
                                      
    }

    public static void Auto3(){
        Robot.MoveTo(0.20,0,0);
        Robot.MoveTo(0,-.20,0);
        Robot.MoveTo(.25,0,0);
        Robot.MoveTo(-.25,0,0);
        Robot.MoveTo(0,0,0);
        Robot.LimeAlign();
    }
    public static void Auto4(){
        Robot.MoveTo(0,5.5,0); 
        Robot.MoveTo(1,5.5,180);
        Robot.MoveTo(0,0,0); 
        Robot.LimeAlign();
    }

    public static void BlueTrench5(){
        Robot.MoveTo(0,-3.223,0);
        //intake
        Robot.MoveTo(7.228,0,0);
        //linealine
        //shootball
    }
    public static void RedSpinner6(){
        Robot.MoveTo(0,-3.941,0);
        //intake
        Robot.MoveTo(2.458,3.941,0);
        //linealine
        //shootball
    }
    public static void DoubleCorner7(){
        Robot.MoveTo(0,-2.5,67.5);
        Robot.MoveTo(0,-0.7865,0);
        //intake
        Robot.MoveTo(0,-0.7865,0);
    }
    

}


