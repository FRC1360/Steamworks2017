package org.usfirst.frc.team1360.new_auto.routines;

import org.usfirst.frc.team1360.new_auto.AutonControl;
import org.usfirst.frc.team1360.new_auto.AutonRoutine;
import org.usfirst.frc.team1360.new_auto.drive.DrivePIDEncoder;
//import org.usfirst.frc.team1360.robot.subsystems.Gear;

public class MiddleGear extends AutonRoutine {
	private boolean pivot = true;
	
	public MiddleGear() {
		super("Middle gear", 0);
	}

	@Override
	protected void runCore() throws InterruptedException
	{
		Thread gear = new Thread(() -> 
		{
			try {
				while (true)
				{
					//Gear.runStateMachine(pivot);
					Thread.sleep(10);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		AutonControl.registerThread(gear);
		gear.start();
		new DrivePIDEncoder(2000, 0.0, 0.75, 850).runUntilFinish();
		new DrivePIDEncoder(1000, 0.0, 0.25, 750).runUntilFinish();
		pivot = false;
		/*while (Gear.getState() != 0)
		{
			Thread.sleep(10);
		}*/
		new DrivePIDEncoder(4000, 0.0, -0.5, -200).runUntilFinish();
		pivot = true;
		new DrivePIDEncoder(4000, 0.0, 0.5, 200).runUntilFinish();
		new DrivePIDEncoder(4000, 0.0, -0.5, -600).runUntilFinish();
		pivot = false;
		Thread.sleep(1000);
		gear.interrupt();
	}
}
