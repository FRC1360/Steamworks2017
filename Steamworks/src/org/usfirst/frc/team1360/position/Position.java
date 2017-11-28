package org.usfirst.frc.team1360.position;

public final class Position implements Cloneable {
	private double x; // X-coordinate (inches, right from center of driver station = positive)
	private double y; // Y-coordinate (inches, forward from driver station wall = positive)
	private double a; // Angle (radians, clockwise from forward = positive)
	
	public Position(double x, double y, double a) {
		this.x = x;
		this.y = y;
		this.a = a;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getA() {
		return a;
	}
	
	public void offset(double x, double y, double a) {
		this.x += x;
		this.y += y;
		this.a += a;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
