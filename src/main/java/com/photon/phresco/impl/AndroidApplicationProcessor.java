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
package com.photon.phresco.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.plist.XMLPropertyListConfiguration;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.pom.POMConstants;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Dependency;
import com.phresco.pom.model.Resource;
import com.phresco.pom.util.PomProcessor;

public class AndroidApplicationProcessor extends AbstractApplicationProcessor implements Constants{
	private static final String NODE_APP_NAME = "app_name";
	private static final String PATH_STRINGS_XML = "/values/strings.xml";
	private static final String HYPHEN_SPLIT = "-";
	private static final String NAME = "name";
	private static final String NODE_STRING = "string";
	private static final String UNIT_TEST_POM_XML = "/test/unit/pom.xml";
	private static final String PERFORMANCE_TEST_POM_XML = "/test/performance/pom.xml";
	private static final String FUNCTIONAL_TEST_POM_XML = "/test/functional/pom.xml";
	private static final String POM = "pom.xml";
	
	private static final String COMPONENT = "COMPONENT";
	private static final String SOURCE_POM_XML = "/source/pom.xml";
	private static final String SOURCE = "source";
	private static final String ASSETS = "assets";
	
	private String ANDROID_VERSION = "android.version";
	
	// android application processor configuration
	private static final String PLIST = "feature-manifest.xml";
	private static final String ENVIRONMENT_PLIST = "environment.xml";
	
	static {
		System.out.println("Loading AndroidApplicationProcessor release build .... ");
	}
	
	@Override
	public void postCreate(ApplicationInfo appInfo) throws PhrescoException {
		try {
			String projectHome = Utility.getProjectHome() + appInfo.getAppDirName();
			
			File path = new File(projectHome);
			
		    updateAndroidVersion(path, appInfo, "", "");
		   
			File projHome = new File(projectHome);
			
			updatePOM(projHome, "", "");
			
            
			// update String.xml app_name with user defined name
			updateAppName(projHome, appInfo.getName());
			
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	@Override
	public void postUpdate(ApplicationInfo appInfo, List<ArtifactGroup> artifactGroups, List<ArtifactGroup> deletedFeatures) throws PhrescoException {
		//		extractPilots(info, path, technology);
		 System.out.println("postUpdate started");
		ProjectUtils projectUtils = new ProjectUtils();
		String rootModulePath = "";
		String subModuleName = "";
		if (StringUtils.isNotEmpty(appInfo.getRootModule())) {
			rootModulePath = Utility.getProjectHome() + appInfo.getRootModule();
			subModuleName = appInfo.getAppDirName();
		} else {
			rootModulePath = Utility.getProjectHome() + appInfo.getAppDirName();
		}
		
		File phrescoPomFile = Utility.getPomFileLocation(rootModulePath, subModuleName);
		ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, subModuleName);
		File sourceFolderLocation = Utility.getSourceFolderLocation(projectInfo, rootModulePath, subModuleName);
		File testFolderLocation = Utility.getTestFolderLocation(projectInfo, rootModulePath, subModuleName);
		String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, subModuleName);
		File pomFile = new File(sourceFolderLocation.getPath() + File.separator + SOURCE + File.separator + appInfo.getPomFile());
		
		
		 deletePluginExecutionFromPom(pomFile);
		 
		if(CollectionUtils.isNotEmpty(artifactGroups)) {
			 
			projectUtils.updatePOMWithPluginArtifact( pomFile , pomFile , artifactGroups);
			
		      BufferedReader breader = extractComponent(pomFile, sourceFolderLocation);
		
		      try {
			    String line = "";
			   while ((line = breader.readLine()) != null) {
				  if (line.startsWith("[ERROR]")) {
					System.err.println(line);
				}
			  }
		     } catch (IOException e) {
			    System.out.println(" Error in extraction ");
			    throw new PhrescoException(e);
		     }
	          
		updatePOM(sourceFolderLocation, testFolderLocation.getPath(),dotPhrescoFolderPath);
		updateAndroidVersion(sourceFolderLocation, appInfo,testFolderLocation.getPath(), phrescoPomFile.getPath());
		// update String.xml app_name with user defined name
		updateAppName(sourceFolderLocation, appInfo.getName());
	  }
	}
	
	@Override
	public List<Configuration> preConfiguration(ApplicationInfo appInfo, String featureName, String envName) throws PhrescoException {
		
		File plistFile = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + SOURCE + getThirdPartyFolder(appInfo) + File.separator + featureName + File.separator+ SOURCE + File.separator +ASSETS + File.separator +PLIST );
		
