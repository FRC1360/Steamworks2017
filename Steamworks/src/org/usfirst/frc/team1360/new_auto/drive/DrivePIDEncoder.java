package org.usfirst.frc.team1360.new_auto.drive;

import org.usfirst.frc.team1360.new_auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

public class DrivePIDEncoder extends AutonRoutine {
	private double target;
	private double speed;
	private int encoderLimit;
	private long timeout;
	private OrbitPID pid = new OrbitPID(0.1, 0.00005, 0.01, 0.5);
	
	public DrivePIDEncoder(double target, double speed, int encoderLimit, long timeout) {
		super(null);
		this.target = target;
		this.speed = speed;
		this.encoderLimit = encoderLimit;
		this.timeout = timeout;
	}

	@Override
	public void runCore() throws InterruptedException {
		pid.SetSetpoint(target);
		encoderLimit += sensorInput.getRightDriveEncoder();
		robotOutput.tankDrive(speed, speed);
		long start = System.currentTimeMillis();
		Thread.sleep(200);
		while (target > 0 ? (sensorInput.getRightDriveEncoder() > encoderLimit) : (sensorInput.getRightDriveEncoder() < encoderLimit))
		{
			pid.SetInput(sensorInput.getAHRSYaw());
			pid.CalculateError();
			robotOutput.arcadeDrivePID(speed, pid.GetOutput());
			if (timeout != 0 && System.currentTimeMillis() > start + timeout)
			{
				break;
			}
		}
		robotOutput.tankDrive(0, 0);
	}
}
