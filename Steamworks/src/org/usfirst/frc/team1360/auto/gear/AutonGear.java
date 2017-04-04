package org.usfirst.frc.team1360.auto.gear;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.AutonControl;
import org.usfirst.frc.team1360.auto.RobotSubsystems;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public class AutonGear extends AutonCommand {

	private RobotOutput robotOutput;
	private boolean piston;
	private boolean fine;
	private double speed;
	
	public AutonGear(boolean piston, boolean fineAdjust, double intakeSpeed) {
		super(RobotSubsystems.GEAR);

		this.robotOutput = RobotOutput.getInstance();
		this.piston = piston;
		this.fine = fineAdjust;
		this.speed = intakeSpeed;
	}

	@Override
	public boolean calculate() {
		//this.robotOutput.flapGear(flap);
		//this.robotOutput.releaseGear(release);
		this.robotOutput.fineAdjustGearMech(fine);
		this.robotOutput.pivotGearMech(piston);
		this.robotOutput.intakeGear(speed);
		return true;
	}

	@Override
	public void override() {
		// TODO Auto-generated method stub
		
	}

}
