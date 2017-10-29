package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.subsystems.Gear;

public class TeleopGear implements TeleopComponent{
	private static TeleopGear instance;
	private RobotOutput robotOutput;
	private HumanInput humanInput;

	
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
		this.robotOutput = RobotOutput.getInstance();
		this.humanInput = HumanInput.getInstance();
	}
	
	@Override											
	public void calculate() //Run every tick to process data for TeleopGear.
	{
		boolean fine = humanInput.getOperatorFineAdjustGear();
		boolean pivot = humanInput.getOperatorPivotBtn();
		double speed = humanInput.getOperatorEatGear();
		
		Gear.runStateMachine(pivot);
		
		this.robotOutput.fineAdjustGearMech(fine);
		this.robotOutput.intakeGear(speed);

	}
	
	@Override
	public void disable() //Run when robot is disabled.
	{
		Gear.disable();
	}

}
