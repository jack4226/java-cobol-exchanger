/*
 * blog/javaclue/test/jcexchanger/test/StringElementTest.java
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import blog.javaclue.jcexchanger.AppProperties;
import blog.javaclue.jcexchanger.StringElement;

public class StringElementTest {

	@Test
	public void testStringElement() {
		int strLen = 22;
		String strName = "string";
		StringElement elem = new StringElement(strName, strLen);
		assertEquals(strName, elem.getName());
		assertEquals(strLen, elem.length());
		elem.setValue("TestString");
		assertEquals("TestString", elem.getValue());
		assertEquals("TestString            ", elem.getFormattedString());
		// test cloning
		StringElement clone = (StringElement) elem.getClone();
		assertEquals(clone.getValue(), elem.getValue());
		assertEquals(clone.getName(), elem.getName());
		assertEquals(clone.length(), elem.length());
		assertEquals(clone.getFormattedString(), elem.getFormattedString());
		clone.setValue("CloneString");
		assertEquals("CloneString", clone.getValue());
		assertEquals("TestString", elem.getValue());
		// test null string with null indicator (default)
		AppProperties.setUseNullIndicator(true);
		elem = new StringElement(strName, strLen, true);
		assertEquals(strLen + 1, elem.length());
		elem.setValue("NI'm nut null");
		assertEquals("I'm nut null", elem.getValue());
		assertEquals("NI'm nut null          ", elem.getFormattedString());
		elem.setValue("YI'm null string");
		assertNull(elem.getValue());
		assertEquals("Y                      ", elem.getFormattedString());
		// test null string with low-value
		AppProperties.setUseNullIndicator(false);
		elem = new StringElement(strName, strLen, true);
		assertEquals(strLen, elem.length());
		elem.setValue("I'm nut null");
		assertEquals("I'm nut null", elem.getValue());
		assertEquals("I'm nut null          ", elem.getFormattedString());
		byte[] bytes = new byte[strLen];
		for (int i=0; i<strLen; i++) {
			bytes[i] = 0x00;
		}
		elem.setValue(bytes);
		assertNull(elem.getValue());
		assertTrue(Arrays.equals(bytes, elem.getFormattedBytes()));
	}
}
