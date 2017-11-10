package org.usfirst.frc.team1360.auto.step1;

import org.usfirst.frc.team1360.auto.drive.AutonDrivePIDEncoder;
import org.usfirst.frc.team1360.auto.drive.AutonDrivePIDTime;
import org.usfirst.frc.team1360.auto.drive.DriveWait;
import org.usfirst.frc.team1360.auto.gear.AutonGear;
import org.usfirst.frc.team1360.auto.mode.AutonBuilder;
import org.usfirst.frc.team1360.auto.mode.AutonMode;
import org.usfirst.frc.team1360.auto.util.AutonWait;

public class DrivePIDTest implements AutonMode {

	@Override
	public void addToMode(AutonBuilder ab) {
		/*ab.addCommand(new AutonDrivePIDTime(0, 0.25, 2000));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonWait(2000));
		ab.addCommand(new AutonGear(true, true));
		ab.addCommand(new AutonWait(10000));*/
		
		ab.addCommand(new AutonDrivePIDEncoder(0, 0.5, 1766, 8000));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonWait(2000));
		ab.addCommand(new AutonDrivePIDEncoder(0, -0.5, -1766, 8000));
		ab.addCommand(new DriveWait());
	}

}
