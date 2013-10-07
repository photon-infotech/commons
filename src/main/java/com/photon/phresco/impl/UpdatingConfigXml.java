package com.photon.phresco.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class UpdatingConfigXml {
	
	private static final String  STYLE_XPATH = "//Configurations/Configuration/";
	private static final String YES = "yes";

	File themeConfigPath = null;
	
	public UpdatingConfigXml(String themeConfigPath) {
		super();
		this.themeConfigPath = new File(themeConfigPath);
	}

    /**
     * Creates or updates a style with name and value
     * @param name
     * @return 
     */
	public boolean updateStyle(String name, String value) {
		
		
		try {
			Document doc = getThemeDocument();
			
			NodeList nList = getXpathStyle(name, doc);
			
			if (nList.getLength() > 0) { // already exists
				
				
				Node item = nList.item(0);
				Element eElement = (Element) item;
				eElement.setTextContent(value);
				
			} 
			writeDocument(doc);
			return true;
		} catch (Exception e) {
			e.getLocalizedMessage();
			return false;
		}
	}
	
   
	private NodeList getXpathStyle(String name, Document doc) throws XPathExpressionException {
		
		// xpath
		XPathFactory factory = XPathFactory.newInstance();
		XPath xPathInstance = factory.newXPath();

		// get config object for this key
		String xPathQuery = STYLE_XPATH + name ;
		
		XPathExpression xPathExpression = xPathInstance.compile(xPathQuery);
		// evalute the xpath query in the entire xml document and define the
		// return type
		Object results = xPathExpression.evaluate(doc, XPathConstants.NODESET);
		NodeList nList = (NodeList) results;
		return nList;
	}

   private Document getThemeDocument() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(themeConfigPath);
		doc.getDocumentElement().normalize();
		return doc;
	}
	
	private void writeDocument(Document doc) throws TransformerFactoryConfigurationError, TransformerConfigurationException, IOException, TransformerException {
		// write the content into xml file
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, YES);
		DOMSource source = new DOMSource(doc);
		FileWriter out = new FileWriter(themeConfigPath);
		Writer bw = new BufferedWriter(out);
		StreamResult result = new StreamResult(bw);
		transformer.transform(source, result);
		bw.close();
		out.close();
	}
}
