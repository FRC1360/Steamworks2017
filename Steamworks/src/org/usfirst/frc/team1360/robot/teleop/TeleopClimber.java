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
	
	private double cooldown = 0; //Cooldown for toggle.
	private boolean isToggled = false; //Toggle value.
	

	
	TeleopClimber() //Define access to HumanInput, RobotOutput, and SensorInput from TeleopClimber.
	{
		this.humanInput = HumanInput.getInstance();
		this.robotOutput = RobotOutput.getInstance();
		this.sensorInput = SensorInput.getInstance();
	}
	
	public static TeleopClimber getInstance() //Get the current instance of TeleopClimber. If none exists, make one.
	{
		if (instance == null)
			instance = new TeleopClimber();
		
		return instance;
	}

	@Override
	public void calculate() //Run each tick to process data for TeleopClimber.
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
	public void disable() //Run when robot is disabled.
	{
		this.robotOutput.climb(0);
	}

}
