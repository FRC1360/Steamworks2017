package org.usfirst.frc.team1360.auto.drive;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.RobotSubsystems;
import org.usfirst.frc.team1360.auto.mode.AutonMode;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

public class AutonDrivePIDTurn extends AutonCommand
{
	private OrbitPID driveController;
	private double angle;
	
	private SensorInput sensorInput;
	private RobotOutput robotOutput;
	
	private boolean firstCycle = true;
	
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
		
		this.driveController = new OrbitPID(p, i, d, 0.5);
	}

	@Override
	public boolean calculate() {
		if(firstCycle)
		{
			this.driveController.SetSetpoint(angle);
			this.sensorInput.resetAHRS();
			firstCycle = false;
			return false;
		}
		else
		{
			if(this.smallLongout >= 5)
			{
				this.driveController.SetInput(this.sensorInput.getAHRSYaw());
				this.driveController.CalculateError();
				
				this.robotOutput.arcadeDrivePID(0, 0.5 * this.driveController.GetOutput());
				
				if(Math.abs(this.sensorInput.getAHRSYaw()) - angle >= 0.5)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				this.robotOutput.arcadeDrive(0, 0);
				return false;
			}
		}
		
	}

	@Override
	public void override()
	{
		
	}
	
}
