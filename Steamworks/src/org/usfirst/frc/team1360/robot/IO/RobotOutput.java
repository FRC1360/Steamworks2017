package org.usfirst.frc.team1360.robot.IO;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

public class RobotOutput {
	
	private Victor driveLeftForward;
	private Victor driveLeftRear;
	private Victor driveRightForward;
	private Victor driveRightRear;
	private Victor climberFront;
	private Victor climberRear;
	private Victor intake;
	
	private Solenoid leftDrive;
	private Solenoid rightDrive;
	private Solenoid gearFlap;
	private Solenoid gearRelease;
	
	private static  RobotOutput instance;
	
	private RobotOutput()
	{
		driveLeftForward = new Victor(0);
		driveLeftRear = new Victor(1);
		driveRightForward = new Victor(2);
		driveRightRear = new Victor(3);
		climberFront = new Victor(4);
		climberRear = new Victor(5);
		intake = new Victor(6);
		
		leftDrive = new Solenoid(0);
		rightDrive = new Solenoid(1);
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
	
	public void setDriveLeft(double speed)
	{
		driveLeftForward.set(-speed);
		driveLeftRear.set(-speed);
	}
	
	public void setDriveRight(double speed)
	{
		driveRightForward.set(speed);
		driveRightRear.set(speed);
	}
	
	public void tankDrive(double left, double right)
	{
		driveLeftForward.set(-left);
		driveLeftRear.set(-left);
		driveRightForward.set(right);
		driveRightRear.set(right);
	}
	
	public void arcadeDrive(double turn, double speed)
	{
		double left = (-turn) - speed;
		double right = (-turn) + speed;
		
		tankDrive(left, right);
	}
	
	public void intake(double speed)
	{
		intake.set(speed);
	}
	
	public void releaseGear(boolean release)
	{
		gearRelease.set(release);
	}
	
	public void flapGear(boolean release)
	{
		gearFlap.set(release);
	}
	
	public void shiftSpeed(boolean shift)
	{
		leftDrive.set(shift);
		rightDrive.set(shift);
	}
	
	public void climb(double speed)
	{
		climberFront.set(speed);
		climberRear.set(speed);
	}
	
	public void stopAll()
	{
		driveLeftForward.set(0);
		driveLeftRear.set(0);
		driveRightForward.set(0);
		driveRightRear.set(0);
		intake.set(0);
		climberFront.set(0);
		climberRear.set(0);
		gearFlap.set(false);
		gearRelease.set(false);
	}
}
