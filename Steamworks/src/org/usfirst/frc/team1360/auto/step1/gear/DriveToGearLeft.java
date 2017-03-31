package org.usfirst.frc.team1360.auto.step1.gear;

import org.usfirst.frc.team1360.auto.drive.AutonDrivePIDEncoder;
import org.usfirst.frc.team1360.auto.drive.AutonDrivePIDTime;
import org.usfirst.frc.team1360.auto.drive.AutonDrivePIDTurn;
import org.usfirst.frc.team1360.auto.drive.DriveWait;
import org.usfirst.frc.team1360.auto.gear.AutonGear;
import org.usfirst.frc.team1360.auto.mode.AutonBuilder;
import org.usfirst.frc.team1360.auto.mode.AutonMode;
import org.usfirst.frc.team1360.auto.util.AutonWait;

public class DriveToGearLeft implements AutonMode {

	@Override
	public void addToMode(AutonBuilder ab) {
		// TODO Auto-generated method stub
		/*
		ab.addCommand(new AutonDrivePIDTime(0, 0.5, 1900));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonWait(500));
		ab.addCommand(new AutonDrivePIDTime(55, 0.5, 2400));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonWait(500));
		ab.addCommand(new AutonDrivePIDTime(0, 0.25, 1000));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonGear(true, true));
		ab.addCommand(new AutonWait(1500));
		ab.addCommand(new AutonDrivePIDTime(0, -0.5, 2000));
		ab.addCommand(new DriveWait());	*/
		
		// Copy-pasted from DriveToGearRight
		
		//Try these time values if those dont work: 2025, 2800, 750, 2000
		/*//ab.addCommand(new AutonDrivePIDTime(0, 0.5, 2375)); // 1776
		ab.addCommand(new AutonDrivePIDEncoder(0, 0.5, 1750, 6000));
		//ab.addCommand(new DriveWait());
		//ab.addCommand(new AutonWait(500));
		//ab.addCommand(new AutonDrivePIDTime(-53, 0.5, 3200)); // 1177
		ab.addCommand(new AutonDrivePIDTurn(52, 1500));
		ab.addCommand(new AutonDrivePIDEncoder(52, 0.5, 1860, 6000));
		ab.addCommand(new DriveWait());
		//ab.addCommand(new AutonWait(500));
		//ab.addCommand(new AutonDrivePIDTime(0, 0.25, 1000)); // 310
		ab.addCommand(new AutonDrivePIDEncoder(52, 0.25, 450, 5000));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonGear(true, true));
		ab.addCommand(new AutonWait(1500));
		//ab.addCommand(new AutonDrivePIDTime(0, -0.5, 2000)); // -1508
		ab.addCommand(new AutonDrivePIDEncoder(52, -0.5, -1508, 4000));
		ab.addCommand(new DriveWait());
		//ab.addCommand(new AutonDrivePIDTime(0, 0.5, 2000));*/
		
		ab.addCommand(new AutonGear(true, false, 1));
		ab.addCommand(new AutonDrivePIDEncoder(0, 0.75, 1700, 6000));
		//ab.addCommand(new AutonDrivePIDTurn(-45, 1500));
		ab.addCommand(new AutonDrivePIDEncoder(50, 0.25, 1100, 4000));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonWait(500));
		ab.addCommand(new AutonGear(true, false, -1));
		ab.addCommand(new AutonWait(500));
		ab.addCommand(new AutonDrivePIDEncoder(52, -0.5, -600, 4000));
		ab.addCommand(new AutonGear(false, false, 0));
		ab.addCommand(new DriveWait());
	}

}
