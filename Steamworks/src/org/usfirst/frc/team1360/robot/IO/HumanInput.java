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
	
	
	//Operator Controls
	
	
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
	
}
