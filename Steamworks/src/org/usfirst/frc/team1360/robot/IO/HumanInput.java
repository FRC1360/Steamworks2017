package org.usfirst.frc.team1360.robot.IO;
import org.usfirst.frc.team1360.robot.util.LogitechExtremeJoystick;
/*****
 * Author: Tatiana Tomas Zahhar
 * Date: 31 Jan 2017 - added comments
 */
import org.usfirst.frc.team1360.robot.util.XboxRemote;

public class HumanInput {
	private static HumanInput instance;

	// Joysticks
	private XboxRemote driver;
	private XboxRemote operator;
	private LogitechExtremeJoystick driver1;
	private LogitechExtremeJoystick driver2;

	// Used for joystick-based autonomous selector
	private boolean autonIncreaseStepWasPressed = false;
	private boolean autonDecreaseStepWasPressed = false;
	
	private HumanInput()									//Constructor to initialize fields
	{
		this.driver = new XboxRemote(0);					//Driver Xbox on USB Port 0 on DS
		this.operator = new XboxRemote(1);					//Operator Xbox on USB Port 1 on DS
		this.driver1 = new LogitechExtremeJoystick(2);
		this.driver2 = new LogitechExtremeJoystick(3);
	}
	
	public static HumanInput getInstance()					//Return intance of HumanInput; create if it doesn't exist
	{
		if (instance == null)
		{
			instance = new HumanInput();
		}
		
		return instance;
	}
	
	//Driver Controls
	
	//---------Racing--------------
	public double getRacingThrottle()
	{
		return this.driver.getRightTrigger() - this.driver.getLeftTrigger();
	}
	
	public double getRacingTurn()
	{
		return this.driver.getLeftXAxis();
	}
	
	public boolean getRacingShifter()
	{
		return this.driver.getButtonA();
	}
	
	public boolean getRacingDampen()
	{
		return this.driver.getButtonX();
	}
	
	//------------Halo--------------
	public double getHaloThrottle()
	{
		return this.driver.getLeftYAxis();
	}
	
	public double getHaloTurn()
	{
		return this.driver.getRightXAxis();
	}
	
	public boolean getHaloShifter()
	{
		return this.driver.getButtonRB();
	}
	
	//-----------Single-Stick Arcade------------
	public double getArcadeThrottle()
	{
		return this.driver.getLeftYAxis();
	}
	
	public double getArcadeTurn()
	{
		return this.driver.getLeftXAxis();
	}
	
	public boolean getArcadeShifter()
	{
		return this.driver.getButtonA();
	}
	
	//----------Tank Controls---------
	public double getTankLeft()
	{
		return this.driver.getLeftYAxis();
	}
	
	public double getTankRight()
	{
		return this.driver.getRightYAxis();
	}
	
	public boolean getTankShifter()
	{
		return this.driver.getButtonRB();
	}
	
	public double getLeftJoystickThrottle()
	{
		return -driver1.getY();
	}
	
	public double getRightJoystickThrottle()
	{
		return -driver2.getY();
	}
	
	public boolean getJoystickShift()
	{
		return driver1.getMainTrigger() || driver2.getMainTrigger();
	}
	


	//Operator Controls
	

	public double getIntake()								
	{
		return this.operator.getLeftYAxis(); //Read the value of the Right Trigger on the Operator Controller for Intake
	}
	
	/*public boolean getOperatorGearRelease()
	{
		return this.operator.getButtonA();				//Read the value of the Left Back Button on the Operator Controller
	}*/
	
	/*public boolean getOperatorGearFlap()
	{
		return this.operator.getButtonB();					//Read the value of the Right Back Button on the Operator Controller
	}*/
	
	public boolean getOperatorPivotGear()
	{
		return this.operator.getButtonRB();
	}
	
	public boolean getOperatorFineAdjustGear()
	{
		return this.operator.getButtonLB();
	}
	
	public double getOperatorEatGear()
	{
		return this.operator.getRightYAxis();
	}
	
	public boolean getOperatorAutoClimb()
	{
		return this.operator.getButtonY();
	}
	
	public boolean getOperatorOutake()
	{
		return this.operator.getButtonX();
	}
	
	/*public boolean getOperatorOpenIntake()
	{
		return this.operator.getButtonLB();
	}*/
	
	public boolean getOverride()
	{
		return this.operator.getButtonBack() && this.operator.getButtonStart();						//Override the value of the A Button on the operator controller
	}

	public double getClimb()
	{
		return operator.getRightTrigger();					//Read the value of the Left Trigger on the Operator Controller for Climb
	}

	//Auto Selector Controls
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
