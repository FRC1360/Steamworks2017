package org.usfirst.frc.team1360.navx;

import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.I2C;

public class PositionTracker {
	private static PositionTracker instance;
	AHRS ahrs;
	RobotOutput robotOutput;
	SensorInput sensorInput;
	double[] position; // First item in array is x value second item in array is y value
	double[] velocity; // X, Y
	long prevTime;
	long thisTime;
	double timeDiff;
	float thisYaw;
	float prevYaw;
	double theta;
	
	public static PositionTracker getInstance() {
		if (instance == null)
			instance = new PositionTracker();
		
		return instance;
	}
	
	public AHRS getAHRS() {
		return ahrs;
	}
	
	// this constructor must be called in the first few lines of code or else it will not work properly
	private PositionTracker() {
		sensorInput = SensorInput.getInstance();
		robotOutput = RobotOutput.getInstance();
		ahrs = SensorInput.getInstance().ahrs;
		position = new double[] { 0.0, 0.0 };
		velocity = new double[] { 0.0, 0.0 };
		prevTime = System.nanoTime();
		prevYaw = ahrs.getYaw();
		
		(new Thread() {
			public void run() {
				while (true) {
					thisYaw = ahrs.getYaw();
					if (thisYaw > prevYaw)
						while (thisYaw - prevYaw >= 360.0)
							thisYaw -= 360.0;
					else
						while (prevYaw - thisYaw >= 360.0)
							thisYaw += 360.0;
					
					thisTime = System.nanoTime();
					timeDiff = (double)(thisTime - prevTime) / 1000000000.0;
					prevTime = thisTime;
					
					double x = ahrs.getWorldLinearAccelX();
					double y = ahrs.getWorldLinearAccelY();
					velocity[0] += x * timeDiff;
					velocity[1] += y * timeDiff;
					position[0] += velocity[0] * timeDiff;
					position[1] += velocity[1] * timeDiff;
					
					System.out.println(velocity[0] + "\n" + velocity[1] + "\n\n\n");
					
					Thread.yield();
				}
			}
		}).start();
	}
	
	public double[] getPosition() {
		return position;
	}
	
	public double getCurrentYaw() {
		return thisYaw;
	}
	
	public double getPreviousYaw() {
		return prevYaw;
	}
}
