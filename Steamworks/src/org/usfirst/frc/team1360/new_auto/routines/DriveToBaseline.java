package org.usfirst.frc.team1360.new_auto.routines;

import org.usfirst.frc.team1360.new_auto.AutonRoutine;
import org.usfirst.frc.team1360.new_auto.drive.DrivePIDEncoder;

public class DriveToBaseline extends AutonRoutine {
	public DriveToBaseline() {
		super("Drive to baseline");
	}
	
	@Override
	public void runCore() throws InterruptedException
	{
		new DrivePIDEncoder(0.0, 1.0, 800, 4000).runUntilFinish();
	}
}
