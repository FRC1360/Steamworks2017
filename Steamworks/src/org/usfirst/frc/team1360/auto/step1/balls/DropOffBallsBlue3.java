package org.usfirst.frc.team1360.auto.step1.balls;

import org.usfirst.frc.team1360.auto.drive.AutonDrive;
import org.usfirst.frc.team1360.auto.goal.AutonLowGoal;
import org.usfirst.frc.team1360.auto.goal.IntakeWait;
import org.usfirst.frc.team1360.auto.mode.AutonBuilder;
import org.usfirst.frc.team1360.auto.mode.AutonMode;

public class DropOffBallsBlue3 implements AutonMode {

	@Override
	public void addToMode(AutonBuilder ab) {
		ab.addCommand(new AutonDrive(1500, -1, 1));
		ab.addCommand(new AutonDrive(5000, 1, 1));
		ab.addCommand(new AutonDrive(1500, -1, 1));
		ab.addCommand(new AutonDrive(1500, 1, 1));
		ab.addCommand(new AutonLowGoal(1500, 1));
		ab.addCommand(new IntakeWait());
	}

}
