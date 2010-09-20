package blog.javaclue.jcexchanger;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class HashList {
	
	private final HashMap<String, Object> map;
	private final ArrayList<BaseElement> list;
	protected final String LF = System.getProperty("line.separator", "\n");
	
	/**
	 * constructor
	 */
	public HashList() {
		map = new HashMap<String, Object>();
		list = new ArrayList<BaseElement>();
	}
	
	/**
	 * add an element into the list.
	 * @param element - the element to be added
	 */
	public synchronized void add(BaseElement element) {
		list.add(element);
		map.put(element.getName(), new Integer(list.size()));
	}
	
	/**
	 * returns an element
	 * @param elementName
	 * @return an element or null if not found
	 */
	public BaseElement get(String elementName) {
		Object obj = map.get(elementName);
		if (obj!=null) {
			int idx = ((Integer)obj).intValue();
			return (BaseElement)list.get(idx-1);
		}
		return null;
	}
	
	/**
	 * returns a list of all element names
	 * @return
	 */
	public List<String> getNames() {
		List<String> names = new ArrayList<String>();
		for (Iterator<BaseElement> it=list.iterator(); it.hasNext();) {
			BaseElement element = (BaseElement)it.next();
			names.add(element.getName());
		}
		return names;
	}
	
	/**
	 * returns a list of all elements
	 * @return a list
	 */
	public List<BaseElement> getElements() {
		return list;
	}
	
	/**
	 * number of elements on the list
	 * @return a number
	 */
	public int size() {
		return list.size();
	}
	
	/**
	 * the total length of all elements.
	 * @return length of all elements
	 */
	public int length() {
		int len = 0;
		for (Iterator<BaseElement> it=list.iterator(); it.hasNext();) {
			BaseElement element = (BaseElement)it.next();
			len += element.length();
		}
		return len;
	}
	
	public String toString() {
		return toString(0);
	}
	
	/**
	 * display all elements in name and value pairs
	 */
	public String toString(int level) {
		StringBuffer sb = new StringBuffer();
		sb.append("This record contains " + list.size() + " elements, length = " + length() + LF);
		for (Iterator<BaseElement> it=list.iterator(); it.hasNext();) {
			BaseElement element = (BaseElement)it.next();
			sb.append(String.format("%-16s", element.getName()) + ": ");
			if (element instanceof BaseGroupElement) {
				sb.append("["+((BaseGroupElement)element).toString(level + 1)+"]"+LF);
			}
			else {
				sb.append("["+element.toString()+"]"+LF);
			}
		}
		return sb.toString();
	}
	
	public void printOut(PrintStream out) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(String.format("%-16s", "Element Name"));
		sb.append("Element Value"+LF);
		sb.append(String.format("%-16s", "--------------"));
		sb.append("-----------"+LF);
		
		for (Iterator<String> it=getNames().iterator(); it.hasNext();) {
			String name = (String)it.next();
			BaseElement element = get(name);
			sb.append(String.format("%-16s", element.getName()));
			sb.append("["+element.getValue()+"]"+LF);
		}
		out.println(sb.toString());
	}
}
