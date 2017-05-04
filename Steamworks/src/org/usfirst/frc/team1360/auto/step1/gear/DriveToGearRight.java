package org.usfirst.frc.team1360.auto.step1.gear;

import org.usfirst.frc.team1360.auto.drive.AutonDrivePIDEncoder;
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
		ab.addCommand(new AutonGear(true, false, 0));
		ab.addCommand(new AutonDrivePIDEncoder(0, 0.75, 1250, 6000)); //1700, speed = 0.75
		//ab.addCommand(new AutonDrivePIDTurn(-45, 1500));
		ab.addCommand(new AutonDrivePIDEncoder(-52, 0.30, 900, 1500)); //-45, 0.25, 1100, 2500
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonGear(false, false, -1));
		ab.addCommand(new AutonWait(250));
		ab.addCommand(new AutonDrivePIDEncoder(-49, -0.5, -200, 4000)); //this is new
		ab.addCommand(new AutonGear(false, false, 0)); //new
		ab.addCommand(new DriveWait()); //this is new
		ab.addCommand(new AutonGear(true, false, 0)); //this is new
		ab.addCommand(new AutonDrivePIDEncoder(-49, 0.35, 175, 3000)); //this is new
		ab.addCommand(new DriveWait()); //this is new
		ab.addCommand(new AutonDrivePIDEncoder(-52, -0.5, -600, 4000));
		ab.addCommand(new AutonGear(false, false, 0));
		ab.addCommand(new DriveWait());
	}

}
