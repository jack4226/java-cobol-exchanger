/*
 * blog/javaclue/jcexchanger/StructElement.java
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
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * This class defines a data structure, which consists of a group of elements,
 * accessible by name or Iterator.
 */
public class StructElement extends BaseGroupElement {
	private static final long serialVersionUID = -2490620855470379941L;

	private LinkedHashMap<String, BaseElement> nameMap;
	private Collection<BaseElement> value; 

	/**
	 * Constructs a StructElement.
	 * 
	 * @param name
	 *            - element name.
	 */
	public StructElement(String name, BaseElement[] elements) {
		super(name, elements == null ? 0 : elements.length);
		if (elements == null) {
			throw new IllegalArgumentException("\"elements\" can not be null.");
		}
		nameMap = new LinkedHashMap<String, BaseElement>();
		for (int i=0; i<elements.length; i++) {
			put(elements[i]);
		}
		value = nameMap.values();
	}

	private void put(BaseElement element) {
		if (element == null) {
			throw new IllegalArgumentException("Input element can not be null.");
		}
		nameMap.put(element.getName(), element);
	}

	public final Iterator<BaseElement> getIterator() {
		return ((Collection<BaseElement>)value).iterator();
	}

	/**
	 * Get a BaseElement in a structure by name
	 * 
	 * @param elementName
	 *            - element name
	 * @return a BaseElement
	 */
	public BaseElement getElement(String elementName) {
		Object element = nameMap.get(elementName);
		if (element == null) {
			throw new IllegalArgumentException("Element name \"" + elementName
					+ "\" not found in the StructElement \"" + getName() + "\".");
		}
		return (BaseElement) element;
	}

	public final StructElement getClone() {
		StructElement baseField = (StructElement) super.getClone();
		baseField.nameMap = (LinkedHashMap<String, BaseElement>) this.nameMap.clone();
		Iterator<BaseElement> it = this.getIterator();
		while (it.hasNext()) {
			BaseElement elem = it.next();
			BaseElement clone = elem.getClone();
			baseField.nameMap.put(elem.getName(), clone);
		}
		baseField.value = baseField.nameMap.values();
		return baseField;
	}

	public static void main(String[] args) {
		ArrayElement array = new ArrayElement("array", new StringElement("item", 4), 4);
		BaseElement elems[] = {
				new StringElement("string", 6),
				new IntegerElement("int1", 4, true),
				new IntegerElement("int2", 4, true),
				new DecimalElement("long", "0000000000000000"),
				new DecimalElement("real", "+00000.0000"),
				new BooleanElement("bool"),
				new DateTimeElement("date", "yyyyMMdd"),
				array
		};
		StructElement struct = new StructElement("struct", elems);
		try {
			String inputString = "String -123 4321 12345              123.456Y20061010 111 222 333 444";
			ByteArrayInputStream bais = new ByteArrayInputStream(inputString.getBytes());
			struct.readBytesToValue(bais);
			
			StructElement clone = struct.getClone();
			
			clone.getElement("string").setValue("clone");
			clone.getElement("int1").setValue("9999");
			clone.getElement("int2").setValue("-999");
			clone.getElement("long").setValue("9999999999999999");
			clone.getElement("real").setValue("9999.999");
			((ArrayElement)clone.getElement("array")).get(1).setValue("999");
			
			System.out.println("Struct Original ##############" + LF + struct);
			System.out.println("Struct    Clone ##############" + LF + clone);
			System.out.println("Struct Original ##############" + LF + struct.getFormattedString());
			System.out.println("Struct    Clone ##############" + LF + clone.getFormattedString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
