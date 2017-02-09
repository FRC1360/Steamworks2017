package org.usfirst.frc.team1360.test;

import java.io.IOException;
import java.util.ArrayList;

import org.usfirst.frc.team1360.auto.mode.AutonMode;
import org.usfirst.frc.team1360.auto.mode.DefaultMode;
import org.usfirst.frc.team1360.server.Connection;
import org.usfirst.frc.team1360.server.components.AutonSelectorComponent;

public class ServerTest {
	public static void main(String[] args) {
		try (Connection conn = new Connection(5800)) {
			ArrayList<ArrayList<AutonMode>> modes = new ArrayList<>();
			modes.add(new ArrayList<>());
			modes.get(0).add(new DefaultMode());
			modes.add(new ArrayList<>());
			modes.get(1).add(new DefaultMode());
			AutonSelectorComponent selector = new AutonSelectorComponent(modes);
			conn.addComponent(selector, 0);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
