package org.usfirst.frc.team1360.auto.step1;

import org.usfirst.frc.team1360.auto.drive.AutonDrive;
import org.usfirst.frc.team1360.auto.drive.DriveWait;
import org.usfirst.frc.team1360.auto.mode.AutonBuilder;
import org.usfirst.frc.team1360.auto.mode.AutonMode;
import org.usfirst.frc.team1360.auto.util.AutonWait;

public class DriveToBaselineOutside implements AutonMode  {

	@Override
	public void addToMode(AutonBuilder ab) {
		// TODO Auto-generated method stub
		ab.addCommand(new AutonDrive(3000,0.75, 0.75));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonWait(12000));
	}

}
