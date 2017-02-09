package org.usfirst.frc.team1360.auto.drive;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.RobotSubsystems;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public class AutonDrive extends AutonCommand {

	public RobotOutput robotOutput;
	public double leftspeed;
	public double rightspeed;
	
	public AutonDrive(long timeout, double leftspeed, double rightspeed){
		super(RobotSubsystems.DRIVE, timeout);
		
		this.robotOutput = RobotOutput.getInstance();
		this.leftspeed = leftspeed;
		this.rightspeed = rightspeed;
	}
	@Override
	public boolean calculate() {
		
		this.robotOutput.tankDrive(leftspeed,rightspeed);
		
		return false;
	}
	@Override
	public void override() {
		this.robotOutput.tankDrive(0, 0);
		
	}

}
