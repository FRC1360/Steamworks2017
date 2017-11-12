package org.usfirst.frc.team1360.auto.util;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.AutonControl;
import org.usfirst.frc.team1360.auto.RobotSubsystems;

public class AutonStop extends AutonCommand {

	public AutonStop() {
		super(RobotSubsystems.UTIL);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean calculate() {
		AutonControl.getInstance().stop();
		return true;
	}

	@Override
	public void override() {
		// TODO Auto-generated method stub
		
	}
	
}
