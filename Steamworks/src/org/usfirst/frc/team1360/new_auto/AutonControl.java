package org.usfirst.frc.team1360.new_auto;

import java.util.ArrayList;

import org.usfirst.frc.team1360.new_auto.routines.DriveToBaseline;
import org.usfirst.frc.team1360.robot.IO.HumanInput;

public class AutonControl {
	private static HumanInput humanInput = HumanInput.getInstance();
	private static ArrayList<AutonRoutine> routines = new ArrayList<>();
	private static int selectedIndex = 0;
	private static boolean lastInc = false;
	private static boolean lastDec = false;
	
	private static ArrayList<Thread> autoThreads = new ArrayList<>();
	
	public AutonControl() {
		routines.add(new DriveToBaseline());
	}
	
	public static void select()
	{
		boolean inc = humanInput.getAutoInc();
		boolean dec = humanInput.getAutoDec();
		
		if (inc && !lastInc && selectedIndex < routines.size() - 1)
		{
			selectedIndex++;
			System.out.println("Selected: " + routines.get(selectedIndex).toString());
		}
		
		if (dec && !lastDec && selectedIndex > 0)
		{
			selectedIndex--;
			System.out.println("Selected: " + routines.get(selectedIndex).toString());
		}
		
		lastInc = inc;
		lastDec = dec;
	}
	
	public static void registerThread(Thread thread)
	{
		autoThreads.add(thread);
	}
	
	public static Thread run(AutonRunnable runnable)
	{
		Thread t = new Thread(() ->
		{
			try {
				runnable.run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		autoThreads.add(t);
		t.start();
		return t;
	}
	
	public static void start()
	{
		routines.get(selectedIndex).start();
		autoThreads.add(routines.get(selectedIndex));
	}
	
	public static void stop()
	{
		autoThreads.forEach(Thread::interrupt);
	}
	
	public static interface AutonRunnable
	{
		void run() throws InterruptedException;
	}
}
