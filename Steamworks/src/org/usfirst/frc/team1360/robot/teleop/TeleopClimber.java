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
		double averageCurrent = (this.sensorInput.getBackClimbCurrent() + this.sensorInput.getFrontClimbCurrent()) / 2;
		double speed = this.humanInput.getClimb();
		
		if (averageCurrent > 30 && !this.humanInput.getOverride())
		{
			this.robotOutput.climb(0);
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
