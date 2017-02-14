package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleopDrive implements TeleopComponent {
	
	private static TeleopDrive instance;
	private HumanInput humanInput;
	private RobotOutput robotOutput;
	private DriverConfig cfg = DriverConfig.RACING;
	
	private TeleopDrive()
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
	
	public void calculate() 
	{
		cfg.calculate(robotOutput, humanInput);
	}

	public void disable() {
		robotOutput.tankDrive(0, 0);
		
	}

}
