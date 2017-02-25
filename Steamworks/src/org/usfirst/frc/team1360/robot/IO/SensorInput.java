package org.usfirst.frc.team1360.robot.IO;
/*****
 * Author: Tatiana Tomas Zahhar
 * Date 30 Jan 2017 - added pdp variable; getClimberFrontCurrent method; getClimberBackCurrent method; removed calculate
 *****/

import org.usfirst.frc.team1360.robot.Robot;
import org.usfirst.frc.team1360.server.components.ClimberCurrentDisplayComponent;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SensorInput {

	private static SensorInput instance;				//Fields of class SensorInput
	
	private PowerDistributionPanel PDP;
	private ClimberCurrentDisplayComponent currentDisplay;
	public AHRS ahrs;
	
	public static final double driveP = 0.1;
	public static final double driveI = 0.00005;
	public static final double driveD = 0.01;
	
	private Encoder driveLeftEncoder;
	
	
	private SensorInput()								//Constructor to initialize fields  
	{
		driveLeftEncoder = new Encoder(0, 1);
		PDP = new PowerDistributionPanel();
		ahrs = new AHRS(I2C.Port.kMXP); // THIS SHOULD BE THE ONLY AHRS CONSTRUCTOR BEING CALLED, IF IT IS NOT, DELETE THE OTHER ONE
	}
	
	public static SensorInput getInstance()				//Check to make sure that SensorInput exists
	{
		if (instance == null)
		{
			instance = new SensorInput();
		}
		
		return instance;
	}
	
	public double getAHRSYaw()
	{
		return this.ahrs.getYaw();
	}
	
	public double getAHRSPitch()
	{
		return this.ahrs.getPitch();
	}
	
	public double getAHRSRoll()
	{
		return this.ahrs.getRoll();
	}
	
	public void resetAHRS()
	{
		this.ahrs.reset();
	}
	
	public double getClimberFrontCurrent()				//Method in class SensorInput
	{
		return this.PDP.getCurrent(0);					//PDP port 0 for ClimberFront Motor
	}
	
	public double getClimberBackCurrent()				
	{
		return this.PDP.getCurrent(1);	//PDP port 1 for ClimberBack Motor
	}
	
	public double getLeftDriveEncoder()
	{
		return this.driveLeftEncoder.get();
	}
	
	public void resetLeftEncoder()
	{
		this.driveLeftEncoder.reset();
	}
	
	public void calculate()
	{
		if (currentDisplay == null)
		{
			currentDisplay = new ClimberCurrentDisplayComponent();
			Robot.getInstance().getConnection().addComponent(currentDisplay, 1);
		}
		currentDisplay.update();
		
		SmartDashboard.putNumber("Left Enc", this.getLeftDriveEncoder());
		SmartDashboard.putNumber("Climber Average Current", (this.getClimberFrontCurrent() + this.getClimberBackCurrent()) / 2);

	}

	public void reset()
	{
		this.ahrs.reset();
	}
}
