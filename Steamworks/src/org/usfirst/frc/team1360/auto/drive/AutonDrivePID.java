package org.usfirst.frc.team1360.auto.drive;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.RobotSubsystems;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1360.robot.IO.RobotOutput;;

public class AutonDrivePID extends AutonCommand {

	private SensorInput sensorInput;
	private RobotOutput robotOutput;
	private OrbitPID drivePID;
	private boolean firstCycle = true;
	
	private double target;
	
	public AutonDrivePID(double target, long timeout, double eps) {
		super(RobotSubsystems.DRIVE, timeout);
		
		double p = SmartDashboard.getNumber("Drive Enc P: ", 0);
		double i = SmartDashboard.getNumber("Drive Enc I:", 0);
		double d = SmartDashboard.getNumber("Drive Enc D: ", 0);
		this.drivePID = new OrbitPID(p, i, d, eps);
		this.target = target;
		this.robotOutput = RobotOutput.getInstance();
		this.sensorInput = SensorInput.getInstance();
	}
	
	@Override
	public boolean calculate() {
		if(this.firstCycle)
		{
			this.drivePID.SetSetpoint(sensorInput.getDriveEncoderAverage() + this.target);
			this.firstCycle = false;
		}
		
		this.drivePID.CalculateError();
		
		if(this.drivePID.isDone())
		{
			this.robotOutput.tankDrive(0, 0);
			return true;
		} 
		else
		{
			this.robotOutput.tankDrive(this.drivePID.GetOutput(), this.drivePID.GetOutput());
			return false;
		}
		
	}

	@Override
	public void override() {
		this.robotOutput.tankDrive(0, 0);
		
	}

}
