/*
 * blog/javaclue/jcexchanger/ExchangeRecord.java
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
package blog.javaclue.jcexchanger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;

public abstract class ExchangeRecord {
	protected final HashList list = new HashList();
	private ExchangeRecord header = null;
	private ExchangeRecord trailer = null;
	protected static final String LF = System.getProperty("line.separator", "\n");
	
	public void importFromBytes(byte[] record) {
		int offset = 0;
		if (header != null) {
			header.importFromBytes(record, offset, header.length());
			offset += header.length();
		}
		this.importFromBytes(record, offset, this.length());
		offset += this.length();
		if (trailer != null) {
			trailer.importFromBytes(record, offset, trailer.length());
		}
	}
	
	public void importFromBytes(byte[] record, int offset, int length) {
		ByteArrayInputStream bais = new ByteArrayInputStream(record, offset, length);
		for (Iterator<BaseElement> it=list.getElements().iterator(); it.hasNext();) {
			BaseElement element = (BaseElement)it.next();
			element.readBytesToValue(bais);
		}
	}
	
	public void importFromString(String record) {
		importFromBytes(record.getBytes());
	}
	
	public byte[] exportToBytes() {
		byte[] hdr = new byte[0];
		if (header != null) {
			hdr = header.exportToBytes();
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (Iterator<BaseElement> it=list.getElements().iterator(); it.hasNext();) {
			BaseElement element = (BaseElement)it.next();
			element.writeValueToBytes(baos);
		}
		byte[] body = baos.toByteArray();
		byte[] tlr = new byte[0];
		if (trailer != null) {
			tlr = trailer.exportToBytes();
		}
		byte[] all = new byte[hdr.length + body.length + tlr.length];
		System.arraycopy(hdr, 0, all, 0, hdr.length);
		System.arraycopy(body, 0, all, hdr.length, body.length);
		System.arraycopy(tlr, 0, all, hdr.length + body.length, tlr.length);
		return all;
	}
	
	public String exportToString() {
		return new String(exportToBytes());
	}
	
	public BaseElement getElement(String elementName) {
		return list.get(elementName);
	}
	
	public int size() {
		return list.size();
	}
	
	/**
	 * the record length excluding header and trailer.
	 * @return
	 */
	public int length() {
		return list.length();
	}
	
	/**
	 * the record length including header and trailer if present.
	 * @return the record length
	 */
	public int getRecordLength() {
		int len = length();
		if (header != null) len += header.length();
		if (trailer != null) len += trailer.length();
		return len;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (header != null) {
			sb.append(">>>>>> HEADER Elements:" + LF);
			sb.append(header.list.toString());
			sb.append("<<<<<< End of HEADER" + LF);
		}
		sb.append(list.toString());
		if (trailer != null) {
			sb.append(">>>>>> TRAILER Elements:" + LF);
			sb.append(trailer.list.toString());
			sb.append("<<<<<< End of TRAILER" + LF);
		}
		return sb.toString();
	}

	protected ExchangeRecord getHeader() {
		return header;
	}

	protected void setHeader(ExchangeRecord header) {
		this.header = header;
	}

	protected ExchangeRecord getTrailer() {
		return trailer;
	}

	protected void setTrailer(ExchangeRecord trailer) {
		this.trailer = trailer;
	}
}
