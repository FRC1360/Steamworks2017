package org.usfirst.frc.team1360.new_auto;

import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.usfirst.frc.team1360.new_auto.providers.SelectionProvider;
import org.usfirst.frc.team1360.new_auto.routines.DriveToBaseline;

public class AutonControl {
	private static SelectionProvider provider;
	public static ArrayList<AutonRoutine> routines = new ArrayList<>();
	private static int selectedIndex = 0;
	private static boolean lastInc = false;
	private static boolean lastDec = false;
	
	public static ArrayList<Thread> autoThreads = new ArrayList<>();
	public static ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
	
	static
	{
		routines.add(new DriveToBaseline());
	}
	
	public static void configure(SelectionProvider provider)
	{
		AutonControl.provider = provider;
	}
	
	public static void select()
	{
		boolean inc = provider.getAutoInc();
		boolean dec = provider.getAutoDec();
		
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
		registerThread(t);
		t.start();
		return t;
	}
	
	public static void start()
	{
		if (selectedIndex < routines.size())
		{
			routines.get(selectedIndex).runNow("");
		}
	}
	
	public static void stop()
	{
		autoThreads.forEach(Thread::interrupt);
		autoThreads.forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		autoThreads.clear();
		scheduler.shutdownNow();
	}
	
	public static void schedule(AutonRunnable runnable, long period)
	{
		scheduler.scheduleAtFixedRate(() -> {
			try {
				runnable.run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, 0, period, TimeUnit.MICROSECONDS);
	}
	
	public static interface AutonRunnable
	{
		void run() throws InterruptedException;
	}
}
