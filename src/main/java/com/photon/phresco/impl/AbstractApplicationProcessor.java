package com.photon.phresco.impl;

import java.util.ArrayList;
import java.util.List;

import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.exception.PhrescoException;

public abstract class AbstractApplicationProcessor implements
		ApplicationProcessor {

	@Override
	public void preCreate(ApplicationInfo appInfo) throws PhrescoException {

	}

	@Override
	public void preUpdate(ApplicationInfo appInfo) throws PhrescoException {

	}

	@Override
	public void postCreate(ApplicationInfo appInfo) throws PhrescoException {

	}

	@Override
	public void postUpdate(ApplicationInfo appInfo,
			List<ArtifactGroup> artifactGroup,
			List<ArtifactGroup> deletedFeatures) throws PhrescoException {

	}

	@Override
	public List<Configuration> preConfiguration(ApplicationInfo appInfo,
			String featureName, String envName) throws PhrescoException {
		return new ArrayList<Configuration>();
	}

	@Override
	public void postConfiguration(ApplicationInfo appInfo,
			List<Configuration> configurations) throws PhrescoException {

	}

	@Override
	public List<Configuration> preFeatureConfiguration(ApplicationInfo appInfo,
			String featureName) throws PhrescoException {
		return new ArrayList<Configuration>();
	}

	@Override
	public void postFeatureConfiguration(ApplicationInfo appInfo,
			List<Configuration> configs, String featureName)
			throws PhrescoException {

	}

	@Override
	public void preBuild(ApplicationInfo appInfo) throws PhrescoException {

	}

	@Override
	public void postBuild(ApplicationInfo appInfo) throws PhrescoException {

	}

}
