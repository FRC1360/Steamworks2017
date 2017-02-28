package org.usfirst.frc.team1360.robot.IO;
/*****
 * Author: Tatiana Tomas Zahhar
 * Date 30 Jan 2017 - added pdp variable; getClimberFrontCurrent method; getClimberBackCurrent method; removed calculate
 *****/

import java.util.concurrent.ConcurrentLinkedQueue;

import org.usfirst.frc.team1360.robot.Robot;
import org.usfirst.frc.team1360.server.components.ClimberCurrentDisplayComponent;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SensorInput {

	private static SensorInput instance;				//Fields of class SensorInput
	
	private PowerDistributionPanel PDP;
	private ClimberCurrentDisplayComponent currentDisplay;
	private AHRS ahrs;
	
	public static final double driveP = 0.1;
	public static final double driveI = 0.00005;
	public static final double driveD = 0.01;
	
	private Encoder driveLeftEncoder;
	
	private Thread ahrsThread;
	private double[] ahrsValues = new double[7];
	private ConcurrentLinkedQueue<Runnable> ahrsThreadDispatchQueue = new ConcurrentLinkedQueue<>();
	
	private SensorInput()								//Constructor to initialize fields  
	{
		driveLeftEncoder = new Encoder(0, 1);
		PDP = new PowerDistributionPanel();
		ahrsThread = new Thread(() ->
		{
			ahrs = new AHRS(SPI.Port.kMXP); // THIS SHOULD BE THE ONLY AHRS CONSTRUCTOR BEING CALLED, IF IT IS NOT, DELETE THE OTHER ONE
			synchronized (this)
			{
				notify();
			}
			while (true)
			{
				synchronized (this)
				{
					ahrsValues[0] = ahrs.getYaw();
					ahrsValues[1] = ahrs.getPitch();
					ahrsValues[2] = ahrs.getRoll();
					ahrsValues[3] = ahrs.getWorldLinearAccelX();
					ahrsValues[4] = ahrs.getWorldLinearAccelY();
					ahrsValues[5] = ahrs.getVelocityX();
					ahrsValues[6] = ahrs.getVelocityY();
					if (!ahrsThreadDispatchQueue.isEmpty())
						ahrsThreadDispatchQueue.remove().run();
				}
				Thread.yield();
			}
		});
		ahrsThread.start();
		synchronized (this)
		{
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static SensorInput getInstance()				//Check to make sure that SensorInput exists
	{
		if (instance == null)
		{
			instance = new SensorInput();
		}
		
		return instance;
	}
	
	public synchronized double getAHRSYaw()
	{
		return ahrsValues[0];
	}
	
	public synchronized double getAHRSPitch()
	{
		return ahrsValues[1];
	}
	
	public synchronized double getAHRSRoll()
	{
		return ahrsValues[2];
	}
	
	public synchronized double getAHRSWorldLinearAccelX()
	{
		return ahrsValues[3];
	}

	public synchronized double getAHRSWorldLinearAccelY()
	{
		return ahrsValues[4];
	}
	
	public synchronized double getAHRSVelocityX()
	{
		return ahrsValues[5];
	}
	
	public synchronized double getAHRSVelocityY()
	{
		return ahrsValues[6];
	}
	
	public synchronized void resetAHRS()
	{
		ahrsThreadDispatchQueue.add(ahrs::reset);
	}
	
	public double getClimberFrontCurrent()				//Method in class SensorInput
	{
		return this.PDP.getCurrent(0);					//PDP port 0 for ClimberFront Motor
	}
	
	public double getClimberBackCurrent()				
	{
		return this.PDP.getCurrent(1);	//PDP port 1 for ClimberBack Motor
	}
	
	public int getLeftDriveEncoder()
	{
		return this.driveLeftEncoder.get();
	}
	
	public void resetLeftEncoder()
	{
		this.driveLeftEncoder.reset();
	}
	
	public void calculate()
	{
		if (currentDisplay == null)
		{
			currentDisplay = new ClimberCurrentDisplayComponent();
			Robot.getInstance().getConnection().addComponent(currentDisplay, 1);
		}
		currentDisplay.update();
		
		SmartDashboard.putNumber("Left Enc", this.getLeftDriveEncoder());
		SmartDashboard.putNumber("Climber Average Current", (this.getClimberFrontCurrent() + this.getClimberBackCurrent()) / 2);
		SmartDashboard.putNumber("NavX Yaw ==", this.getAHRSYaw());
	}

	public void reset()
	{
		resetAHRS();
	}
}
