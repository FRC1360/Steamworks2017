package org.usfirst.frc.team1360.auto.drive;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.RobotSubsystems;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

public class AutonDrivePIDBrake extends AutonCommand {
	private OrbitPID leftPID = new OrbitPID(0.2, 0.001, 0.01, 0.05);
	private OrbitPID rightPID = new OrbitPID(0.2, 0.001, 0.01, 0.05);
	private SensorInput sensorInput = SensorInput.getInstance();
	private RobotOutput robotOutput = RobotOutput.getInstance();

	public AutonDrivePIDBrake(RobotSubsystems type, long timeoutLength) {
		super(type, timeoutLength);
		leftPID.SetSetpoint(0);
		rightPID.SetSetpoint(0);
	}

	@Override
	public boolean calculate() {
		leftPID.SetInput(sensorInput.getLeftEncoderVelocity());
		leftPID.CalculateError();
		rightPID.SetInput(sensorInput.getRightEncoderVelocity());
		rightPID.CalculateError();
		robotOutput.tankDrive(leftPID.GetOutput(), rightPID.GetOutput());
		return false;
	}

	@Override
	public void override() {
		// TODO Auto-generated method stub

	}

}
