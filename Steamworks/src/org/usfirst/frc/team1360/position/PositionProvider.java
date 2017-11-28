package org.usfirst.frc.team1360.position;

public interface PositionProvider {
	double getX();
	double getY();
	double getA();
	
	void reset(double x, double y, double a);
	
	default void reset() {
		reset(0, 0, 0);
	}
	
	default Position getPosition() {
		synchronized (this) {
			return new Position(getX(), getY(), getA());
		}
	}
}
