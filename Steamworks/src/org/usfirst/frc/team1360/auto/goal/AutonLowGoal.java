package org.usfirst.frc.team1360.auto.goal;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.RobotSubsystems;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public class AutonLowGoal extends AutonCommand {

	private RobotOutput robotOutput;
	private double speed;
<<<<<<< HEAD
	private boolean shift;
	
	public AutonLowGoal(long timeout, boolean shift, double speed) {
		super(RobotSubsystems.INTAKE, timeout);
=======
	private boolean open;
	
	public AutonLowGoal(double speed, boolean open) {
		super(RobotSubsystems.INTAKE);
>>>>>>> Auto
		// TODO Auto-generated constructor stub
		
		this.robotOutput = RobotOutput.getInstance();
		this.speed = speed;
<<<<<<< HEAD
		this.shift = shift;
=======
		this.open = open;
>>>>>>> Auto
	}

	@Override
	public boolean calculate() {
		this.robotOutput.intake(speed);
<<<<<<< HEAD
		this.robotOutput.openItake(shift);
		return false;
=======
		this.robotOutput.outtake(open);
		return true;
>>>>>>> Auto
	}

	@Override
	public void override() {
		// TODO Auto-generated method stub
		
	}

}
