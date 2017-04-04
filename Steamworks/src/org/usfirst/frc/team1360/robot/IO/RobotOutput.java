package org.usfirst.frc.team1360.robot.IO;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotOutput {
<<<<<<< HEAD
	
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
=======
	// Victors
	private Victor vicDriveLeftForward;
	private Victor vicDriveLeftRear;
	private Victor vicDriveRightForward;
	private Victor vicDriveRightRear;
	private Victor vicClimberFront;
	private Victor vicIntake;
	private Victor vicIndexSystem;
	private Victor vicGearMech;
	
	private Solenoid solDriveShifter;
	//private Solenoid solGearFlap;
	private Solenoid solPivotPiston;
	private Solenoid solFineAdjustment;
	//private Solenoid solGearRelease;
	private Solenoid solOutFlap;
	private Solenoid solIntake;
>>>>>>> Auto
	
	//private final double TURN_WEIGHT_FACTOR = 1.5d; This is the constant for the drive without the Math.exp
	private final double TURN_WEIGHT_FACTOR = 0.2; //0.1
	
	private static  RobotOutput instance;
	
	private RobotOutput()
	{
<<<<<<< HEAD
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
=======
		vicDriveLeftForward = new Victor(0);
		vicDriveLeftRear = new Victor(1);
		vicDriveRightForward = new Victor(2);
		vicDriveRightRear = new Victor(3);
		vicClimberFront = new Victor(4);
		vicIntake = new Victor(5);
		vicIndexSystem = new Victor(7);
		vicGearMech = new Victor(6);
		
		solDriveShifter = new Solenoid(1);
		//solGearFlap = new Solenoid(3);
		//solGearRelease = new Solenoid(2);
		solFineAdjustment = new Solenoid(3);
		solPivotPiston = new Solenoid(2);
		solOutFlap = new Solenoid(4);
		//solIntake = new Solenoid(5);
>>>>>>> Auto
		
	}
	
	public static RobotOutput getInstance() // Return instance of RobotOutpu; create if it doesn't exist
	{
		if (instance == null)
		{
			instance = new RobotOutput();
		}
		
		return instance;
	}

	public void setDriveLeft(double speed)
	{
		vicDriveLeftForward.set(-speed);
		vicDriveLeftRear.set(-speed);
		
		SmartDashboard.putNumber("Left Voltage", -speed);
	}
	
	public void setDriveRight(double speed)
	{
		vicDriveRightForward.set(speed);
		vicDriveRightRear.set(speed);
		
		SmartDashboard.putNumber("Right Voltage", speed);
	}
	
	public void tankDrive(double left, double right) // Basic tank drive helper
	{
		//setDriveLeft(left);
		//setDriveLeft(right);
		vicDriveLeftForward.set(-left);
		vicDriveLeftRear.set(-left);
		vicDriveRightForward.set(right);
		vicDriveRightRear.set(right);
	}
	
	public void arcadeDrive(double speed, double turn) // Arcade drive algorithm that filters turn
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
		
		setDriveLeft(left);
		setDriveRight(right);
	}
	
	public void arcadeDrivePID(double speed, double turn) // Non-filtering arcade drive algorithm for use in PID-based autos
	{
		double left = (speed) + turn;
		double right = (speed) - turn;
		
		tankDrive(left, right);
	}
	
	public void intake(double speed) // Sets intake/index motors
	{
		vicIntake.set(-speed);
		vicIndexSystem.set(-speed);
	}
	
	/*public void openItake(boolean shift) // Sets intake position
	{
		solIntake.set(shift);
	}*/
	
	/*public void releaseGear(boolean release) // Sets gear release position
	{
		solGearRelease.set(release);
	}*/
	
	public void pivotGearMech(boolean pivot)
	{
		solPivotPiston.set(pivot);
	}
	
	/*public void flapGear(boolean release) // Sets gear flap position
	{
		solGearFlap.set(release);
	}*/
	
	public void fineAdjustGearMech(boolean pivot)
	{
		solFineAdjustment.set(pivot);
	}
	
	public void intakeGear(double speed)
	{
		vicGearMech.set(speed);
	}
	
	public void shiftSpeed(boolean shift) // Sets drive shifter position
	{
		solDriveShifter.set(shift);
	}
	
	public void outtake(boolean release) // Sets outtake flap position
	{
<<<<<<< HEAD
		climberFront.set(speed);
=======
		solOutFlap.set(release);
>>>>>>> Auto
	}
	
	public void climb(double speed) // Runs climber at given speed
	{
		vicClimberFront.set(speed);
	}

<<<<<<< HEAD
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
=======
	public void stopAll() // Stops all motors and resets all solenoids
	{
		vicDriveLeftForward.set(0);
		vicDriveLeftRear.set(0);
		vicDriveRightForward.set(0);
		vicDriveRightRear.set(0);
		vicIntake.set(0);
		vicIndexSystem.set(0);
		vicClimberFront.set(0);
		//solGearFlap.set(false);
		//solGearRelease.set(false);
		solPivotPiston.set(false);
		solFineAdjustment.set(false);
		solDriveShifter.set(false);
		solOutFlap.set(false);
>>>>>>> Auto
	}
}
