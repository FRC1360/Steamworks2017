package org.usfirst.frc.team1360.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.usfirst.frc.team1360.new_auto.AutonControl;
import org.usfirst.frc.team1360.new_auto.AutonRoutine;

public class AutonControl1 {
	static boolean started = false;

	@Test
	public final void testRun() {
		if (!started)
			testStart();
		Thread thread = AutonControl.run(() -> Thread.sleep(100));
		assertTrue(thread.isAlive());
		assertTrue(AutonControl.autoThreads.contains(thread));
		testStop();
		assertFalse(thread.isAlive());
	}

	@Test
	public final void testStart() {
		AutonControl.routines.clear();
		AutonRoutine routine = new AutonRoutine("Test Routine", 0) {
			@Override
			protected void runCore() throws InterruptedException {
				System.out.println("Test");
			}
		};
		AutonControl.routines.add(routine);
		AutonControl.start();
		assertTrue(routine.isAlive());
		started = true;
	}

	@Test
	public final void testStop() {
		if (!started)
			testStart();
		AutonControl.stop();
		for (Thread thread : AutonControl.autoThreads)
			assertFalse(thread.isAlive());
	}

}
