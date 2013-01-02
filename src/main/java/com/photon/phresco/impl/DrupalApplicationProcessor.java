package com.photon.phresco.impl;

import java.io.File;
import java.util.*;

import javax.xml.parsers.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.*;
import org.apache.commons.lang.*;
import org.w3c.dom.*;

import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.CoreOption;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class DrupalApplicationProcessor implements ApplicationProcessor{
	
	private static final String XML = "feature-manifest.xml";
	private static final String FEATURES = "features";
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
		File path = new File(Utility.getProjectHome() + appInfo.getAppDirName());
		updateDrupalVersion(path, appInfo);
		
	}

	@Override
	public void postUpdate(ApplicationInfo appInfo,
			List<ArtifactGroup> artifactGroups) throws PhrescoException {
		File pomFile = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + Constants.POM_NAME);
		ProjectUtils projectUtils = new ProjectUtils();
		projectUtils.deletePluginExecutionFromPom(pomFile);
		if(CollectionUtils.isNotEmpty(artifactGroups)) {
			projectUtils.updatePOMWithPluginArtifact(pomFile, artifactGroups);
			excludeModule(appInfo, artifactGroups);
		}
	}
	
	private void updateDrupalVersion(File path, ApplicationInfo info) throws PhrescoException {
		try {
			File xmlFile = new File(path, Constants.POM_NAME);
			PomProcessor processor = new PomProcessor(xmlFile);
			String selectedVersion = info.getTechInfo().getVersion();
			processor.setProperty(Constants.DRUPAL_VERSION, selectedVersion);
			processor.save();
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}
	
	private void excludeModule(ApplicationInfo appInfo, List<ArtifactGroup> artifactGroups) throws PhrescoException {
		try {
			File projectPath = new File(Utility.getProjectHome()+ File.separator + appInfo.getAppDirName() + File.separator + Constants.POM_NAME);
			PomProcessor processor = new PomProcessor(projectPath);
			StringBuilder exclusionStringBuff = new StringBuilder();
			StringBuilder exclusionValueBuff = new StringBuilder();
			if (CollectionUtils.isEmpty(artifactGroups)) {
				return;
			}
			for (ArtifactGroup artifactGroup : artifactGroups) {
				List<CoreOption> appliesTo = artifactGroup.getAppliesTo();
				for (CoreOption coreOption : appliesTo) {
					if (coreOption.isCore()) {
						exclusionValueBuff.append(artifactGroup.getName().toLowerCase());
						exclusionValueBuff.append(",");
						exclusionStringBuff.append("**\\");
						exclusionStringBuff.append(artifactGroup.getName().toLowerCase());
						exclusionStringBuff.append("\\**");
						exclusionStringBuff.append(",");
					}
				}
			}
			String exclusionValue = exclusionStringBuff.toString();
			if (exclusionValue.lastIndexOf(',') != -1) {
				exclusionValue = exclusionValue.substring(0, exclusionValue.lastIndexOf(','));
			}
			
			String exclusiontoolValue = exclusionValueBuff.toString();
			if (exclusiontoolValue.lastIndexOf(',') != -1) {
				exclusiontoolValue = exclusiontoolValue.substring(0, exclusiontoolValue.lastIndexOf(','));
			}
			processor.setProperty("sonar.exclusions", exclusionValue);
			processor.setProperty("sonar.phpDepend.argumentLine", "--ignore=" + exclusiontoolValue);
			processor.setProperty("sonar.phpPmd.argumentLine", "--exclude" + exclusiontoolValue);
			processor.save();
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
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
		System.out.println("prefeature configuration !!!!!!! ");
		File featureManifest = new File(Utility.getProjectHome() + appInfo.getAppDirName() + getThirdPartyFolder(appInfo) + File.separator + featureName + File.separator + XML);
		List<Configuration> configs = getConfigObjFromXml(featureManifest.getPath());
		return configs;
	}
	
	public List<Configuration> getConfigObjFromXml(String featureManifestXml) throws PhrescoException {
		List<Configuration> configs = new ArrayList<Configuration>();
		try {
			System.out.println("featureManifestXml => " + featureManifestXml);
			File featureManifestXmlFile = new File(featureManifestXml);
			
	        Configuration config = null;
	        if (featureManifestXmlFile.isFile()) {
	            config = new Configuration(featureManifestXmlFile.getName(), FEATURES);
	        } else {
	            return Collections.emptyList();
	        }
	        
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(featureManifestXmlFile);
			doc.getDocumentElement().normalize();
	 
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			String rootElemName = doc.getDocumentElement().getAttribute("name");
			String rootElemDescription = doc.getDocumentElement().getAttribute("description");
			System.out.println("rootElemName => " + rootElemName);
			System.out.println("rootElemDescription => " + rootElemDescription);
			NodeList nList = doc.getElementsByTagName("configuration");
			System.out.println("-----------------------");
			Properties properties = new Properties();
			for (int temp = 0; temp < nList.getLength(); temp++) {
			   Node nNode = nList.item(temp);
			   // get attributes
			   if (nNode.hasAttributes()) {
				   NamedNodeMap attributes = nNode.getAttributes();
				   Node name = attributes.getNamedItem("name");
				   if (name != null) {
					   System.out.println(name.getNodeValue());
				   }
				   Node required = attributes.getNamedItem("required");
				   if (required != null) {
					   System.out.println(required.getNodeValue());
				   }				   
			   }
			   
			   // get config object values
			   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				   NodeList childNodes = nNode.getChildNodes();
				   // get all the key and value pairs
				   for (int temp1 = 0; temp1 < childNodes.getLength(); temp1++) {
					   Node childNode = childNodes.item(temp1);
					   if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						   System.out.println("childNode name => " + childNode.getNodeName());
						   System.out.println("childNode name => " + childNode.getTextContent());
						   String nodeName = childNode.getNodeName();
						   String textContent = childNode.getTextContent();
						   properties.put(nodeName, textContent);
					   }
				   }
			   }
			}
	        
	        config.setProperties(properties);
	        configs.add(config);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PhrescoException(e);
		}
		return configs;
	}

	private String getThirdPartyFolder(ApplicationInfo appInfo) throws PhrescoException { 
		File pomPath = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + Constants.POM_NAME);
		try {
			PomProcessor processor = new PomProcessor(pomPath);
			String property = processor.getProperty(Constants.POM_PROP_KEY_MODULE_SOURCE_DIR);
			if(StringUtils.isNotEmpty(property)) {
				return property;
			}
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
		return "";
	}
	
	@Override
	public void postFeatureConfiguration(ApplicationInfo appInfo,
			List<Configuration> configs, String featureName)
			throws PhrescoException {
		System.out.println("post feature configuration !!!!!!! ");
		try {
			String propertyValue = getPropertyValue(appInfo, Constants.POM_PROP_KEY_SQL_FILE_DIR);
			System.out.println(propertyValue);
			File featureSqlFile = new File(Utility.getProjectHome() + appInfo.getAppDirName() + propertyValue + File.separator + "configuration.sql");
			storeConfigObj(configs, featureSqlFile);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	private void storeConfigObj(List<Configuration> configs, File featureSqlFile) throws PhrescoException { 
		try {
			String query = "";
			for (Configuration configuration : configs) {
				String tableQuery = "";
				String fieldQuery = "";
				String valueQuery = "";
				String constructedQuery = "";
			    Properties properties = configuration.getProperties();
			    Enumeration em = properties.keys();
			    while (em.hasMoreElements()) {
			        String key = (String) em.nextElement();
			        Object value = properties.get(key);
			        System.out.println("Key => " + key);
			        System.out.println("value => " + value);
			        if ("tableName".equals(key)) {
			        	tableQuery = tableQuery + " delete from " + value.toString() + ";";
			        	tableQuery = tableQuery + " insert into `" + value.toString() + "`";
			        } else if ("variableName".equals(key)) {
			        	fieldQuery = " (`" + value.toString() + "`)";
			        } else if ("defaultValue".equals(key)) {
			        	valueQuery = " values ('"+ value.toString() + "');";
			        } else {
			        	// other key value pairs ....
			        }
			    }
			    System.out.println("query  => " + tableQuery + fieldQuery + valueQuery);
		        constructedQuery = tableQuery + fieldQuery + valueQuery;
		        query =  query + constructedQuery;
			}
			System.out.println("featureSqlFile => " + featureSqlFile);
			FileUtils.writeStringToFile(featureSqlFile, query);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PhrescoException(e);
		}
	}
	
	private String getPropertyValue(ApplicationInfo appInfo, String propertyKey) throws PhrescoException { 
		File pomPath = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + Constants.POM_NAME);
		try {
			PomProcessor processor = new PomProcessor(pomPath);
			String property = processor.getProperty(propertyKey);
			if(StringUtils.isNotEmpty(property)) {
				return property;
			}
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
		return "";
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
