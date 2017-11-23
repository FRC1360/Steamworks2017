package org.usfirst.frc.team1360.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.usfirst.frc.team1360.new_auto.AutonControl;
import org.usfirst.frc.team1360.new_auto.AutonRoutine;
import org.usfirst.frc.team1360.new_auto.drive.DrivePIDEncoder;
import org.usfirst.frc.team1360.new_auto.providers.RobotOutputProvider;
import org.usfirst.frc.team1360.new_auto.providers.SensorInputProvider;
import org.usfirst.frc.team1360.new_auto.util.AutonLog;

@RunWith(MockitoJUnitRunner.class)
public class FullNewAutoTest {
	@Mock
	private RobotOutputProvider robotOutput;
	
	@Mock
	private SensorInputProvider sensorInput;

	@Before
	public void setUp() throws Exception {
		AutonRoutine.configure(robotOutput, sensorInput);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws InterruptedException {
		//ArgumentCaptor<Double> left = ArgumentCaptor.forClass(Double.class);
		//ArgumentCaptor<Double> right = ArgumentCaptor.forClass(Double.class);
		
		//doNothing().when(robotOutput).setDriveLeft(left.capture());
		//doNothing().when(robotOutput).setDriveRight(right.capture());
		
		long start = System.currentTimeMillis();
		
		when(sensorInput.getAHRSYaw()).thenReturn(0.0);
		when(sensorInput.getRightDriveEncoder()).then(new Answer<Integer>() {

			@Override
			public Integer answer(InvocationOnMock invocation) throws Throwable {
				return (int)(System.currentTimeMillis() - start);
				//return 0;
			}
		});
		
		Object notifier = new Object();
		
		AutonRoutine routine = new AutonRoutine("Test", 0) {
			
			@Override
			protected void runCore() throws InterruptedException {
				new AutonLog("Auton start").runUntilFinish();
				new DrivePIDEncoder(2000, 0.0, 1.0, 500).runNow("drive");
				new AutonLog(() -> String.format("Done: %f %d", sensorInput.getAHRSYaw(), sensorInput.getRightDriveEncoder())).runAfter("drive", "log");
				waitFor("log", 0);
				synchronized (notifier) {
					notifier.notifyAll();
				}
			}
		};
		
		AutonControl.routines.clear();
		AutonControl.routines.add(routine);
		AutonControl.start();
		
		synchronized (notifier) {
			notifier.wait();
		}
		
		assertTrue(sensorInput.getRightDriveEncoder() >= 500);
	}

}
