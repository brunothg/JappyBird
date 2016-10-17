package com.github.brunothg.jappybird.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.github.brunothg.stream.autokey.AutoKey;
import com.github.brunothg.stream.manipulate.ManipulatorInputStream;
import com.github.brunothg.stream.manipulate.ManipulatorOutputStream;

public class Settings {

	private static final String SAVE_PATH = "./data/";
	private static final String SAVE_FILE = SAVE_PATH + "settings.ak";

	private static Properties props;

	public Settings() {

		props = null;
	}

	private static synchronized void load() {

		props = new Properties();
		try {
			props.loadFromXML(
					new ManipulatorInputStream(new FileInputStream(SAVE_FILE),
							new AutoKey(Settings.class.getSimpleName(),
									AutoKey.MODUS_ENCODE)));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Properties getSettings() {

		if (props == null) {
			load();
		}

		return props;
	}

	public static synchronized void set(String var, String val) {

		getSettings().setProperty(var, val);
	}

	public static synchronized void save()
			throws FileNotFoundException, IOException {

		File path = new File(SAVE_PATH);
		if (!path.exists()) {

			path.mkdirs();
		}

		props.storeToXML(
				new ManipulatorOutputStream(new FileOutputStream(SAVE_FILE),
						new AutoKey(Settings.class.getSimpleName(),
								AutoKey.MODUS_DECODE)),
				"AK - Encrypted Settings", "UTF-8");

	}

	public static String getValue(String var, String def) {

		String ret = getSettings().getProperty(var, def);

		return ret;
	}

	public static int getIntValue(String var, int def) {

		try {
			return Integer.valueOf(getValue(var, "" + def)).intValue();
		} catch (Exception e) {
			return def;
		}
	}

	public static long getLongValue(String var, long def) {

		try {
			return Long.valueOf(getValue(var, "" + def)).longValue();
		} catch (Exception e) {
			return def;
		}
	}

	public static double getDoubleValue(String var, double def) {

		try {
			return Double.valueOf(getValue(var, "" + def)).doubleValue();
		} catch (Exception e) {
			return def;
		}
	}

	public static boolean getBooleanValue(String var, boolean def) {

		try {
			return Boolean.valueOf(getValue(var, "" + def)).booleanValue();
		} catch (Exception e) {
			return def;
		}
	}
}
