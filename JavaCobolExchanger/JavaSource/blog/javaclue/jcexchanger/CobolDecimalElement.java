package blog.javaclue.jcexchanger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

/**
 * Define a COBOL decimal element. This element will remember the decimal place
 * and it is specifically designed to exchange numeric data between java and
 * COBOL declaration like 9(4)V9(2) where the decimal point is not displayed.
 * 
 */
public class CobolDecimalElement extends BaseElement {
	private static final long serialVersionUID = 1344902305258554416L;

	private BigDecimal value = null;
	private final int decimals;
	private final DecimalFormat decimalFormat;
	private final boolean decimal_scaling;

	/**
	 * Construct an unsigned CobolDecimalElement instance. 
	 * 
	 * @param name
	 *            - element name
	 * @param length
	 *            - element length
	 * @param decimals
	 *            - decimal place
	 */
	public CobolDecimalElement(String name, int length, int decimals) {
		this(name, length, decimals, false);
	}

	/**
	 * Construct a CobolDecimalElement instance.
	 * 
	 * @param name
	 *            - element name
	 * @param length
	 *            - element length
	 * @param decimals
	 *            - decimal place
	 * @param signed
	 *            - if true, a sign will be inserted to the beginning of the
	 *            element and the element length will increase by 1.
	 */
	public CobolDecimalElement(String name, int length, int decimals, boolean signed) {
		super(name, length + (signed ? 1 : 0));
		if (decimals < 0) {
			throw new IllegalArgumentException("Invalid \"decimals\" received: " + decimals);
		}
		this.decimals = decimals;
		String format = getZeros(length);
		if (signed) {
			format = "+" + format + ";-" + format;
		}
		decimalFormat = new DecimalFormat(format);
		setMaximumDigits(decimalFormat);
		decimal_scaling = true;
	}

	public BigDecimal getValue() {
		return this.value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public void setValue(String value) {
		BigDecimal bdValue = new BigDecimal(value);
		//bdValue.setScale(decimals);
		setValue(bdValue);
	}

	public String getFormattedString() {
		if (value == null || toString().trim().length() == 0) {
			return getBlanks(length);
		}
		else {
			BigDecimal bdValue = value.scaleByPowerOfTen(decimals);
			return decimalFormat.format(bdValue);
		}
	}

	public String toString() {
		if (value == null) {
			return null;
		}
		else {
			return value.toString();
		}
	}

	/*
	 * @param byte array
	 */
	void setValue(byte[] bytes) throws NumberFormatException {
		if (isLowValue(bytes)) {
			value = null;
		}
		else {
			String tmpValue = new String(bytes).trim();
			try {
				if (tmpValue.length() > 0) {
					if (tmpValue.length() > length) {
						logger.warn("Input \"" + tmpValue + "\" exceeds element size of " + length
								+ ", return from getFormattedString() will be truncated.");
					}
					BigDecimal bdValue = new BigDecimal(tmpValue);
					if (decimal_scaling) {
						BigInteger bint = BigInteger.valueOf((long)Math.pow(10, decimals));
						BigDecimal divisor = new BigDecimal(bint, 0);
						bdValue = bdValue.divide(divisor);
					}
					value = bdValue;
				}
				else {
					value = null;
				}
			}
			catch (NumberFormatException e) {
				throw new NumberFormatException("Invalid value \"" + tmpValue
						+ "\" receive by element - " + getName());
			}
		}
	}

	public static void main(String[] args) {
		try {
			CobolDecimalElement elem = new CobolDecimalElement("amount1", 8, 2);
			elem.setValue("12345.67");
			System.out.println("value = " + elem.getValue() + ", <" + elem.getFormattedString() + ">");
			elem.setValue("00000.00");
			System.out.println("value = " + elem.getValue() + ", <" + elem.getFormattedString() + ">");
			elem.setValue(" ".getBytes());
			System.out.println("value = " + elem.getValue() + ", <" + elem.getFormattedString() + ">");
			elem = new CobolDecimalElement("amount2", 20, 2);
			elem.setValue("123456789012345678.90");
			System.out.println("value = " + elem.getValue() + ", <" + elem.getFormattedString() + ">");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
