package blog.javaclue.jcexchanger.test;

import java.text.SimpleDateFormat;
import junit.framework.TestCase;
import org.junit.Test;
import blog.javaclue.jcexchanger.DateTimeElement;

public class DateTimeElementTest extends TestCase {

	@Test
	public void testDateTimeElement() {
		DateTimeElement elem = new DateTimeElement("datetime", "yyyy-MM-dd");
		elem.setValue("2010-09-11");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		assertEquals("2010-09-11", sdf.format(elem.getValue()));
		assertEquals("datetime", elem.getName());
		assertEquals(10, elem.length());
		assertEquals("2010-09-11", elem.getFormattedString());
		// test cloning
		DateTimeElement clone = (DateTimeElement) elem.getClone();
		assertEquals(clone.getValue(), elem.getValue());
		assertEquals(clone.getName(), elem.getName());
		assertEquals(clone.length(), elem.length());
		assertEquals(clone.getFormattedString(), elem.getFormattedString());
		clone.setValue("2000-01-01");
		assertEquals("2000-01-01", sdf.format(clone.getValue()));
		assertEquals("2010-09-11", sdf.format(elem.getValue()));
	}

	@Test(expected=java.lang.NumberFormatException.class)
	public void testException() {
		DateTimeElement elem = new DateTimeElement("datetime", "yyyy-MM-dd");
		elem.setValue("2010-11-aa");
	}
}
