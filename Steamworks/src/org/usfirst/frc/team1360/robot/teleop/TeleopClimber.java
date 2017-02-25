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
	private boolean overCurrent = false;
	private boolean overriden = false;
	
	private final double currentThreshold = 0; 
	

	
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
		double averageCurrent = (this.sensorInput.getClimberBackCurrent() + this.sensorInput.getClimberFrontCurrent()) / 2;
		double speed = this.humanInput.getClimb();
		boolean override = this.humanInput.getOverride();
		
		if(speed > 0.001)
			System.out.println(averageCurrent);
		
		
		if(averageCurrent > this.currentThreshold)
			this.overCurrent = true;
		
		if(this.overCurrent && override)
			this.overriden = true;
		
		if(this.overCurrent && !overriden)
		{
			this.robotOutput.climb(0.2);
		}
		else if(this.overCurrent && overriden)
		{
			this.robotOutput.climb(speed);
		}
		else
		{
			this.robotOutput.climb(speed);
		}
		
		
		this.robotOutput.climb(speed);
	}

	@Override
	public void disable() 
	{
		this.robotOutput.climb(0);
	}

}
