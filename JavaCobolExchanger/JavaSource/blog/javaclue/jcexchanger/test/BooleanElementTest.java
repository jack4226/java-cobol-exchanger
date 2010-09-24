/*
 * blog/javaclue/jcexchanger/test/BooleanElementTest.java
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

import blog.javaclue.jcexchanger.BooleanElement;
import junit.framework.TestCase;

public class BooleanElementTest extends TestCase {

	public void testBooleanElement() {
		BooleanElement elem = new BooleanElement("boolean");
		elem.setValue("Y");
		assertEquals(Boolean.valueOf(true), elem.getValue());
		assertEquals("boolean", elem.getName());
		assertEquals(1, elem.length());
		assertEquals("Y", elem.getFormattedString());
		// test cloning
		BooleanElement clone = (BooleanElement) elem.getClone();
		assertEquals(clone.getValue(), elem.getValue());
		assertEquals(clone.getName(), elem.getName());
		assertEquals(clone.length(), elem.length());
		assertEquals(clone.getFormattedString(), elem.getFormattedString());
		clone.setValue("N");
		assertEquals(Boolean.valueOf(false), clone.getValue());
		assertEquals(Boolean.valueOf(true), elem.getValue());
	}

	public void testBooleanElement2() {
		BooleanElement elem = new BooleanElement("boolean", "True", "False");
		elem.setValue("True");
		assertEquals(Boolean.valueOf(true), elem.getValue());
		assertEquals("boolean", elem.getName());
		assertEquals(5, elem.length());
		assertEquals("True ", elem.getFormattedString());
	}
}
