/*
 * blog/javaclue/jcexchanger/AppProperties.java
 * 
 * Copyright (C) 2010 JackW
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this library.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package blog.javaclue.jcexchanger;

import java.io.IOException;
import java.util.Properties;

public class AppProperties {

	private static Properties appProps = null;

	private AppProperties() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try {
			appProps = new Properties();
			appProps.load(loader.getResourceAsStream("jcexchanger.properties"));
		}
		catch (IOException e) {
			throw new RuntimeException("Failed to load properties file: " + e);
		}
	}
	
	private static void loadProperties() {
		if (appProps == null) {
			new AppProperties();
		}
	}
	
	public static boolean useLowValueAsNull() {
		loadProperties();
		return !(useNullIndicator());
	}

	public static boolean useNullIndicator() {
		loadProperties();
		return ("yes".equalsIgnoreCase(appProps.getProperty("use_null_indicator")));
	}

	public static void setUseNullIndicator(boolean useNullIndicator) {
		loadProperties();
		appProps.setProperty("use_null_indicator", useNullIndicator?"yes":"no");
	}

	public static void main(String[] args) {
		System.out.println("useNullIndicator : " + useNullIndicator());
		System.out.println("useLowValueAsNull: " + useLowValueAsNull());
	}
}
