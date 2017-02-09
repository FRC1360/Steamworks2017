package org.usfirst.frc.team1360.robot.teleop;

import java.util.ArrayList;

public class TeleopControl {
	
	private ArrayList<TeleopComponent> components;
	private static TeleopControl instance;
	
	private TeleopControl()
	{
		this.components = new ArrayList<TeleopComponent>();
		this.components.add(TeleopDrive.getInstance());
		this.components.add(TeleopGear.getInstance());
		this.components.add(TeleopIntake.getInstance());
		this.components.add(TeleopClimber.getInstance());
	}
	
	public static TeleopControl getInstance()
	{
		if (instance == null)
		{
			instance = new TeleopControl();
		}
		
		return instance;
	}
	
	public void runCycle()
	{
		for (TeleopComponent t: this.components)
		{
			t.calculate();
		}
	}
	
	public void disable()
	{
		for (TeleopComponent t: this.components)
		{
			t.disable();
		}
	}
}
