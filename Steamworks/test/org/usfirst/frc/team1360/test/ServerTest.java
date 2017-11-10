package org.usfirst.frc.team1360.test;

import java.io.IOException;
import java.util.ArrayList;

import org.usfirst.frc.team1360.auto.mode.AutonMode;
import org.usfirst.frc.team1360.auto.mode.DefaultMode;
import org.usfirst.frc.team1360.robot.teleop.DriverConfig;
import org.usfirst.frc.team1360.server.Connection;
import org.usfirst.frc.team1360.server.components.AutonSelectorComponent;
import org.usfirst.frc.team1360.server.components.DriverConfigSelectorComponent;

public class ServerTest {
	public static void main(String[] args) {
		try (Connection conn = new Connection(5801)) {
			/*ArrayList<ArrayList<AutonMode>> modes = new ArrayList<>();
			modes.add(new ArrayList<>());
			modes.get(0).add(new DefaultMode());
			modes.add(new ArrayList<>());
			modes.get(1).add(new DefaultMode());
			AutonSelectorComponent selector = new AutonSelectorComponent(modes);
			conn.addComponent(selector, 0);*/
			DriverConfigSelectorComponent selector = new DriverConfigSelectorComponent(c -> System.out.printf("Selected %s\n", c), DriverConfig.ARCADE);
			conn.addComponent(selector, 3);
			while (true)
				Thread.yield();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
