package blog.javaclue.jcexchanger.test;

import org.junit.Test;

import blog.javaclue.jcexchanger.IntegerElement;
import junit.framework.TestCase;

public class IntegerElementTest extends TestCase {

	@Test
	public void testIntegerElement() {
		IntegerElement elem = new IntegerElement("integer", 8);
		elem.setValue("1234567");
		assertEquals(Integer.valueOf(1234567), elem.getValue());
		assertEquals("integer", elem.getName());
		assertEquals(8, elem.length());
		assertEquals("01234567", elem.getFormattedString());
		// test cloning
		IntegerElement clone = (IntegerElement) elem.getClone();
		assertEquals(clone.getValue(), elem.getValue());
		assertEquals(clone.getName(), elem.getName());
		assertEquals(clone.length(), elem.length());
		assertEquals(clone.getFormattedString(), elem.getFormattedString());
		clone.setValue("7654321");
		assertEquals(Integer.valueOf(7654321), clone.getValue());
		assertEquals(Integer.valueOf(1234567), elem.getValue());
		// test signed integer
		elem = new IntegerElement("signed-int", 10, true);
		elem.setValue("-2345678");
		assertEquals(Integer.valueOf(-2345678), elem.getValue());
		assertEquals("-0002345678", elem.getFormattedString());
		elem.setValue("2345678");
		assertEquals("+0002345678", elem.getFormattedString());
	}
	
//	@Test(expected=java.lang.NumberFormatException.class)
//	public void testException() {
//		IntegerElement elem = new IntegerElement("integer", 8);
//		elem.setValue("abc");
//	}
}
