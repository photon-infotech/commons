package com.photon.phresco.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactGroup.Type;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class HtmlApplicationProcessor implements ApplicationProcessor {

	@Override
	public void preCreate(ApplicationInfo appInfo) throws PhrescoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preUpdate(ApplicationInfo appInfo) throws PhrescoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postCreate(ApplicationInfo appInfo) throws PhrescoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postUpdate(ApplicationInfo appInfo, List<ArtifactGroup> artifactGroups) throws PhrescoException {
		File pomFile = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator
				+ Constants.POM_NAME);
		ProjectUtils projectUtils = new ProjectUtils();
		projectUtils.deletePluginExecutionFromPom(pomFile);
		List<ArtifactGroup> featuresArtifact = new ArrayList<ArtifactGroup>();
		List<ArtifactGroup> jsArtifact = new ArrayList<ArtifactGroup>();
		if (CollectionUtils.isNotEmpty(artifactGroups)) {
			for (ArtifactGroup artifactGroup : artifactGroups) {
				if (artifactGroup.getType().name().equals(Type.FEATURE.name())) {
					featuresArtifact.add(artifactGroup);
				} else if (artifactGroup.getType().name().equals(Type.JAVASCRIPT.name())
						|| artifactGroup.getType().name().equals(Type.COMPONENT.name())) {
					jsArtifact.add(artifactGroup);
				}
			}
			projectUtils.updatePOMWithModules(pomFile, featuresArtifact);
			projectUtils.updatePOMWithPluginArtifact(pomFile, jsArtifact);
			projectUtils.deletePluginFromPom(pomFile);
			projectUtils.addServerPlugin(appInfo, pomFile);
		}
	}

	@Override
	public void postConfiguration(ApplicationInfo appInfo)
			throws PhrescoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Configuration> preFeatureConfiguration(ApplicationInfo appInfo,
			String featureName) throws PhrescoException {
		try {
			File fXmlFile = new File("C:\\Documents and Settings\\suresh_ma\\Desktop\\sample.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getDocumentElement().getChildNodes();
			return getConfigurations(nodeList);
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		} catch (SAXException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}

	private List<Configuration> getConfigurations(NodeList nList) {
		List<Configuration> configurations = new ArrayList<Configuration>();
		for (int root = 0; root < nList.getLength(); root++) {
			Node nNode = nList.item(root);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) nNode;
				NodeList childNodes = element.getChildNodes();
				for (int child	 = 0; child	 < childNodes.getLength() ; child++) {
					Node ChildNode = childNodes.item(child);
					if (ChildNode.getNodeType() == Node.ELEMENT_NODE) {
						Element childElement = (Element) ChildNode;
						NodeList subChildNodes = childElement.getChildNodes();
						Configuration configuration = new Configuration();
						Properties properties = new Properties();
						String configType = childElement.getNodeName();
						for (int subChild	 = 0; subChild	 < subChildNodes.getLength() ; subChild++) {
							Node subChildNode = subChildNodes.item(subChild);
							if (subChildNode.getNodeType() == Node.ELEMENT_NODE) {
								Element subChildElement = (Element) subChildNode;
								String tagName = subChildElement.getTagName();
								configuration.setType(configType);
								properties.setProperty(tagName, subChildElement.getTextContent());
								configuration.setProperties(properties);
							}
						}
						configurations.add(configuration);
					}
				}
			}
		}
		return configurations;
	}
	
	public static void main(String[] args) throws PhrescoException {
		HtmlApplicationProcessor html = new HtmlApplicationProcessor();
		html.preFeatureConfiguration(null, null);
	}

	@Override
	public void postFeatureConfiguration(ApplicationInfo appInfo,
			List<Configuration> configs, String featureName)
	throws PhrescoException {
		String jsonPath = Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + getFeaturePath(appInfo) + File.separator + featureName + File.separator + "config/";
		Gson gson = new Gson();
		java.lang.reflect.Type jsonType = new TypeToken<Collection<Configuration>>(){}.getType();
		String json = gson.toJson(configs, jsonType);
		if(new File(jsonPath).exists()) {
			try {
				//write converted json data to a file named "info.json"
				FileWriter writer = new FileWriter(jsonPath + "config.json");
				writer.write(json);
				writer.close();
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}
	
	private String getFeaturePath(ApplicationInfo appInfo) throws PhrescoException { 
		String pomPath = Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + Constants.POM_NAME;
		try {
			PomProcessor processor = new PomProcessor(new File(pomPath));
			return processor.getProperty(Constants.POM_PROP_KEY_COMPONENTS_SOURCE_DIR);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	@Override
	public void preBuild(ApplicationInfo appInfo) throws PhrescoException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void postBuild(ApplicationInfo appInfo) throws PhrescoException {
		// TODO Auto-generated method stub
		
	}
}
