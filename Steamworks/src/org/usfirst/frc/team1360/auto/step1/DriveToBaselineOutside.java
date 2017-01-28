// Goal - Reach the baseline when starting on the outside.
// Last edited by Cathal on Jan 19th 2017
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
		ab.addCommand(new AutonDrive(3000, 0.25, 0.25));
		ab.addCommand(new DriveWait());
	}

}
