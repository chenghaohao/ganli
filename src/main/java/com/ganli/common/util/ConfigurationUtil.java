package com.ganli.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationUtil {

	private static Properties properties;

	static {
		load();
	}

	public ConfigurationUtil() {
		this("configure.properties");
	}

	public ConfigurationUtil(String fileName) {
		load(fileName);
	}

	private static void load(String fileName) {
		try {
			properties = new Properties();
			File f = new File(ConfigurationUtil.class.getResource("/")
					.getPath() + fileName);
			System.out.println("conf path:" + f.getAbsolutePath());
			FileInputStream fis = new FileInputStream(f);
			properties.load(fis);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void load() {
		load("configure.properties");
	}

	public static String getString(String name) {
		if (properties == null) {
			load();
		}
		return properties.getProperty(name);
	}

	public static String getString(String name, String defVal) {
		String val = getString(name);
		if (val == null) {
			return defVal;
		}
		return val;
	}

	public static int getInt(String name, int defVal) {
		String val = getString(name);
		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
			return defVal;
		}
	}

	public static long getLong(String name, long defVal) {
		String val = getString(name);
		try {
			return Long.parseLong(val);
		} catch (Exception e) {
			return defVal;
		}
	}
}
