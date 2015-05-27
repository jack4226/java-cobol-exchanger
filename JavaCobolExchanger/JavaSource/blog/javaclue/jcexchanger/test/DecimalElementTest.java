/*
 * blog/javaclue/test/jcexchanger/test/DecimalElementTest.java
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
package blog.javaclue.jcexchanger.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import blog.javaclue.jcexchanger.DecimalElement;

public class DecimalElementTest {

	@Test
	public void testDecimalElement() {
		DecimalElement elem = new DecimalElement("decimal", "000000000000000000.000");
		elem.setValue("123456789012345678.56");
		assertEquals(new BigDecimal("123456789012345678.56"), elem.getValue());
		assertEquals("decimal", elem.getName());
		assertEquals(22, elem.length());
		assertEquals("123456789012345678.560", elem.getFormattedString());
		// test cloning
		DecimalElement clone = (DecimalElement) elem.getClone();
		assertEquals(clone.getValue(), elem.getValue());
		assertEquals(clone.getName(), elem.getName());
		assertEquals(clone.length(), elem.length());
		assertEquals(clone.getFormattedString(), elem.getFormattedString());
		clone.setValue("567.123");
		clone.setName("decimal-clone");
		assertEquals(BigDecimal.valueOf(567.123), clone.getValue());
		assertEquals(new BigDecimal("123456789012345678.56"), elem.getValue());
		assertEquals("decimal-clone", clone.getName());
		assertEquals("decimal", elem.getName());
		// test truncation
		clone.setValue("567.12345");
		assertEquals("000000000000000567.123", clone.getFormattedString());
	}

	@Test
	public void testDecimalElementSigned() {
		DecimalElement elem = new DecimalElement("signed-decimal", "+00000000.0000;-00000000.0000");
		elem.setValue("-1234.567");
		assertEquals(BigDecimal.valueOf(-1234.567), elem.getValue());
		assertEquals("signed-decimal", elem.getName());
		assertEquals(14, elem.length());
		assertEquals("-00001234.5670", elem.getFormattedString());
	}

	@Test (expected=NumberFormatException.class)
	public void testException1() {
		DecimalElement elem = new DecimalElement("decimal", "000000000000.000");
		elem.setValue("abc123.45");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testException2() {
		try {
			new DecimalElement("signed-decimal", "+00000000.0;-00000000.0000");
		}
		catch (IllegalArgumentException e) {
			assertEquals("The length of positive pattern must equal to the length of negative pattern", e.getMessage());
			throw e;
		}
	}
}
