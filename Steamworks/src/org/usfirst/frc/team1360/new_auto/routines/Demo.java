package org.usfirst.frc.team1360.new_auto.routines;

import org.usfirst.frc.team1360.new_auto.AutonRoutine;
import org.usfirst.frc.team1360.new_auto.drive.DrivePIDEncoder;

public final class Demo extends AutonRoutine {
	public Demo() {
		super("Demo autonomous routine", 0);
	}

	@Override
	protected void runCore() throws InterruptedException
	{
		new DrivePIDEncoder(10000, 0.0, 1.0, 2000).runNow("first");
		new DrivePIDEncoder(10000, 90.0, 1.0, 2000).runAfter("first", "second");
		waitFor("second", 0);
	}
}
