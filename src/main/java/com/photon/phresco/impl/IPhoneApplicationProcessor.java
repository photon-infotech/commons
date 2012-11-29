package com.photon.phresco.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.HierarchicalConfiguration.Node;
import org.apache.commons.configuration.plist.XMLPropertyListConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;

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
		} catch (ConfigurationException e) {
			throw new PhrescoException(e);
		} catch (org.apache.commons.configuration.ConfigurationException e) {
			throw new PhrescoException(e);
		}
	}

	@Override
	public void preFeatureConfiguration(ApplicationInfo appInfo)
			throws PhrescoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postFeatureConfiguration(ApplicationInfo appInfo)
			throws PhrescoException {
		// TODO Auto-generated method stub
		
	}
}
