package org.usfirst.frc.team1360.auto.drive;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.RobotSubsystems;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

public class AutonDrivePIDTime extends AutonCommand {


	private SensorInput sensorInput;
	private RobotOutput robotOutput;
	private OrbitPID drivePID;
	
	private double speed;
	
	private boolean firstRun = true;
	private double target;
	private long smallLongout = 0;
	
	private int encoderStart;
	
	public AutonDrivePIDTime(double target, double speed, long timeout)
	{
		this(target, speed, 0.5, timeout);
	}
	
	public AutonDrivePIDTime(double target, double speed, double eps, long timeout) {
		super(RobotSubsystems.DRIVE, timeout);
		
		this.sensorInput = SensorInput.getInstance();
		this.robotOutput = RobotOutput.getInstance();
		
		this.speed = speed;
		this.target = target;
		
		/*double p = SensorInput.driveP;
		double i = SensorInput.driveI;
		double d = SensorInput.driveD;*/
		
		double p = 0.1;
		double i = 0.00005;
		double d = 0.01;
				
		this.drivePID = new OrbitPID(p, i, d, eps);
		
	}
	
	@Override
	public boolean calculate()
	{
		if(firstRun)
		{
			this.drivePID.SetSetpoint(target);
			this.sensorInput.resetAHRS();
			this.firstRun = false;
			this.encoderStart = sensorInput.getLeftDriveEncoder();
		}
		
		if(this.smallLongout >= 5)
		{
			this.drivePID.SetInput(this.sensorInput.getAHRSYaw());
			this.drivePID.CalculateError();
					
			this.robotOutput.arcadeDrivePID(speed, Math.abs(speed) * drivePID.GetOutput());
		}
		else
		{
			this.robotOutput.tankDrive(speed, speed);
			this.smallLongout++;
		}	
		return false;
	}

	@Override
	public void override() {
		this.robotOutput.tankDrive(0, 0);
		System.out.printf("Drive moved %d ticks\n", sensorInput.getLeftDriveEncoder() - encoderStart);
	}

}
