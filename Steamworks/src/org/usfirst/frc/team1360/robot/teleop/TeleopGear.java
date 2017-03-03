package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInput;	//import appropriate classes 
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public class TeleopGear implements TeleopComponent{
	private static TeleopGear instance;
	private RobotOutput robotOutput;
	private HumanInput humanInput;
	private boolean isOpen = false;
	private boolean lastShift = false;
	
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
		
		/*if(flap != lastShift)
		{
			robotOutput.flapGear(isOpen = !isOpen);
			lastShift = !isOpen;
		}*/
		
		this.robotOutput.flapGear(flap);
				
		this.robotOutput.releaseGear(release);

	}
	
	@Override
	public void disable()
	{
		this.robotOutput.releaseGear(false);
		this.robotOutput.flapGear(false);
	}

}
