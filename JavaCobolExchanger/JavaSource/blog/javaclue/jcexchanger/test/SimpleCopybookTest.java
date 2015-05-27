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

import junit.framework.TestCase;

import org.junit.Test;

import blog.javaclue.jcexchanger.sample.SimpleCopybook;

public class SimpleCopybookTest extends TestCase {

	String record = "ADDYUSA00012300199.992010-01-0120100405 20:45:12.234   ";
	
	@Test
	public void testSimpleCopybook() {
		SimpleCopybook copybook1 = new SimpleCopybook();
		copybook1.initialize();
		
		assertEquals(8, copybook1.size());
		assertEquals(55, copybook1.length());
		
		String cobolString = copybook1.exportToString();
		assertEquals(record, cobolString);
		
		SimpleCopybook copybook2 = new SimpleCopybook();
		copybook2.importFromString(record);
		assertEquals(record, copybook2.exportToString());
		assertEquals(copybook1.size(), copybook2.size());
		assertEquals(copybook1.length(), copybook2.length());
		
		String origValue = copybook1.getElement("type").getFormattedString();
		copybook1.getElement("type").setValue("DEL");
		assert(!record.equals(copybook1.exportToString()));
		copybook1.getElement("type").setValue(origValue);
		assert(record.equals(copybook1.exportToString()));
	}
}
