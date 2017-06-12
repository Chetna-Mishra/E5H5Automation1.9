package com.adv.qa.selenium.helpers;

import java.io.File;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class MainXMLStructure {

	public static String getMainXML(String prefix) throws ParserConfigurationException, TransformerException {
		String filePath = "test-output/XMLReport/TestResult_"+prefix+".xml";

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("SeleniumTestReport");
		doc.appendChild(rootElement);
		
		// TestCase elements
		Element staff = doc.createElement("TestCase");
		rootElement.appendChild(staff);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		
		StreamResult result = new StreamResult(new File(filePath));
		transformer.transform(source, result);
		
		System.out.println(filePath + "File saved!");
		return filePath;
	}

}
