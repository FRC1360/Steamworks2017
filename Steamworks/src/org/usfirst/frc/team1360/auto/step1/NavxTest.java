package org.usfirst.frc.team1360.auto.step1;

import org.usfirst.frc.team1360.auto.drive.GoToPosition;
import org.usfirst.frc.team1360.auto.mode.AutonBuilder;
import org.usfirst.frc.team1360.auto.mode.AutonMode;

public class NavxTest implements AutonMode {

	@Override
	public void addToMode(AutonBuilder ab) {
		ab.addCommand(new GoToPosition(new double[] {2, 3}, 0.5, 0.3));
	}

}
