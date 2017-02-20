package org.usfirst.frc.team1360.navx;

import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.I2C;

public class PositionTracker {
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
	
	// this constructor must be called in the first few lines of code or else it will not work properly
	public PositionTracker() {
		sensorInput = SensorInput.getInstance();
		robotOutput = RobotOutput.getInstance();
		ahrs = new AHRS(I2C.Port.kMXP);
		position = new double[] { 0.0, 0.0 };
		prevTime = System.nanoTime();
		prevYaw = ahrs.getYaw() < 0 ? 360 + ahrs.getYaw() : ahrs.getYaw();
		
		(new Thread() {
			public void run() {
				while (true) {
					thisYaw = ahrs.getYaw() < 0 ? 360 + ahrs.getYaw() : ahrs.getYaw();
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
	
	public void goTo(double[] target) {
		double xLen = target[0] - position[0];
		double yLen = target[1] - position[1];
		double targetLen = (float)Math.sqrt((double)(xLen * xLen + yLen * yLen));
		
		float targetYaw = (float)Math.atan(yLen / xLen);
		
		(new Thread() {
			public void run() {
				while (thisYaw < targetYaw - 0.25 || targetYaw + 0.25 < thisYaw) {
					if (thisYaw < targetYaw) {
						robotOutput.tankDrive(0.5, -0.5);
					} else { 
						robotOutput.tankDrive(-0.5, 0.5);
					}
				}
				robotOutput.tankDrive(0, 0);
				
				while (targetLen > 0.5) {
					robotOutput.tankDrive(1, 1);
					double xLen = target[0] - position[0];
					double yLen = target[1] - position[1];
					double targetLen = Math.sqrt(xLen * xLen + yLen * yLen);
				}
				robotOutput.tankDrive(0, 0);
			}
		}).start();
		
	}
}
