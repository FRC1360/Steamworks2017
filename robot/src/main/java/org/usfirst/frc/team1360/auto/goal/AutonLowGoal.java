package org.usfirst.frc.team1360.auto.goal;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.RobotSubsystems;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public class AutonLowGoal extends AutonCommand {

	private RobotOutput robotOutput;
	private double speed;
	private boolean open;
	
	public AutonLowGoal(double speed, boolean open) {
		super(RobotSubsystems.INTAKE);
		// TODO Auto-generated constructor stub
		
		this.robotOutput = RobotOutput.getInstance();
		this.speed = speed;
		this.open = open;
	}

	@Override
	public boolean calculate() {
		this.robotOutput.intake(speed);
		this.robotOutput.outtake(open);
		return true;
	}

	@Override
	public void override() {
		// TODO Auto-generated method stub
		
	}

}
