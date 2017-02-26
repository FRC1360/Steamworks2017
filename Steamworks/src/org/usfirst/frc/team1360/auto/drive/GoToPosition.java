package org.usfirst.frc.team1360.auto.drive;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.RobotSubsystems;
import org.usfirst.frc.team1360.navx.PositionTracker;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public class GoToPosition extends AutonCommand {
	double[] target;
	double throttle;
	PositionTracker pt;
	double k;
	double range;
	RobotOutput robotOutput;

	public GoToPosition(double[] target, double throttle, double range)
	{
		super(RobotSubsystems.DRIVE);
		k = 0.1;
		robotOutput = RobotOutput.getInstance();
		this.range = range;
		this.target = target;
		this.throttle = throttle;
		pt = PositionTracker.getInstance();
	}

	@Override
	public boolean calculate()
	{
		double xLen = target[0] - pt.getPosition()[0];
		double yLen = target[1] - pt.getPosition()[1];
		double targetLen = Math.sqrt((double)(xLen * xLen + yLen * yLen));
		if (targetLen <= range)
			return true;
		
		double targetYaw = Math.atan2(yLen, xLen);
		double rotation = throttle * k * (pt.getCurrentYaw() - targetYaw);
		
		robotOutput.arcadeDrive(throttle, rotation);
		
		return false;
	}

	@Override
	public void override() {
		// TODO Auto-generated method stub
		
	}

}
