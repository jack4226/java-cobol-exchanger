/*
 * blog/javaclue/jcexchanger/test/DateTimeElementTest.java
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

import java.text.SimpleDateFormat;
import junit.framework.TestCase;
import org.junit.Test;
import blog.javaclue.jcexchanger.DateTimeElement;

public class DateTimeElementTest extends TestCase {

	@Test
	public void testDateTimeElement() {
		DateTimeElement elem = new DateTimeElement("datetime", "yyyy-MM-dd");
		elem.setValue("2010-09-11");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		assertEquals("2010-09-11", sdf.format(elem.getValue()));
		assertEquals("datetime", elem.getName());
		assertEquals(10, elem.length());
		assertEquals("2010-09-11", elem.getFormattedString());
		// test cloning
		DateTimeElement clone = (DateTimeElement) elem.getClone();
		assertEquals(clone.getValue(), elem.getValue());
		assertEquals(clone.getName(), elem.getName());
		assertEquals(clone.length(), elem.length());
		assertEquals(clone.getFormattedString(), elem.getFormattedString());
		clone.setValue("2000-01-01");
		assertEquals("2000-01-01", sdf.format(clone.getValue()));
		assertEquals("2010-09-11", sdf.format(elem.getValue()));
	}

//	@Test(expected=java.lang.NumberFormatException.class)
//	public void testException() {
//		DateTimeElement elem = new DateTimeElement("datetime", "yyyy-MM-dd");
//		elem.setValue("2010-11-aa");
//	}
}
