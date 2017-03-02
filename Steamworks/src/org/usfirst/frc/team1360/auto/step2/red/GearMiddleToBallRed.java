package org.usfirst.frc.team1360.auto.step2.red;

import org.usfirst.frc.team1360.auto.drive.DriveWait;
import org.usfirst.frc.team1360.auto.goal.AutonLowGoal;
import org.usfirst.frc.team1360.auto.goal.IntakeWait;
import org.usfirst.frc.team1360.auto.mode.AutonBuilder;
import org.usfirst.frc.team1360.auto.mode.AutonMode;

public class GearMiddleToBallRed implements AutonMode
{
	@Override
	public void addToMode(AutonBuilder ab)
	{
		/*ab.addCommand(new AutonDrivePID(-80, -0.5, 3000));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonLowGoal(1, 1000));
		ab.addCommand(new IntakeWait());*/
	}

}