		try {
			
			return getConfigFromXml(plistFile.getPath());
			
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	@Override
	public void postConfiguration(ApplicationInfo appInfo, List<Configuration> configs) throws PhrescoException {
		File ConfigFilePath = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + Constants.DOT_PHRESCO_FOLDER + File.separator + Constants.CONFIGURATION_INFO_FILE);
		try {
			
			ConfigManager configManager = new ConfigManagerImpl(ConfigFilePath);
			List<Environment> environments = configManager.getEnvironments();
			for (Environment environment : environments) {
				String environmenName = "";
				Map<String, List<Properties>> values = new HashMap<String, List<Properties>>();
				String environmentName = environment.getName();
				File file = new File(Utility.getProjectHome() + appInfo.getAppDirName() + getThirdPartyFolder(appInfo) + File.separator + environmentName);
				if(!file.exists()) {
					file.mkdir();
				}
				List<Configuration> configurations = environment.getConfigurations();
				if (CollectionUtils.isNotEmpty(configurations)) {
					String plistFile = file.getPath() + File.separator + ENVIRONMENT_PLIST;
					XMLPropertyListConfiguration plist = new XMLPropertyListConfiguration();
					for (Configuration configuration : configurations) {
						if(configuration != null) {
							String configType = configuration.getType();
							environmenName = environment.getName();
							Properties properties = configuration.getProperties();
							properties.setProperty(NAME, configuration.getName());
							if (values.containsKey(configType)) {
								List<Properties> list = values.get(configType);
								list.add(properties);
								values.put(configType, list);
							} else {
								List<Properties> listProps = new ArrayList<Properties>();
								listProps.add(properties);
								values.put(configType, listProps);
							}
						}
					}
					plist.addProperty(environmenName, values);
					plist.save(plistFile);
				}
			}
		} catch (ConfigurationException e) {
			throw new PhrescoException(e);
		} catch (org.apache.commons.configuration.ConfigurationException e) {
			throw new PhrescoException(e);
		}
	}

