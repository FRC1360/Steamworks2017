package org.usfirst.frc.team1360.auto.step2;

import org.usfirst.frc.team1360.auto.drive.AutonDrivePIDEncoder;
import org.usfirst.frc.team1360.auto.drive.AutonDrivePIDTime;
import org.usfirst.frc.team1360.auto.drive.AutonDrivePIDTurn;
import org.usfirst.frc.team1360.auto.drive.AutonDriveShift;
import org.usfirst.frc.team1360.auto.drive.DriveWait;
import org.usfirst.frc.team1360.auto.gear.AutonGear;
import org.usfirst.frc.team1360.auto.mode.AutonBuilder;
import org.usfirst.frc.team1360.auto.mode.AutonMode;
import org.usfirst.frc.team1360.auto.util.AutonWait;

public class NeutralZoneAfterSideGear implements AutonMode
{

	@Override
	public void addToMode(AutonBuilder ab)
	{
		// Go to middle of field
		ab.addCommand(new AutonDriveShift(true));
		ab.addCommand(new AutonDrivePIDEncoder(0, 1, 2000, 4000));
        ab.addCommand(new AutonDriveShift(false));
        ab.addCommand(new DriveWait());
	}

}