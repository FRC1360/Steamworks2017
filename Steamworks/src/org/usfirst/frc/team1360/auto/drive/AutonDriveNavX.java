package org.usfirst.frc.team1360.auto.drive;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.RobotSubsystems;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;


public class AutonDriveNavX extends AutonCommand
{
	private static final double kOffBalanceAngleThresholdDegrees = 10;
	private static final double kOnBalanceAngleThresholdDegrees = 5;
	private RobotOutput robotOutput;
	private double speed;
	private boolean autoBalanceMode;
	private SensorInput sensorInput;
	
	
	public AutonDriveNavX(long timeoutLength, double speed) 
	{
		super(RobotSubsystems.DRIVE, timeoutLength);
		
		this.sensorInput = SensorInput.getInstance();
		this.robotOutput = RobotOutput.getInstance();
		
		this.speed = speed;
		this.sensorInput.resetAHRS();
	}

	@Override
	public boolean calculate() 
	{
		double yawAngleDegrees = this.sensorInput.getAHRSYaw();
		
		if(!this.autoBalanceMode && Math.abs(yawAngleDegrees)
				>= Math.abs(kOffBalanceAngleThresholdDegrees))
		{
			autoBalanceMode = true;
		} 
		else if (this.autoBalanceMode && Math.abs(yawAngleDegrees)
				<= Math.abs(kOnBalanceAngleThresholdDegrees))
		{
			autoBalanceMode = false;
		}
		
		if(this.autoBalanceMode)
		{
			double yawAxisRadians = yawAngleDegrees * (Math.PI / 180);
			speed = Math.sin(yawAxisRadians) * -1;
		}
		
		this.robotOutput.tankDrive(speed, speed);
		return false;
	}

	@Override
	public void override() {
		// TODO Auto-generated method stub
		
	}

}
