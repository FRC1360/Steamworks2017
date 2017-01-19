package org.usfirst.frc.team1360.auto.drive;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.RobotSubsystems;

public class DriveWait extends AutonCommand{
	public DriveWait(){
		super(RobotSubsystems.DRIVE);
	}
	@Override
	public boolean calculate() {
		return true;
	}
	@Override
	public void override() {	
	}
}
