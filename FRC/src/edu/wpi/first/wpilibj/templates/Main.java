/*----------------------------------------------------------------------------*/
/*                                                                            */
/*                       Written by Steven Burnley                            */
/*                                                                            */
/*                                                                            */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;


public class Main extends SimpleRobot {
    
    //Declarations
    
    //Constants
    
    final int ABUTTON = 1;
    final int BBUTTON = 2;
    final int XBUTTON = 3;
    final int YBUTTON = 4;
    final int sL1 = 5;
    final int sR1 = 6;
    final double DEADZONE = .5;
    
    //Contoller input
    boolean A;
    boolean B;
    boolean X;
    boolean Y;
    boolean L1;
    boolean R1;
    double LX;
    double LY;
    double RX;
    
    //Motors
    
    Jaguar backRight; //Port 1
    
    Jaguar backLeft; //Port 2
    
    Jaguar frontLeft; //Port 3
    
    Jaguar frontRight; //Port 4
    
    Jaguar trigger; //Port 6
    
    Victor armPull; //Port 5
    
    Jaguar ballPick; //Port 7
    
    //Joystick
    
    Joystick stick1; //Port 1
    Joystick stick2; //Port 2
    
    //Drivetrain
    
    RobotDrive driveSystem;
    
    //Driver Station
    
    DriverStationLCD driver;
    
    Timer timer;
    
    AnalogChannel distance;
    
    DigitalInput armDown;
    
    public void autonomous() {
        frontLeft = new Jaguar(3);
        frontRight = new Jaguar(4);
        backRight = new Jaguar(1);
        backLeft = new Jaguar(2);
        
        timer.start();
                
        while(timer.get() < 100){
            frontLeft.set(100);
            frontRight.set(100);
            backRight.set(100);
            backLeft.set(100);
        }
        
        timer.stop();
        timer.reset();
        
        frontLeft.set(0);
        frontRight.set(0);
        backRight.set(0);
        backLeft.set(0);
        
        timer.start();
        
        while(timer.get() < .5){
            trigger.set(-100);
        }
        
        trigger.set(0);
        
        timer.stop();
        timer.reset();
    }

    
    public void operatorControl() {
        frontLeft = new Jaguar(3);
        frontRight = new Jaguar(4);
        backRight = new Jaguar(1);
        backLeft = new Jaguar(2);
        trigger = new Jaguar(6);
        armPull = new Victor(5);
        ballPick = new Jaguar(7);
        
        stick1 = new Joystick(1);
        stick2 = new Joystick(2);
        
        distance = new AnalogChannel(1);
        
        driver = DriverStationLCD.getInstance();
        
        armDown = new DigitalInput(13);
        
        timer = new Timer();
        
        boolean driveDir = false;
        
        driveSystem = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
        
        driveSystem.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        driveSystem.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        
        while(isOperatorControl() && isEnabled()){
            
            contollerAssign();
            
            //Set the motors
            driveSystem.mecanumDrive_Cartesian(LX, LY, RX, 0.0);
            
            //Pull arm down
            if(X && Y){
                armPull.set(0);
            }else if(Y){
                armPull.set(-25);
            }else if(X && !armDown.get()){
                armPull.set(25);
            }else{
                armPull.set(0);
            }
            
            //Trigger action
            if(A && B){
                trigger.set(0);
            }else if(A){
                trigger.set(-100);
            }else if(B){
                trigger.set(100);
            }else{
                trigger.set(0);
            }
            
            //Control the picker
            if(L1 && R1){
                ballPick.set(0);
            }else if(L1){
                ballPick.set(25);
            }else if(R1){
                ballPick.set(-1);
            }else{
                ballPick.set(0);
                
            }
            
            //driver.println(DriverStationLCD.Line.kUser1, 1, String.valueOf(armDown.get()));
            //driver.updateLCD();
            
            
        }
    }
    
    
    public void test() {
    
    }
    
    
    
    public void autoCock(){
        timer.start();
        
        while(timer.get() < 1){
            trigger.set(-10);
        }
        
        while(!armDown.get()){
            armPull.set(-20);
        }
        
        
        
    }
    
    public void fire(){
        timer.start();
        
        while(timer.get() < .05){
            trigger.set(50);
        }
        
        
        while(timer.get() > .05 && timer.get() < .1){
            trigger.set(-50);
        }
        
        timer.stop();
        timer.reset();
    }
    
    /*
        This function is disgusting, but is a necessary evil. It nicely assigns the controller
        buttons to variables declared above. It makes it so that if there is no input coming in
        from controller 1, it takes input from controller 2.
        THIS FUNCTION IS LARGE, CONFUSING, AND GROSS. DO NOT TOUCH.
    */
    public void contollerAssign(){
        
        if(!stick1.getRawButton(1) && !stick1.getRawButton(2) && !stick1.getRawButton(3) && !stick1.getRawButton(4) &&
           !stick1.getRawButton(5) && !stick1.getRawButton(6) && !stick1.getRawButton(7) && !stick1.getRawButton(8) &&
           (stick1.getRawAxis(1) < .5 && stick1.getRawAxis(1) > -.5) && (stick1.getRawAxis(2) < .5 && stick1.getRawAxis(2) > -.5) &&
           (stick1.getRawAxis(4) < .5 && stick1.getRawAxis(4) > -.5)){
          
            //Lance
            A = stick2.getRawButton(ABUTTON);
            B = stick2.getRawButton(BBUTTON);
            X = stick2.getRawButton(XBUTTON);
            Y = stick2.getRawButton(YBUTTON);
            L1 = stick2.getRawButton(sL1);
            R1 = stick2.getRawButton(sR1);
            
            if(stick2.getRawAxis(2) < -DEADZONE || stick2.getRawAxis(2) > DEADZONE){
                LX = stick2.getRawAxis(2);
            }
            if(stick2.getRawAxis(1) < -DEADZONE || stick2.getRawAxis(1) > DEADZONE){
                LY = stick2.getRawAxis(1);
            }
            if(stick2.getRawAxis(4) < -DEADZONE || stick2.getRawAxis(4) > DEADZONE){
                RX = stick2.getRawAxis(4);
            }
            
            
        }else{
            
            //Shubh
            A = stick1.getRawButton(ABUTTON);
            B = stick1.getRawButton(BBUTTON);
            X = stick1.getRawButton(XBUTTON);
            Y = stick1.getRawButton(YBUTTON);
            L1 = stick1.getRawButton(sL1);
            R1 = stick1.getRawButton(sR1);
            
            if(stick1.getRawAxis(2) < -DEADZONE || stick1.getRawAxis(2) > DEADZONE){
                LX = -stick1.getRawAxis(2);
            }
            if(stick1.getRawAxis(1) < -DEADZONE || stick1.getRawAxis(1) > DEADZONE){
                LY = -stick1.getRawAxis(1);
            }
            if(stick1.getRawAxis(4) < -DEADZONE || stick1.getRawAxis(4) > DEADZONE){
                RX = stick1.getRawAxis(4);
            }
            
            
        }
    }
}
