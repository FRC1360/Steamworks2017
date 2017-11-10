package org.usfirst.frc.team1360.server.components;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.usfirst.frc.team1360.robot.teleop.DriverConfig;
import org.usfirst.frc.team1360.server.CommandComponentBase;
import org.usfirst.frc.team1360.server.util.IOUtils;

public class DriverConfigSelectorComponent extends CommandComponentBase {
	private Consumer<DriverConfig> updater;
	private DriverConfig defaultValue;
	
	public DriverConfigSelectorComponent(Consumer<DriverConfig> updater, DriverConfig defaultValue)
	{
		this.updater = updater;
		this.defaultValue = defaultValue;
	}
	
	@Override
	protected void onCommand(int id, byte[] data) throws Exception
	{
		switch (id)
		{
		case 0:
			DriverConfig[] options = DriverConfig.values();
			sendCommand(0, Stream.concat(Stream.concat(Stream.of(defaultValue.ordinal()), Stream.of(options.length)), Arrays.stream(options).map(DriverConfig::toString).map(IOUtils::String1360)).toArray());
			break;
		case 1:
			updater.accept(DriverConfig.values()[IOUtils.Int32Big(data, 0)]);
			break;
		}
	}
}
