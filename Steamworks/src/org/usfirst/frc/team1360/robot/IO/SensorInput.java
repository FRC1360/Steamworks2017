package org.usfirst.frc.team1360.robot.IO;
/*****
 * Author: Tatiana Tomas Zahhar
 * Date 30 Jan 2017 - added pdp variable; getClimberFrontCurrent method; getClimberBackCurrent method; removed calculate
 *****/

import edu.wpi.first.wpilibj.PowerDistributionPanel;


public class SensorInput {

	private static SensorInput instance;				//Fields of class SensorInput
	
	private PowerDistributionPanel PDP;
	
	private SensorInput()								//Constructor to initialize fields  
	{
		PDP = new PowerDistributionPanel();
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
		return this.PDP.getCurrent(4);					//PDP port 4 for ClimberFront Motor
	}
	
	public double getClimberBackCurrent()				
	{
		return this.PDP.getCurrent(5);					//PDP port 5 for ClimberBack Motor
	}
	
	public void calculate()
	{
		
	}

	public void reset()
	{
		
	}
}
