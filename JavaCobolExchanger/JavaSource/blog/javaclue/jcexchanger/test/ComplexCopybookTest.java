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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import blog.javaclue.jcexchanger.ArrayElement;
import blog.javaclue.jcexchanger.StructElement;
import blog.javaclue.jcexchanger.sample.ComplexCopybook;

public class ComplexCopybookTest {

	String record = "ADDYUSA00012300199.992010-01-0120100405 20:45:12.234-099912345678901234567890-01234.567801234578Yes111 222 333 444 555    ";
	
	@Test
	public void testComplexCopybook() {
		ComplexCopybook copybook1 = new ComplexCopybook();
		copybook1.initialize();
		
		assertEquals(10, copybook1.size());
		assertEquals(122, copybook1.length());
		
		String cobolString = copybook1.exportToString();
		assertEquals(record, cobolString);
		
		ComplexCopybook copybook2 = new ComplexCopybook();
		copybook2.importFromString(record);
		assertEquals(record, copybook2.exportToString());
		assertEquals(copybook1.size(), copybook2.size());
		assertEquals(copybook1.length(), copybook2.length());
		
		String origValue = copybook1.getElement("type").getFormattedString();
		copybook1.getElement("type").setValue("DEL");
		assert(!record.equals(copybook1.exportToString()));
		copybook1.getElement("type").setValue(origValue);
		assert(record.equals(copybook1.exportToString()));
		
		String structStringValue = "-099912345678901234567890-01234.567801234578Yes";
		String arrayStringValue = "111 222 333 444 555 ";
		
		StructElement struct = (StructElement) copybook1.getElement("struct");
		assert(structStringValue.equals(struct.getFormattedString()));
		struct.getElement("boolean").setValue("No");
		assert(!structStringValue.equals(struct.getFormattedString()));
		
		ArrayElement array = (ArrayElement) copybook1.getElement("array");
		assert(arrayStringValue.equals(array.getFormattedString()));
		array.get(1).setValue("-" + array.get(1));
		assert(!arrayStringValue.equals(array.getFormattedString()));
	}
}
