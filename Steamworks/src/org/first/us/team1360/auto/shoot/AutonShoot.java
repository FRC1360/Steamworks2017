package org.usfirst.frc.team1360.auto.drive;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.RobotSubsystems;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public class AutonShoot extends AutonCommand {

public RobotOutput robotOutput;
public double speed;

public AutonShoot(long timeout, double speed){
	super(RobotSubsystems.DRIVE);
	
	this.speed=speed;
}
	@Override
	public boolean calculate() {
		this.robotOutput.Shoot(zoom);
		return false;
	}

	@Override
	public void override() {
		this.robotOutput.tankDrive(0, 0);
		
	}

}
