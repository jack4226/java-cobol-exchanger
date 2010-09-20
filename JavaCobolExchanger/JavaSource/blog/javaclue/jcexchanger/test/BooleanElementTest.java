package blog.javaclue.jcexchanger.test;

import blog.javaclue.jcexchanger.BooleanElement;
import junit.framework.TestCase;

public class BooleanElementTest extends TestCase {

	public void testBooleanElement() {
		BooleanElement elem = new BooleanElement("boolean");
		elem.setValue("Y");
		assertEquals(Boolean.valueOf(true), elem.getValue());
		assertEquals("boolean", elem.getName());
		assertEquals(1, elem.length());
		assertEquals("Y", elem.getFormattedString());
		// test cloning
		BooleanElement clone = (BooleanElement) elem.getClone();
		assertEquals(clone.getValue(), elem.getValue());
		assertEquals(clone.getName(), elem.getName());
		assertEquals(clone.length(), elem.length());
		assertEquals(clone.getFormattedString(), elem.getFormattedString());
		clone.setValue("N");
		assertEquals(Boolean.valueOf(false), clone.getValue());
		assertEquals(Boolean.valueOf(true), elem.getValue());
	}

	public void testBooleanElement2() {
		BooleanElement elem = new BooleanElement("boolean", "True", "False");
		elem.setValue("True");
		assertEquals(Boolean.valueOf(true), elem.getValue());
		assertEquals("boolean", elem.getName());
		assertEquals(5, elem.length());
		assertEquals("True ", elem.getFormattedString());
	}
}
