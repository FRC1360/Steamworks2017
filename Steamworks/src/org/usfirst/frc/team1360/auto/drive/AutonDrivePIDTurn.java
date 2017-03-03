package org.usfirst.frc.team1360.auto.drive;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.RobotSubsystems;
import org.usfirst.frc.team1360.auto.mode.AutonMode;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

public class AutonDrivePIDTurn extends AutonCommand
{
	private OrbitPID drivePID;
	private double angle;
	
	private SensorInput sensorInput;
	private RobotOutput robotOutput;
	
	private boolean firstRun = true;
	
	private long smallLongout = 0;

	public AutonDrivePIDTurn(double angle, long timeout)
	{
		super(RobotSubsystems.DRIVE, timeout);
		
		this.robotOutput = RobotOutput.getInstance();
		this.sensorInput = SensorInput.getInstance();
		this.angle = angle;
		
		double p = this.sensorInput.driveP;
		double i = this.sensorInput.driveI;
		double d = this.sensorInput.driveD;
		
		this.drivePID = new OrbitPID(p, i, d, 0.5);
	}

	@Override
	public boolean calculate() {
		if(firstRun)
		{
			this.drivePID.SetSetpoint(angle);
			//this.sensorInput.resetAHRS();
			this.firstRun = false;
		}
		
		if(smallLongout >= 5)
		{
			if(Math.abs(this.sensorInput.getAHRSYaw() - angle) > 0.5)
			{
				this.drivePID.SetInput(this.sensorInput.getAHRSYaw());
				this.drivePID.CalculateError();
					
				this.robotOutput.arcadeDrivePID(0, 0.5 * drivePID.GetOutput());
				return false;
			}
			else
			{
				return true;
        	}
		}
		else
		{
			smallLongout++;
			return false;
		}

		
	}

	@Override
	public void override()
	{
		
	}
	
}
