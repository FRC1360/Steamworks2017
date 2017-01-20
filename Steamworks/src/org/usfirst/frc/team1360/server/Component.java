package org.usfirst.frc.team1360.server;

import java.io.InputStream;
import java.io.OutputStream;

public interface Component {
	void initialize(InputStream i, OutputStream o);
}
