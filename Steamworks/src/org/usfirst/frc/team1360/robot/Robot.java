
package org.usfirst.frc.team1360.robot;

import org.usfirst.frc.team1360.auto.AutonControl;
import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.teleop.TeleopControl;
import org.usfirst.frc.team1360.server.Connection;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	private static Robot instance;
	
	private RobotOutput robotOutput;
	private HumanInput humanInput;
	private SensorInput sensorInput;
	private TeleopControl teleopControl;
	private AutonControl autonControl;
	private Connection connection;
	
	public Robot()
	{
		// Called by WPILib for us
		instance = this;
	}
	
	public static Robot getInstance()
	{
		// Already initialized by WPILib through constructor
		return instance;
	}
	
    public void robotInit() 
    {
    	System.out.println("Nick Mertin - GUI Test Code");
		this.connection = new Connection(5801);
    	this.robotOutput = RobotOutput.getInstance();
    	this.humanInput = HumanInput.getInstance();
    	this.teleopControl = TeleopControl.getInstance();
    	this.sensorInput = SensorInput.getInstance();
    	this.autonControl = AutonControl.getInstance();
    }
    
    public Connection getConnection()
    {
    	return connection;
    }

    public void autonomousInit() 
    {
    	autonControl.initialize();
    }

    public void disabledInit()
    {
    	this.robotOutput.stopAll();
    	this.teleopControl.disable();
    }
    
    public void disabledPeriodic()
    {
    	this.sensorInput.calculate();
    }

    public void autonomousPeriodic()
    {
    	sensorInput.calculate();
    	autonControl.runCycle();
    }


    public void teleopPeriodic()
    {
        this.sensorInput.calculate();
        this.teleopControl.runCycle();
    }
 
}
