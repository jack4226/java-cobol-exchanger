package blog.javaclue.jcexchanger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;

/**
 * Define a super class that are used by Array element and Structure
 * element.
 * 
 * @author Jack W
 */
public abstract class BaseGroupElement extends BaseElement {
	private static final long serialVersionUID = 2501320945995374969L;

	private final int size;

	public abstract Iterator<BaseElement> getIterator();

	public BaseGroupElement(String name, int len) {
		super(name,len);
		this.size = len;
	}

	/**
	 * return the size of the list or array.
	 * 
	 * @return size of the array or list.
	 */
	public int size() {
		return size;
	}

	/**
	 * Return the length of the entire group, by adding up the lengths of
	 * each item in the group.
	 * 
	 * @return the length of the group element.
	 */
	public int length() {
		int len = 0;
		for (Iterator<BaseElement> it = getIterator(); it.hasNext();) {
			len += it.next().length();
		}
		return len;
	}

	/**
	 * write the group element to a output stream in order to convert the
	 * group to a string.
	 * 
	 * @param baos
	 *            - a ByteArrayOutputStream
	 */
	public void writeValueToBytes(ByteArrayOutputStream baos) {
		Iterator<BaseElement> it = getIterator();
		while (it.hasNext()) {
			BaseElement field = it.next();
			if (field instanceof StructElement) {
				((StructElement) field).writeValueToBytes(baos);
			}
			else if (field instanceof BaseGroupElement) {
				((BaseGroupElement) field).writeValueToBytes(baos);
			}
			else {
				field.writeValueToBytes(baos);
			}
		}
	}

	/**
	 * load the group element from an input stream.
	 * 
	 * @param bais
	 *            - a ByteArrayInputStream
	 */
	public void readBytesToValue(ByteArrayInputStream bais) {
		Iterator<BaseElement> it = getIterator();
		while (it.hasNext()) {
			BaseElement field = it.next();
			if (field instanceof StructElement) {
				((StructElement) field).readBytesToValue(bais);
			}
			else if (field instanceof BaseGroupElement) {
				((BaseGroupElement) field).readBytesToValue(bais);
			}
			else {
				field.readBytesToValue(bais);
			}
		}
	}

	/**
	 * returns fixed format string.
	 */
	public String getValue() {
		return getFormattedString();
	}

	/**
	 * returns fixed format string.
	 */
	public String getFormattedString() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		this.writeValueToBytes(baos);
		return baos.toString();
	}

	void setValue(byte[] bytes) throws NumberFormatException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		readBytesToValue(bais);
	}

	/**
	 * Returns a string representation of the group element.
	 * 
	 * @return as a String
	 */
	public String toString() {
		return toString(0);
	}

	/**
	 * Returns a string representation of this group element.
	 * 
	 * @return as a String
	 */
	protected String toString(int level) {
		StringBuffer sb = new StringBuffer();
		Iterator<BaseElement> it = getIterator();
		sb.append(getName() + " - contains " + length + " elements, length = " + length());
		while (it.hasNext()) {
			BaseElement elem = it.next();
			sb.append(LF + getDots(level) + String.format("%-16s", elem.getName()) + ": ");
			if (elem instanceof BaseGroupElement) {
				sb.append(((BaseGroupElement)elem).toString(level + 1));
			}
			else {
				sb.append(elem.toString());
			}
		}
		return sb.toString();
	}
}
