package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public class TeleopDrive implements TeleopComponent {
	
	private static TeleopDrive instance;
	private HumanInput humanInput;
	private RobotOutput robotOutput;
	
	TeleopDrive()
	{
		humanInput = HumanInput.getInstance();
		robotOutput = RobotOutput.getInstance();
	}

	public static TeleopDrive getInstance()
	{
		if (instance == null)
			instance = new TeleopDrive();
		
		return instance;
	}
	
	@Override
	public void calculate() {
		double speed = humanInput.getDriveRight() - humanInput.getDriveLeft();
		double turn = humanInput.getTurn();
		
		robotOutput.arcadeDrive(speed, turn);
	}

	@Override
	public void disable() {
		robotOutput.tankDrive(0, 0);
		
	}

}
