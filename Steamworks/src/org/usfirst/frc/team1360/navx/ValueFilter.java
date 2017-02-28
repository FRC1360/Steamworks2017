package org.usfirst.frc.team1360.navx;

import java.util.ArrayDeque;

public class ValueFilter {
	private int queue_size;
	private ArrayDeque<Double> values = new ArrayDeque<>();
	
	public ValueFilter(int queue_size)
	{
		this.queue_size = queue_size;
		for (int i = 0; i < queue_size; ++i)
			values.add(0.0);
	}
	
	public double caluclate(double value)
	{/*
		values.removeFirst();
		values.add(value);
		double mean = values.stream().mapToDouble(x -> x).sum() / queue_size;
		double std_dev_2 = 2 * Math.sqrt(values.stream().mapToDouble(x -> Math.pow(x - mean, 2)).sum() / queue_size);
		double diff = value - mean;
		return Math.abs(diff) < std_dev_2 ? value : (mean + Math.copySign(std_dev_2, diff));*/
		return value;
	}
}
