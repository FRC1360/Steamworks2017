package org.usfirst.frc.team1360.auto.step2;

import org.usfirst.frc.team1360.auto.drive.AutonDrivePIDEncoder;
import org.usfirst.frc.team1360.auto.drive.DriveWait;
import org.usfirst.frc.team1360.auto.goal.AutonLowGoal;
import org.usfirst.frc.team1360.auto.mode.AutonBuilder;
import org.usfirst.frc.team1360.auto.mode.AutonMode;
import org.usfirst.frc.team1360.auto.util.AutonWait;

public class ToBoilerFromRed implements AutonMode {

	@Override
	public void addToMode(AutonBuilder ab)
	{
		ab.addCommand(new AutonDrivePIDEncoder(-25, -0.75, -1100, 5000));
		ab.addCommand(new AutonDrivePIDEncoder(-30, -0.5, -550, 3000));
		ab.addCommand(new AutonLowGoal(0, true));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonLowGoal(-1, true));
		ab.addCommand(new AutonWait(3000));
	}

}
