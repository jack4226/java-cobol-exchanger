package blog.javaclue.jcexchanger.test;

import java.math.BigDecimal;

import org.junit.Test;

import blog.javaclue.jcexchanger.CobolDecimalElement;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CobolDecimalElementTest extends TestCase {

	public static void main(String... args) {
		junit.textui.TestRunner.run(suite());
	}

	public static junit.framework.Test suite() {
		return new TestSuite(CobolDecimalElementTest.class);
	}

	@Test
	public void testCobolDecimalElement() {
		CobolDecimalElement elem = new CobolDecimalElement("decimal", 8, 2);
		elem.setValue("1234.56");
		assertEquals(BigDecimal.valueOf(1234.56), elem.getValue());
		assertEquals("decimal", elem.getName());
		assertEquals(8, elem.length());
		assertEquals("00123456", elem.getFormattedString());
		// test cloning
		CobolDecimalElement clone = (CobolDecimalElement) elem.getClone();
		assertEquals(clone.getValue(), elem.getValue());
		assertEquals(clone.getName(), elem.getName());
		assertEquals(clone.length(), elem.length());
		assertEquals(clone.getFormattedString(), elem.getFormattedString());
		clone.setValue("567.12");
		clone.setName("decimal-clone");
		assertEquals(BigDecimal.valueOf(567.12), clone.getValue());
		assertEquals(BigDecimal.valueOf(1234.56), elem.getValue());
		assertEquals("decimal-clone", clone.getName());
		assertEquals("decimal", elem.getName());
	}

	@Test
	public void testCobolDecimalElementSigned() {
		CobolDecimalElement elem = new CobolDecimalElement("signed-decimal", 10, 3, true);
		elem.setValue("-1234.560");
		assertEquals(new BigDecimal("-1234.560"), elem.getValue());
		assertEquals("signed-decimal", elem.getName());
		assertEquals(11, elem.length());
		assertEquals("-0001234560", elem.getFormattedString());
	}
}
