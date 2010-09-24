/*
 * blog/javaclue/jcexchanger/test/ArrayElementTest.java
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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.junit.Test;

import junit.framework.TestCase;
import blog.javaclue.jcexchanger.ArrayElement;
import blog.javaclue.jcexchanger.BaseElement;
import blog.javaclue.jcexchanger.BooleanElement;
import blog.javaclue.jcexchanger.DateTimeElement;
import blog.javaclue.jcexchanger.DecimalElement;
import blog.javaclue.jcexchanger.IntegerElement;
import blog.javaclue.jcexchanger.StringElement;
import blog.javaclue.jcexchanger.StructElement;

public class ArrayElementTest extends TestCase {

	@Test
	public void testArrayElement() {
		String in1 = "String1  -1234321 12345              123.456Y20061010";
		String in2 = "String2  -2345321 23456              223.456Y20071010";
		String in3 = "String3  -3456321 34567              323.456Y20081010";
		String in4 = "String4  -4567321 45678              423.456Y20091010";
		String fm1 = "String1 -012343210000000000012345+00123.4560Y20061010";
		String fm3 = "String3 -034563210000000000034567+00323.4560Y20081010";
		BaseElement elems[] = {
				new StringElement("string", 8),
				new IntegerElement("int1", 4, true),
				new IntegerElement("int2", 4),
				new DecimalElement("long", "0000000000000000"),
				new DecimalElement("real", "+00000.0000;-00000.0000"),
				new BooleanElement("bool"),
				new DateTimeElement("date", "yyyyMMdd")
		};
		StructElement struct = new StructElement("struct", elems);
		ArrayElement elem = new ArrayElement("array", struct, 4);
		elem.setValue(in1+in2+in3+in4);
		
		assertEquals("array", elem.getName());
		assertEquals(in1.length() * 4, elem.length());
		assertEquals(fm1, elem.get(0).getValue());
		assertEquals(fm3, elem.get(2).getValue());
		StructElement item2 = (StructElement) elem.get(1);
		assertEquals("String2", item2.getElement("string").getValue());
		assertEquals(Integer.valueOf(-234), ((IntegerElement)item2.getElement("int1")).getValue());
		assertEquals(BigDecimal.valueOf(223.456), ((DecimalElement)item2.getElement("real")).getValue());
		assertEquals("Y", item2.getElement("bool").getFormattedString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		assertEquals("20071010", sdf.format(((DateTimeElement)item2.getElement("date")).getValue()));
		// test cloning
		ArrayElement arrayClone = (ArrayElement) elem.getClone();
		StructElement item1 = (StructElement) elem.get(0);
		StructElement clone = (StructElement) arrayClone.get(0);
		// make sure we got a good clone
		assertEquals(clone.getValue(), item1.getValue());
		assertEquals(clone.getName(), item1.getName());
		assertEquals(clone.length(), item1.length());
		assertEquals(clone.getFormattedString(), item1.getFormattedString());
		String cs1 = "StringC +888843210000000000012345-00999.9900Y20100808";
		// modify the clone
		clone.getElement("string").setValue("StringC");
		clone.getElement("int1").setValue("8888");
		clone.getElement("real").setValue("-999.99");
		clone.getElement("date").setValue("20100808");
		// verify the modification
		assertEquals(cs1, clone.getValue());
		// make sure no side effect to the original
		assertEquals(fm1, ((StructElement)elem.get(0)).getFormattedString());
	}
}
