package org.usfirst.frc.team1360.auto.gear;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.AutonControl;
import org.usfirst.frc.team1360.auto.RobotSubsystems;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public class AutonGear extends AutonCommand {

	private RobotOutput robotOutput;
	private boolean release;
	private boolean flap;
	
	public AutonGear(boolean release, boolean flap) {
		super(RobotSubsystems.GEAR);

		this.robotOutput = RobotOutput.getInstance();
		this.flap = flap;
		this.release = release;
	}

	@Override
	public boolean calculate() {
		this.robotOutput.flapGear(flap);
		this.robotOutput.releaseGear(release);
		return true;
	}

	@Override
	public void override() {
		// TODO Auto-generated method stub
		
	}

}
