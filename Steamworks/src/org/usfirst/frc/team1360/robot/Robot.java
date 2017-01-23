
package org.usfirst.frc.team1360.robot;

import java.io.IOException;

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

	private RobotOutput robotOutput;
	private HumanInput humanInput;
	private SensorInput sensorInput;
	private TeleopControl teleopControl;
	private Connection connection;
	
	
    public void robotInit() 
    {
    	this.robotOutput = RobotOutput.getInstance();
    	this.humanInput = HumanInput.getInstance();
    	this.teleopControl = TeleopControl.getInstance();
    	this.sensorInput = SensorInput.getInstance();
    	try
    	{
			this.connection = new Connection(5800);
		}
    	catch (IOException e)
    	{
			System.err.println("Unable to open connection to driver station!");
			e.printStackTrace();
		}
    }
    
    public Connection GetConnection()
    {
    	return connection;
    }

    public void autonomousInit() 
    {

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
    	
    }


    public void teleopPeriodic()
    {
        this.sensorInput.calculate();
        this.teleopControl.runCycle();
    }
 
}
