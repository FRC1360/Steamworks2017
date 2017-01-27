package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class TeleopClimber implements TeleopComponent {
	
	private PowerDistributionPanel pdp = new PowerDistributionPanel();
	private static TeleopClimber instance;
	private HumanInput humanInput;
	private RobotOutput robotOutput;
	
	TeleopClimber()
	{
		humanInput = HumanInput.getInstance();
		robotOutput = RobotOutput.getInstance();
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
		
	}

	@Override
	public void disable() 
	{
		
		
	}

}
