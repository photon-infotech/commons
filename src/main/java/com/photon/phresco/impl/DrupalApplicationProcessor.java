package com.photon.phresco.impl;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.xpath.*;

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
	
	private static final String CONFIGURATION_SQL = "configuration.sql";
	private static final String MYSQL = "mysql";
	private static final String NAME = "name";
	private static final String CONFIG_TAG = "configuration";
	private static final String CONFIG_XPATH_END_TAG = "']";
	private static final String CONFIG_XPATH = "//configurations/configuration[@name='";
	private static final String SEMI_COLON = ";";
	private static final String INSERT_INTO_END_TAG = "";
	private static final String VARIABLE_END_TAG = "`)";
	private static final String VARIABLE_START_TAG = " (`";
	private static final String VALUES_END_TAG = "');";
	private static final String VALUES_START_TAG = " values ('";
	private static final String INSERT_INTO = " insert into ";
	private static final String DELETE_FROM = " delete from ";
	private static final String DEFAULT_VALUE = "defaultValue";
	private static final String VARIABLE_NAME = "variableName";
	private static final String TABLE_NAME = "tableName";
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
		File featureManifest = new File(Utility.getProjectHome() + appInfo.getAppDirName() + getThirdPartyFolder(appInfo) + File.separator + featureName + File.separator + XML);
		List<Configuration> configs = getConfigObjFromXml(featureManifest.getPath());
		return configs;
	}
	
	public List<Configuration> getConfigObjFromXml(String featureManifestXml) throws PhrescoException {
		List<Configuration> configs = new ArrayList<Configuration>();
		try {
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
	 
			NodeList nList = doc.getElementsByTagName(CONFIG_TAG);
			Properties properties = new Properties();
			for (int temp = 0; temp < nList.getLength(); temp++) {
			   Node nNode = nList.item(temp);
			   // get attributes
			   if (nNode.hasAttributes()) {
				   NamedNodeMap attributes = nNode.getAttributes();
				   Node name = attributes.getNamedItem(NAME);
				   if (name != null) {
					   System.out.println("name.getNodeValue() => " + name.getNodeValue());
					   Element eElement = (Element) nNode;
					   String defaultValue = getTagValue(DEFAULT_VALUE, eElement);
					   System.out.println("defaultValue => " + defaultValue);
					   properties.put(name.getNodeValue(), defaultValue);
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

	private static String getTagValue(String sTag, Element eElement) {
		System.out.println("sTag => " + sTag);
		String tagValue = "";
		NodeList elementsByTagName = eElement.getElementsByTagName(sTag);
		System.out.println("elementsByTagName => " + elementsByTagName);
		if (elementsByTagName != null) {
			NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		    Node nValue = (Node) nlList.item(0);
		    tagValue = nValue.getNodeValue();
		}
		return tagValue;
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
			File featureManifest = new File(Utility.getProjectHome() + appInfo.getAppDirName() + getThirdPartyFolder(appInfo) + File.separator + featureName + File.separator + XML);
			File featureSqlDir = new File(Utility.getProjectHome() + appInfo.getAppDirName() + propertyValue);
//			File featureSqlFile = new File(Utility.getProjectHome() + appInfo.getAppDirName() + propertyValue + File.separator + "configuration.sql");
			storeConfigObj(configs, featureManifest, featureSqlDir);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	private void storeConfigObj(List<Configuration> configs, File featureManifestXmlFile, File featureSqlDir) throws PhrescoException { 
		try {
			if (!featureManifestXmlFile.isFile()) {
				throw new PhrescoException("manifest file is not available ");
			}
			
			// Document
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(featureManifestXmlFile);
			doc.getDocumentElement().normalize();
			
			// xpath
			XPathFactory factory= XPathFactory.newInstance();
            XPath xPathInstance = factory.newXPath();
            
//			String queryStr = "";
			List<String> sqlLines = new ArrayList<String>();
			for (Configuration configuration : configs) {
				System.out.println(" @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ");
			    Properties properties = configuration.getProperties();
			    Enumeration em = properties.keys();
			    while (em.hasMoreElements()) {
					String tableQuery = "";
					String fieldQuery = "";
					String valueQuery = "";
					String constructedQuery = "";
			    	
			        String key = (String) em.nextElement();
			        Object object = properties.get(key);
			        
			        System.out.println("post feature config key => " + key);
			        // get config object for this key
			        String xPathQuery= CONFIG_XPATH + key + CONFIG_XPATH_END_TAG;			        
			        XPathExpression xPathExpression = xPathInstance.compile(xPathQuery);
			        //evalute the xpath query in the entire xml document and define the return type
		            Object results = xPathExpression.evaluate(doc, XPathConstants.NODESET);
		            NodeList nList = (NodeList) results;
		            
		            // config objects
		            for (int i = 0; i < nList.getLength(); i++) {
		                Node nNode = nList.item(i);
		                // get config object values
		 			   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 				  // getting child nodes to construct query
		 				  NodeList childNodes = nNode.getChildNodes();
						   for (int temp1 = 0; temp1 < childNodes.getLength(); temp1++) {
							   Node childNode = childNodes.item(temp1);
							   if (childNode.getNodeType() == Node.ELEMENT_NODE) {
								   if (TABLE_NAME.equals(childNode.getNodeName())) {
							        	tableQuery = tableQuery + DELETE_FROM + childNode.getTextContent() + SEMI_COLON + "\n";
							        	tableQuery = tableQuery + INSERT_INTO + childNode.getTextContent() + INSERT_INTO_END_TAG;
							        } else if (VARIABLE_NAME.equals(childNode.getNodeName())) {
							        	fieldQuery = VARIABLE_START_TAG + childNode.getTextContent() + VARIABLE_END_TAG;
							        } else if (DEFAULT_VALUE.equals(childNode.getNodeName())) {
							        	valueQuery = VALUES_START_TAG + object + VALUES_END_TAG;
							        }
								   System.out.println("childNode name => " + childNode.getNodeName());
								   System.out.println("childNode name => " + childNode.getTextContent());
							   }
						   }
		 			   }
		            }
		            
				    System.out.println("query  => " + tableQuery + fieldQuery + valueQuery);
			        constructedQuery = tableQuery + fieldQuery + valueQuery;
//			        queryStr =  queryStr + constructedQuery;
			        
					List<File> sqlFolders = getSqlFolders(featureSqlDir);
					for (File sqlFolder : sqlFolders) {
						replaceSqlBlock(sqlFolder, CONFIGURATION_SQL, key, constructedQuery);
					}
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PhrescoException(e);
		}
	}
	
	public List<File> getSqlFolders(File sqlFolder) throws PhrescoException {
		List<File> sqlFolders = null;
		try {
			FilenameFilter mysqlDirectoryFilter = new FilenameFilter() {
		        public boolean accept(File directory, String fileName) {
		        	return directory.isDirectory() && MYSQL.equalsIgnoreCase(fileName);
		        }
		    };
			
			FileFilter directoryFilter = new FileFilter() {
				public boolean accept(File directory) {
					return directory.isDirectory();
				}
			};
			
			File[] dirs = sqlFolder.listFiles(mysqlDirectoryFilter);
			for (File dir : dirs) {
				File[] versionFiles = dir.listFiles(directoryFilter);
				sqlFolders = Arrays.asList(versionFiles);
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return sqlFolders;
	}
	
	public void replaceSqlBlock(File versionFile, String fileName, String moduleName, String queryString) throws Exception {
		BufferedReader buff = null;
		try {
			File scriptFile = new File(versionFile + File.separator + fileName);
			StringBuffer sb = new StringBuffer();
			System.out.println("scriptFile => " + scriptFile);
			if (scriptFile.isFile()) {
				// if script file is available need to replace the content
				buff = new BufferedReader(new FileReader(scriptFile));
				String readBuff = buff.readLine();
	            String sectionStarts = "-- '"+ moduleName + "' starts";
	            String sectionEnds = "-- '"+ moduleName + "' ends";
	            
	            while (readBuff != null) {
	            	sb.append(readBuff);
	            	sb.append("\n");
	                readBuff = buff.readLine();
	            }
	            
	            System.out.println("Original text => " + sb.toString());
	            System.out.println("replace => " + queryString);
	            int cnt1 = sb.indexOf(sectionStarts);
	            int cnt2 = sb.indexOf(sectionEnds);
	            if (cnt1 != -1 || cnt2 != -1) {
	            	System.out.println("This feature found and can be replaced ");
	            	sb.replace(cnt1 + sectionStarts.length(), cnt2, queryString);
	            } else {
	            	System.out.println("can not find this feature and adding it newly ");
	            	// if this module is not added already in the file and need to add this config alone
					sb.append("\n--\n");
					sb.append("--");
					sb.append("-- '"+ moduleName + "' starts\n");
					sb.append(queryString);
					sb.append("\n");
					sb.append("--");
					sb.append("-- '"+ moduleName + "' ends\n");
					sb.append("--\n");
	            }
	            
			} else {
            // else construct the format and write
				// query string buffer
				sb.append("--\n");
				sb.append("--");
				sb.append("-- '"+ moduleName + "' starts\n");
				sb.append(queryString);
				sb.append("\n");
				sb.append("--");
				sb.append("-- '"+ moduleName + "' ends\n");
				sb.append("--\n");
			}
			
			System.out.println("writing or updating ======> ");
			System.out.println("scriptFile ======> " + scriptFile);
			System.out.println("sb.toString() ======> " + sb.toString());
            FileUtils.writeStringToFile(scriptFile, sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (buff != null) {
				buff.close();
			}
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
