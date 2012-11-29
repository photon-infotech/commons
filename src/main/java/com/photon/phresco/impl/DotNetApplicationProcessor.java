package com.photon.phresco.impl;

import java.util.List;

import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.exception.PhrescoException;

public class DotNetApplicationProcessor implements ApplicationProcessor {

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
	public void postUpdate(ApplicationInfo appInfo,
			List<ArtifactGroup> artifactGroup) throws PhrescoException {
		// TODO Auto-generated method stub
		
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
