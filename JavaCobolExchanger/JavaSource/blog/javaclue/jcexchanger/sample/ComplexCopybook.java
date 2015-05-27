/*
 * blog/javaclue/jcexchanger/sample/ComplexCopybook.java
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
package blog.javaclue.jcexchanger.sample;

import blog.javaclue.jcexchanger.ArrayElement;
import blog.javaclue.jcexchanger.BaseElement;
import blog.javaclue.jcexchanger.BooleanElement;
import blog.javaclue.jcexchanger.CobolDecimalElement;
import blog.javaclue.jcexchanger.DateTimeElement;
import blog.javaclue.jcexchanger.DecimalElement;
import blog.javaclue.jcexchanger.ExchangeRecord;
import blog.javaclue.jcexchanger.IntegerElement;
import blog.javaclue.jcexchanger.StringElement;
import blog.javaclue.jcexchanger.StructElement;

public class ComplexCopybook extends ExchangeRecord {

	public ComplexCopybook() {
		// define a structure element
		BaseElement elems[] = {
				new IntegerElement("int-signed", 4, true),
				new DecimalElement("long20", "00000000000000000000"),
				new DecimalElement("decimal-signed", "+00000.0000;-00000.0000"),
				new CobolDecimalElement("decimal-cobol", 8, 2),
				new BooleanElement("boolean", "Yes", "No"),
		};
		StructElement struct = new StructElement("struct", elems);
		
		// define an array element
		ArrayElement array = new ArrayElement("array", new StringElement("item", 4), 5);
		
		// define the exchange record
		list.add(new StringElement("type", 3));
		list.add(new BooleanElement("flag"));
		list.add(new StringElement("countryCode", 3));
		list.add(new IntegerElement("sequence", 6));
		list.add(new DecimalElement("amount", "00000.00"));
		list.add(new DateTimeElement("beginDate", "yyyy-MM-dd"));
		list.add(new DateTimeElement("updateDateTime", "yyyyMMdd HH:mm:ss.SSS"));
		list.add(struct);
		list.add(array);
		list.add(new StringElement("filler", 3));
	}
	
	public void initialize() {
		// load the exchange record with data
		this.getElement("type").setValue("ADD");
		this.getElement("flag").setValue("Y");
		this.getElement("countryCode").setValue("USA");
		this.getElement("sequence").setValue("123");
		this.getElement("amount").setValue("199.99");
		this.getElement("beginDate").setValue("2010-01-01");
		this.getElement("updateDateTime").setValue("20100405 20:45:12.234");
		StructElement struct = (StructElement) this.getElement("struct");
		struct.getElement("int-signed").setValue("-999");
		struct.getElement("long20").setValue("12345678901234567890");
		struct.getElement("decimal-signed").setValue("-1234.5678");
		struct.getElement("decimal-cobol").setValue("12345.78");
		struct.getElement("boolean").setValue("Yes");
		ArrayElement array = (ArrayElement) this.getElement("array");
		for (int i = 1; i <= array.size(); i++) {
			array.get(i - 1).setValue(i + "" + i + "" + i);
		}
	}
	
	/*
	 * starts from java exchange record and export it to COBOL string
	 */
	void javaToCobol() {
		// create an exchange record
		ExchangeRecord bean = new ComplexCopybook();
		initialize();
		System.out.println("ComplexCopybook:" + LF + bean);
		System.out.println("Size  : " + bean.size());
		System.out.println("Length: " + bean.length());
		// export the exchange record to a COBOL text string
		String cobolString = bean.exportToString();
		System.out.println("bean1 - [" + cobolString + "]");
		// add your code here to send it to mainframe
	}
	
	/*
	 * starts from COBOL string and import it to java exchange record.
	 */
	void cobolToJava() {
		// a COBOL text string returned from mainframe
		String record = "ADDYUSA00012300199.992010-01-0120100405 20:45:12.234-099912345678901234567890-01234.567801234578Yes111 222 333 444 555    ";
		// create an exchange record
		ExchangeRecord bean = new ComplexCopybook();
		// import the COBOL text string string to the exchange record
		bean.importFromString(record);
		// print out the exchange record
		System.out.println("ComplexCopybook:" + LF + bean);
		System.out.println("Size  : " + bean.size());
		System.out.println("Length: " + bean.length());
		System.out.println("bean2 - [" + bean.exportToString() + "]");
	}

	public static void main(String[] args) {
		ComplexCopybook test = new ComplexCopybook();
		try {
			test.javaToCobol();
			System.out.println();
			test.cobolToJava();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}
