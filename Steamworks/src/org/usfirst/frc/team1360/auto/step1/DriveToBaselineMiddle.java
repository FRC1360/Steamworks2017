package org.usfirst.frc.team1360.auto.step1;

import org.usfirst.frc.team1360.auto.drive.*;
import org.usfirst.frc.team1360.auto.mode.AutonBuilder;
import org.usfirst.frc.team1360.auto.mode.AutonMode;
import org.usfirst.frc.team1360.auto.util.AutonWait;

public class DriveToBaselineMiddle implements AutonMode {
	@Override
	public void addToMode(AutonBuilder ab) {
		ab.addCommand(new AutonDrive(1500, 0, 0)); //Waits for other robots
		ab.addCommand(new AutonDrive(500, 0, 1)); //Turns right in 500ms?
		ab.addCommand(new AutonDrive (1000,1,1)); //Goes forward for a second
		ab.addCommand(new AutonDrive(500,1,0)); //Goes left in 500ms?
		ab.addCommand(new AutonDrive(3000,1,1)); //Go forward to cross baseline in 3s?
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonWait(12000));
	}	
}
