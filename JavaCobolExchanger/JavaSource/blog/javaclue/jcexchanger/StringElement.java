/*
 * blog/javaclue/jcexchanger/StringElement.java
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

import org.apache.commons.lang.StringUtils;

public class StringElement extends BaseElement {
	private static final long serialVersionUID = -2448191264843717300L;
	private String value = null;
	private final boolean trim;

	/**
	 * construct a StringElement.
	 * 
	 * @param name
	 *            - the name of the element.
	 * @param length
	 *            - element length
	 */
	public StringElement(String name, int length) {
		this(name, length, false, true);
	}

	/**
	 * construct a StringElement.
	 * 
	 * @param name
	 *            - the name of the element.
	 * @param length
	 *            - element length, if isNullable is true, actual element length
	 *            will increase by 1.
	 * @param isNullable
	 *            - if true, an extra character is inserted to the beginning of
	 *            the element serving as null indicator.
	 */
	public StringElement(String name, int length, boolean isNullable) {
		this(name, length, isNullable, true);
	}

	/**
	 * construct a StringElement.
	 * 
	 * @param name
	 *            - the name of the element.
	 * @param length
	 *            - element length, if isNullable is true, actual element length
	 *            will increase by 1.
	 * @param isNullable
	 *            - if true, an extra character is inserted to the beginning of
	 *            the element serving as null indicator.
	 * @param trim
	 *            - true to trim the string when the value is set. default to
	 *            true.
	 */
	public StringElement(String name, int length, boolean isNullable, boolean trim) {
		super(name, length, isNullable);
		this.trim = trim;
		if (!isNullable) value = "";
	}

	public String getFormattedString() {
		return new String(getFormattedBytes());
	}

	public byte[] getFormattedBytes() {
		if (value == null) {
			if (isNullable) {
				if (AppProperties.useNullIndicator()) {
					return ("Y" + getBlanks(length - 1)).getBytes();
				}
				else {
					return getLowValues(length);
				}
			}
			else {
				return getBlanks(length).getBytes();
			}
		}
		else {
			String rtn_str = toString();
			if (isNullable && AppProperties.useNullIndicator()) {
				rtn_str = "N" + rtn_str;
			}
			return StringUtils.rightPad(StringUtils.left(rtn_str, length), length, ' ').getBytes();
		}
	}

	public String toString() {
		if (value==null) {
			return null;
		}
		else {
			return value.toString();
		}
	}

	public String getValue() {
		return this.value;
	}

	/*
	 * @param byte array
	 */
	public void setValue(byte[] bytes) {
		if (isLowValue(bytes)) {
			value = null;
		}
		else {
			String field = new String(bytes);
			if (isNullable && field.length() > 0) {
				if (AppProperties.useNullIndicator()) { // use null indicator
					if ("Y".equalsIgnoreCase(field.substring(0,1))) {
						value = null;
					}
					else {
						value = field.substring(1);
					}
				}
				else {
					value = field;
				}
			}
			else {
				value = field;
			}
			if (trim == true && value != null) {
				value = value.trim();
			}
		}
		if (value != null && value.length() > length) {
			logger.warn("Input \"" + value + "\" exceeds the element size of " + length
					+ ", return from getFormattedString() will be truncated.");
		}
	}

	public static void main(String[] args) {
		StringElement elem = new StringElement("string", 25);
		byte[] bytes = {0,0,0,0,0,0,0,0};
		elem.setValue(bytes);
		System.out.println("LowValue? " + elem.isLowValue(bytes) + ", value: " + elem);
		elem.setValue("I'm a String Element");
		System.out.println(elem.getName() + ".toString() :<" + elem + ">");
		System.out.println(elem.getName() + ".formatted  :<" + elem.getFormattedString() + ">");
		// test null string
		elem = new StringElement("string", 25, true);
		bytes = "Y              ".getBytes();
		elem.setValue(bytes);
		System.out.println("LowValue? " + elem.isLowValue(bytes) + ", value: " + elem);
		elem.setValue("NI'm a String Element");
		System.out.println(elem.getName() + ".toString() :<" + elem + ">");
		System.out.println(elem.getName() + ".formatted  :<" + elem.getFormattedString() + ">");
	}
}