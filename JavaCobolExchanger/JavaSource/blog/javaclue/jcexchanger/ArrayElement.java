package blog.javaclue.jcexchanger;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class defines a traditional array with a specified size, all items in
 * the array will have the same data type and the same length.
 * 
 * @author Jack W
 */
public class ArrayElement extends BaseGroupElement {
	private static final long serialVersionUID = 4915172611617124583L;
	static final String LF = System.getProperty("line.separator", "\n");

	private ArrayList<BaseElement> value;

	/**
	 * Constructs an ArrayElement of specified size.
	 * 
	 * @param name
	 *            - element name.
	 * 
	 * @param size
	 *            - size of the array.
	 */
	public ArrayElement(String name, BaseElement element, int arraySize) {
		super(name, arraySize);
		if (element == null) {
			throw new IllegalArgumentException("Input element can not be null.");
		}
		value = new ArrayList<BaseElement>();
		loadEmptyElements(element);
	}

	public void loadEmptyElements(BaseElement element) {
		value.clear();
		String itemName = element.getName();
		for (int i = 0; i < length; i++) {
			BaseElement elem = element.getClone();
			elem.setName(itemName + "_" + i);
			value.add(elem);
		}
	}

	public final Iterator<BaseElement> getIterator() {
		return value.iterator();
	}

	/**
	 * Return the n'th item of the ArrayElement.
	 * 
	 * @param index
	 *            - location of the item
	 * @return the element stored at the specified location.
	 * @throws IndexOutOfBoundsException
	 */
	public BaseElement get(int index) throws IndexOutOfBoundsException {
		return value.get(index);
	}

	/**
	 * Set the n'th item with the value provided.
	 * 
	 * @param index
	 *            - location of the element to replace.
	 * @param value
	 *            - value to be stored at the specified location.
	 * @throws IndexOutOfBoundsException
	 */
	public void set(int index, String value) throws IndexOutOfBoundsException {
		get(index).setValue(value);
	}

	public final ArrayElement getClone() {
		ArrayElement baseField = (ArrayElement) super.getClone();
		baseField.value = (ArrayList<BaseElement>) this.value.clone();
		baseField.value.clear();
		Iterator<BaseElement> it = this.getIterator();
		while (it.hasNext()) {
			BaseElement elem = it.next();
			baseField.value.add(elem.getClone());
		}
		return baseField;
	}

	public static void main(String[] args) {
		try {
			ArrayElement array = new ArrayElement("array", new StringElement("item", 4), 10);
			String arrayItems = "111122223333444455556666777788889999AAAA";
			ByteArrayInputStream bais = new ByteArrayInputStream(arrayItems.getBytes());
			array.readBytesToValue(bais);
			array.set(1, "####@@@@");
			System.out.println("Array: " + array.getClone());
			System.out.println("Array: " + array.getFormattedString());
			System.out.println(array.get(2).getName() + ": " + array.get(2));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
