package org.usfirst.frc.team1360.navx;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.I2C;

public class PositionTracker {
	AHRS ahrs;
	float[] position; // First item in array is x value second item in array is y value
	long prevTime;
	long thisTime;
	long timeDiff;
	float xDisp;
	float yDisp;
	boolean yawTurn; // true is right, false is left
	float thisYaw;
	float prevYaw;
	float theta;
	
	// this constructor must be called in the first few lines of code or else it will not work properly
	public PositionTracker() {
		ahrs = new AHRS(I2C.Port.kMXP);
		position = new float[] { 0.0f, 0.0f };
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
					
					// this if - else statement assumes that forward is x positive and right is y positive, it may have to be changed
					if (thisYaw >= 0 && thisYaw <= 90) {
						theta = 90 - thisYaw;
						position[0] += xDisp * Math.sin(theta) + yDisp * Math.sin(thisYaw);
						position[1] += xDisp * Math.cos(theta) + yDisp * Math.cos(thisYaw);
					} else if (thisYaw >= 90 && thisYaw <= 180) {
						theta = thisYaw - 90;
						position[0] += xDisp * Math.sin(theta);
						position[1] += xDisp * Math.cos(theta);
						
						theta = 270 - (thisYaw + 90);
						position[0] += yDisp * Math.sin(theta);
						position[1] += yDisp * Math.cos(theta);
						
					} else if (thisYaw >= 180 && thisYaw <= 270) {
						theta = 270 - thisYaw;
						position[0] += xDisp * Math.sin(theta);
						position[1] += xDisp * Math.cos(theta);
						
						theta = (thisYaw + 90) - 270;
						position[0] += yDisp * Math.sin(theta);
						position[1] += yDisp * Math.cos(theta);
					} else {
						theta = thisYaw - 270;
						position[0] += xDisp * Math.sin(theta);
						position[1] += xDisp * Math.cos(theta);
						
						theta = 90 - (thisYaw + 90 - 360);
						position[0] += yDisp * Math.sin(theta);
						position[1] += yDisp * Math.cos(theta);
					}
				}
			}
		}).start();
	}
	
	public float[] getPosition() {
		return position;
	}
}
