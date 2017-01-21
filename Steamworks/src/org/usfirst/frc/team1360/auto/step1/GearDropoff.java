package org.usfirst.frc.team1360.auto.step1;

import org.usfirst.frc.team1360.auto.drive.AutonDrive;
import org.usfirst.frc.team1360.auto.drive.DriveWait;
import org.usfirst.frc.team1360.auto.mode.AutonBuilder;
import org.usfirst.frc.team1360.auto.mode.AutonMode;

public class GearDropoff implements AutonMode {
	
	@Override
	public void addToMode(AutonBuilder ab) {
		ab.addCommand(new AutonDrive(5000, 1, 1));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonDrive(500, -1, 1));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonDrive(1500, 1, 1));
	}

}
