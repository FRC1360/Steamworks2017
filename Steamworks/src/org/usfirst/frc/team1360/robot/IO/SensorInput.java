package org.usfirst.frc.team1360.robot.IO;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SensorInput {

	private static SensorInput instance;
	
	private PowerDistributionPanel PDP;
	
	private SensorInput()
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
	
	public double getFrontClimbCurrent()
	{
		return this.PDP.getCurrent(4);
	}
	
	public double getBackClimbCurrent()
	{
		return this.PDP.getCurrent(5);
	}
	
	public void calculate()
	{
		SmartDashboard.putNumber("Front Climb Current", getFrontClimbCurrent());
		SmartDashboard.putNumber("Back Climb Current", getBackClimbCurrent());
	}

	public void reset()
	{
		
	}
}
