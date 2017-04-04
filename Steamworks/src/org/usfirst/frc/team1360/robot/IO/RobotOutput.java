package org.usfirst.frc.team1360.robot.IO;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotOutput {
	
	private Victor driveLeftForward;
	private Victor driveLeftRear;
	private Victor driveRightForward;
	private Victor driveRightRear;
	private Victor climberFront;
	private Victor intake;
	private Victor indexSystem;
	
	private Solenoid driveShifter;
	private Solenoid gearFlap;
	private Solenoid gearRelease;
	private Solenoid outFlap;
	private Solenoid intakeSolenoid;
	
	//private final double TURN_WEIGHT_FACTOR = 1.5d; This is the constant for the drive without the Math.exp
	private final double TURN_WEIGHT_FACTOR = 0.4d;
	
	private static  RobotOutput instance;
	
	private RobotOutput()
	{
		driveLeftForward = new Victor(0);
		driveLeftRear = new Victor(1);
		driveRightForward = new Victor(2);
		driveRightRear = new Victor(3);
		climberFront = new Victor(4);
		intake = new Victor(5);
		indexSystem = new Victor(6);
		
		driveShifter = new Solenoid(1);
		gearFlap = new Solenoid(3);
		gearRelease = new Solenoid(2);
		outFlap = new Solenoid(4);
		intakeSolenoid = new Solenoid(5);
		
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
		
		SmartDashboard.putNumber("Left Voltage", left);
		SmartDashboard.putNumber("Right Voltage", right);
	}
	
	public void arcadeDrive(double speed, double turn)
	{
		//double left = (speed) + (TURN_WEIGHT_FACTOR * turn);
		//double right = (speed) + (TURN_WEIGHT_FACTOR * -turn);
		
		double left;
		double right;
		
		if (turn > 0)
		{
			left = (speed) + ((Math.exp(TURN_WEIGHT_FACTOR * turn) * turn));
			right = (speed) + ((Math.exp(TURN_WEIGHT_FACTOR * turn) * -turn));
		}
		else if (turn < 0)
		{
			left = (speed) + ((Math.exp(TURN_WEIGHT_FACTOR * -turn) * turn));
			right = (speed) + ((Math.exp(TURN_WEIGHT_FACTOR * -turn) * -turn));
		}
		else
		{
			left = speed;
			right = speed;
		}
		
		tankDrive(left, right);
	}
	
	public void arcadeDrivePID(double speed, double turn)
	{
		double left = (speed) + turn;
		double right = (speed) - turn;
		
		tankDrive(left, right);
	}
	
	public void intake(double speed)
	{
		intake.set(-speed);
		indexSystem.set(-speed);
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
		gearFlap.set(false);
		gearRelease.set(false);
		driveShifter.set(false);
		outFlap.set(false);
	}
}
