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
	double[] acceleration;
	long prevTime;
	long thisTime;
	public double timeDiff;
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
		acceleration = new double[] { 0.0, 0.0 };
		prevTime = System.nanoTime();
		prevYaw = sensorInput.getAHRSYaw();
		
		(new Thread() {
			public void run() {
				ValueFilter vx = new ValueFilter(10), vy = new ValueFilter(10);
				while (true) {
					thisYaw = sensorInput.getAHRSYaw();
					if (thisYaw > prevYaw)
						while (thisYaw - prevYaw >= 360.0)
							thisYaw -= 360.0;
					else
						while (prevYaw - thisYaw >= 360.0)
							thisYaw += 360.0;
					
					thisTime = System.nanoTime();
					timeDiff = (thisTime - prevTime) / 1000000000.0;
					prevTime = thisTime;
					
					/*acceleration[0] = ax.caluclate(sensorInput.getAHRSWorldLinearAccelX());
					acceleration[1] = ay.caluclate(sensorInput.getAHRSWorldLinearAccelY());
					velocity[0] = vx.caluclate(velocity[0] + acceleration[0] * timeDiff);
					velocity[1] = vy.caluclate(velocity[1] + acceleration[1] * timeDiff);*/
					Vector v = new Vector(vx.caluclate(sensorInput.getAHRSVelocityX()), vy.caluclate(sensorInput.getAHRSVelocityY()));
					v.rotate(Math.toRadians(sensorInput.getAHRSYaw()));
					velocity[0] = v.getX();
					velocity[1] = v.getY();
					position[0] += v.getX() * timeDiff;
					position[1] += v.getY() * timeDiff;
					
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
	
	public double[] getVelocity() {
		return velocity;
	}
	
	public double[] getAcceleration() {
		return acceleration;
	}
	
	public double getCurrentYaw() {
		return thisYaw;
	}
	
	public double getPreviousYaw() {
		return prevYaw;
	}
}
