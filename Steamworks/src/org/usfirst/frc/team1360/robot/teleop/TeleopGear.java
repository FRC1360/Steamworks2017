package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public class TeleopGear implements TeleopComponent{
	private static TeleopGear instance;
	private RobotOutput robotOutput;
	private HumanInput humanInput;
	private boolean isOpen = false; //Stores if the gear flap is open.
	private boolean lastShift = false;
	
	public static TeleopGear getInstance() //Get the current instance of TeleopGear. If none exists, make one.
	{
		if (instance == null)
		{	
			instance = new TeleopGear();
		}
		return instance;
	}
	
	private TeleopGear() //Define access to HumanInput and RobotOutput from TeleopGear.
	{
		this.humanInput= HumanInput.getInstance();   
		this.robotOutput = RobotOutput.getInstance();  	
	}
	
	@Override											
	public void calculate() //Run every tick to process data for TeleopGear.
	{
		//boolean flap = humanInput.getOperatorGearFlap();
		//boolean release = humanInput.getOperatorGearRelease();
		
		/*if(flap != lastShift)
		{
			robotOutput.flapGear(isOpen = !isOpen);
			lastShift = !isOpen;
		}*/
		
		//this.robotOutput.flapGear(flap);
				
		//this.robotOutput.releaseGear(release);
		
		boolean fine = humanInput.getOperatorFineAdjustGear();
		boolean pivot = humanInput.getOperatorPivotGear();
		double speed = humanInput.getOperatorEatGear();
		
		
		
		this.robotOutput.pivotGearMech(pivot);
		this.robotOutput.fineAdjustGearMech(fine);
		this.robotOutput.intakeGear(speed);
		
		/*if (pivot && speed > 0.2 && speed < -0.2)
		{
			this.robotOutput.intakeGear(0.75);	
		}
		else
		{
			this.robotOutput.intakeGear(speed);
		}*/
	}
	
	@Override
	public void disable() //Run when robot is disabled.
	{
		//this.robotOutput.releaseGear(false);
		//this.robotOutput.flapGear(false);
		
		this.robotOutput.pivotGearMech(false);
		this.robotOutput.fineAdjustGearMech(false);
		this.robotOutput.intakeGear(0);
	}

}
