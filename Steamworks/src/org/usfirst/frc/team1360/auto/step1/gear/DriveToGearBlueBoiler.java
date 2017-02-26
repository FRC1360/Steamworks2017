package org.usfirst.frc.team1360.auto.step1.gear;

import org.usfirst.frc.team1360.auto.drive.AutonDrivePID;
import org.usfirst.frc.team1360.auto.drive.AutonDrivePIDTurn;
import org.usfirst.frc.team1360.auto.drive.DriveWait;
import org.usfirst.frc.team1360.auto.gear.AutonGear;
import org.usfirst.frc.team1360.auto.mode.AutonBuilder;
import org.usfirst.frc.team1360.auto.mode.AutonMode;
import org.usfirst.frc.team1360.auto.util.AutonWait;

public class DriveToGearBlueBoiler implements AutonMode
{

	@Override
	public void addToMode(AutonBuilder ab)
	{
		ab.addCommand(new AutonDrivePID(0, 0.75, 100, 4000));
		ab.addCommand(new AutonDrivePIDTurn(60, 4000));
		ab.addCommand(new AutonDrivePID(0, 0.75, 100, 4000));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonGear(true, true));
		ab.addCommand(new AutonWait(1500));
		ab.addCommand(new AutonDrivePID(0, -0.75, -100, 4000));
	}

}