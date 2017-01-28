package org.usfirst.frc.team1360.robot.IO;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

public class RobotOutput {
	
	private Victor driveLeftForward;
	private Victor driveLeftBackward;
	private Victor driveRightForward;
	private Victor driveRightBackward;
	private Victor climberFront;
	private Victor climberBack;
	
	private Solenoid gearFlap;
	private Solenoid gearRelease;
	
	private static  RobotOutput instance;
	
	private RobotOutput()
	{
		driveLeftForward = new Victor(0);
		driveLeftBackward = new Victor(1);
		driveRightForward = new Victor(2);
		driveRightBackward = new Victor(3);
		climberFront = new Victor(4);
		climberBack = new Victor(5);
		
		gearFlap = new Solenoid(2);
		gearRelease = new Solenoid(3);
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
	
	public void arcadeDrive(double turn, double speed)
	{
		double left = (-turn) - speed;
		double right = (-turn) + speed;
		
		tankDrive(left, right);
	}
	
	public void releaseGear(boolean release)
	{
		gearFlap.set(release);
		gearRelease.set(release);
	}
	
	public void climb(double speed)
	{
		climberFront.set(speed);
		climberBack.set(speed);
	}
	
	public void stopAll()
	{
		driveLeftForward.set(0);
		driveLeftBackward.set(0);
		driveRightForward.set(0);
		driveRightBackward.set(0);
	}
}
