package blog.javaclue.jcexchanger.test;

import java.math.BigDecimal;

import org.junit.Test;

import junit.framework.TestCase;
import blog.javaclue.jcexchanger.ArrayElement;
import blog.javaclue.jcexchanger.BaseElement;
import blog.javaclue.jcexchanger.BooleanElement;
import blog.javaclue.jcexchanger.DateTimeElement;
import blog.javaclue.jcexchanger.DecimalElement;
import blog.javaclue.jcexchanger.IntegerElement;
import blog.javaclue.jcexchanger.StringElement;
import blog.javaclue.jcexchanger.StructElement;

public class StructElementTest extends TestCase {

	@Test
	public void testStructElement() {
		String inputString = "String -1234321 12345                123.456Y20061010 111  222  333  444";
		String fmttdString = "String-01234321000000000000012345+00123.4560Y20061010111  222  333  444  ";
		ArrayElement array = new ArrayElement("array", new StringElement("item", 5), 4);
		BaseElement elems[] = {
				new StringElement("string", 6),
				new IntegerElement("int1", 4, true),
				new IntegerElement("int2", 4),
				new DecimalElement("long", "000000000000000000"),
				new DecimalElement("real", "+00000.0000;-00000.0000"),
				new BooleanElement("bool"),
				new DateTimeElement("date", "yyyyMMdd"),
				array
		};
		StructElement elem = new StructElement("struct", elems);
		elem.setValue(inputString);
		
		assertEquals("struct", elem.getName());
		assertEquals(inputString.length() + 1, elem.length());
		assertEquals(fmttdString, elem.getFormattedString());
		assertEquals("String", elem.getElement("string").getValue());
		assertEquals(Integer.valueOf(-123), ((IntegerElement)elem.getElement("int1")).getValue());
		assertEquals(BigDecimal.valueOf(123.456), ((DecimalElement)elem.getElement("real")).getValue());
		assertEquals("Y", elem.getElement("bool").getFormattedString());
		assertEquals("222", ((ArrayElement)elem.getElement("array")).get(1).getValue());
		// test cloning
		StructElement clone = (StructElement) elem.getClone();
		// make sure we got a good clone
		assertEquals(clone.getValue(), elem.getValue());
		assertEquals(clone.getName(), elem.getName());
		assertEquals(clone.length(), elem.length());
		assertEquals(clone.getFormattedString(), elem.getFormattedString());
		String cloneString = "String+88884321123456789012345678-00999.9900Y20100808111  #####333  444  ";
		// modify the clone
		clone.getElement("int1").setValue("8888");
		clone.getElement("long").setValue("123456789012345678");
		clone.getElement("real").setValue("-999.99");
		clone.getElement("date").setValue("20100808");
		((ArrayElement)clone.getElement("array")).get(1).setValue("#####");
		// verify the modification
		assertEquals(cloneString, clone.getValue());
		// make sure no side effect to the original
		assertEquals(fmttdString, elem.getValue());
	}
}
