package blog.javaclue.jcexchanger.test;

import blog.javaclue.jcexchanger.StringElement;
import junit.framework.TestCase;

public class StringElementTest extends TestCase {

	public StringElementTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testStringElement() {
		StringElement elem = new StringElement("string", 12);
		elem.setValue("TestString");
		assertEquals("TestString", elem.getValue());
		assertEquals("string", elem.getName());
		assertEquals(12, elem.length());
		assertEquals("TestString  ", elem.getFormattedString());
		// test cloning
		StringElement clone = (StringElement) elem.getClone();
		assertEquals(clone.getValue(), elem.getValue());
		assertEquals(clone.getName(), elem.getName());
		assertEquals(clone.length(), elem.length());
		assertEquals(clone.getFormattedString(), elem.getFormattedString());
		clone.setValue("CloneString");
		assertEquals("CloneString", clone.getValue());
		assertEquals("TestString", elem.getValue());
	}
}
