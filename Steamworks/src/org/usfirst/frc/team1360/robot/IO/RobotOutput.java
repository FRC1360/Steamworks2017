package org.usfirst.frc.team1360.robot.IO;

import org.usfirst.frc.team1360.new_auto.providers.RobotOutputProvider;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotOutput implements RobotOutputProvider {
	// Victors
	private Victor vicDriveLeftForward;
	private Victor vicDriveLeftRear;
	private Victor vicDriveRightForward;
	private Victor vicDriveRightRear;
	private Victor vicClimberFront;
	private Victor vicPivot;
	private Victor vicGearMech;
	
	private Solenoid solDriveShifter;
	//private Solenoid solGearFlap;
	private Solenoid solFineAdjustment;
	//private Solenoid solGearRelease;
	private Solenoid solOutFlap;
	private Solenoid solIntake;
	
	//private final double TURN_WEIGHT_FACTOR = 1.5d; This is the constant for the drive without the Math.exp
	private final double TURN_WEIGHT_FACTOR = 0.2; //0.1
	
	private static  RobotOutput instance;
	
	private RobotOutput()
	{
		vicDriveLeftForward = new Victor(0);
		vicDriveLeftRear = new Victor(1);
		vicDriveRightForward = new Victor(2);
		vicDriveRightRear = new Victor(3);
		vicClimberFront = new Victor(4);
		//vicIntake = new Victor(6); //5;
		vicPivot = new Victor(6);
		//vicIndexSystem = new Victor(5);
		vicGearMech = new Victor(7); //7
		
		solDriveShifter = new Solenoid(1);
		//solGearFlap = new Solenoid(3);
		//solGearRelease = new Solenoid(2);
		solFineAdjustment = new Solenoid(3);
		//solPivotPiston = new Solenoid(5);
		solOutFlap = new Solenoid(4);
		//solIntake = new Solenoid(5);
		
	}
	
	public static RobotOutput getInstance() // Return instance of RobotOutpu; create if it doesn't exist
	{
		if (instance == null)
		{
			instance = new RobotOutput();
		}
		
		return instance;
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.RobotOutputProvider#setDriveLeft(double)
	 */
	@Override
	public void setDriveLeft(double speed)
	{
		vicDriveLeftForward.set(-speed);
		vicDriveLeftRear.set(-speed);
		
		SmartDashboard.putNumber("Left Voltage", -speed);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.RobotOutputProvider#setDriveRight(double)
	 */
	@Override
	public void setDriveRight(double speed)
	{
		vicDriveRightForward.set(speed);
		vicDriveRightRear.set(speed);
		
		SmartDashboard.putNumber("Right Voltage", speed);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.RobotOutputProvider#tankDrive(double, double)
	 */
	@Override
	public void tankDrive(double left, double right) // Basic tank drive helper
	{
		//setDriveLeft(left);
		//setDriveLeft(right);
		vicDriveLeftForward.set(-left);
		vicDriveLeftRear.set(-left);
		vicDriveRightForward.set(right);
		vicDriveRightRear.set(right);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.RobotOutputProvider#arcadeDrive(double, double)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.RobotOutputProvider#arcadeDrivePID(double, double)
	 */
	@Override
	public void arcadeDrivePID(double speed, double turn) // Non-filtering arcade drive algorithm for use in PID-based autos
	{
		double left = (speed) + turn;
		double right = (speed) - turn;
		
		tankDrive(left, right);
	}
	
	/*public void openItake(boolean shift) // Sets intake position
	{
		solIntake.set(shift);
	}*/
	
	/*public void releaseGear(boolean release) // Sets gear release position
	{
		solGearRelease.set(release);
	}*/
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.RobotOutputProvider#pivotGear(double)
	 */
	@Override
	public void pivotGear(double speed)
	{
		vicPivot.set(speed);
	}
	
	/*public void flapGear(boolean release) // Sets gear flap position
	{
		solGearFlap.set(release);
	}*/
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.RobotOutputProvider#fineAdjustGearMech(boolean)
	 */
	@Override
	public void fineAdjustGearMech(boolean pivot)
	{
		solFineAdjustment.set(pivot);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.RobotOutputProvider#intakeGear(double)
	 */
	@Override
	public void intakeGear(double speed)
	{
		vicGearMech.set(speed);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.RobotOutputProvider#shiftSpeed(boolean)
	 */
	@Override
	public void shiftSpeed(boolean shift) // Sets drive shifter position
	{
		solDriveShifter.set(shift);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.RobotOutputProvider#outtake(boolean)
	 */
	@Override
	public void outtake(boolean release) // Sets outtake flap position
	{
		solOutFlap.set(release);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.RobotOutputProvider#climb(double)
	 */
	@Override
	public void climb(double speed) // Runs climber at given speed
	{
		vicClimberFront.set(speed);
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.RobotOutputProvider#stopAll()
	 */
	@Override
	public void stopAll() // Stops all motors and resets all solenoids
	{
		vicDriveLeftForward.set(0);
		vicDriveLeftRear.set(0);
		vicDriveRightForward.set(0);
		vicDriveRightRear.set(0);
		vicPivot.set(0);
		vicClimberFront.set(0);
		//solGearFlap.set(false);
		//solGearRelease.set(false);
		solFineAdjustment.set(false);
		solDriveShifter.set(false);
		solOutFlap.set(false);
	}
}
