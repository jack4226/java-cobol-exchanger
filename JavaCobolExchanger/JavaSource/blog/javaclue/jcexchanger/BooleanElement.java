/*
 * blog/javaclue/jcexchanger/BooleanElement.java
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

public class BooleanElement extends BaseElement {
	private static final long serialVersionUID = -3556002501200171348L;

	private Boolean value = null;
	private final String trueValue;
	private final String falseValue;
	
	/**
	 * Construct a BooleanElement instance. The element length is default to 1,
	 * "Y" will represent true, and "N" will represent false.
	 * 
	 * @param name
	 *            - field name.
	 */
	public BooleanElement(String name) {
		this(name, "Y", "N");
	}

	/**
	 * Construct a BooleanElement instance. The element length will be the
	 * longer one of the length of trueValue and the length of falseValue.
	 * 
	 * @param name
	 *            - element name.
	 * @param trueValue
	 *            - string representation when the element is true.
	 * @param falseValue
	 *            - string representation when the element is false.
	 */
	public BooleanElement(String name, String trueValue, String falseValue) {
		super(name, getFieldLength(trueValue, falseValue));
		if (trueValue == null || falseValue == null) {
			throw new IllegalArgumentException("Neither trueValue nor falseValue can be null.");
		}
		else if (StringUtils.equals(trueValue, falseValue)) {
			throw new IllegalArgumentException("trueValue and falseValue can not be same.");
		}
		this.trueValue = StringUtils.rightPad(trueValue, length, ' ');
		this.falseValue = StringUtils.rightPad(falseValue, length, ' ');
	}

	private static int getFieldLength(String trueValue, String falseValue) {
		if (trueValue != null && falseValue != null) {
			return Math.max(trueValue.length(), falseValue.length());
		}
		else {
			return 0;
		}
	}

	public Boolean getValue() {
		return this.value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}

	public String getFormattedString() {
		if (value == null || toString().trim().length() == 0) {
			return getBlanks(length);
		}
		else {
			return (value.booleanValue() == true ? trueValue : falseValue);
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

	/*
	 * @param byte array
	 */
	void setValue(byte[] bytes) throws NumberFormatException {
		if (isLowValue(bytes)) {
			value = null;
		} else {
			String tmpValue = new String(bytes).trim();
			if (tmpValue.length() > 0) {
				if (trueValue.trim().equalsIgnoreCase(tmpValue)) {
					value = Boolean.valueOf(true);
				}
				else {
					value = Boolean.valueOf(false);
				}
			}
			else {
				value = null;
			}
		}
	}

	public static void main(String[] args) {
		BooleanElement elem = new BooleanElement("bool", "YES", "NO");
		elem.setValue("YES".getBytes());
		System.out.println("Boolean value: ["+elem.getFormattedString()+"]");
		elem = new BooleanElement("bool", "true", "false");
		elem.setValue("true".getBytes());
		System.out.println("Boolean value: ["+elem.getFormattedString()+"]");
	}
}
