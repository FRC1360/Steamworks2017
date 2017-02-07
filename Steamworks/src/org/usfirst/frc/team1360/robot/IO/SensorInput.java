package org.usfirst.frc.team1360.robot.IO;
/*****
 * Author: Tatiana Tomas Zahhar
 * Date 30 Jan 2017 - added pdp variable; getClimberFrontCurrent method; getClimberBackCurrent method; removed calculate
 *****/

import org.usfirst.frc.team1360.robot.Robot;
import org.usfirst.frc.team1360.server.components.ClimberCurrentDisplayComponent;

import edu.wpi.first.wpilibj.PowerDistributionPanel;


public class SensorInput {

	private static SensorInput instance;     //fields of class SensorInput
	
	private PowerDistributionPanel PDP;
	private ClimberCurrentDisplayComponent currentDisplay;
	
	private SensorInput()                   //constructor to initialize fields  
	{
		PDP = new PowerDistributionPanel();
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
	
	public void calculate()
	{
		if (currentDisplay == null)
		{
			currentDisplay = new ClimberCurrentDisplayComponent();
			Robot.getInstance().getConnection().addComponent(currentDisplay, 1);
		}
		currentDisplay.update();
	}

	public void reset()
	{
		
	}
}
