package org.usfirst.frc.team1360.navx;

import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.I2C;

public class PositionTracker {
	private static PositionTracker instance;
	RobotOutput robotOutput;
	SensorInput sensorInput;
	double[] position; // First item in array is x value second item in array is y value in CENTIMETRES
	double[] velocity; // X, Y
	long prevTime;
	long thisTime;
	long timeDiff;
	double thisYaw;
	double prevYaw;
	double theta;
	
	public static PositionTracker getInstance() {
		if (instance == null)
			instance = new PositionTracker();
		
		return instance;
	}
	
	// this constructor must be called in the first few lines of code or else it will not work properly
	private PositionTracker() {
		sensorInput = SensorInput.getInstance();
		robotOutput = RobotOutput.getInstance();
		position = new double[] { 0.0, 0.0 };
		velocity = new double[] { 0.0, 0.0 };
		prevTime = System.nanoTime();
		prevYaw = sensorInput.getAHRSYaw();
		
		(new Thread() {
			public void run() {
				while (true) {
					thisYaw = sensorInput.getAHRSYaw();
					if (thisYaw > prevYaw)
						while (thisYaw - prevYaw >= 360.0)
							thisYaw -= 360.0;
					else
						while (prevYaw - thisYaw >= 360.0)
							thisYaw += 360.0;
					
					thisTime = System.nanoTime();
					timeDiff = (thisTime - prevTime);
					prevTime = thisTime;
					
					double x = sensorInput.getAHRSWorldLinearAccelX();
					double y = sensorInput.getAHRSWorldLinearAccelY();
					velocity[0] += x * timeDiff;
					velocity[1] += y * timeDiff;
					position[0] += velocity[0] * timeDiff;
					position[1] += velocity[1] * timeDiff;
					
					//System.out.printf("%f\n%f\n", velocity[0], velocity[1]);
					//System.out.printf("%f\n%f\n", x * timeDiff, y * timeDiff);
					//System.out.printf("%f\n%f\n", SensorInput.getInstance().ahrs.getVelocityX(), SensorInput.getInstance().ahrs.getVelocityY());
					//System.out.println(timeDiff + "\n\n\n");
					
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
