/**
 * 
 */
package com.adv.qa.selenium.helpers;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * 
 * Used to store information about one unit of data obtained from CSV or XML file.
 * The unit has 2 fields: name and value. 
 */
public class DataUnit {
	private String name;
	private String value;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	
	/**
	 * Fill fields
	 * @param name
	 * @param value
	 */
	public DataUnit (String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 * Fill fields from XML Element  
	 * @param dataElement
	 */
	public DataUnit (Element dataElement) {
		if (dataElement.getNodeType() == Node.ELEMENT_NODE) {
			name = dataElement.getNodeName();
			value = dataElement.getTextContent();
		}
		else {
			throw new IllegalArgumentException("The argument is not an Element Node");
		};
	}


	@Override
	public String toString() {
		return "DataUnit [" + name + " = " + value + "]";
	}
	

}
