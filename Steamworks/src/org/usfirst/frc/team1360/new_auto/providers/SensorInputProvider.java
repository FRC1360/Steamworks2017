package org.usfirst.frc.team1360.new_auto.providers;

public interface SensorInputProvider {
	double getAHRSYaw();
	double getAHRSPitch();
	double getAHRSRoll();
	double getAHRSWorldLinearAccelX();
	double getAHRSWorldLinearAccelY();
	double getAHRSVelocityX();
	double getAHRSVelocityY();
	void resetAHRS();
	double getClimberFrontCurrent();
	double getClimberBackCurrent();
	int getLeftDriveEncoder();
	int getRightDriveEncoder();
	int getPivotEncoder();
	double getLeftEncoderVelocity();
	double getRightEncoderVelocity();
	void resetLeftEncoder();
	void resetRightEncoder();
	void resetPivotEncoder();
	void calculate();
	void reset();
}
