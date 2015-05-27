/*
 * blog/javaclue/jcexchanger/sample/JMSMessageRecord.java
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

/**
 * This class demonstrate a typical JMS message exchange record that includes a
 * header and a trailer.
 */
public class JMSMessageRecord extends ExchangeRecord {
	
	/**
	 * defines the JMS message layout.
	 * 
	 * @param header
	 *            - the header layout
	 * @param trailer
	 *            - the trailer layout
	 */
	public JMSMessageRecord(ExchangeRecord header, ExchangeRecord trailer) {
		this.setHeader(header);
		this.setTrailer(trailer);
		// define message body
		list.add(new StringElement("countryCode", 3));
		list.add(new IntegerElement("sequence", 6));
		list.add(new DecimalElement("amount", "00000.00"));
		list.add(new DateTimeElement("beginDate", "yyyy-MM-dd"));
		list.add(new StringElement("filler", 3));
	}

	/*
	 * defines header layout.
	 */
	static class Header extends ExchangeRecord {
		public Header() {
			list.add(new StringElement("type", 3));
			list.add(new StringElement("application", 18));
		}
	}

	/*
	 * defines trailer layout.
	 */
	static class Trailer extends ExchangeRecord {
		public Trailer() {
			list.add(new BooleanElement("flag"));
			list.add(new DateTimeElement("dateTime", "yyyyMMdd HH:mm:ss.SSS"));
		}
	}

	public void initialize() {
		// load header with data
		ExchangeRecord header = this.getHeader();
		if (header != null) {
			header.getElement("type").setValue("ADD");
			header.getElement("application").setValue("SampleJMSApp");
		}
		// load trailer with data
		ExchangeRecord trailer = this.getTrailer();
		if (trailer != null) {
			trailer.getElement("flag").setValue("Y");
			trailer.getElement("dateTime").setValue("20100405 20:45:12.234");
		}
		
		// load the exchange record with data
		this.getElement("countryCode").setValue("USA");
		this.getElement("sequence").setValue("123");
		this.getElement("amount").setValue("199.99");
		this.getElement("beginDate").setValue("2010-01-01");
	}

	/*
	 * starts from java exchange record and export it to COBOL string
	 */
	void javaToCobol() {
		initialize();
		System.out.println("JMSMessageRecord:" + LF + this);
		System.out.println("Size  : " + this.size());
		System.out.println("Length: " + this.length());

		// export the exchange record to a COBOL text string
		String cobolString = this.exportToString();
		System.out.println("bean1 - [" + cobolString + "]");

		// add your code here to send it to a mainframe queue
	}

	/*
	 * starts from COBOL string and import it to java exchange record.
	 */
	void cobolToJava() {
		// message text returned from a mainframe queue:
		String record = "ADDSampleJMSApp      USA00012300199.992010-01-01   Y20100405 20:45:12.234";
		// import the message text to the exchange record
		this.importFromString(record);
		// print out the exchange record
		System.out.println("JMSMessageRecord:" + LF + this);
		System.out.println("Size  : " + this.size());
		System.out.println("Length: " + this.length());
		System.out.println("bean2 - [" + this.exportToString() + "]");
	}
	
	public static void main(String[] args) {
		JMSMessageRecord test = new JMSMessageRecord(new Header(), new Trailer());
		try {
			test.javaToCobol();
			System.out.println(LF);
			test.cobolToJava();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
