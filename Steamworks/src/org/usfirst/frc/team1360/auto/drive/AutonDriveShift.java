package org.usfirst.frc.team1360.auto.drive;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.RobotSubsystems;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public class AutonDriveShift extends AutonCommand {
	
	private RobotOutput robotOutput;
	private boolean shift;


	public AutonDriveShift(boolean shift) 
	{
		super(RobotSubsystems.UTIL);
		
		this.robotOutput = RobotOutput.getInstance();
		this.shift = shift;
		
	}

	@Override
	public boolean calculate() {
		this.robotOutput.shiftSpeed(shift);
		return true;
	}

	@Override
	public void override() {
		// TODO Auto-generated method stub
		
	}

}
