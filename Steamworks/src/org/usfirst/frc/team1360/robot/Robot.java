
package org.usfirst.frc.team1360.robot;

import java.io.IOException;
import java.util.Arrays;

import org.usfirst.frc.team1360.new_auto.AutonControl;
import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.teleop.TeleopControl;
import org.usfirst.frc.team1360.robot.util.OrbitCamera;
import org.usfirst.frc.team1360.server.Connection;
import org.usfirst.frc.team1360.navx.*;

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
	private OrbitCamera camera;
	private Connection connection;
	private PositionTracker pt; 
	int i;
	
	public Robot()
	{
		instance = this;
	}
	
    public void robotInit()
    {
    	System.out.println("Nick Mertin - GUI Test Code");
		this.connection = new Connection(5801);
    	this.robotOutput = RobotOutput.getInstance();
    	this.humanInput = HumanInput.getInstance();
    	this.teleopControl = TeleopControl.getInstance();
    	this.sensorInput = SensorInput.getInstance();
    	this.sensorInput.reset();
    	
    	camera = new OrbitCamera();
    	pt = PositionTracker.getInstance();
    	i = 0;
    }
    
    public static Robot getInstance()
    {
    	return instance;
    }
    
    public Connection getConnection()
    {
    	return connection;
    }

    public void autonomousInit() 
    {
    	AutonControl.start();
    	this.sensorInput.reset();
    	this.sensorInput.resetAHRS();
    }

    public void disabledInit()
    {
    	this.robotOutput.stopAll();
    	this.teleopControl.disable();
    	this.sensorInput.calculate();
    	AutonControl.stop();
    }
    
    public void disabledPeriodic()
    {
    	this.sensorInput.calculate();
    	AutonControl.select();
    	this.camera.updateCamera();
    }

    public void autonomousPeriodic()
    {
    	sensorInput.calculate();
    	this.sensorInput.calculate();
		SmartDashboard.putNumber("NavX Yaw", this.sensorInput.getAHRSYaw());
    }


    public void teleopPeriodic()
    {
        this.sensorInput.calculate();
        this.teleopControl.runCycle();
        this.camera.updateCamera();
        if (i == 10)
        {
        	//System.out.println(pt.getPosition()[0] + "\n" + pt.getPosition()[1] + "\n\n\n");
        	/*SmartDashboard.putNumber("Accel X", pt.getAcceleration()[0]);
        	SmartDashboard.putNumber("Accel Y", pt.getAcceleration()[1]);
        	SmartDashboard.putNumber("Vel X", pt.getVelocity()[0]);
        	SmartDashboard.putNumber("Vel Y", pt.getVelocity()[1]);
        	SmartDashboard.putNumber("Pos X", pt.getPosition()[0]);
        	SmartDashboard.putNumber("Pos Y", pt.getPosition()[1]);
        	SmartDashboard.putNumber("AHRS Vel X", sensorInput.getAHRSVelocityX());
        	SmartDashboard.putNumber("AHRS Vel Y", sensorInput.getAHRSVelocityY());
        	SmartDashboard.putNumber("PT Update Time", pt.timeDiff);*/
        	i = 0;
        }
        
        i++;
    }
 
}
