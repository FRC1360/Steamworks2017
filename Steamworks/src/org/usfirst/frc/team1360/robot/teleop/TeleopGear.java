package org.usfirst.frc.team1360.robot.teleop;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Predicate;

import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;

public class TeleopGear implements TeleopComponent{
	private static TeleopGear instance;
	private RobotOutput robotOutput;
	private HumanInput humanInput;
	private SensorInput sensorInput;
	private int state = 0;
	/*
	 * 0: down
	 * 1: pseudo-pid to go + stay up
	 * 2: release + go down
	 */
	
	
	private static double DOWN_SPEED = -0.2;
	private static double UP_SPEED = 0.35;
	private static double HOLD_SPEED = 0.1;
	
	//TODO these need to be set to something legit
	private static int UP_TARGET = 70; //middle of range
	private static int EPSILON = 4;
	private static int DOWN_TARGET = 10;
	
	private static int lastVal = 0;
	
	private static Queue<Integer> last3Loops = new LinkedList<>();
	
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
		this.sensorInput = SensorInput.getInstance();
	}
	
	@Override											
	public void calculate() //Run every tick to process data for TeleopGear.
	{
		boolean fine = humanInput.getOperatorFineAdjustGear();
		boolean pivot = humanInput.getOperatorPivotBtn();
		double speed = humanInput.getOperatorEatGear();
		int position = sensorInput.getPivotEncoder();
		
		
		switch(state)
		{
		case 0:
			if (pivot)
			{
				state = 1;
			}
			sensorInput.resetPivotEncoder();
			//robotOutput.pivotGear(DOWN_SPEED);
			break;
		case 1:
			// pseudo-pid
			
			if (!pivot)
			{
				state = 2;
				lastVal = position;
			}
			else if (Math.abs(position - UP_TARGET) < EPSILON)
			{
				robotOutput.pivotGear(HOLD_SPEED);
			}
			else
			{
				robotOutput.pivotGear(position > UP_TARGET ?  DOWN_SPEED : UP_SPEED);
			}
			break;
		case 2:
			// Go down to pass a certain value then cut power + outtake, change to state 0 when done	
			
			System.out.println(last3Loops);
			
			if(position < DOWN_TARGET || last3Loops.stream().allMatch(val -> val == position && val != lastVal))
			{
				robotOutput.intakeGear(0);
				state = 0;
			}
			else
			{
				last3Loops.add(position); 
				if (last3Loops.size()>=3) {
					int oldPosition = last3Loops.poll();
				}
				
				robotOutput.pivotGear(DOWN_SPEED);
				robotOutput.intakeGear(-1);
			}
			break;
		}
		
		this.robotOutput.fineAdjustGearMech(fine);
		this.robotOutput.intakeGear(speed);

	}
	
	@Override
	public void disable() //Run when robot is disabled.
	{
		this.robotOutput.pivotGear(0);
		this.robotOutput.fineAdjustGearMech(false);
		this.robotOutput.intakeGear(0);
		
		this.state = 0;
	}

}
