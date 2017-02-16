
package org.usfirst.frc.team1360.robot;

import java.io.IOException;

import org.usfirst.frc.team1360.auto.AutonControl;
import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.teleop.TeleopControl;
import org.usfirst.frc.team1360.robot.util.OrbitCamera;
import org.usfirst.frc.team1360.server.Connection;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {	
	private RobotOutput robotOutput;
	private SensorInput sensorInput;
	private TeleopControl teleopControl;
	private AutonControl autonControl;
	private OrbitCamera camera;
	
    public void robotInit() 
    {	
    	this.robotOutput = RobotOutput.getInstance();
    	this.teleopControl = TeleopControl.getInstance();
    	this.sensorInput = SensorInput.getInstance();
    	this.autonControl = AutonControl.getInstance();
    	this.sensorInput.reset();
    	camera = new OrbitCamera("10.13.60.3", "Axis Camera");
    }
    

    public void autonomousInit() 
    {
    	this.autonControl.initialize();
    	this.sensorInput.reset();
    }

    public void disabledInit()
    {
    	this.robotOutput.stopAll();
    	this.teleopControl.disable();
    	this.sensorInput.calculate();
    }
    
    public void disabledPeriodic()
    {
    	this.sensorInput.calculate();
    	this.autonControl.updateModes();
    }

    public void autonomousPeriodic()
    {
    	autonControl.runCycle();
    	this.sensorInput.calculate();
    }


    public void teleopPeriodic()
    {
        this.sensorInput.calculate();
        this.teleopControl.runCycle();
    }
 
}
