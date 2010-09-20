package blog.javaclue.jcexchanger;

import org.apache.commons.lang.StringUtils;

public class StringElement extends BaseElement {
	private static final long serialVersionUID = -2448191264843717300L;
	private String value = null;
	private final boolean trim;

	/**
	 * construct a StringElement.
	 * 
	 * @param name
	 *            - the name of the element.
	 * @param length
	 *            - element length
	 */
	public StringElement(String name, int length) {
		this(name, length, false, true);
	}

	/**
	 * construct a StringElement.
	 * 
	 * @param name
	 *            - the name of the element.
	 * @param length
	 *            - element length, if isNullable is true, actual element length
	 *            will increase by 1.
	 * @param isNullable
	 *            - if true, an extra character is inserted to the beginning of
	 *            the element serving as null indicator.
	 */
	public StringElement(String name, int length, boolean isNullable) {
		this(name, length, isNullable, true);
	}

	/**
	 * construct a StringElement.
	 * 
	 * @param name
	 *            - the name of the element.
	 * @param length
	 *            - element length, if isNullable is true, actual element length
	 *            will increase by 1.
	 * @param isNullable
	 *            - if true, an extra character is inserted to the beginning of
	 *            the element serving as null indicator.
	 * @param trim
	 *            - true to trim the string when the value is set. default to
	 *            true.
	 */
	public StringElement(String name, int length, boolean isNullable, boolean trim) {
		super(name, length, isNullable);
		this.trim = trim;
		if (!isNullable) value = "";
	}

	public String getFormattedString() {
		if (value==null) {
			return getBlanks(length);
		}
		else {
			return StringUtils.rightPad(StringUtils.left(toString(), length), length, ' ');
		}
	}

	public String toString() {
		if (value==null) {
			return null;
		}
		else {
			return value.toString();
		}
	}

	public String getValue() {
		return this.value;
	}

	/*
	 * @param byte array
	 */
	void setValue(byte[] bytes) {
		if (isLowValue(bytes)) {
			value = null;
		}
		else {
			String field = new String(bytes);
			if (isNullable && field.length()>0) {
				if ("Y".equalsIgnoreCase(field.substring(0,1))) {
					value = null;
				}
				else {
					value = field.substring(1);
					if (trim) value = value.trim();
				}
			}
			else {
				value = field;
				if (trim) value = value.trim();
			}
		}
		if (value != null && value.length() > length) {
			logger.warn("Input \"" + value + "\" exceeds the element size of " + length
					+ ", return from getFormattedString() will be truncated.");
		}
	}

	public static void main(String[] args) {
		StringElement elem = new StringElement("string", 25);
		byte[] bytes = {0,0,0,0,0,0,0,0};
		elem.setValue(bytes);
		System.out.println(elem.isLowValue(bytes) + ", value: " + elem);
		elem.setValue("I'm a String Element");
		System.out.println(elem.getName() + ".toString() :<" + elem + ">");
		System.out.println(elem.getName() + ".formatted  :<" + elem.getFormattedString() + ">");
	}
}