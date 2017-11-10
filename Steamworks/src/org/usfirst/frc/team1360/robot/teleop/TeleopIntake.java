package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public class TeleopIntake implements TeleopComponent {

	private static TeleopIntake instance;
	private RobotOutput robotOutput;
	private HumanInput humanInput;
	
	public static TeleopIntake getInstance() //Get the current instance of TeleopIntake. If none exists, make one.
	{
		if (instance == null)
		{
			instance = new TeleopIntake();
		}
		
		return instance;
	}
	
	private TeleopIntake() //Define access to HumanInput and RobotOutput from TeleopIntake.
	{
		this.robotOutput = RobotOutput.getInstance();
		this.humanInput = HumanInput.getInstance();
	}
	
	@Override
	public void calculate() { //Run every tick to process data for TelopIntake.
		//double speed = this.humanInput.getIntake();
		//boolean pivot = this.humanInput.getOperatorPivotGear();
		
		boolean release = this.humanInput.getOperatorOutake();
		/*//boolean open = this.humanInput.getOperatorOpenIntake();
		
		if(Math.abs(speed) < 0.20)
		{
			speed = 0;
		}
		
		if(pivot)
		{
			speed = 0.1;
		}
		
		this.robotOutput.pivotGear(speed);*/
		this.robotOutput.outtake(release);
		//this.robotOutput.openItake(open);
	}

	@Override
	public void disable() { //Run when the robot is disabled.
		//this.robotOutput.pivotGear(0);
		this.robotOutput.outtake(false);
	}

}
