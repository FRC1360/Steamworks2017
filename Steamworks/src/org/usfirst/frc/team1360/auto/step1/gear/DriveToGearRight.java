package org.usfirst.frc.team1360.auto.step1.gear;

import org.usfirst.frc.team1360.auto.drive.AutonDrivePID;
import org.usfirst.frc.team1360.auto.drive.AutonDrivePIDTime;
import org.usfirst.frc.team1360.auto.drive.AutonDrivePIDTurn;
import org.usfirst.frc.team1360.auto.drive.DriveWait;
import org.usfirst.frc.team1360.auto.gear.AutonGear;
import org.usfirst.frc.team1360.auto.mode.AutonBuilder;
import org.usfirst.frc.team1360.auto.mode.AutonMode;
import org.usfirst.frc.team1360.auto.util.AutonWait;

public class DriveToGearRight implements AutonMode
{

	@Override
	public void addToMode(AutonBuilder ab)
	{
		ab.addCommand(new AutonDrivePIDTime(0, 0.5, 2025));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonWait(500));
		ab.addCommand(new AutonDrivePIDTime(-53, 0.5, 2800));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonWait(500));
		ab.addCommand(new AutonDrivePIDTime(0, 0.25, 750));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonGear(true, true));
		ab.addCommand(new AutonWait(1500));
		ab.addCommand(new AutonDrivePIDTime(0, -0.5, 2000));
		ab.addCommand(new DriveWait());
		//ab.addCommand(new AutonDrivePIDTime(0, 0.5, 2000));
	}

}
