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
	
	private double speed;
	
	private boolean firstCycle = true;
	private double target;
	private long smallLongout = 0;
	
	
	public AutonDrivePID(double target, double speed, double eps, long timeout) {
		super(RobotSubsystems.DRIVE, timeout);
		
		this.sensorInput = SensorInput.getInstance();
		this.robotOutput = RobotOutput.getInstance();
		
		this.speed = speed;
		this.target = target;
		
		double p = 0.1;//SmartDashboard.getNumber("Drive P:", 0.0);
		double i = 0.00005;//SmartDashboard.getNumber("Drive I:", 0.0);
		double d = 0.01;//SmartDashboard.getNumber("Drive D:", 0.0);
		
		this.drivePID = new OrbitPID(p, i, d, eps);
		

		
	}
	
	@Override
	public boolean calculate()
	{
		if(firstCycle)
		{
			this.drivePID.SetSetpoint(target);
			this.sensorInput.resetAHRS();
			firstCycle = false;
		}
		
		if(this.smallLongout >= 5)
		{
			this.drivePID.SetInput(this.sensorInput.getAHRSYaw());
			this.drivePID.CalculateError();
				
			this.robotOutput.arcadeDrive(speed, speed * drivePID.GetOutput());
			System.out.println("Hi there");
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
		
	}

}
