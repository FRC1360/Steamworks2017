package org.usfirst.frc.team1360.position;

public final class Position implements Cloneable {
	private double x; // X-coordinate (right from center of driver station = positive)
	private double y; // Y-coordinate (forward from driver station wall = positive)
	private double a; // Angle (clockwise from forward = positive)
	
	public Position(double x, double y, double a) {
		this.x = x;
		this.y = y;
		this.a = a;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
