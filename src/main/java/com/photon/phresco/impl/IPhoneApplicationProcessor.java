package com.photon.phresco.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.plist.XMLPropertyListConfiguration;
import org.apache.commons.configuration.plist.XMLPropertyListConfiguration.PListNode;

import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;

public class IPhoneApplicationProcessor implements ApplicationProcessor {

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
	public void postUpdate(ApplicationInfo appInfo, List<ArtifactGroup> artifactGroup) throws PhrescoException {
		File pomFile = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + Constants.POM_NAME);
		ProjectUtils projectUtils = new ProjectUtils();
		projectUtils.deletePluginExecutionFromPom(pomFile);
		if(CollectionUtils.isNotEmpty(artifactGroup)) { 
			projectUtils.updatePOMWithPluginArtifact(pomFile, artifactGroup);
		}
	}

	@Override
	public void postConfiguration(ApplicationInfo appInfo) throws PhrescoException {
		File ConfigFilePath = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + Constants.DOT_PHRESCO_FOLDER + File.separator + Constants.CONFIGURATION_INFO_FILE);
		try {
			ConfigManager configManager = new ConfigManagerImpl(ConfigFilePath);
			List<Environment> environments = configManager.getEnvironments();
			for (Environment environment : environments) {
				String environmenName = "";
				Map<String, List<Properties>> values = new HashMap<String, List<Properties>>();
				String environmentName = environment.getName();
				File file = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + "source/ThirdParty/" + environmentName);
				if(!file.exists()) {
					file.mkdir();
				}
				List<Configuration> configurations = environment.getConfigurations();
				if (CollectionUtils.isNotEmpty(configurations)) {
					String plistFile = file.getPath() + File.separator + "environment.plist";
					XMLPropertyListConfiguration plist = new XMLPropertyListConfiguration();
					for (Configuration configuration : configurations) {
						if(configuration != null) {
							String configType = configuration.getType();
							environmenName = environment.getName();
							Properties properties = configuration.getProperties();
							properties.setProperty("name", configuration.getName());
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
	public List<Configuration> preFeatureConfiguration(ApplicationInfo appInfo, String featureName)
	throws PhrescoException {
		File plistFile = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + "source/ThirdParty/" + featureName + File.separator + featureName+".plist");
		try {
			return getConfigurationFromPlist(plistFile.getPath());
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	@Override
	public void postFeatureConfiguration(ApplicationInfo appInfo, List<Configuration> configs, String featureName)
	throws PhrescoException {
		try {
			File plistFile = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + "source/ThirdParty/" + featureName + File.separator + featureName+".plist");
			XMLPropertyListConfiguration plist = new XMLPropertyListConfiguration();
			for (Configuration configuration : configs) {
				plist.addProperty(configuration.getName(), configuration.getProperties());
			}
			plist.save(plistFile);
		} catch (org.apache.commons.configuration.ConfigurationException e) {

		}

	}
	
	@SuppressWarnings("unchecked")
	private List<Configuration> getConfigurationFromPlist(String plistPath) throws Exception {
		XMLPropertyListConfiguration conf = new XMLPropertyListConfiguration(plistPath);
		List children = conf.getRoot().getChildren();

		List<Configuration> phrescoConfigs = new ArrayList<Configuration>();
		// getting root node childrens
		if (children instanceof List) {
			List<PListNode> configs = (List<PListNode>) children;
			for (PListNode configuration : configs) {
				// there will be configParam and iconpath
				List configChildren = configuration.getChildren();
				if (configChildren instanceof List) {
					for (PListNode configChild : (List<PListNode>) configChildren) {
						List configParamChildren = configChild.getChildren();
						// getting config param
						if (configParamChildren instanceof List) {
							for (PListNode configParamChild : (List<PListNode>) configParamChildren) {
								if (configParamChild.getChildrenCount() > 0) {
									Configuration config = new Configuration();
									config.setName(configParamChild.getName());
									// gets contstants, theme
									List userDefinedChildren = configParamChild.getChildren();
									if (userDefinedChildren instanceof List) {
										Properties prop = getPropetyObj(userDefinedChildren);
										config.setProperties(prop);
										phrescoConfigs.add(config);
									}
								}
							}
						}
					}
				}
			}
		}
		return phrescoConfigs;
	}
	
	private Properties getPropetyObj(List<PListNode> children) {
		Properties prop = new Properties();
		if (children instanceof List) {
			for (PListNode childrenConfigs : (List<PListNode>) children) {
				prop.setProperty(childrenConfigs.getName(), childrenConfigs.getValue().toString());
			}
		}
		return prop;
	}
}
