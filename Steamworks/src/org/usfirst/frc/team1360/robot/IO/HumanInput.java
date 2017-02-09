package org.usfirst.frc.team1360.robot.IO;
/*****
 * Author: Tatiana Tomas Zahhar
 * Date: 31 Jan 2017 - added comments
 */
import org.usfirst.frc.team1360.robot.util.XboxRemote;

public class HumanInput {
	
	private static HumanInput instance;						//Fields of class HumanInput
	private XboxRemote driver;
	private XboxRemote operator;
	private boolean autonIncreaseStepWasPressed = false;	//Confirm that Autonomous Period is done
	private boolean autonDecreaseStepWasPressed = false;
	
	private HumanInput()									//Constructor to initialize fields
	{
		this.driver = new XboxRemote(0);					//Driver Xbox on USB Port 0 on DS
		this.operator = new XboxRemote(1);					//Operator Xbox on USB Port 1 on DS			
	}
	
	public static HumanInput getInstance()					//Check to make sure that HumanInput exists
	{
		if (instance == null)
		{
			instance = new HumanInput();
		}
		
		return instance;
	}
	
	//Driver Controls
<<<<<<< HEAD
=======
	public double getDriveRight()							//Method in class HumanInput
	{
		return driver.getRightTrigger();					
	}
	
	public double getDriveLeft()
	{
		return driver.getLeftTrigger();
	}
	
	public double getTurn()
	{	
		return driver.getLeftXAxis();						//Get the value of the Axis						
	}
	
	public boolean getShiftSpeed()
	{
		return driver.getButtonA();							//Read the value of the A Button on the Driver Controller
	}
	


	//Operator Controls
	
<<<<<<< HEAD
=======
	public double getIntake()								
	{
		return operator.getRightTrigger();					//Read the value of the Right Trigger on the Operator Controller for Intake
	}
	
	public boolean getOperatorGearRelease()
	{
		return this.operator.getButtonLB();					//Read the value of the Left Back Button on the Operator Controller
	}
	
	public boolean getOperatorGearFlap()
	{
		return this.operator.getButtonRB();					//Read the value of the Right Back Button on the Operator Controller
	}
	
	public boolean getOverride()
	{
		return operator.getButtonA();						//Override the value of the A Button on the operator controller
	}

	public double getClimb()
	{
		return operator.getLeftTrigger();					//Read the value of the Left Trigger on the Operator Controller for Climb
	}

	//Auto Controls
	 public boolean getAutonSetModeButton() 
	 {
		 return this.driver.getButtonA();					//Button A is assigned as the Auto Mode Button 
	 }
	    
	 public boolean getAutonSetDelayButton() 
	 {
		 return this.driver.getButtonB();					//Button B is assigned as the Auto Delay Button
	 }
	    
	 public double getAutonSelectStick() 
	 {
		 return this.driver.getLeftYAxis();					//Left Y Axis is assigned as the Auto Selecting Stick
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
