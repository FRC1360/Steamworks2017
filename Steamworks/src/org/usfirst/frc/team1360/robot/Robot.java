
package org.usfirst.frc.team1360.robot;

import java.io.IOException;
import java.util.Arrays;

import org.usfirst.frc.team1360.auto.AutonControl;
import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.teleop.TeleopControl;
import org.usfirst.frc.team1360.robot.util.OrbitCamera;
import org.usfirst.frc.team1360.server.Connection;
import org.usfirst.frc.team1360.navx.*;
import org.usfirst.frc.team1360.position.DriveEncoderPositionProvider;
import org.usfirst.frc.team1360.position.PositionProvider;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {	
	private static Robot instance;
	
	private final double DRIVE_WIDTH = 36;
	private final double WHEEL_DIAMETER = 4;
	private final double ENCODER_DRIVE_GEAR_RATIO = 1;
	private final int ENCODER_TICKS_PER_ROTATION = 1024;
	
	private RobotOutput robotOutput;
	private HumanInput humanInput;
	private SensorInput sensorInput;
	private TeleopControl teleopControl;
	private AutonControl autonControl;
	private OrbitCamera camera;
	private Connection connection;
	private PositionProvider position;
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
    	this.autonControl = AutonControl.getInstance();
    	this.sensorInput.reset();
    	
    	camera = new OrbitCamera();
    	position = new DriveEncoderPositionProvider(sensorInput, 1000, DRIVE_WIDTH, WHEEL_DIAMETER, ENCODER_DRIVE_GEAR_RATIO, ENCODER_TICKS_PER_ROTATION);
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
    	this.position.start();
    	this.autonControl.initialize();
    	this.sensorInput.reset();
    	this.sensorInput.resetAHRS();
    }

    public void disabledInit()
    {
    	this.position.stop();
    	this.robotOutput.stopAll();
    	this.teleopControl.disable();
    	this.sensorInput.calculate();
    }
    
    public void disabledPeriodic()
    {
    	this.sensorInput.calculate();
    	this.autonControl.updateModes();
    	this.camera.updateCamera();
    }

    public void autonomousPeriodic()
    {
    	sensorInput.calculate();
    	autonControl.runCycle();
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
