package com.photon.phresco.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;

public class SharepointApplicationProcessor implements ApplicationProcessor{

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
		if(CollectionUtils.isNotEmpty(artifactGroup)) {
			projectUtils.updatePOMWithPluginArtifact(pomFile, artifactGroup);
		}
		
	}

	@Override
	public void postConfiguration(ApplicationInfo appInfo)
			throws PhrescoException {
		// TODO Auto-generated method stub
		
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
