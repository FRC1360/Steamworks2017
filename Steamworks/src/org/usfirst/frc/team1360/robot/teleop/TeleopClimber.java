package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class TeleopClimber implements TeleopComponent {
	
	private static TeleopClimber instance;
	private HumanInput humanInput;
	private RobotOutput robotOutput;
	private SensorInput sensorInput;
	
	private double cooldown = 0
			;
	private boolean isToggled = false;
	

	
	TeleopClimber()
	{
		this.humanInput = HumanInput.getInstance();
		this.robotOutput = RobotOutput.getInstance();
		this.sensorInput = SensorInput.getInstance();
	}
	
	public static TeleopClimber getInstance()
	{
		if (instance == null)
			instance = new TeleopClimber();
		
		return instance;
	}

	@Override
	public void calculate() 
	{
		double speed = this.humanInput.getClimb();
		boolean toggleButton = this.humanInput.getOperatorAutoClimb();
		
		if(toggleButton && cooldown >= 25)
		{
			cooldown = 0;
			
			if(isToggled == false)
			{
				isToggled = true;
			}
			else
			{
				isToggled = false;
			}
		}
		else
		{
			cooldown++;
		}
		
		if(isToggled)
		{
			this.robotOutput.climb(0.25);
		}
		else
		{
			this.robotOutput.climb(speed);
		}
	}

	@Override
	public void disable() 
	{
		this.robotOutput.climb(0);
	}

}
