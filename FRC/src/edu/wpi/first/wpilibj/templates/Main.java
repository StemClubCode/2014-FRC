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
    final int L1 = 5;
    final int R1 = 6;
    
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
        
        frontLeft.set(0);
        frontRight.set(0);
        backRight.set(0);
        backLeft.set(0);
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
        
        distance = new AnalogChannel(1);
        
        driver = DriverStationLCD.getInstance();
        
        armDown = new DigitalInput(13);
        
        timer = new Timer();
        
        boolean driveDir = false;
        
        driveSystem = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
        
        driveSystem.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        driveSystem.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        
        while(isOperatorControl() && isEnabled()){
            
            double dir;
            double alsoDir;
            double alsoAlsoDir;
            
            if(stick1.getRawButton(7) && stick1.getRawButton(8)){
                
            }else if(stick1.getRawButton(8)){
                driveDir = true;
            }else if(stick1.getRawButton(7)){
                driveDir = false;
            }else{
                
            }
            
            if(driveDir){
                dir =  stick1.getRawAxis(2);
                alsoDir = stick1.getRawAxis(1);
                alsoAlsoDir = -stick1.getRawAxis(4);
            }else{
                dir = -stick1.getRawAxis(2);
                alsoDir = -stick1.getRawAxis(1);
                alsoAlsoDir = -stick1.getRawAxis(4);
            }
            
            driveSystem.mecanumDrive_Cartesian(alsoDir, dir, alsoAlsoDir, 0.0);
            
            if(stick1.getRawButton(3) && stick1.getRawButton(4)){
                armPull.set(0);
            }else if(stick1.getRawButton(4) ){
                armPull.set(-25);
            }else if(stick1.getRawButton(3) && !armDown.get()){
                armPull.set(25);
            }else{
                armPull.set(0);
            }
            
            if(stick1.getRawButton(1) && stick1.getRawButton(2)){
                trigger.set(0);
            }else if(stick1.getRawButton(1)){
                trigger.set(-100);
            }else if(stick1.getRawButton(2)){
                trigger.set(100);
            }else{
                trigger.set(0);
            }
            
            if(stick1.getRawButton(L1) && stick1.getRawButton(R1)){
                ballPick.set(0);
                
            }else if(stick1.getRawButton(L1)){
                ballPick.set(25);
            }else if(stick1.getRawButton(R1)){
                ballPick.set(-1);
            }else{
                ballPick.set(0);
                
            }
            
            driver.println(DriverStationLCD.Line.kUser1, 1, String.valueOf(armDown.get()));
            driver.updateLCD();
            
            
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
}
