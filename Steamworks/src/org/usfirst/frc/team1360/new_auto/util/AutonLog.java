package org.usfirst.frc.team1360.new_auto.util;

import java.util.function.Supplier;

import org.usfirst.frc.team1360.new_auto.AutonControl;
import org.usfirst.frc.team1360.new_auto.AutonRoutine;

public class AutonLog extends AutonRoutine {
	Supplier<String> supplier;

	public AutonLog(Supplier<String> supplier) {
		super(null, 0);
		this.supplier = supplier;
	}
	
	public AutonLog(String text) {
		this(() -> text);
	}

	@Override
	protected void runCore() throws InterruptedException {
		System.out.printf("Auton @ %d ms: %s\n", AutonControl.getRunTime(), supplier.get());
	}
}
