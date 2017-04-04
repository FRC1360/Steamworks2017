package org.usfirst.frc.team1360.auto.goal;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.RobotSubsystems;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public class AutonLowGoal extends AutonCommand {

	private RobotOutput robotOutput;
	private double speed;
	private boolean shift;
	
	public AutonLowGoal(long timeout, boolean shift, double speed) {
		super(RobotSubsystems.INTAKE, timeout);
		// TODO Auto-generated constructor stub
		
		this.robotOutput = RobotOutput.getInstance();
		this.speed = speed;
		this.shift = shift;
	}

	@Override
	public boolean calculate() {
		this.robotOutput.intake(speed);
		this.robotOutput.openItake(shift);
		return false;
	}

	@Override
	public void override() {
		// TODO Auto-generated method stub
		
	}

}
