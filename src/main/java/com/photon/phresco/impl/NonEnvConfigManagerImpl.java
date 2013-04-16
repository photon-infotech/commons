package com.photon.phresco.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
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

import com.photon.phresco.api.NonEnvConfigManager;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.Utility;

public class NonEnvConfigManagerImpl implements NonEnvConfigManager{

	private File configFile = null;
	private Document document = null;

	public NonEnvConfigManagerImpl(File configXML) throws PhrescoException {
		this.configFile = configXML;
		try {
			if (!configXML.exists()) {
				createNewDoc();
			} else {
				configFile = configXML;
				initXML(new FileInputStream(configXML));
			}
		} catch (ConfigurationException e) {
			throw new PhrescoException(e);
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		}
	}

	protected void initXML(InputStream xmlStream) throws ConfigurationException {
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(false);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			document = builder.parse(xmlStream);
		} catch (ParserConfigurationException e) {
			throw new ConfigurationException(e);
		} catch (SAXException e) {
			throw new ConfigurationException(e);
		} catch (IOException e) {
			throw new ConfigurationException(e);
		} finally {
			try {
				xmlStream.close();
			} catch (IOException e) {
				throw new ConfigurationException(e);
			}
		}
	}

	private void createNewDoc() throws ConfigurationException {
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(false);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			document = builder.newDocument();
			Element rootElement = document.createElement("configurations");
			document.appendChild(rootElement);
			writeXml(new FileOutputStream(configFile));
		} catch (ParserConfigurationException e) {
			throw new ConfigurationException(e);
		} catch (FileNotFoundException e) {
			throw new ConfigurationException(e);
		}
	}

	@Override
	public List<Configuration> getConfigurations() throws PhrescoException {
		Node configurationsNode = document.getElementsByTagName("configurations").item(0);
		NodeList childNodes = configurationsNode.getChildNodes();
		List<Configuration> configurations = new ArrayList<Configuration>();
		for (int i = 0 ; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (node.getNodeType() !=  Element.TEXT_NODE && node.getNodeType() !=  Element.COMMENT_NODE
					&& node.getNodeType() !=  Element.CDATA_SECTION_NODE) {
				Configuration configuration = new Configuration();
				Element configEle = (Element) childNodes.item(i);
				String configType = configEle.getNodeName();
				String configName = configEle.getAttribute("name");
				String desc = configEle.getAttribute("desc");
				Properties properties = getProperties(configEle);
				configuration.setName(configName);
				configuration.setType(configType);
				configuration.setDesc(desc);
				configuration.setProperties(properties);
				configurations.add(configuration);
			}
		}
		return configurations;
	}
	
	@Override
	public List<Configuration> getConfigurations(String type) throws PhrescoException {
		Node configurationsNode = document.getElementsByTagName("configurations").item(0);
		NodeList childNodes = configurationsNode.getChildNodes();
		List<Configuration> configurations = new ArrayList<Configuration>();
		for (int i = 0 ; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (node.getNodeType() !=  Element.TEXT_NODE && node.getNodeType() !=  Element.COMMENT_NODE
					&& node.getNodeType() !=  Element.CDATA_SECTION_NODE) {
				Configuration configuration = new Configuration();
				Element configEle = (Element) childNodes.item(i);
				String configType = configEle.getNodeName();
				if (configType.equals(type)) {
					String configName = configEle.getAttribute("name");
					String desc = configEle.getAttribute("desc");
					configuration.setName(configName);
					configuration.setType(configType);
					configuration.setDesc(desc);
					Properties properties = getProperties(configEle);
					configuration.setProperties(properties);
					configurations.add(configuration);
				}
			}
		}
		return configurations;
	}

	@Override
	public Configuration getConfiguration(String configName) throws PhrescoException {
		Node configurationsNode = document.getElementsByTagName("configurations").item(0);
		NodeList childNodes = configurationsNode.getChildNodes();
		for(int i = 0 ; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (node.getNodeType() !=  Element.TEXT_NODE && node.getNodeType() !=  Element.COMMENT_NODE
					&& node.getNodeType() !=  Element.CDATA_SECTION_NODE) {
				Element configEle = (Element) childNodes.item(i);
				if (configEle.getAttribute("name").equalsIgnoreCase(configName)) {
					Configuration configuration = new Configuration();
					String configType = configEle.getNodeName();
					String configurationName = configEle.getAttribute("name");
					String desc = configEle.getAttribute("desc");
					configuration.setName(configurationName);
					configuration.setType(configType);
					configuration.setDesc(desc);
					Properties properties = getProperties(configEle);
					configuration.setProperties(properties);
					return configuration;
				}
			}
		}

		return null;
	}

	@Override
	public void createConfiguration(Configuration configuration) throws PhrescoException {
		try {
			Element element = (Element) document.getElementsByTagName("configurations").item(0);
			element.appendChild(createConfigElement(configuration));
			writeXml(new FileOutputStream(configFile));
		} catch (ConfigurationException e) {
			e.printStackTrace();
			throw new PhrescoException(e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new PhrescoException(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Element createConfigElement(Configuration configuration) throws ConfigurationException {
		try {
			Element configNode = document.createElement(configuration.getType());
			configNode.setAttribute("name", configuration.getName());
			configNode.setAttribute("desc", configuration.getDesc());
			createProperties(configNode, configuration.getProperties());
			return configNode;
		} catch (Exception e) {
			throw new ConfigurationException(e);
		}
	}

	private void createProperties(Element configNode, Properties properties) throws ConfigurationException {
		try {
			Set<Object> keySet = properties.keySet();
			for (Object key : keySet) {
				String value = (String) properties.get(key);
				Element propNode = document.createElement(key.toString());
				propNode.setTextContent(value);
				configNode.appendChild(propNode);
			}
		} catch (Exception e) {
			throw new ConfigurationException(e);
		}
	}

	private void writeXml(OutputStream fos) throws ConfigurationException {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			Source src = new DOMSource(document);
			Result res = new StreamResult(fos);
			transformer.transform(src, res);
		} catch (TransformerConfigurationException e) {
			throw new ConfigurationException(e);
		} catch (TransformerException e) {
			throw new ConfigurationException(e);
		} finally {
			if(fos != null) {
				Utility.closeStream(fos);
			}
		}
	}

	@Override
	public void deleteConfiguration(String configName) throws PhrescoException {
		try {
			String xpath = getXpathConfig(configName).toString();
			Element configNode = (Element) getNode(xpath);
			configNode.getParentNode().removeChild(configNode);
			writeXml(new FileOutputStream(configFile));
		}catch (ConfigurationException e) {
			throw new PhrescoException(e);
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		}
	}

	private String getXpathConfig(String configName) {
		StringBuilder expBuilder = new StringBuilder();
		expBuilder.append("/configurations/*[@name='");
		expBuilder.append(configName);
		expBuilder.append("']");
		return expBuilder.toString();
	}

	private Node getNode(String xpath) throws ConfigurationException {
		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath newXPath = xPathFactory.newXPath();	
		XPathExpression xPathExpression;
		Node xpathNode = null;
		try {
			xPathExpression = newXPath.compile(xpath);
			xpathNode = (Node) xPathExpression.evaluate(document, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			throw new ConfigurationException(e);
		}
		return xpathNode;
	}

	@Override
	public void updateConfiguration(String oldConfigName, Configuration configuration) throws PhrescoException {
		try {
			Node oldConfigNode = getNode(getXpathConfig(oldConfigName));
			Element configElement = createConfigElement(configuration);
			oldConfigNode.getParentNode().replaceChild(configElement, oldConfigNode);
			writeXml(new FileOutputStream(configFile));
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		} catch (ConfigurationException e) {
			throw new PhrescoException(e);
		}
	}
	
	private Properties getProperties(Element configEle) {
		Properties properties = new Properties();
		NodeList propNodes = configEle.getChildNodes();
		for (int i = 0 ; i < propNodes.getLength(); i++) {
			if (propNodes.item(i).getNodeType() !=  Element.TEXT_NODE && propNodes.item(i).getNodeType() !=  Element.COMMENT_NODE
					&& propNodes.item(i).getNodeType() !=  Element.CDATA_SECTION_NODE) {
				Element propNode = (Element) propNodes.item(i);
				String propName = propNode.getNodeName();
				String propValue = propNode.getTextContent();
				properties.put(propName, propValue);
			}
		}
		return properties;
	}
}