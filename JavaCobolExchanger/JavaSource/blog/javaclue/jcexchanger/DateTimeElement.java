/*
 * blog/javaclue/jcexchanger/DateTimeElement.java
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DateTimeElement extends BaseElement {
	private static final long serialVersionUID = -8766990505632056923L;
	protected static Logger logger = LogManager.getLogger(DateTimeElement.class);
	protected static boolean isDebugEnabled = logger.isDebugEnabled();

	private Date value = null;
	private final SimpleDateFormat dateFormatter;

	/**
	 * Construct a DateTimeElement instance.
	 * 
	 * @param name
	 *            - element name.
	 * @param format
	 *            - date time format, must be a valid Date Time pattern.
	 * @throws IllegalArgumentException
	 *             - if the format received is invalid.
	 */
	public DateTimeElement(String name, String format) {
		super(name, format != null ? format.length() : 1);
		if (format == null) {
			throw new IllegalArgumentException("Invalid Date Time format received: " + format);
		}
		try {
			dateFormatter = new SimpleDateFormat(format);
			dateFormatter.setTimeZone(TimeZone.getDefault());
		} 
		catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid format \"" + format + "\" received by element - " + getName(), e);
		}
	}

	public Date getValue() {
		return this.value;
	}

	public void setValue(Date value) {
		this.value = value;
	}

	public String getFormattedString() {
		if (value==null) {
			return getBlanks(length);
		}
		else {
			return dateFormatter.format(value);
		}
	}

	/**
	 * return as formatted Date string
	 */
	public String toString() {
		if (value==null) {
			return null;
		}
		else {
			return dateFormatter.format(value);
		}
	}

	/* 
	 * Set the value of this instance from a string input.
	 * @param byte array 
	 */
	void setValue(byte[] bytes) throws NumberFormatException {
		if (isLowValue(bytes)) {
			value = null;
		} else {
			String tmpValue = new String(bytes).trim();
			try {
				if (tmpValue.length() > 0) {
					value = dateFormatter.parse(tmpValue);
				} else {
					value = null;
				}
			} catch (ParseException e) {
				throw new NumberFormatException("Invalid DateTime \"" + tmpValue + "\" received by element - " + getName());
			}
		}
	}

	public static void main(String[] args) {
		DateTimeElement elem = new DateTimeElement("dateTimeField", "yyyyMMddHHmmss");
		String date = "20070325091005";
		elem.setValue(date.getBytes());
		System.out.println("Date Time: " + elem.getValue());
		System.out.println("Formatted output: "+elem.getFormattedString());
		
		elem = new DateTimeElement("dateTimeField", "yyyy-MM-dd");
		date = "2009-10-10";
		elem.setValue(date);
		System.out.println("Date Time: " + elem.getValue());
		System.out.println("Formatted output: "+elem.getFormattedString());
	}
}
