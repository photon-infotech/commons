package com.photon.phresco.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class WordPressApplicationProcessor implements ApplicationProcessor{
	
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
		updateWordpressVersion(path, appInfo);
		
	}

	@Override
	public void postUpdate(ApplicationInfo appInfo,
			List<ArtifactGroup> artifactGroups) throws PhrescoException {
		File pomFile = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + Constants.POM_NAME);
		ProjectUtils projectUtils = new ProjectUtils();
		projectUtils.deletePluginExecutionFromPom(pomFile);
		if(CollectionUtils.isNotEmpty(artifactGroups)) {
			projectUtils.updatePOMWithPluginArtifact(pomFile, artifactGroups);
		}
		BufferedReader breader = projectUtils.ExtractFeature(appInfo);
		try {
		String line = "";
			while ((line = breader.readLine()) != null) {
				if (line.startsWith("[ERROR]")) {
					System.out.println(line);
				}
			}
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}
	
	private void updateWordpressVersion(File path, ApplicationInfo info) throws PhrescoException {
		try {
			File xmlFile = new File(path, Constants.POM_NAME);
			PomProcessor processor = new PomProcessor(xmlFile);
			String selectedVersion = info.getTechInfo().getVersion();
			processor.setProperty(Constants.WORDPRESS_VERSION, selectedVersion);
			processor.save();
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	@Override
	public void postConfiguration(ApplicationInfo appInfo, List<Configuration> configurations)
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
		// TODO Auto-generated method stub
		
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
