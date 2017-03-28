package org.usfirst.frc.team1360.auto.tracking;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;

public final class PositionTracker {
	private static PositionTracker instance;
	private ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
	private ScheduledFuture future;
	private RobotOutput robotOutput;
	private SensorInput sensorInput;
	private volatile boolean work = false;
	private volatile boolean logging = false;
	private File logFile;
	private Object notifier = new Object();
	private ConcurrentLinkedQueue<Object[]> logQueue = new ConcurrentLinkedQueue<>();
	private Thread logThread;
	private Vector position; 
	private Vector velocity;
	private long prevTime;
	private long thisTime;
	private double thisYaw;
	public double timeDiff;
	private double lastLeftVel = 0;
	private double lastRightVel = 0;
	private long lastLog = 0;
	private double lastLeft = 0;
	private double lastRight = 0;
	
	private static final double WHEEL_RADIUS = 0.10033;
	private static final double ROBOT_RADIUS = 0.5;
	private static final double MOTOR_EFFICIENCY = 1.0;
	private static final double RADIANS_PER_TICK = Math.PI * 1.375 / 512.0;
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
		double left = sensorInput.getLeftDriveEncoder();
		double right = sensorInput.getRightDriveEncoder();
		
		double leftExpectedAccel = (2.0 * WHEEL_RADIUS * MOTOR_EFFICIENCY * leftCurrent * leftVoltage) / (ROBOT_MASS * leftVel * RADIANS_PER_TICK);
		double rightExpectedAccel = (2.0 * WHEEL_RADIUS * MOTOR_EFFICIENCY * rightCurrent * rightVoltage) / (ROBOT_MASS * rightVel * RADIANS_PER_TICK);
		
		double leftActualAccel = RADIANS_PER_TICK * (leftVel - lastLeftVel) / timeDiff;
		double rightActualAccel = RADIANS_PER_TICK * (rightVel - lastRightVel) / timeDiff;
		
		boolean leftSlip = leftActualAccel / leftExpectedAccel > ACCEL_RATIO_THRESHOLD;
		boolean rightSlip = rightActualAccel / rightExpectedAccel > ACCEL_RATIO_THRESHOLD;
		
		lastLeftVel = leftVel;
		lastRightVel = rightVel;
		
		double ahrsAngularVel = Math.toRadians(sensorInput.getAHRSYawRate());
		
		double leftActualDisp, rightActualDisp;
		
		if (leftSlip && rightSlip)
		{
			// This is bad
			//System.err.println("Both drive sides slipped!");
			/*work = false;
			break;*/
			return;
		}
		else if (leftSlip)
		{
			rightActualDisp = (right - lastRight) * WHEEL_RADIUS;
			leftActualDisp = rightActualDisp + 2.0 * ROBOT_RADIUS * ahrsAngularVel; // TODO: calculate offset from actual angle difference
		}
		else if (rightSlip)
		{/*
			leftActualVel = leftVel * WHEEL_RADIUS;
			rightActualVel = leftActualVel - 2.0 * ROBOT_RADIUS * ahrsAngularVel;*/ //TODO: update
		}
		else
		{/*
			leftActualVel = leftVel * WHEEL_RADIUS;
			rightActualVel = rightVel * WHEEL_RADIUS;
			double wheelsAngularVel = (leftActualVel - rightActualVel) / (2.0 * ROBOT_RADIUS);*/
			/*if (Math.abs(wheelsAngularVel - ahrsAngularVel) > ANGULAR_VEL_DIFF_THRESHOLD)
			{
				// This is also bad
				//System.err.println("Drive angular velocity does not match NavX angular velocity!");
				//work = false;
				//break;
				return;
			}*/
			// Here we assume that our range for angular velocity is "good enough";
			// if it isn't, then we shouldn't be trusting our encoder data anyway. 
		}
		
		// TODO: update
		/*double linearVel = (leftActualVel + rightActualVel) / 2;
		velocity.update(linearVel * Math.cos(thisYaw), -linearVel * Math.sin(thisYaw));
		position.addFrom(velocity, timeDiff);*/
		
		if (logging && thisTime - lastLog >= 1000000)
		{
			lastLog = thisTime;
			logQueue.add(new Object[] { thisTime, voltage, leftVoltage, rightVoltage, leftCurrent, rightCurrent, leftVel, rightVel, leftExpectedAccel, rightExpectedAccel, leftActualAccel, rightActualAccel, leftSlip, rightSlip });
		}
		
		synchronized (notifier)
		{
			notifier.notifyAll();
		}
	}
	
	private void log()
	{
		logQueue.clear();
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
		position = new Vector(0, 0);
		velocity = new Vector(0, 0);
		prevTime = System.nanoTime();
		lastLeft = sensorInput.getLeftDriveEncoder();
		lastRight = sensorInput.getRightDriveEncoder();
		
		future = scheduler.scheduleAtFixedRate(this::run, 0, 500, TimeUnit.MICROSECONDS);
		System.out.println("Position tracking started");
	}
	
	public void stop() throws InterruptedException // Call this to stop position tracking
	{
		if (isRunning())
		{
			future.cancel(false);
			synchronized (notifier)
			{
				notifier.wait();
			}
			future = null;
			System.out.println("Position tracking stopped");
		}
	}
	
	public void startLogging(File file)
	{
		logQueue.add(new Object[] { "Time", "Battery Voltage", "Left Voltage", "Right Voltage", "Left Current", "Right Current", "Left Velocity", "Right Velocity", "Left Expected Acceleration", "Right Expected Acceleration", "Left Measured Acceleration", "Right Measured Acceleration", "Left Slip", "Right Slip" });
		logging = true;
		logFile = file;
		logThread = new Thread(this::log);
		logThread.start();
	}
	
	public void stopLogging() throws InterruptedException
	{
		if (logging)
		{
			logging = false;
			logThread.join();
			logThread = null;
			System.out.println("Position tracker logging stopped");
		}
	}
	
	public boolean isRunning()
	{
		return future != null;
	}
	
	public Vector getPosition()
	{
		if (!isRunning())
			throw new IllegalStateException("Position tracking is not currently running!");
		return position.copy();
	}
	
	public Vector getVelocity()
	{
		if (!isRunning())
			throw new IllegalStateException("Position tracking is not currently running!");
		return velocity.copy();
	}
}
