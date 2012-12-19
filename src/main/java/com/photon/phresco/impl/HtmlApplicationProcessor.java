package com.photon.phresco.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

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
		// TODO Auto-generated method stub
		return null;
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
	public void postBuild(ApplicationInfo appInfo) throws PhrescoException {
		// TODO Auto-generated method stub
		
	}
}
