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
		/*ab.addCommand(new AutonDrivePIDTime(0, 0.5, 2300));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonDrivePIDTime(0, 0.25, 1600));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonWait(2000));
		ab.addCommand(new AutonGear(false, true));
		ab.addCommand(new AutonWait(1000));
		ab.addCommand(new AutonDrivePIDTime(0, -0.5, 1500));
		ab.addCommand(new DriveWait());*/
		
		/*ab.addCommand(new AutonDrivePIDEncoder(0, 0.75, 1600, 4000)); //4000
		ab.addCommand(new AutonDrivePIDEncoder(0, 0.25, 900, 3000)); //4000
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonGear(false, true));
		ab.addCommand(new AutonWait(500));
		ab.addCommand(new AutonDrivePIDEncoder(0, -0.5, -1600, 4000));
		ab.addCommand(new DriveWait());*/
	}

}
