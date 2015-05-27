/*
 * blog/javaclue/jcexchanger/test/CobolDecimalElementTest.java
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

import blog.javaclue.jcexchanger.CobolDecimalElement;

public class CobolDecimalElementTest {

	@Test
	public void testCobolDecimalElement() {
		CobolDecimalElement elem = new CobolDecimalElement("decimal", 8, 2);
		elem.setValue("1234.56");
		assertEquals(BigDecimal.valueOf(1234.56), elem.getValue());
		assertEquals("decimal", elem.getName());
		assertEquals(8, elem.length());
		assertEquals("00123456", elem.getFormattedString());
		// test cloning
		CobolDecimalElement clone = (CobolDecimalElement) elem.getClone();
		assertEquals(clone.getValue(), elem.getValue());
		assertEquals(clone.getName(), elem.getName());
		assertEquals(clone.length(), elem.length());
		assertEquals(clone.getFormattedString(), elem.getFormattedString());
		clone.setValue("567.12");
		clone.setName("decimal-clone");
		assertEquals(BigDecimal.valueOf(567.12), clone.getValue());
		assertEquals(BigDecimal.valueOf(1234.56), elem.getValue());
		assertEquals("decimal-clone", clone.getName());
		assertEquals("decimal", elem.getName());
	}

	@Test
	public void testCobolDecimalElementSigned() {
		CobolDecimalElement elem = new CobolDecimalElement("signed-decimal", 10, 3, true);
		elem.setValue("-1234.560");
		assertEquals(new BigDecimal("-1234.560"), elem.getValue());
		assertEquals("signed-decimal", elem.getName());
		assertEquals(11, elem.length());
		assertEquals("-0001234560", elem.getFormattedString());
	}
	
	@Test (expected=NumberFormatException.class)
	public void testException1() {
		CobolDecimalElement elem = new CobolDecimalElement("decimal", 8, 2);
		elem.setValue("not a number");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testException2() {
		new CobolDecimalElement("decimal", 8, -1);
	}
}
