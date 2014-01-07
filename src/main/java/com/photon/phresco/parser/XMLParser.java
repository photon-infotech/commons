package com.photon.phresco.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.photon.phresco.configuration.Configuration;

public class XMLParser {
	

	private Document document = null;
	private String filePath = "";
	private HashMap<String, String> configMap = new HashMap<String, String>();
	/**
	 * XMLParser single instance created by configuration xml
	 * @throws Exception
	 */
	public XMLParser() throws Exception {
		File configXML = new File(this.getFilePath());
		if (configXML.exists()) {
			initXML(new FileInputStream(configXML));
		}
	}

	/**
	 * XMLParser single instance created by configuration xml input stream
	 * 
	 * @param xmlStream
	 * @return
	 * @throws Exception
	 */
	public XMLParser(InputStream xmlStream) throws Exception {
		initXML(xmlStream);
	}

	/**
	 * loads the configuration xml as input stream
	 * 
	 * @param xmlStream
	 * @throws Exception
	 */
	protected void initXML(InputStream xmlStream) throws Exception {
		try {
			if (xmlStream == null) {
				xmlStream = new FileInputStream(this.getFilePath());
			}
			initDocument(xmlStream);
		} finally {
			try {
				xmlStream.close();
			} catch (IOException e) {
				throw e;
			}
		}
	}

	/**
	 * Creating Dom object to parse the configuration xml
	 * 
	 * @param xmlStream
	 * @throws Exception
	 */
	private void initDocument(InputStream xmlStream) throws Exception {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(false);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		document = builder.parse(xmlStream);
		this.setDocument(document);
	}

	/**
	 * parse the configuration xml
	 * 
	 * @param document
	 */
	public HashMap<String, String> read() {
		if (this.getDocument().hasChildNodes()) {
			configMap = read(this.getDocument().getChildNodes());
		}
		
		return configMap;

		// return nodeList;
	}

	private HashMap<String, String> read(NodeList nodeList) {

		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

				if (tempNode.hasAttributes()) {
					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
					}
				}
				if (tempNode.hasChildNodes()) {
					// loop again if has child nodes
					read(tempNode.getChildNodes());
				}

				if (!tempNode.getNodeName().equalsIgnoreCase("Configurations")
						&& !tempNode.getNodeName().equalsIgnoreCase(
								"Configuration"))
					configMap.put(tempNode.getNodeName(),
							tempNode.getTextContent());
			}
		}

		/*Set set = configMap.entrySet();
		// Get an iterator
		Iterator i = set.iterator();
		// Display elements
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			System.out.print(me.getKey() + ": " + me.getValue() + "\n");
		}*/
		
		return configMap;

	}

	public void write(String node, String attribute, int color) {

		try {
			this.getDocument().getDocumentElement().normalize();

			// Get the staff element , it may not working if tag has spaces, or
			// whatever weird characters in front...it's better to use
			// getElementsByTagName() to get it directly.
			// Node staff = company.getFirstChild();

			// Get the staff element by tag name directly
			Node control = this.getDocument().getElementsByTagName(node)
					.item(0);

			// update staff attribute
			NamedNodeMap attr = control.getAttributes();
			Node nodeAttr = attr.getNamedItem(attribute);
			nodeAttr.setTextContent(Integer.toHexString(color));

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(this.getDocument());
			StreamResult result = new StreamResult(new File(this.getFilePath()));
			transformer.transform(source, result);

		} catch (DOMException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	 @SuppressWarnings("unchecked")
     private List<Configuration> getConfigFromXml(String plistPath) throws IOException, JSONException
				  {
				    @SuppressWarnings("rawtypes")
					List configs = new ArrayList();
				     try {
				      Configuration config = null;
				      File plistFile = new File(plistPath);
				      if (plistFile.isFile())
				        config = new Configuration(plistFile.getName(), "features");
				      else {
				        return Collections.EMPTY_LIST;
				      }
                   DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                   org.w3c.dom.Document doc = dBuilder.parse(plistFile);
				      doc.getDocumentElement().normalize();
                   NodeList nList = doc.getElementsByTagName("Configuration");
                   Properties properties = new Properties();
			          for (int temp = 0; temp < nList.getLength(); temp++)
				      {
				        Node nNode = nList.item(temp);
				        if (nNode.getNodeType() == Node.ELEMENT_NODE){
				        NodeList childNodes = nNode.getChildNodes();
				          for (int temp1 = 0; temp1 <= childNodes.getLength();temp1++) {
				            Node nNode1 = childNodes.item(temp1);
				           if (nNode1 != null){
				            	if (!nNode1.getNodeName().equalsIgnoreCase("#text") ){
				            
				              String key = nNode1.getNodeName();
				              //System.out.println("-->getNodeName " + key);
				              String value = nNode1.getTextContent();
				              //System.out.println("-->getNodeValue " + value);
				              properties.setProperty(key, value);
				               }
				             }
				           }
				         }
				      }
				      config.setProperties(properties);
					  configs.add(config);
				  
					} catch (ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				    return configs;
		  }
     
}
