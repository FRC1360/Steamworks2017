package org.usfirst.frc.team1360.robot.subsystems;

import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.teleop.TeleopGear;

public class Gear {
	private static RobotOutput robotOutput = RobotOutput.getInstance();
	private static SensorInput sensorInput = SensorInput.getInstance();
	private static int state = 0;
	/*
	 * 0: down
	 * 1: pseudo-pid to go + stay up
	 * 2: release + go down
	 */
	
	
	private static double DOWN_SPEED = -0.1;
	private static double UP_SPEED = 0.35;
	private static double HOLD_SPEED = 0.1;
	
	//TODO these need to be set to something legit
	private static int UP_TARGET = 1360; //middle of range
	private static int EPSILON = 1360;
	private static int DOWN_TARGET = 1360;
	
	public static void setState(int stateToSet)
	{
		state = stateToSet;
	}
	
	public static int getState()
	{
		return state;
	}
	
	public static void runStateMachine(boolean pivot)
	{
		int position = sensorInput.getPivotEncoder();
		
		switch(state)
		{
		case 0:
			if (pivot)
			{
				state = 1;
			}
			robotOutput.pivotGear(DOWN_SPEED);
			break;
		case 1:
			// pseudo-pid
			if (!pivot)
			{
				state = 2;
			}
			else if (Math.abs(position - UP_TARGET) < EPSILON)
			{
				robotOutput.pivotGear(HOLD_SPEED);
			}
			else
			{
				robotOutput.pivotGear(position > UP_TARGET ? UP_SPEED : DOWN_SPEED);
			}
			break;
		case 2:
			// Go down to pass a certain value then cut power + outtake, change to state 0 when done
			if(position < DOWN_TARGET)
			{
				robotOutput.intakeGear(0);
				state = 0;
			}
			else
			{
				robotOutput.pivotGear(DOWN_SPEED);
				robotOutput.intakeGear(-0.75);
			}
			break;
		}
	}
	
	public static void disable() //Run when robot is disabled.
	{
		robotOutput.pivotGear(0);
		robotOutput.fineAdjustGearMech(false);
		robotOutput.intakeGear(0);
		
		state = 0;
	}
}
