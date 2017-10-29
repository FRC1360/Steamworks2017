package org.usfirst.frc.team1360.new_auto.routines;

import org.usfirst.frc.team1360.new_auto.AutonRoutine;

public class DriveToBaseline extends AutonRoutine {
	public DriveToBaseline() {
		super("Drive to baseline");
	}
	
	@Override
	public void runCore() throws InterruptedException
	{
		robotOutput.tankDrive(1.0, 1.0);
		Thread.sleep(1000);
		robotOutput.stopAll();
	}
}
