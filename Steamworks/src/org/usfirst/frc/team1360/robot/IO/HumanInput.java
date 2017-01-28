package org.usfirst.frc.team1360.robot.IO;

import org.usfirst.frc.team1360.robot.util.XboxRemote;

public class HumanInput {
	
	private static HumanInput instance;
	private XboxRemote driver;
	private XboxRemote operator;
	private boolean autonIncreaseStepWasPressed = false;
	private boolean autonDecreaseStepWasPressed = false;
	
	private HumanInput()
	{
		this.driver = new XboxRemote(0);
		this.operator = new XboxRemote(1);
	}
	
	public static HumanInput getInstance()
	{
		if (instance == null)
		{
			instance = new HumanInput();
		}
		
		return instance;
	}
	
	//Driver Controls
	public double getDriveRight()
	{
		return driver.getRightTrigger();
	}
	
	public double getDriveLeft()
	{
		return driver.getLeftTrigger();
	}
	
	public double getTurn()
	{	
		return driver.getLeftXAxis();
	}
	


	//Operator Controls
	
	public double getIntake()
	{
		return operator.getRightTrigger();
	}
	
	public boolean getOperatorGearRelease()
	{
		return this.operator.getButtonLB();
	}
	
	public boolean getOperatorGearFlap()
	{
		return this.operator.getButtonRB();
	}
	
	public boolean getOverride()
	{
		return operator.getButtonA();
	}

	public double getClimb()
	{
		return operator.getLeftTrigger();
	}

	//Auto Controls
	 public boolean getAutonSetModeButton() 
	 {
		 return this.driver.getButtonA();
	 }
	    
	 public boolean getAutonSetDelayButton() 
	 {
		 return this.driver.getButtonB();
	 }
	    
	 public double getAutonSelectStick() 
	 {
		 return this.driver.getLeftYAxis();
	 }

	 public boolean getAutonStepIncrease() 
	 {
	    	// only returns true on rising edge
		boolean result = this.driver.getButtonRB() && !this.autonIncreaseStepWasPressed;
	    this.autonIncreaseStepWasPressed = this.driver.getButtonRB();
	    return result;
	    	
	}
	    
	public boolean getAutonStepDecrease() 
	{
	    	// only returns true on rising edge
	    boolean result = this.driver.getButtonLB() && !this.autonDecreaseStepWasPressed;
	    this.autonDecreaseStepWasPressed = this.driver.getButtonLB();
	    return result;
	}
	
	//media is the best job {do memes}
	//lol media suxs
}
