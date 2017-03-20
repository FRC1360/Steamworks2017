package org.usfirst.frc.team1360.auto.tracking;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;

public final class PositionTracker {
	private static PositionTracker instance;
	private Thread thread;
	private RobotOutput robotOutput;
	private SensorInput sensorInput;
	private volatile boolean work = false;
	private volatile boolean logging = false;
	private ConcurrentLinkedQueue<Object[]> logQueue = new ConcurrentLinkedQueue<>();
	private File logFile;
	private Thread logThread;
	private Vector position; 
	private Vector velocity;
	private long prevTime;
	private long thisTime;
	private double thisYaw;
	public double timeDiff;
	
	private static final double WHEEL_RADIUS = 0.10033;
	private static final double ROBOT_RADIUS = 0.5;
	private static final double MOTOR_EFFICIENCY = 1.0;
	private static final double RADIANS_PER_TICK = Math.PI / 512.0;
	private static final double ROBOT_MASS = 54.4311;
	private static final double ACCEL_RATIO_THRESHOLD = 1.1;
	private static final double ANGULAR_VEL_DIFF_THRESHOLD = 0.1;
	
	public static PositionTracker getInstance()
	{
		if (instance == null)
			instance = new PositionTracker();
		
		return instance;
	}
	
	private PositionTracker()
	{
		sensorInput = SensorInput.getInstance();
		robotOutput = RobotOutput.getInstance();
	}
	
	private void run() {
		System.out.println("Position tracking started");
		
		double lastLeftVel = 0;
		double lastRightVel = 0;
		
		position = new Vector(0, 0);
		velocity = new Vector(0, 0);
		
		prevTime = System.nanoTime();
		long lastLog = 0;
		
		while (work) {
			thisYaw = sensorInput.getAHRSYaw();
			
			thisTime = System.nanoTime();
			timeDiff = (thisTime - prevTime) / 1000000000.0;
			prevTime = thisTime;
			
			double voltage = sensorInput.getVoltage();
			double leftVoltage = voltage * robotOutput.leftDrivePower;
			double rightVoltage = voltage * robotOutput.rightDrivePower;
			double leftCurrent = sensorInput.getLeftDriveCurrent();
			double rightCurrent = sensorInput.getRightDriveCurrent();
			double leftVel = sensorInput.getLeftEncoderVelocity();
			double rightVel = sensorInput.getRightEncoderVelocity();
			
			double leftExpectedAccel = (2.0 * WHEEL_RADIUS * MOTOR_EFFICIENCY * leftCurrent * leftVoltage) / (ROBOT_MASS * leftVel * RADIANS_PER_TICK);
			double rightExpectedAccel = (2.0 * WHEEL_RADIUS * MOTOR_EFFICIENCY * rightCurrent * rightVoltage) / (ROBOT_MASS * rightVel * RADIANS_PER_TICK);
			
			double leftActualAccel = (leftVel - lastLeftVel) / timeDiff;
			double rightActualAccel = (rightVel - lastRightVel) / timeDiff;
			
			boolean leftSlip = leftActualAccel / leftExpectedAccel > ACCEL_RATIO_THRESHOLD;
			boolean rightSlip = rightActualAccel / rightExpectedAccel > ACCEL_RATIO_THRESHOLD;
			
			lastLeftVel = leftVel;
			lastRightVel = rightVel;
			
			double ahrsAngularVel = Math.toRadians(sensorInput.getAHRSYawRate());
			
			double leftActualVel, rightActualVel;
			
			if (leftSlip && rightSlip)
			{
				// This is bad
				System.err.println("Both drive sides slipped!");
				work = false;
				break;
			}
			else if (leftSlip)
			{
				rightActualVel = rightVel * WHEEL_RADIUS;
				leftActualVel = rightActualVel + 2.0 * ROBOT_RADIUS * ahrsAngularVel;
			}
			else if (rightSlip)
			{
				leftActualVel = leftVel * WHEEL_RADIUS;
				rightActualVel = leftActualVel - 2.0 * ROBOT_RADIUS * ahrsAngularVel;
			}
			else
			{
				leftActualVel = leftVel * WHEEL_RADIUS;
				rightActualVel = rightVel * WHEEL_RADIUS;
				double wheelsAngularVel = (leftActualVel - rightActualVel) / (2.0 * ROBOT_RADIUS);
				if (Math.abs(wheelsAngularVel - ahrsAngularVel) > ANGULAR_VEL_DIFF_THRESHOLD)
				{
					// This is also bad
					System.err.println("Drive angular velocity does not match NavX angular velocity!");
					work = false;
					break;
				}
				// Here we assume that our range for angular velocity is "good enough";
				// if it isn't, then we shouldn't be trusting our encoder data anyway. 
			}
			
			double linearVel = (leftActualVel + rightActualVel) / 2;
			velocity.update(linearVel * Math.cos(thisYaw), -linearVel * Math.sin(thisYaw));
			position.addFrom(velocity, timeDiff);
			
			if (logging && thisTime - lastLog >= 1000000)
			{
				lastLog = thisTime;
				logQueue.add(new Object[] { thisTime, voltage, leftVoltage, rightVoltage, leftCurrent, rightCurrent, leftVel, rightVel, leftExpectedAccel, rightExpectedAccel, leftActualAccel, rightActualAccel, leftSlip, rightSlip });
			}
			
			Thread.yield();
		}
	}
	
	private void log()
	{
		synchronized (logQueue)
		{
			logQueue.clear();
		}
		try (FileOutputStream fos = new FileOutputStream(logFile); PrintStream w = new PrintStream(fos))
		{
			System.out.println("Position tracking logger started");
			while (logging)
			{
				while (true)
				{
					Object[] data;
					synchronized (logQueue)
					{
						data = logQueue.poll();
					}
					if (data == null)
						break;
					w.println(String.join(",", (Iterable<String>)Arrays.stream(data).map(Object::toString)::iterator));
				}
				Thread.yield();
			}
		}
		catch (Throwable t)
		{
			System.err.println("Error while writing to position tracking log file!");
			t.printStackTrace();
		}
	}
	
	public void start() // Call this to start position tracking
	{
		work = true;
		thread = new Thread(this::run);
		thread.start();
	}
	
	public void stop() throws InterruptedException // Call this to stop position tracking
	{
		work = false;
		thread.join();
		thread = null;
		System.out.println("Position tracking stopped");
	}
	
	public void startLogging(File file)
	{
		logging = true;
		logFile = file;
		logThread = new Thread(this::log);
		logThread.start();
	}
	
	public void stopLogging() throws InterruptedException
	{
		logging = false;
		logThread.join();
		logThread = null;
		System.out.println("Position tracker logging stopped");
	}
	
	public boolean isRunning()
	{
		return work;
	}
	
	public Vector getPosition()
	{
		if (!work)
			throw new IllegalStateException("Position tracking is not currently running!");
		return position.copy();
	}
	
	public Vector getVelocity()
	{
		if (!work)
			throw new IllegalStateException("Position tracking is not currently running!");
		return velocity.copy();
	}
}
