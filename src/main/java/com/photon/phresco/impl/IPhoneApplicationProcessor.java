/**
 * Phresco Commons
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
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
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.plist.XMLPropertyListConfiguration;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.parser.JSONParser;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.util.ArchiveUtil;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.FileUtil;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;


public class IPhoneApplicationProcessor extends AbstractApplicationProcessor {

	private static final String PLIST = "feature-manifest.plist";
	private static final String INFO_PLIST = "info.plist";
	private static final String FEATURES = "features";
	private static final String NAME = "name";
	private static final String CONFIG = "config";
	private static final String CONFIG_JSON = "config.json";
	private static final String ENVIRONMENT_PLIST = "environment.plist";
	private static final String WWW_JSON_CONFIG_FILE ="/source/ThirdParty/cordova/www/json/config.json";

	@Override
	public void postUpdate(ApplicationInfo appInfo, List<ArtifactGroup> artifactGroup, List<ArtifactGroup> deletedFeatures) throws PhrescoException {
		String rootModulePath = "";
		String subModuleName = "";
		if (StringUtils.isNotEmpty(appInfo.getRootModule())) {
			rootModulePath = Utility.getProjectHome() + appInfo.getRootModule();
			subModuleName = appInfo.getAppDirName();
		} else {
			rootModulePath = Utility.getProjectHome() + appInfo.getAppDirName();
		}
		
		File phrescoPomFile = Utility.getPomFileLocation(rootModulePath, subModuleName);
		ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
		File sourceFolderLocation = Utility.getSourceFolderLocation(projectInfo, rootModulePath, subModuleName);
		File pomFile = new File(sourceFolderLocation.getPath() + File.separator + appInfo.getPomFile());
		ProjectUtils projectUtils = new ProjectUtils();
		
		projectUtils.deletePluginExecutionFromPom(phrescoPomFile);
		if(CollectionUtils.isNotEmpty(deletedFeatures)) {
			projectUtils.removeExtractedFeatures(phrescoPomFile,sourceFolderLocation, deletedFeatures);
			projectUtils.deleteFeatureDependencies(pomFile, deletedFeatures);
		}
		if(CollectionUtils.isNotEmpty(artifactGroup)) { 
			projectUtils.updatePOMWithPluginArtifact(pomFile,phrescoPomFile, artifactGroup);
		}
		BufferedReader breader = projectUtils.ExtractFeature(phrescoPomFile);
		try {
			String line = "";
			while ((line = breader.readLine()) != null) {
				if (line.startsWith("[ERROR]")) {
					System.err.println(line);
				}
			}
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}

	@Override
	public void postConfiguration(ApplicationInfo appInfo, List<Configuration> configs) throws PhrescoException {
		
		try {
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
			String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, subModuleName);
			File ConfigFilePath = new File(dotPhrescoFolderPath +  File.separator + Constants.CONFIGURATION_INFO_FILE);
			ConfigManager configManager = new ConfigManagerImpl(ConfigFilePath);
			List<Environment> environments = configManager.getEnvironments();
			for (Environment environment : environments) {
				String environmenName = "";
				Map<String, List<Properties>> values = new HashMap<String, List<Properties>>();
				String environmentName = environment.getName();
				File file = new File(sourceFolderLocation + getThirdPartyModuleFolder(phrescoPomFile) + File.separator + environmentName);
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
		
		try {
			String rootModulePath = "";
			String subModuleName = "";
			File plistFile =null;
			if (StringUtils.isNotEmpty(appInfo.getRootModule())) {
				rootModulePath = Utility.getProjectHome() + appInfo.getRootModule();
				subModuleName = appInfo.getAppDirName();
			} else {
				rootModulePath = Utility.getProjectHome() + appInfo.getAppDirName();
			}
			File phrescoPomFile = Utility.getPomFileLocation(rootModulePath, subModuleName);
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, subModuleName);
			File sourceFolderLocation = Utility.getSourceFolderLocation(projectInfo, rootModulePath, subModuleName);
			System.out.println("sourceFolderLocation=="+sourceFolderLocation);
			plistFile = new File(sourceFolderLocation + getThirdPartyModuleFolder(phrescoPomFile) + File.separator + featureName + File.separator + PLIST);
			if(plistFile.exists()){
				return getConfigFromPlist(plistFile.getPath());
			} 
			if(featureName.equalsIgnoreCase("application")){
				
				plistFile = new File(sourceFolderLocation + WWW_JSON_CONFIG_FILE) ;
				
			}else{
				plistFile = new File(sourceFolderLocation + getThirdPartyComponentFolder(phrescoPomFile) + File.separator + featureName + File.separator + CONFIG + File.separator +CONFIG_JSON);
			}
			
			System.out.println("plistFile=="+plistFile);
			
			if(plistFile.exists())	{
			    JSONParser jsonParser = new JSONParser();
			     return jsonParser.getConfigFromJson(null, plistFile.getPath());
		    }
		 
		 } catch (Exception e) {
			throw new PhrescoException(e);
		}
		return null;
	}

	@Override
	public void postFeatureConfiguration(ApplicationInfo appInfo, List<Configuration> configs, String featureName)
	throws PhrescoException {
		try {
			String rootModulePath = "";
			String subModuleName = "";
			String plistPath  ="";
			File configFile =null;
			if (StringUtils.isNotEmpty(appInfo.getRootModule())) {
				rootModulePath = Utility.getProjectHome() + appInfo.getRootModule();
				subModuleName = appInfo.getAppDirName();
			} else {
				rootModulePath = Utility.getProjectHome() + appInfo.getAppDirName();
			}
			File phrescoPomFile = Utility.getPomFileLocation(rootModulePath, subModuleName);
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, subModuleName);
			File sourceFolderLocation = Utility.getSourceFolderLocation(projectInfo, rootModulePath, subModuleName);
		    plistPath = sourceFolderLocation.getPath() + getThirdPartyModuleFolder(phrescoPomFile) + File.separator + featureName + File.separator + INFO_PLIST;
		    configFile = new File(plistPath);
		    if(configFile.exists()){
		    	storeConfigObjAsPlist(configs.get(0), plistPath);
		    }else{
		    	Properties properties = configs.get(0).getProperties();
			    String configJsonFilePath=null;
			    String globalConfig = sourceFolderLocation + WWW_JSON_CONFIG_FILE ;
				 if(featureName.equalsIgnoreCase("application")){
					configJsonFilePath = sourceFolderLocation + WWW_JSON_CONFIG_FILE ;
				 }else{
	                configJsonFilePath = sourceFolderLocation + getThirdPartyComponentFolder(phrescoPomFile) + File.separator + featureName + File.separator + CONFIG + File.separator +CONFIG_JSON;
				 }
				
		        JSONParser jsonParser = new JSONParser(properties ,featureName ,configJsonFilePath ,globalConfig);
		        jsonParser.read();
		    }
			
		} catch (Exception e) {
			throw new PhrescoException(e);
		}

	}

	public void storeConfigObjAsPlist(Configuration keyValueObj, String plistPath) throws Exception {
		XMLPropertyListConfiguration plist = new XMLPropertyListConfiguration();
		Properties properties = keyValueObj.getProperties();
		Enumeration em = properties.keys();
		while (em.hasMoreElements()) {
			String str = (String) em.nextElement();
			plist.addProperty(str, properties.get(str));
		}
		plist.save(plistPath);

	}

	private String getThirdPartyModuleFolder(File pomFile) throws PhrescoException { 
		try {
			PomProcessor processor = new PomProcessor(pomFile);
			String property = processor.getProperty(Constants.POM_PROP_KEY_MODULE_SOURCE_DIR);
			if(StringUtils.isNotEmpty(property)) {
				return property;
			}
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
		return "";
	}
	
	private String getThirdPartyComponentFolder(File pomFile) throws PhrescoException { 
		try {
			PomProcessor processor = new PomProcessor(pomFile);
			String property = processor.getProperty(Constants.POM_PROP_KEY_COMPONENTS_SOURCE_DIR);
			if(StringUtils.isNotEmpty(property)) {
				return property;
			}
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
		return "";
	}

	private List<Configuration> getConfigFromPlist(String plistPath) throws PhrescoException {
		List<Configuration> configs = new ArrayList<Configuration>();
		try {
			Configuration config = null;
			File plistFile = new File(plistPath);
			if (plistFile.isFile()) {
				config = new Configuration(plistFile.getName(), FEATURES);
			} else {
				return Collections.EMPTY_LIST;
			}

			// get all the key and value pairs
			Properties properties = new Properties();
			XMLPropertyListConfiguration conf = new XMLPropertyListConfiguration(plistPath);
			Iterator it = conf.getKeys();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object property = conf.getProperty(key);
				String value = property.toString();
				properties.setProperty(key, value);
			}
			config.setProperties(properties);
			configs.add(config);
		} catch (org.apache.commons.configuration.ConfigurationException e) {
			throw new PhrescoException(e);
		}
		return configs;
	}

	@Override
	public void preBuild(ApplicationInfo appInfo) throws PhrescoException {
		Configuration configuration = new Configuration();
		Properties properties = new Properties();
		String rootModulePath = "";
		String subModuleName = "";
		if (StringUtils.isNotEmpty(appInfo.getRootModule())) {
			rootModulePath = Utility.getProjectHome() + appInfo.getRootModule();
			subModuleName = appInfo.getAppDirName();
		} else {
			rootModulePath = Utility.getProjectHome() + appInfo.getAppDirName();
		}
		ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, subModuleName);
		File sourceFolderLocation = Utility.getSourceFolderLocation(projectInfo, rootModulePath, subModuleName);
		String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, subModuleName);
		
		File pluginInfoFile = new File(dotPhrescoFolderPath + File.separator + Constants.PACKAGE_INFO_XML);
		MojoProcessor mojoProcessor = new MojoProcessor(pluginInfoFile);
		Parameter environmentParameter = mojoProcessor.getParameter(Constants.MVN_GOAL_PACKAGE, Constants.MOJO_KEY_ENVIRONMENT_NAME);
		String environmentValue = environmentParameter.getValue();
		Parameter themeParameter = mojoProcessor.getParameter(Constants.MVN_GOAL_PACKAGE, Constants.MOJO_KEY_THEME);
		if (themeParameter != null) {
			String themeValue = themeParameter.getValue();
			properties.put(Constants.MOJO_KEY_THEME, themeValue);
		}
		properties.put(Constants.MOJO_KEY_ENVIRONMENT_NAME, environmentValue);
		configuration.setProperties(properties);
		try {
			File pomFileLocation = Utility.getPomFileLocation(Utility.getProjectHome() + appInfo.getAppDirName(), "");
			PomProcessor pomProcessor = new PomProcessor(new File(pomFileLocation.getPath()));
			String sourceDirectory = pomProcessor.getSourceDirectory();
			String plistFile = sourceFolderLocation.getPath() + File.separator + sourceDirectory + File.separator + "info.plist";
			storeConfigObjAsPlist(configuration, plistFile);
			
			String configJson = pomProcessor.getProperty(Constants.POM_PROP_KEY_PHRESCO_ENV_CONFIG_JSON);
			if (StringUtils.isNotEmpty(configJson)) {
				String totalPath = Utility.getProjectHome() + appInfo.getAppDirName() + configJson;
				ConfigManagerImpl conf = new ConfigManagerImpl(new File(dotPhrescoFolderPath, Constants.CONFIGURATION_INFO_FILE));
				Environment environment = conf.getEnvironment(environmentValue);
				JSONObject jsonObj = envToJsonConverter(environment);
				updateJsonInfo(jsonObj, totalPath);
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	private JSONObject envToJsonConverter(Environment env) throws PhrescoException {
		System.out.println("env "+env);
		List<Configuration> configurations = env.getConfigurations();
		JSONObject envJson = new JSONObject();
		
		envJson.put("-name", env.getName());
		envJson.put("-desc", env.getDesc());
		envJson.put("-default", env.isDefaultEnv());

		for (Configuration config : configurations) {
			JSONObject configJson = new JSONObject();
			configJson.put("-name", config.getName());
			configJson.put("-desc", config.getDesc());
			Properties properties = config.getProperties();
			Enumeration em = properties.keys();
			
			while (em.hasMoreElements()) {
				String key = (String) em.nextElement();
				Object object = properties.get(key);
				configJson.put(key, object.toString());
			}
			envJson.put(config.getType(), configJson);
		}
		JSONObject envsJson = new JSONObject();
		envsJson.put("environment", envJson);
		
		JSONObject finalJson = new JSONObject();
		finalJson.put("environments", envsJson);
		System.out.println("finalJson ====> "+finalJson);
		return finalJson;
	}
	
	public static void updateJsonInfo(JSONObject toJson, String jsonFile) throws PhrescoException {
		BufferedWriter out = null;
		FileWriter fstream = null;
		try {
			Gson gson = new Gson();
			FileWriter writer = null;
			String json = gson.toJson(toJson);
			writer = new FileWriter(jsonFile);
			writer.write(json);
			writer.flush();
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (fstream != null) {
					fstream.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}

	@Override
	public List<Configuration> preConfiguration(ApplicationInfo appInfo, String featureName, String envName) throws PhrescoException {
		try {
			String rootModulePath = "";
			String subModuleName = "";
			if (StringUtils.isNotEmpty(appInfo.getRootModule())) {
				rootModulePath = Utility.getProjectHome() + appInfo.getRootModule();
				subModuleName = appInfo.getAppDirName();
			} else {
				rootModulePath = Utility.getProjectHome() + appInfo.getAppDirName();
			}
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, subModuleName);
			File sourceFolderLocation = Utility.getSourceFolderLocation(projectInfo, rootModulePath, subModuleName);
			File phrescoPomFile = Utility.getPomFileLocation(rootModulePath, subModuleName);
			File plistFile = new File(sourceFolderLocation + getThirdPartyModuleFolder(phrescoPomFile) + File.separator + featureName + File.separator + PLIST);
			return getConfigFromPlist(plistFile.getPath());
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	@Override
	public Map<Boolean, String> themeBundleUpload(ApplicationInfo appInfo, byte[] byteArray, String zipfileName) throws PhrescoException {
		Map<Boolean, String> resultMap = new HashMap<Boolean, String>();
		File tempDirectory;
		try {
			InputStream inputStream = new ByteArrayInputStream(byteArray);
			StringBuilder sb = new StringBuilder(Utility.getProjectHome());
			sb.append(appInfo.getAppDirName())
			.append(File.separator)
			.append(Constants.PROJECTS_TEMP);

			tempDirectory = new File(sb.toString());
			tempDirectory.mkdir(); //create temp dir

			String tempZipPath = tempDirectory.getPath() + File.separator + zipfileName;

			//create zip file from inputstream
			File tempZipFile = FileUtil.writeFileFromInputStream(inputStream, tempZipPath);

			//extract the zip file inside temp directory
			ArchiveUtil.unzip(tempZipPath, tempDirectory.getPath(), "");

			//after extracting, delete that zip file
			FileUtil.delete(tempZipFile);

			//to validate the extract file contains .bundle extension
			File[] listFiles = tempDirectory.listFiles();
			File extractedFile = listFiles[0]; 
			if (extractedFile.getName().endsWith(Constants.DOT_BUNDLE)) {
				//if true, then move extracted file to the path specified in the pom
				FileUtil.moveUploadedThemeBundle(extractedFile, appInfo);
				resultMap.put(true, Constants.THEME_BUNDLE_SUCCESS_MSG);
			} else {
				resultMap.put(false, Constants.THEME_BUNDLE_INVALID_MSG);
			}
		} catch (Exception e) {
			resultMap.put(false, Constants.THEME_BUNDLE_FAILURE_MSG);
			throw new PhrescoException(e);
		}
		FileUtil.delete(tempDirectory);

		return resultMap;
	}
}