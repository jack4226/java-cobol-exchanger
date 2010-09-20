package blog.javaclue.jcexchanger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

public abstract class BaseElement implements java.io.Serializable, Cloneable {
	private static final long serialVersionUID = -1342264858006898139L;
	protected static Logger logger = Logger.getLogger(BaseElement.class);
	protected static boolean isDebugEnabled = logger.isDebugEnabled();

	protected static final String LF = System.getProperty("line.separator", "\n");

	private String name;
	protected final int length;
	protected final boolean isNullable;

	/**
	 * construct a base element.
	 * @param name - the name of the element.
	 * @param length - the length of the element.
	 */
	public BaseElement(String name, int length) {
		this(name, length, false);
	}

	/**
	 * construct a base element.
	 * 
	 * @param name
	 *            - the name of the element.
	 * @param length
	 *            - the length of the element, if isNullable is true, the actual
	 *            length will increase by 1
	 * @param isNullable
	 *            - if true, an extra character will be inserted to the
	 *            beginning of the element serving as null indicator.
	 */
	public BaseElement(String name, int length, boolean isNullable) {
		if (name == null) {
			throw new IllegalArgumentException("Element name can not be null.");
		}
		if (length < 0) {
			throw new IllegalArgumentException("Invalid Length: " + length);
		}
		this.name = name;
		this.length = isNullable ? (length + 1) : length;
		this.isNullable = isNullable;
	}

	abstract void setValue(byte[] bytes) throws NumberFormatException;

	public abstract String getFormattedString();

	public abstract Object getValue();

	public void setValue(String value) {
		if (value == null) setValue(getLowValues());
		else setValue(value.getBytes());
	}

	public void readBytesToValue(ByteArrayInputStream bais) {
		byte[] bytes = new byte[length()];
		bais.read(bytes,0,length());
		setValue(bytes);
	}

	public void writeValueToBytes(ByteArrayOutputStream baos) {
		byte[] buffer =getFormattedString().getBytes();
		baos.write(buffer, 0, buffer.length);
	}

	protected final boolean isLowValue(byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] != 0)
				return false;
		}
		return true;
	}
	
	protected final byte[] getLowValues() {
		byte[] bytes = new byte[length];
		for (int i = 0; i < length; i++) {
			bytes[i] = 0;
		}
		return bytes;
	}

	/**
	 * @return
	 */
	public int length() {
		return length;
	}

	protected static int getElementLengthByFormat(String format) {
		if (format == null) return 0;
		int semiColonPos = format.indexOf(";"); // handles format like "+0000;-0000"
		if (semiColonPos > 0) {
			int rightPartLen = format.substring(semiColonPos+1).length();
			if (semiColonPos != rightPartLen) {
				throw new IllegalArgumentException("The length of positive pattern must equal to the length of negative pattern");
			}
			return semiColonPos;
		}
		else {
			return format.length();
		}
	}

	/**
	 * @return
	 */
	public final String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected final String getBlanks(int len) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	protected final String getZeros(int len) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			sb.append("0");
		}
		return sb.toString();
	}

	protected String getDots(int level) {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<level; i++) {
			sb.append(".");
		}
		if (level > 0) {
			sb.append(" ");
		}
		return sb.toString();
	}

	protected final void setMaximumDigits(DecimalFormat decimalFormat) {
		if (decimalFormat == null) {
			throw new IllegalArgumentException("Input decimalFormat is null");
		}
		decimalFormat.setMaximumFractionDigits(decimalFormat.getMinimumFractionDigits());
		decimalFormat.setMaximumIntegerDigits(decimalFormat.getMinimumIntegerDigits());
	}

	public BaseElement getClone() {
		try {
			return (BaseElement) this.clone();
		}
		catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}
