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
import java.io.ByteArrayInputStream;
import java.io.File;
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

import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
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
	private static final String ENVIRONMENT_PLIST = "environment.plist";

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
		ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, subModuleName);
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
				File file = new File(sourceFolderLocation + getThirdPartyFolder(phrescoPomFile) + File.separator + environmentName);
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
			if (StringUtils.isNotEmpty(appInfo.getRootModule())) {
				rootModulePath = Utility.getProjectHome() + appInfo.getRootModule();
				subModuleName = appInfo.getAppDirName();
			} else {
				rootModulePath = Utility.getProjectHome() + appInfo.getAppDirName();
			}
			File phrescoPomFile = Utility.getPomFileLocation(rootModulePath, subModuleName);
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, subModuleName);
			File sourceFolderLocation = Utility.getSourceFolderLocation(projectInfo, rootModulePath, subModuleName);
			File plistFile = new File(sourceFolderLocation + getThirdPartyFolder(phrescoPomFile) + File.separator + featureName + File.separator + PLIST);
			//		    if (!plistFile.exists()) {
			//		        throw new PhrescoException("feature-manifest.plist file does not exist");
			//		    }
			return getConfigFromPlist(plistFile.getPath());
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	@Override
	public void postFeatureConfiguration(ApplicationInfo appInfo, List<Configuration> configs, String featureName)
	throws PhrescoException {
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
			String plistPath = sourceFolderLocation.getPath() + getThirdPartyFolder(phrescoPomFile) + File.separator + featureName + File.separator + INFO_PLIST;
			storeConfigObjAsPlist(configs.get(0), plistPath);
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

	private String getThirdPartyFolder(File pomFile) throws PhrescoException { 
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
		String plistFile = sourceFolderLocation.getPath() + File.separator + "source/info.plist";
		try {
			storeConfigObjAsPlist(configuration, plistFile);
		} catch (Exception e) {
			throw new PhrescoException(e);
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
			File plistFile = new File(sourceFolderLocation + getThirdPartyFolder(phrescoPomFile) + File.separator + featureName + File.separator + PLIST);
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
