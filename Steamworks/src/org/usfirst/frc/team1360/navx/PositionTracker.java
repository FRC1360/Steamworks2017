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
	long prevTime;
	long thisTime;
	long timeDiff;
	float xDisp;
	float yDisp;
	boolean yawTurn; // true is right, false is left
	float thisYaw;
	float prevYaw;
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
		ahrs = new AHRS(I2C.Port.kMXP);
		position = new double[] { 0.0, 0.0 };
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
					yawTurn = thisYaw - prevYaw >= 0;
					thisTime = System.nanoTime();
					timeDiff = thisTime - prevTime;
					prevTime = thisTime;
				
					xDisp = ahrs.getVelocityX() * 1000000000 * timeDiff;
					yDisp = ahrs.getVelocityY() * 1000000000 * timeDiff;
					
					theta = Math.toRadians(90 - thisYaw);
					
					position[0] = yDisp * Math.cos(theta) + xDisp * Math.sin(theta);
					position[1] = yDisp * Math.sin(theta) - xDisp * Math.cos(theta);
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
