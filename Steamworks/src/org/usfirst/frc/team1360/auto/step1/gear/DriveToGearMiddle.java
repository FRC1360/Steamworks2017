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
<<<<<<< HEAD
	{
		/*ab.addCommand(new AutonDrivePIDTime(0, 0.5, 2300));
=======
	{		
		ab.addCommand(new AutonGear(true, false, 1));
		ab.addCommand(new AutonDrivePIDEncoder(0, 0.75, 850, 4000)); //1400
		ab.addCommand(new AutonDrivePIDEncoder(0, 0.25, 700, 2000)); //750
>>>>>>> Auto
		ab.addCommand(new DriveWait());
		//ab.addCommand(new AutonWait(500));
		ab.addCommand(new AutonGear(true, false, -1));
		ab.addCommand(new AutonWait(500));
		ab.addCommand(new AutonDrivePIDEncoder(0, -0.5, -200, 4000)); //this is new
		ab.addCommand(new AutonGear(false, false, 0)); //new
		ab.addCommand(new DriveWait()); //this is new
		ab.addCommand(new AutonGear(true, false, 0)); //this is new
		ab.addCommand(new AutonDrivePIDEncoder(0, 0.5, 200, 4000)); //this is new
		ab.addCommand(new DriveWait()); //this is new
		ab.addCommand(new AutonDrivePIDEncoder(0, -0.5, -600, 3000));
		ab.addCommand(new AutonGear(false, false, 0));
		ab.addCommand(new DriveWait());
<<<<<<< HEAD
		ab.addCommand(new AutonWait(2000));
		ab.addCommand(new AutonGear(false, true));
		ab.addCommand(new AutonWait(1000));
		ab.addCommand(new AutonDrivePIDTime(0, -0.5, 1500));
		ab.addCommand(new DriveWait());*/
		
		ab.addCommand(new AutonDrivePIDEncoder(0, 0.75, 1600, 4000)); //4000
		ab.addCommand(new AutonDrivePIDEncoder(0, 0.25, 900, 3000)); //4000
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonGear(false, true));
		ab.addCommand(new AutonWait(500));
		ab.addCommand(new AutonDrivePIDEncoder(0, -0.5, -1600, 4000));
		ab.addCommand(new DriveWait());
=======
>>>>>>> Auto
	}

}
