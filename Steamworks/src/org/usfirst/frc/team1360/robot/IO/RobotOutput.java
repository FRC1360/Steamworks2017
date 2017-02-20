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
	private Victor indexSystem;
	
	private Solenoid driveShifter;
	private Solenoid gearFlap;
	private Solenoid gearRelease;
	private Solenoid outFlap;
	private Solenoid intakeSolenoid;
	
	private static  RobotOutput instance;
	
	private RobotOutput()
	{
		driveLeftForward = new Victor(0);
		driveLeftRear = new Victor(1);
		driveRightForward = new Victor(2);
		driveRightRear = new Victor(3);
		climberFront = new Victor(4);
		climberRear = new Victor(5);
		intake = new Victor(8);
		indexSystem = new Victor(7);
		
		driveShifter = new Solenoid(0);
		gearFlap = new Solenoid(3); //We swtiched this port with outFlap
		gearRelease = new Solenoid(1);
		outFlap = new Solenoid (2);
		intakeSolenoid = new Solenoid(4);
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
		driveLeftForward.set(speed);
		driveLeftRear.set(speed);
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
	
	public void arcadeDrive(double speed, double turn)
	{
		double left = (speed) + turn;
		double right = (speed) - turn;
		
		tankDrive(left, right);
	}
	
	public void intake(double speed)
	{
		intake.set(speed);
		indexSystem.set(speed);
	}
	
	public void openItake(boolean shift)
	{
		intakeSolenoid.set(shift);
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
		driveShifter.set(shift);
	}
	
	public void outtake(boolean release)
	{
		outFlap.set(release);
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
		indexSystem.set(0);
		climberFront.set(0);
		climberRear.set(0);
		gearFlap.set(false);
		gearRelease.set(false);
		driveShifter.set(false);
		outFlap.set(false);
	}
}
