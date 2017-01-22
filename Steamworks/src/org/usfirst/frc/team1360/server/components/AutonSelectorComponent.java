package org.usfirst.frc.team1360.server.components;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.usfirst.frc.team1360.auto.mode.AutonMode;
import org.usfirst.frc.team1360.server.CommandComponentBase;
import org.usfirst.frc.team1360.server.util.IOUtils;

public class AutonSelectorComponent extends CommandComponentBase {
	private ArrayList<ArrayList<AutonMode>> modeOptions;
	private int[] selections;
	
	public AutonSelectorComponent(ArrayList<ArrayList<AutonMode>> modeOptions) {
		this.modeOptions = modeOptions;
		selections = new int[modeOptions.size()];
	}

	@Override
	protected void onCommand(int id, byte[] data) throws IOException {
		switch (id) {
		case 0:
			sendCommand(0, IOUtils.UInt16Big(modeOptions.size()));
			for (int i = 0; i < modeOptions.size(); ++i)
				for (int j = 0; j < modeOptions.get(i).size(); ++j)
					sendCommand(1, IOUtils.UInt16Big(i), IOUtils.UInt16Big(j), modeOptions.get(i).get(j).toString().getBytes());
			break;
		case 1:
			selections[IOUtils.UInt16Big(data, 0)] = IOUtils.UInt16Big(data, 1);
			break;
		case 2:
			for (AutonMode mode : getSelections())
				System.out.println(mode.toString());
		}
	}
	
	public AutonMode[] getSelections() {
		AutonMode[] r = new AutonMode[selections.length];
		for (int i = 0; i < r.length; ++i)
			r[i] = modeOptions.get(i).get(selections[i]);
		return r;
	}
}
