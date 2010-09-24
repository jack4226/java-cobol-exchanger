/*
 * blog/javaclue/jcexchanger/DecimalElement.java
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

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DecimalElement extends BaseElement {
	private static final long serialVersionUID = 3689086689070577137L;

	private BigDecimal value = null;
	private final DecimalFormat decimalFormat;

	/**
	 * Construct a DecimalElement instance.
	 * 
	 * @param name
	 *            - element name.
	 * @param format
	 *            - element format, must be a valid Decimal Format.
	 * @throws IllegalArgumentException
	 *            - when an invalid format is received.
	 */
	public DecimalElement(String name, String format) {
		super(name, getElementLengthByFormat(format));
		if (format == null) {
			throw new IllegalArgumentException("Invalid format: " + format);
		}
		decimalFormat = new DecimalFormat(format);
		setMaximumDigits(decimalFormat);
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getFormattedString() {
		if (value == null || toString().trim().length() == 0) {
			return getBlanks(length);
		}
		else {
			return decimalFormat.format(value);
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
		}
		else {
			String tmpValue = new String(bytes).trim();
			try {
				if (tmpValue.length() > 0) {
					value = new BigDecimal(tmpValue);
				}
				else {
					value = null;
				}
			}
			catch (NumberFormatException e) {
				throw new NumberFormatException("Invalid amount \"" + tmpValue
						+ "\" received by element - " + getName());
			}
		}
	}

	public static void main(String[] args) {
		try {
			DecimalElement elem = new DecimalElement("amount2", "0000000.00");
			System.out.println("length: " + elem.length());
			elem.setValue("234.56");
			System.out.println("value = " + elem.getValue() + ", <" + elem.getFormattedString() + ">");
			elem = new DecimalElement("amount2", "+0000000.00;-0000000.00");
			System.out.println("length: " + elem.length());
			elem.setValue("-234.56");
			System.out.println("value = " + elem.getValue() + ", <" + elem.getFormattedString() + ">");
			elem = new DecimalElement("long20", "00000000000000000000");
			elem.setValue("12345678901234567890");
			System.out.println("value = " + elem.getValue() + ", <" + elem.getFormattedString() + ">");
			elem = new DecimalElement("bigdecimal", "00000000000000000000.00000");
			elem.setValue("12345678901234567890.1234");
			System.out.println("value = " + elem.getValue() + ", <" + elem.getFormattedString() + ">");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
