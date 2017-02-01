package org.usfirst.frc.team1360.robot.IO;
/*****
 * Author: Tatiana Tomas Zahhar
 * Date 30 Jan 2017 - added pdp variable; getClimberFrontCurrent method; getClimberBackCurrent method; removed calculate
 *****/

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SensorInput {

	private static SensorInput instance;     //fields of class SensorInput
	
	private PowerDistributionPanel PDP;
	private Encoder leftDriveEncoder;
	private Encoder rightDriveEncoder;
	
	
	//We have to add some extra stuff for gearing calculation
	private double ticksPerInch = /*Ticks per rotation divided by */ Math.PI /* multiplied by wheel diameter*/;
	
	private SensorInput()                   //constructor to initialize fields  
	{
		PDP = new PowerDistributionPanel();
		leftDriveEncoder = new Encoder(4, 5);  //Random numbers are random
		rightDriveEncoder = new Encoder(1, 2); //Random numbers are random
	}
	
	public static SensorInput getInstance()
	{
		if (instance == null)
		{
			instance = new SensorInput();
		}
		
		return instance;
	}
	
	public double getClimberFrontCurrent()    //method in class SensorInput
	{
		return this.PDP.getCurrent(4);        //PDP port 4 for ClimberFront Motor
	}
	
	public double getClimberBackCurrent()     //PDP port 5 for ClimberBack Motor
	{
		return this.PDP.getCurrent(5);
	}
	
	public double getLeftDriveEncoder()
	{
		return leftDriveEncoder.get() / ticksPerInch;
	}
	
	public double getRightDriveEncoder()
	{
		return rightDriveEncoder.get() / ticksPerInch;
	}
	
	public void calculate()
	{
		SmartDashboard.putNumber("Left Encoder", this.getLeftDriveEncoder());
		SmartDashboard.putNumber("Right Encoder", this.getRightDriveEncoder());
	}

	public void reset()
	{
		rightDriveEncoder.reset();
		leftDriveEncoder.reset();
	}
}
