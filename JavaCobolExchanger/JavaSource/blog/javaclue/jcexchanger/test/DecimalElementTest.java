package blog.javaclue.jcexchanger.test;

import java.math.BigDecimal;

import org.junit.Test;

import blog.javaclue.jcexchanger.DecimalElement;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DecimalElementTest extends TestCase {

	public static void main(String... args) {
		junit.textui.TestRunner.run(suite());
	}

	public static junit.framework.Test suite() {
		return new TestSuite(DecimalElementTest.class);
	}

	@Test
	public void testDecimalElement() {
		DecimalElement elem = new DecimalElement("decimal", "000000000000000000.000");
		elem.setValue("123456789012345678.56");
		assertEquals(new BigDecimal("123456789012345678.56"), elem.getValue());
		assertEquals("decimal", elem.getName());
		assertEquals(22, elem.length());
		assertEquals("123456789012345678.560", elem.getFormattedString());
		// test cloning
		DecimalElement clone = (DecimalElement) elem.getClone();
		assertEquals(clone.getValue(), elem.getValue());
		assertEquals(clone.getName(), elem.getName());
		assertEquals(clone.length(), elem.length());
		assertEquals(clone.getFormattedString(), elem.getFormattedString());
		clone.setValue("567.123");
		clone.setName("decimal-clone");
		assertEquals(BigDecimal.valueOf(567.123), clone.getValue());
		assertEquals(new BigDecimal("123456789012345678.56"), elem.getValue());
		assertEquals("decimal-clone", clone.getName());
		assertEquals("decimal", elem.getName());
		// test truncation
		clone.setValue("567.12345");
		assertEquals("000000000000000567.123", clone.getFormattedString());
	}

	@Test
	public void testDecimalElementSigned() {
		DecimalElement elem = new DecimalElement("signed-decimal", "+00000000.0000;-00000000.0000");
		elem.setValue("-1234.567");
		assertEquals(BigDecimal.valueOf(-1234.567), elem.getValue());
		assertEquals("signed-decimal", elem.getName());
		assertEquals(14, elem.length());
		assertEquals("-00001234.5670", elem.getFormattedString());
	}

//	@Test (expected=IllegalArgumentException.class)
//	public void testException() {
//		DecimalElement elem = new DecimalElement("signed-decimal", "+00000000.000;-00000000.0000");
//	}
}
