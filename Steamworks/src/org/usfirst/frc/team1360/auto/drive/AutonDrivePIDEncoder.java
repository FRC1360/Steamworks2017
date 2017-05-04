package org.usfirst.frc.team1360.auto.drive;

import org.usfirst.frc.team1360.auto.AutonCommand;
import org.usfirst.frc.team1360.auto.RobotSubsystems;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

public class AutonDrivePIDEncoder extends AutonCommand {
	private static double SCALAR = 1.4;
	//private static double SCALAR = 0.75;

	private SensorInput sensorInput;
	private RobotOutput robotOutput;
	private OrbitPID drivePID;
	
	private double speed;
	
	private boolean firstRun = true;
	private double target;
	private long smallLongout = 0;
	
	private int encoderStart;
    private int encoderLimit;
	
	public AutonDrivePIDEncoder(double target, double speed, int encoderLimit, long timeout)
	{
		this(target, speed, 0.5, timeout);
        this.encoderLimit = (int)(encoderLimit * SCALAR);
	}
	
	public AutonDrivePIDEncoder(double target, double speed, double eps, long timeout) {
		super(RobotSubsystems.DRIVE, timeout);
		
		this.sensorInput = SensorInput.getInstance();
		this.robotOutput = RobotOutput.getInstance();
		
		this.speed = speed;
		this.target = target;
		
		/*double p = SensorInput.driveP;
		double i = SensorInput.driveI;
		double d = SensorInput.driveD;*/
		
		double p = 0.1; //0.1
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
			//this.sensorInput.resetAHRS();
			this.firstRun = false;
			this.encoderStart = sensorInput.getRightDriveEncoder();
		}
		
		if(this.smallLongout >= 2)
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

        int diff = sensorInput.getRightDriveEncoder() - encoderStart;
        
        return encoderLimit > 0 ? diff > encoderLimit : encoderLimit > diff;
	}

	@Override
	public void override() {
		this.robotOutput.tankDrive(0, 0);
	}

}