	@Override
	public List<Configuration> preFeatureConfiguration(ApplicationInfo appInfo, String featureName) throws PhrescoException {
		
		String plistDir = Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + SOURCE + getThirdPartyFolder(appInfo) + File.separator + featureName + File.separator+ SOURCE + File.separator +ASSETS ;
		

    	File plistFile =null;
		
		try {
			
			return getConfigFromXml(plistFile.getPath());
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	

	@Override
	public void postFeatureConfiguration(ApplicationInfo appInfo, List<Configuration> configs, String featureName)
	throws PhrescoException {
		try {
			
			String plistPath = Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + SOURCE + getThirdPartyFolder(appInfo) + File.separator + featureName + File.separator+ SOURCE + File.separator +ASSETS + File.separator +PLIST;
			
			storeConfigObjAsPlist(configs.get(0), plistPath);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}

	}
	

	
   
	public void deletePluginExecutionFromPom(File pomFile) throws PhrescoException {
		try {
			
			 PomProcessor processor = new PomProcessor(pomFile);
			 processor.deletePlugin(DEPENDENCY_PLUGIN_GROUPID, DEPENDENCY_PLUGIN_ARTIFACTID);
			 processor.save();
			System.out.println(" Deleting Plugin Execution from pom>> end "); 
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}
	
	public void deleteFeatureDependenciesFromPom(File sourceFolder, List<ArtifactGroup> deletedFeatures) throws PhrescoException {
		 
		String pomDir = sourceFolder.getPath() + File.separator + SOURCE + File.separator + Constants.POM_NAME;
		try {
			PomProcessor processor = new PomProcessor(new File(pomDir));
			List<Dependency> dependencies = processor.getModel().getDependencies().getDependency();
			if(CollectionUtils.isNotEmpty(dependencies)) {
				for (ArtifactGroup artifactGroup : deletedFeatures) {
					processor.deleteDependency(artifactGroup.getGroupId(), artifactGroup.getArtifactId(), artifactGroup.getPackaging());
				}
			}
			processor.save();
			
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}
	
private BufferedReader extractComponent(File phrescoPomFile, File sourceFolder) throws PhrescoException {
		System.out.println("extractFeature start ... ");
		BufferedReader breader = null;
//		String pomFileName = Utility.getPomFileName(appInfo);
		StringBuilder sb = new StringBuilder();
		sb.append(MVN_COMMAND);
		sb.append(STR_BLANK_SPACE);
		sb.append(PHASE);
		if(!POM_NAME.equals(phrescoPomFile.getName())) {
			sb.append(STR_BLANK_SPACE);
			sb.append(HYPHEN_F);
			sb.append(STR_BLANK_SPACE);
			sb.append(phrescoPomFile.getName());
		}
		breader = Utility.executeCommand(sb.toString(), sourceFolder + File.separator + SOURCE );
		System.out.println("extractFeature end... ");
		return breader;
	}

	public void storeConfigObjAsPlist(Configuration keyValueObj, String plistPath) throws Exception {
		Properties properties = keyValueObj.getProperties();
		System.out.println("properties > " + properties);
		
		UpdatingConfigXml plist = new UpdatingConfigXml(plistPath);
		
		
		Enumeration key = properties.keys();
		while (key.hasMoreElements()) {
			String xmlTag = (String) key.nextElement();
			String tagValue = properties.get(xmlTag).toString();
			
			System.out.println("str--key > " + xmlTag);
			System.out.println("str--Value > " + properties.get(tagValue));
			
			plist.updateStyle(xmlTag, tagValue);
		}
	}

	private String getThirdPartyFolder(ApplicationInfo appInfo) throws PhrescoException { 
		File pomPath = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + SOURCE + File.separator + Utility.getPomFileName(appInfo));
		
		try {
			PomProcessor processor = new PomProcessor(pomPath);
			String property = processor.getProperty(Constants.POM_PROP_KEY_COMPONENTS_SOURCE_DIR);
			if(StringUtils.isNotEmpty(property)) {
				return property;
			}
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
		return "";
	}
        @SuppressWarnings("unchecked")
		private List<Configuration> getConfigFromXml(String plistPath)
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
				              System.out.println("-->getNodeName " + key);
				              String value = nNode1.getTextContent();
				              System.out.println("-->getNodeValue " + value);
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
        
	public void updateAndroidVersion(File sourceFolder, ApplicationInfo appInfo, String testFolder, String phrescoPomFile) throws PhrescoException {
		PomProcessor processor;
//		File pomPath = new File(sourceFolder ,Utility.getPhrescoPomFile(appInfo));
		List<File> fileList = new ArrayList<File>();
//		if(!pomPath.exists()) {
//			return;
//		}
		
		if(StringUtils.isEmpty(testFolder)) {
			fileList.add(new File(sourceFolder, Utility.getPhrescoPomFile(appInfo)));
			fileList.add(new File(sourceFolder, SOURCE_POM_XML));
			fileList.add(new File(sourceFolder, FUNCTIONAL_TEST_POM_XML));
			fileList.add(new File(sourceFolder, PERFORMANCE_TEST_POM_XML));
			fileList.add(new File(sourceFolder, UNIT_TEST_POM_XML));
			
		} else {
			fileList.add(new File(phrescoPomFile));
			fileList.add(new File(sourceFolder, SOURCE_POM_XML));
			fileList.add(new File(testFolder, FUNCTIONAL_TEST_POM_XML));
			fileList.add(new File(testFolder, PERFORMANCE_TEST_POM_XML));
			fileList.add(new File(testFolder, UNIT_TEST_POM_XML));
		}
		for (File pomFile : fileList) {
			if(pomFile.exists()) {
				try {
					processor = new PomProcessor(pomFile);
					String selectedVersion = appInfo.getTechInfo().getVersion();
					processor.setProperty(ANDROID_VERSION, selectedVersion);
					processor.save();
				} catch (Exception e) {
					throw new PhrescoException(e);
				}
			}
		}
	}

	private boolean updateAppName(File path, String appName) throws PhrescoException {
		try {
			String appPath = path + File.separator + SOURCE ;
			File projectHomePOM = new File(appPath, POM);
			PomProcessor processor = new PomProcessor(projectHomePOM);
			List<Resource> resources = processor.getModel().getBuild().getResources().getResource();
			String directory = "";
			if (resources != null) {
				Resource resource = resources.get(0);
				directory = resource.getDirectory();
				
				
			} 
			File stringsXml = new File(appPath, directory + PATH_STRINGS_XML);
			List<String> appNames = Arrays.asList(appName.split(HYPHEN_SPLIT));
			return updateAppName(stringsXml, NODE_APP_NAME, appNames.get(0));
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * @param stringsXml file path where the strings.xml present.
	 * @return 
	 * @throws PhrescoException
	 */
	private boolean updateAppName(File stringsXml, String stringId, String appName) throws PhrescoException {
		try {
			if (stringsXml.isFile()) {
				SAXBuilder builder = new SAXBuilder();
				Document document = (Document) builder.build(stringsXml);
				Element rootNode = document.getRootElement();
				List list = rootNode.getChildren(NODE_STRING);
				for (int i = 0; i < list.size(); i++) {
					Element node = (Element) list.get(i);
					String name = node.getAttributeValue(NAME);
					if (stringId.equals(name)) { // "app_name"
						node.setText(appName); //Shopping Cart
						XMLOutputter xmlOutput = new XMLOutputter();
						// display nice nice
						xmlOutput.setFormat(Format.getPrettyFormat());
						xmlOutput.output(document, new FileWriter(stringsXml));
						break;
					}
				}
				return true;
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return false;
	}

	/**
	 * @param path
	 *            file path where the pom.xml present.
	 * @return 
	 * @throws PhrescoException
	 * @throws PhrescoPomException 
	 * @throws JAXBException 
	 */
	private boolean updatePOM(File srcFolder, String testFolder, String dotPhrescoPath) throws PhrescoException {
		File projectHome = null;
		File testFunctionalPom = null;
		File testUnitPom = null;
		File testPerformancePom = null;
		if(StringUtils.isEmpty(testFolder)) {
			 projectHome = new File(srcFolder, SOURCE_POM_XML);
			 testFunctionalPom = new File(srcFolder, FUNCTIONAL_TEST_POM_XML);
			 testUnitPom = new File(srcFolder, UNIT_TEST_POM_XML);
			 testPerformancePom = new File(srcFolder, PERFORMANCE_TEST_POM_XML);
		} else {
		 projectHome = new File(srcFolder, SOURCE_POM_XML);
		 testFunctionalPom = new File(testFolder, FUNCTIONAL_TEST_POM_XML);
		 testUnitPom = new File(testFolder, UNIT_TEST_POM_XML);
		 testPerformancePom = new File(testFolder, PERFORMANCE_TEST_POM_XML);
		}
		boolean functionalTest = updateTestPom(testFunctionalPom, projectHome, dotPhrescoPath);
		boolean unitTest = updateTestPom(testUnitPom, projectHome, dotPhrescoPath);
		boolean performanceTest = updateTestPom(testPerformancePom, projectHome, dotPhrescoPath);
		if (Boolean.TRUE.equals(functionalTest) || Boolean.TRUE.equals(unitTest) || Boolean.TRUE.equals(performanceTest)  ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param testPom
	 * @param projectPom
	 * @param projectHome 
	 * @return 
	 * @throws PhrescoException 
	 * @throws JDOMException
	 * @throws IOException
	 * @throws JAXBException 
	 * @throws PhrescoPomException 
	 */
	private boolean updateTestPom(File projectPom, File projectHome, String dotPhrescoPath) throws PhrescoException {
		SAXBuilder builder = new SAXBuilder();
		if(!projectPom.exists()) {
			return false;
		}
		try {
			String oldArtifactId = getOldArtifactId(projectHome.getParent(), dotPhrescoPath);
			Document projectDoc = builder.build(projectHome);
			Element projectRootNode = projectDoc.getRootElement();
			Element artifact = getNode(projectRootNode, POMConstants.ARTIFACT_ID);
			Element group = getNode(projectRootNode, POMConstants.GROUP_ID);
			Element version = getNode(projectRootNode, POMConstants.VERSION);
			Element name = getNode(projectRootNode, POMConstants.NAME);
			PomProcessor processor = new PomProcessor(projectPom);
			processor.setArtifactId(artifact.getText());
			processor.setName(name.getText());
			if(StringUtils.isNotEmpty(oldArtifactId)) {
				processor.deleteDependency(group.getText(), oldArtifactId, POMConstants.JAR);
				processor.deleteDependency(group.getText(), oldArtifactId, POMConstants.APK);
			}
			processor.addDependency(group.getText(), artifact.getText(),  version.getText() , POMConstants.PROVIDED , POMConstants.JAR, "");
			processor.addDependency(group.getText(), artifact.getText(),  version.getText() , POMConstants.PROVIDED , POMConstants.APK, "");
			processor.save();

		} catch (JDOMException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
		return true;
	}

	private String getOldArtifactId(String projectHome, String dotPhrescoPath) throws PhrescoException {
		File backupFile = null;
		if(StringUtils.isEmpty(dotPhrescoPath)) {
			backupFile = new File(projectHome + File.separator + Constants.DOT_PHRESCO_FOLDER + File.separator + Constants.PROJECT_INFO_BACKUP_FILE);
		} else {
			backupFile = new File(dotPhrescoPath + File.separator + Constants.PROJECT_INFO_BACKUP_FILE);
		}
		
		if(!backupFile.exists()) {
			return null;
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(backupFile));
			ProjectInfo projectInfo = new Gson().fromJson(reader, ProjectInfo.class);
			return projectInfo.getAppInfos().get(0).getCode();
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		} finally {
			Utility.closeReader(reader);
		}
	}

	/**
	 * @param rootNode
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static Element getNode(Element rootNode, String nodeName) {
		Element dependencies = rootNode.getChild(nodeName);
		// sometime, this doesn't work. So as workaround this stint.
		if (dependencies == null) {
			List children = rootNode.getChildren();
			for (Object object : children) {
				if ((object instanceof Element) && ((Element) object).getName().equals(nodeName)) {
					dependencies = (Element) object;
					break;
				}
			}
		}
		return dependencies;
	}
}
