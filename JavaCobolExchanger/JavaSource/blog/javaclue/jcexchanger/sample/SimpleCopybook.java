/*
 * blog/javaclue/jcexchanger/sample/SimpleCopybook.java
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

import blog.javaclue.jcexchanger.BooleanElement;
import blog.javaclue.jcexchanger.DateTimeElement;
import blog.javaclue.jcexchanger.DecimalElement;
import blog.javaclue.jcexchanger.ExchangeRecord;
import blog.javaclue.jcexchanger.IntegerElement;
import blog.javaclue.jcexchanger.StringElement;

public class SimpleCopybook extends ExchangeRecord {

	/*
	 * This class demonstrate data exchange with following COBOL layout:
	 * PIC X(3).
	 * PIC X(1).
	 * PIC X(3).
	 * PIC 9(6).
	 * PIC 9(5).9(2).
	 * PIC X(10).
	 * PIC X(21).
	 * PIC X(3).
	 */
	public SimpleCopybook() {
		// define the exchange record.
		list.add(new StringElement("type", 3));
		list.add(new BooleanElement("flag"));
		list.add(new StringElement("countryCode", 3));
		list.add(new IntegerElement("sequence", 6));
		list.add(new DecimalElement("amount", "00000.00"));
		list.add(new DateTimeElement("beginDate", "yyyy-MM-dd"));
		list.add(new DateTimeElement("updateDateTime", "yyyyMMdd HH:mm:ss.SSS"));
		list.add(new StringElement("filler",3));
	}
	
	public void initialize() {
		// load the exchange record with data
		this.getElement("type").setValue("ADD");
		((BooleanElement)this.getElement("flag")).setValue(Boolean.valueOf(true));
		this.getElement("countryCode").setValue("USA");
		this.getElement("sequence").setValue("123");
		this.getElement("amount").setValue("199.99");
		this.getElement("beginDate").setValue("2010-01-01");
		this.getElement("updateDateTime").setValue("20100405 20:45:12.234");
	}
	
	/*
	 * starts from java exchange record and export it to COBOL string
	 */
	void javaToCobol() {
		// create an exchange record
		SimpleCopybook bean = new SimpleCopybook();
		bean.initialize();
		// print out the exchange record
		System.out.println("SimpleCopybook:" + LF + bean);
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
		String record = "ADDYUSA00012300199.992010-01-0120100405 20:45:12.234   ";
		// create an exchange record
		ExchangeRecord bean = new SimpleCopybook();
		// import the COBOL text string string to the exchange record
		bean.importFromString(record);
		// access the data in the exchange record
		bean.getElement("type").getValue();
		((StringElement)bean.getElement("countryCode")).getValue();
		((BooleanElement)bean.getElement("flag")).getValue();
		((DecimalElement)bean.getElement("amount")).getValue();
		// print out the exchange record
		System.out.println("SimpleCopybook:" + LF + bean);
		System.out.println("Size  : " + bean.size());
		System.out.println("Length: " + bean.length());
		System.out.println("bean2 - [" + bean.exportToString() + "]");
	}

	public static void main(String[] args) {
		SimpleCopybook test = new SimpleCopybook();
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
