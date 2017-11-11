package org.usfirst.frc.team1360.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team1360.new_auto.AutonRoutine;
import org.usfirst.frc.team1360.server.util.Container;

public class AutonRoutine1 {
	private volatile boolean done;
	
	public class TestCommand extends AutonRoutine {
		public TestCommand() {
			super("Test", 1000);
		}

		@Override
		protected void runCore() throws InterruptedException {
			System.out.println("Start");
			Thread.sleep(500);
			System.out.println("End");
			done = true;
		}
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public final void testRunUntilFinish() throws InterruptedException {
		done = false;
		new TestCommand().runUntilFinish();
		assertTrue(done);
	}

	@Test
	public final void testRunNow() throws InterruptedException {
		done = false;
		TestCommand command = new TestCommand();
		command.runNow("testRunNow");
		assertTrue(command.isAlive());
		command.join();
		assertTrue(done);
		assertFalse(command.isAlive());
	}

	@Test
	public final void testRunAfter() throws InterruptedException {
		done = false;
		TestCommand command1 = new TestCommand();
		command1.runNow("testRunAfter1");
		Container<Boolean> notifier = new Container<>(false);
		AutonRoutine command2 = new AutonRoutine("Test2", 0) {
			@Override
			protected void runCore() throws InterruptedException {
				Thread.sleep(100);
				notifier.put(true);
			}
		};
		command2.runAfter("testRunAfter1", "testRunAfter2");
		command1.join();
		assertTrue(done);
		assertFalse(command1.isAlive());
		Thread.yield();
		assertTrue(command2.isAlive());
		assertFalse(notifier.get());
		command2.join();
		assertTrue(notifier.get());
	}

	@Test
	public final void testKill() {
		done = false;
		TestCommand command = new TestCommand();
		command.runNow("testKill");
		Thread.yield();
		assertFalse(done);
		assertTrue(command.isAlive());
		command.kill();
		assertFalse(done);
		assertFalse(command.isAlive());
	}

	@Test
	public final void testWaitFor() throws InterruptedException {
		done = false;
		TestCommand command = new TestCommand();
		command.runNow("testWaitFor");
		AutonRoutine.waitFor("testWaitFor", 0);
		assertTrue(done);
		assertFalse(command.isAlive());
	}

}
