package org.usfirst.frc.team1360.auto.step1;

import org.usfirst.frc.team1360.auto.drive.AutonDrivePIDEncoder;
import org.usfirst.frc.team1360.auto.drive.AutonDrivePIDTurn;
import org.usfirst.frc.team1360.auto.drive.AutonDriveShift;
import org.usfirst.frc.team1360.auto.drive.DriveWait;
import org.usfirst.frc.team1360.auto.goal.AutonLowGoal;
import org.usfirst.frc.team1360.auto.mode.AutonBuilder;
import org.usfirst.frc.team1360.auto.mode.AutonMode;

public class DriveLikeYouStoleItAndHitTheHopper implements AutonMode{

	@Override
	public void addToMode(AutonBuilder ab)
	{
		ab.addCommand(new AutonDriveShift(true));
		ab.addCommand(new AutonLowGoal(-1, true, 100000));
		ab.addCommand(new AutonDrivePIDEncoder(0, -1, -7000, 10000));
		//ab.addCommand(new DriveWait());
		//ab.addCommand(new AutonDriveShift(false));
		ab.addCommand(new AutonDrivePIDTurn(40, 1000));
		//ab.addCommand(new AutonDrivePIDTurn(0, 1000));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonDrivePIDEncoder(40, 0.5, 2000, 100000));
		ab.addCommand(new DriveWait());
	}

}
