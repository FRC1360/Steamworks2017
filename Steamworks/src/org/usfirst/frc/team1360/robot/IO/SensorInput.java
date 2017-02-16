package org.usfirst.frc.team1360.robot.IO;
/*****
 * Author: Tatiana Tomas Zahhar
 * Date 30 Jan 2017 - added pdp variable; getClimberFrontCurrent method; getClimberBackCurrent method; removed calculate
 *****/

import org.usfirst.frc.team1360.robot.Robot;
import org.usfirst.frc.team1360.server.components.ClimberCurrentDisplayComponent;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SensorInput {

	private static SensorInput instance;				//Fields of class SensorInput
	
	private PowerDistributionPanel PDP;
	private ClimberCurrentDisplayComponent currentDisplay;
	//private double ticksPerInch = 1024 * 24.0 / 40.0 * Math.PI * 8;
	private Encoder leftDriveEncoder;
	private Encoder rightDriveEncoder;
	private AHRS ahrs;
	
	private SensorInput()								//Constructor to initialize fields  
	{
		PDP = new PowerDistributionPanel();
		leftDriveEncoder = new Encoder(2, 3);
		rightDriveEncoder = new Encoder(0, 1);
		ahrs = new AHRS(SPI.Port.kMXP);
		
		SmartDashboard.putNumber("Drive Enc P: ", 1.0);
		SmartDashboard.putNumber("Drive Enc I: ", 0.01);
		SmartDashboard.putNumber("Drive Enc D: ", 0.1);		
	}
	
	public static SensorInput getInstance()				//Check to make sure that SensorInput exists
	{
		if (instance == null)
		{
			instance = new SensorInput();
		}
		
		return instance;
	}
	
	
	
	public double getClimberFrontCurrent()				//Method in class SensorInput
	{
		return this.PDP.getCurrent(0);					//PDP port 0 for ClimberFront Motor
	}
	
	public double getClimberBackCurrent()				
	{
		return this.PDP.getCurrent(1);					//PDP port 1 for ClimberBack Motor
	}
	
	
	public double getLeftDriveEncoder()
	{
		return this.leftDriveEncoder.get();// / ticksPerInch;
	}
	
	public double getRightDriveEncoder()
	{
		return this.rightDriveEncoder.get();// / ticksPerInch;
	}
	
	public double getDriveEncoderAverage()
	{
		return (this.getRightDriveEncoder() + this.getLeftDriveEncoder()) / 2;
	}
	
	public double getEncoderDifference()
	{
		return this.getLeftDriveEncoder() - this.getRightDriveEncoder();
	}
	
	public double getAHRSYaw()
	{
		return this.ahrs.getYaw();
	}
	
	public void calculate()
	{
		if (currentDisplay == null)
		{
			currentDisplay = new ClimberCurrentDisplayComponent();
			Robot.getInstance().getConnection().addComponent(currentDisplay, 1);
		}
		currentDisplay.update();
		SmartDashboard.putNumber("Left Encoder", this.getLeftDriveEncoder());
		SmartDashboard.putNumber("Right Drive Encoder", this.getRightDriveEncoder());
	}

	public void reset()
	{
		this.leftDriveEncoder.reset();
		this.rightDriveEncoder.reset();
	}
}
