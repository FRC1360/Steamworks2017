package org.usfirst.frc.team1360.new_auto.routines;

import org.usfirst.frc.team1360.new_auto.AutonRoutine;
import org.usfirst.frc.team1360.new_auto.drive.DrivePIDEncoder;

public class DriveToBaseline extends AutonRoutine {
	public DriveToBaseline() {
		super("Drive to baseline", 0);
	}
	
	@Override
	public void runCore() throws InterruptedException
	{
		new DrivePIDEncoder(4000, 0.0, 1.0, 800).runUntilFinish();
	}
}
