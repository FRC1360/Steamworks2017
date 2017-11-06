package org.usfirst.frc.team1360.robot.teleop;

import java.util.ArrayList;

public class TeleopControl {
	
	private ArrayList<TeleopComponent> components;
	private static TeleopControl instance;
	
	private TeleopControl()
	{
		this.components = new ArrayList<TeleopComponent>(); //Create an array with the current instances of Drive, Gear, Intake, Climber.
		this.components.add(TeleopDrive.getInstance());
		this.components.add(TeleopGear.getInstance());
		this.components.add(TeleopIntake.getInstance());
		this.components.add(TeleopClimber.getInstance());
	}
	
	public static TeleopControl getInstance() //Get the current instance of TeleopControl. If none exists, make one.
	{
		if (instance == null)
		{
			instance = new TeleopControl();
		}
		
		return instance;
	}
	
	public void runCycle() //Run every tick. Executes calculate for each component.
	{
		for (TeleopComponent t: this.components)
		{
			t.calculate();
		}
	}
	
	public void disable() //Run when robot is disabled. Executes disable for each component.
	{
		for (TeleopComponent t: this.components)
		{
			t.disable();
		}
	}
}
