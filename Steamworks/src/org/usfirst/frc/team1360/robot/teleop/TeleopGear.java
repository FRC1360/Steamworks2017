package org.usfirst.frc.team1360.robot.teleop;

//David Kozma 22 Jan 2017
//Gear routine to put gear on air ship
//Class follows structure of TeleopDrive

import org.usfirst.frc.team1360.robot.IO.HumanInput;	//import appropriate classes 
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public class TeleopGear implements TeleopComponent{
	private static TeleopGear instance;
	private RobotOutput robotOutput;
	private HumanInput humanInput;
	
	public static TeleopGear getInstance()		
	{
		if (instance == null)							//ensure only one TeleopGear
		{	
			instance = new TeleopGear();
		}
		return instance;
	}
	
	private TeleopGear()								//point local variables to external variables
	{
		this.humanInput= HumanInput.getInstance();   
		this.robotOutput = RobotOutput.getInstance();  	
	}
	
	@Override											
	public void calculate()								//output for this routine
	{
		//double speed = this.humanInput.getGear;		//TO ADD??? to HumanInput class
		double speed = 0.5;								//??? termporarly set speed to 0.5
		//this.robotOutput.gear(speed);					//method needs to be created in robot output
	}
	
	@Override
	public void disable()
	{
		//this.robotOutput.gear(0);						//TO ADD??? to RobotOutput class?
	}
	

}
