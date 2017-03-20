package org.usfirst.frc.team1360.auto.drive;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.RobotSubsystems;
import org.usfirst.frc.team1360.auto.tracking.PositionTracker;
import org.usfirst.frc.team1360.auto.tracking.Vector;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;

public class GoToPosition extends AutonCommand {
	Vector target;
	double throttle;
	double k;
	double range;
	PositionTracker pt;
	RobotOutput robotOutput;
	SensorInput sensorInput;

	public GoToPosition(Vector target, double throttle, double range)
	{
		super(RobotSubsystems.DRIVE);
		k = 0.1;
		robotOutput = RobotOutput.getInstance();
		sensorInput = SensorInput.getInstance();
		this.range = range;
		this.target = target;
		this.throttle = throttle;
		pt = PositionTracker.getInstance();
	}

	@Override
	public boolean calculate()
	{
		Vector relTarget = target.copy();
		relTarget.addFrom(pt.getPosition(), -1);
		double targetLen = relTarget.getMagnitude();
		if (targetLen <= range)
			return true;
		
		double targetYaw = relTarget.getPhase();
		double rotation = throttle * k * (sensorInput.getAHRSYaw() - Math.toDegrees(targetYaw));
		
		robotOutput.arcadeDrive(throttle, rotation);
		
		return false;
	}

	@Override
	public void override() {
		robotOutput.tankDrive(0, 0);
	}

}
