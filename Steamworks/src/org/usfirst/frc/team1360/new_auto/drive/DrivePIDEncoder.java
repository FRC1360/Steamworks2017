package org.usfirst.frc.team1360.new_auto.drive;

import org.usfirst.frc.team1360.new_auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

public class DrivePIDEncoder extends AutonRoutine {
	private double target;
	private double speed;
	private int encoderLimit;
	private OrbitPID pid = new OrbitPID(0.1, 0.00005, 0.01, 0.5);
	
	public DrivePIDEncoder(long timeout, double target, double speed, int encoderLimit) {
		super(null, timeout);
		this.target = target;
		this.speed = speed;
		this.encoderLimit = encoderLimit;
	}

	@Override
	protected void runCore() throws InterruptedException {
		pid.SetSetpoint(target);
		encoderLimit += sensorInput.getRightDriveEncoder();
		robotOutput.arcadeDrivePID(speed, 0);
		Thread.sleep(200);
		while (encoderLimit > 0 ? (sensorInput.getRightDriveEncoder() < encoderLimit) : (sensorInput.getRightDriveEncoder() > encoderLimit))
		{
			pid.SetInput(sensorInput.getAHRSYaw());
			pid.CalculateError();
			robotOutput.arcadeDrivePID(speed, pid.GetOutput());
			Thread.sleep(1);
		}
		robotOutput.tankDrive(0, 0);
	}
	
	@Override
	protected void overrideCore()
	{
		robotOutput.tankDrive(0, 0);
	}
}
