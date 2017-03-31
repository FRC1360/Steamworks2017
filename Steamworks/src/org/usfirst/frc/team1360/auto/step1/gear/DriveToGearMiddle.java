package org.usfirst.frc.team1360.auto.step1.gear;

import org.usfirst.frc.team1360.auto.drive.AutonDrive;
import org.usfirst.frc.team1360.auto.drive.AutonDrivePIDEncoder;
import org.usfirst.frc.team1360.auto.drive.AutonDrivePIDTime;
import org.usfirst.frc.team1360.auto.drive.DriveWait;
import org.usfirst.frc.team1360.auto.gear.AutonGear;
import org.usfirst.frc.team1360.auto.mode.AutonBuilder;
import org.usfirst.frc.team1360.auto.mode.AutonMode;
import org.usfirst.frc.team1360.auto.util.AutonWait;

public class DriveToGearMiddle implements AutonMode
{

	@Override
	public void addToMode(AutonBuilder ab)
	{		
		ab.addCommand(new AutonGear(true, false, 1));
		ab.addCommand(new AutonDrivePIDEncoder(0, 0.75, 1000, 4000));
		ab.addCommand(new AutonDrivePIDEncoder(0, 0.25, 750, 4000));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonWait(500));
		ab.addCommand(new AutonGear(true, false, -1));
		ab.addCommand(new AutonWait(500));
		ab.addCommand(new AutonDrivePIDEncoder(0, -0.5, -600, 3000));
		ab.addCommand(new AutonGear(false, false, 0));
		ab.addCommand(new DriveWait());
	}

}
