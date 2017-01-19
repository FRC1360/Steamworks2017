package org.usfirst.frc.team1360.robot.IO;

import edu.wpi.first.wpilibj.Victor;

public class RobotOutput {
	
	private Victor driveLeftForward;
	private Victor driveLeftBackward;
	private Victor driveRightForward;
	private Victor driveRightBackward;
	
	private static  RobotOutput instance;
	
	private RobotOutput()
	{
		driveLeftForward = new Victor(0);
		driveLeftBackward = new Victor(1);
		driveRightForward = new Victor(2);
		driveRightBackward = new Victor(3);
	}
	
	public static RobotOutput getInstance()
	{
		if (instance == null)
		{
			instance = new RobotOutput();
		}
		
		return instance;
	}
	
	public void tankDrive(double left, double right)
	{
		driveLeftForward.set(-left);
		driveLeftBackward.set(-left);
		driveRightForward.set(right);
		driveRightBackward.set(right);
}
	public void stopAll()
	{
		
	}
}
