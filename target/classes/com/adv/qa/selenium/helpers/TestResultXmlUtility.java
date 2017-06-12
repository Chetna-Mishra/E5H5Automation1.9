package com.adv.qa.selenium.helpers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.adv.qa.selenium.framework.BaseTest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class TestResultXmlUtility extends BaseTest{
	
	public static void createXMLFile(List<String>  value,String prefix,String filePath,String testCaseName) throws ParserConfigurationException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = null;
	    try { 

	        db = dbf.newDocumentBuilder();
	        Document doc = db.parse(filePath);
	        NodeList people = doc.getElementsByTagName("TestCase");
	        
	        Element e = createPersonElement(doc,value,testCaseName);
	        people.item(0).appendChild(e);
	        nodeToString(doc,filePath);
	    } catch (SAXException e) {
	        // handle SAXException
	    } catch (IOException e) {
	        // handle IOException
	    } catch (TransformerException e) {
	        // handle TransformerException
	    } catch (ParserConfigurationException e1) {
	        // handle ParserConfigurationException
	    }
	}
	
	private static Element createPersonElement(Document doc, List<String> value,String testCaseName) {
	    Element el = doc.createElement(testCaseName);    	    
	    for(int i =0;i<=value.size()-1;i++){
	    	el.appendChild(createPersonDetailElement(doc, "Verification"+(i+1), value.get(i)));
	    }
	    return el;
	}
	
	private static Element createPersonDetailElement(Document doc, String name,String value) {
	    Element el = doc.createElement(name);
	    el.appendChild(doc.createTextNode(value));
	    return el;
	}
	
	private static String nodeToString(Node node,String filePath) throws TransformerException {
	    StringWriter buf = new StringWriter();
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.METHOD,"xml");
	    DOMSource source = new DOMSource(node);
	
	    StreamResult result = new StreamResult(filePath);
	    transformer.transform(source, result);
	    System.out.println("File "+ filePath +"Saved");
	    return buf.toString();
	}

}
