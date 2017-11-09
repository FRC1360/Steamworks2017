package org.usfirst.frc.team1360.new_auto.providers;

public interface RobotOutputProvider {
	void setDriveLeft(double speed);
	void setDriveRight(double speed);
	void tankDrive(double left, double right);
	void arcadeDrive(double speed, double turn);
	void arcadeDrivePID(double speed, double turn);
	void pivotGear(double speed);
	void fineAdjustGearMech(boolean pivot);
	void intakeGear(double speed);
	void shiftSpeed(boolean shift);
	void outtake(boolean release);
	void climb(double speed);
	void stopAll();
}
