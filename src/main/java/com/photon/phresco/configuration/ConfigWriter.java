/**
 * Phresco Commons
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photon.phresco.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
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

import org.hibernate.validator.internal.util.privilegedactions.GetConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.util.Utility;

public class ConfigWriter {

	private ConfigReader reader = null;
	private Document document = null;
	private Element rootElement = null;

	/**
	 * Constructor of ConfigWriter
	 * @param reader
	 * @param newFile
	 * @throws ConfigurationException 
	 * @throws Exception
	 */
	public ConfigWriter(ConfigReader reader, boolean newFile) throws ConfigurationException {
		this.reader = reader;
		if (newFile) {
			createNewXml();
		} else {
			document = reader.getDocument();
			rootElement = (Element) document.getElementsByTagName("environments").item(0);
		}
	}


	/**
	 * Created the configuration xml of selected environment using File object
	 * @param configXmlPath
	 * @param selectedEnvStr
	 * @throws ConfigurationException 
	 * @throws Exception
	 */
	public void saveXml(File configXmlPath, String selectedEnvStr) throws ConfigurationException {
		createConfiguration(selectedEnvStr);
		writeXml(getFileOutoutStream(configXmlPath));
	}
	
	/**
	 * Created the configuration xml of selected environment with Selected Webservice using File object
	 * @param configXmlPath
	 * @param selectedEnvStr
	 * @param selectedWebService
	 * @throws ConfigurationException 
	 * @throws Exception
	 */
	public void saveXml(File configXmlPath, String selectedEnvStr, String selectedWebService) throws ConfigurationException {
		createConfiguration(selectedEnvStr, selectedWebService);
		writeXml(getFileOutoutStream(configXmlPath));
	}
	

	/**
	 * Append the selected environment to the reader's document
	 * @param srcReaderToAppend
	 * @param selectedEnvStr
	 * @throws ConfigurationException 
	 * @throws Exception
	 */
	public void saveXml(ConfigReader srcReaderToAppend, String selectedEnvStr) throws ConfigurationException {
		document = srcReaderToAppend.getDocument();
		rootElement = (Element) document.getElementsByTagName("environments").item(0);
		createConfiguration(selectedEnvStr);
		try {
			writeXml(new FileOutputStream(srcReaderToAppend.getConfigFile()));
		} catch (FileNotFoundException e) {
			throw new ConfigurationException(e);
		}
	}
	
	/**
	 * Append the selected environment to the reader's document
	 * @param srcReaderToAppend
	 * @param selectedEnvStr
	 * @param selectedWebservice
	 * @throws ConfigurationException 
	 * @throws Exception
	 */
	public void saveXml(ConfigReader srcReaderToAppend, String selectedEnvStr, String selectedWebService) throws ConfigurationException {
		document = srcReaderToAppend.getDocument();
		rootElement = (Element) document.getElementsByTagName("environments").item(0);
		createConfiguration(selectedEnvStr, selectedWebService);
		try {
			writeXml(new FileOutputStream(srcReaderToAppend.getConfigFile()));
		} catch (FileNotFoundException e) {
			throw new ConfigurationException(e);
		}
	}
	
	private FileOutputStream getFileOutoutStream(File file) throws ConfigurationException {
		try {
			return new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			throw new ConfigurationException(e);
		}
	}
	/**
	 * Created the configuration xml of selected environment using OutputStream object
	 * @param fos
	 * @param selectedEnvStr
	 * @throws ConfigurationException 
	 * @throws Exception
	 */
	public void saveXml(OutputStream fos, String selectedEnvStr) throws ConfigurationException {
		createConfiguration(selectedEnvStr);
		writeXml(fos);
	}

	/**
	 * Read the Environments to create the configurations
	 * @param selectedEnvStr
	 * @param selectedWebService
	 * @throws Exception
	 */
	private void createConfiguration(String selectedEnvStr, String selectedWebService) {
		String[] envs = selectedEnvStr.split(",");
		for (String envName : envs) {
			List<Configuration> configByEnv = reader.getConfigByEnv(envName);
			if (configByEnv != null && !configByEnv.isEmpty()) {
				boolean defaultEnv = envName.equals(reader.getDefaultEnvName());
				createConfigurations(configByEnv, envName, defaultEnv, selectedWebService);
			}
		}
	}
	
	/**
	 * Read the Environments to create the configurations
	 * @param selectedEnvStr
	 * @throws Exception
	 */
	private void createConfiguration(String selectedEnvStr) {
		String[] envs = selectedEnvStr.split(",");
		for (String envName : envs) {
			List<Configuration> configByEnv = reader.getConfigByEnv(envName);
			if (configByEnv != null && !configByEnv.isEmpty()) {
				boolean defaultEnv = envName.equals(reader.getDefaultEnvName());
				createConfigurations(configByEnv, envName, defaultEnv);
			}
		}
	}

	/**
	 * Create the Configuration element of selected Environments
	 * @param configList
	 * @param envName
	 * @param defaultEnv
	 * @throws Exception
	 */
	private void createConfigurations(List<Configuration> configList, String envName, boolean defaultEnv) {
		Element envNode = document.createElement("environment");
		envNode.setAttribute("name", envName);
		envNode.setAttribute("default", Boolean.toString(defaultEnv));
		for (Configuration configuration : configList) {
			Element configNode = document.createElement(configuration.getType());
			configNode.setAttribute("name", configuration.getName());
			createProperties(configNode, configuration.getProperties());
			envNode.appendChild(configNode);
		}
		rootElement.appendChild(envNode);
	}
	
	/**
	 * Create the Configuration element of selected Environments based on selected webservice
	 * @param configList
	 * @param envName
	 * @param defaultEnv
	 * @throws Exception
	 */
	private void createConfigurations(List<Configuration> configList, String envName, boolean defaultEnv, String selectedWebService) {
		Element envNode = document.createElement("environment");
		envNode.setAttribute("name", envName);
		envNode.setAttribute("default", Boolean.toString(defaultEnv));
		for (Configuration configuration : configList) {
			if(selectedWebService.equals(configuration.getName())) {
				Element configNode = document.createElement(configuration.getType());
				configNode.setAttribute("name", configuration.getName());
				createProperties(configNode, configuration.getProperties());
				envNode.appendChild(configNode);
			}	
		}
		rootElement.appendChild(envNode);
	}

	/**
	 * Write the xml document using OutputStream
	 * @param fos
	 * @throws ConfigurationException 
	 * @throws TransformerException
	 * @throws Exception
	 */
	protected void writeXml(OutputStream fos) throws ConfigurationException {
		try {
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
			}
		} finally {
			if (fos != null) {
				Utility.closeStream(fos);
			}
		}
	}

	/**
	 * Create the new Xml Dom object
	 * @throws ConfigurationException 
	 * @throws Exception
	 */
	protected void createNewXml() throws ConfigurationException {
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(false);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            document = builder.newDocument();
            rootElement = document.createElement("environments");
            document.appendChild(rootElement);
		} catch (ParserConfigurationException e) {
			throw new ConfigurationException(e);
		}
	}

	protected Document getDocument() {
		return document;
	}

	protected Element getRootElement() {
		return rootElement;
	}
	
	/**
	 * create the properties to the configuration element
	 * @param configNode
	 * @param properties
	 */
	private void createProperties(Element configNode, Properties properties) {
		Set<Object> keySet = properties.keySet();
		for (Object key : keySet) {
			String value = (String) properties.get(key);
			Element propNode = document.createElement(key.toString());
			propNode.setTextContent(value);
			configNode.appendChild(propNode);
		}
	}
}
