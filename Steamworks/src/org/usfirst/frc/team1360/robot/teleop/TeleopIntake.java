package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public class TeleopIntake implements TeleopComponent {

	private static TeleopIntake instance;
	private RobotOutput robotOutput;
	private HumanInput humanInput;
	
	public static TeleopIntake getInstance()
	{
		if (instance == null)
		{
			instance = new TeleopIntake();
		}
		
		return instance;
	}
	
	private TeleopIntake()
	{
		this.robotOutput = RobotOutput.getInstance();
		this.humanInput = HumanInput.getInstance();
	}
	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		double speed = this.humanInput.getIntake();
		this.robotOutput.intake(speed);
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		this.robotOutput.intake(0);
	}

}