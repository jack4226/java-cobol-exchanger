/*
 * blog/javaclue/jcexchanger/IntegerElement.java
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

import java.text.DecimalFormat;

public class IntegerElement extends BaseElement {
	private static final long serialVersionUID = -5526617719473521915L;

	private Integer value;
	private final DecimalFormat decimalFormat;

	/**
	 * Construct an unsigned IntegerElement instance.
	 * 
	 * @param name
	 *            - element name.
	 * @param length
	 *            - element length.
	 */
	public IntegerElement(String name, int length) {
		this(name, length, false);
	}

	/**
	 * Construct an IntegerElement instance.
	 * 
	 * @param name
	 *            - element name.
	 * @param length
	 *            - element length.
	 * @param signed
	 *            - if true, a sign will be inserted to the beginning of the
	 *            element and the length of the element will increase by 1.
	 */
	public IntegerElement(String name, int length, boolean signed) {
		super(name, length + (signed ? 1 : 0));
		String format = getZeros(length);
		if (signed) {
			format = "+" + format + ";-" + format;
		}
		decimalFormat = new DecimalFormat(format);
		setMaximumDigits(decimalFormat);
	}

	public Integer getValue() {
		return this.value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getFormattedString() {
		if (value==null || toString().trim().length()==0) 
			return getBlanks(length);
		else
			return decimalFormat.format(value.intValue());
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
					if (tmpValue.length() > length) {
						logger.warn("Input " + tmpValue + " is longer than element size: " + length
								+ ", return from getFormattedString() will be truncated.");
					}
					value = Integer.valueOf(tmpValue);
				}
				else {
					value = null;
				}
			}
			catch (NumberFormatException e) {
				throw new NumberFormatException("Invalid integer \"" + tmpValue + "\" received by element - " + getName());
			}
		}
	}

	public static void main(String[] args) {
		try {
			IntegerElement elem = new IntegerElement("amount1", 8);
			elem.setValue("1234567");
			System.out.println("value = " + elem.getValue() + ", <" + elem.getFormattedString() + ">");
			elem.setValue(" ".getBytes());
			System.out.println("value = " + elem.getValue() + ", <" + elem.getFormattedString() + ">");
			elem = new IntegerElement("amount1", 8, true);
			elem.setValue("1234567");
			System.out.println("value = " + elem.getValue() + ", <" + elem.getFormattedString() + ">");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
