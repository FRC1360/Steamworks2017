package org.usfirst.frc.team1360.robot.teleop;

<<<<<<< HEAD
public class TeleopGear {
=======
//David Kozma 22 Jan 2017
//Gear routine to put gear on air ship
//Class follows structure of TeleopDrive

import org.usfirst.frc.team1360.robot.IO.HumanInput;	//import appropriate classes 
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public class TeleopGear implements TeleopComponent{
	private static TeleopGear instance;
	private RobotOutput robotOutput;
	private HumanInput humanInput;
	private boolean isOpen = false;
	
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
		boolean flap = humanInput.getOperatorGearFlap();
		boolean release = humanInput.getOperatorGearRelease();
		
		if(flap && this.isOpen)
		{
			this.robotOutput.flapGear(false);
			isOpen = false;
		} 
		else if(flap && !this.isOpen)
		{
			this.robotOutput.flapGear(true);
			isOpen = true;
		}
		
		this.robotOutput.releaseGear(release);

	}
	
	@Override
	public void disable()
	{
		this.robotOutput.releaseGear(false);
		this.robotOutput.flapGear(false);
	}
	
>>>>>>> Auto

}
