package com.photon.phresco.api;

import java.util.List;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.exception.PhrescoException;

public interface ApplicationProcessor {
	
	/**
	 * 
	 * @param appInfo
	 * @throws PhrescoException
	 */
	void preCreate(ApplicationInfo appInfo) throws PhrescoException;

	/**
	 * 
	 * @param appInfo
	 * @throws PhrescoException
	 */
	void preUpdate(ApplicationInfo appInfo) throws PhrescoException;
	
	/**
	 * 
	 * @param appInfo
	 * @throws PhrescoException
	 */
	void postCreate(ApplicationInfo appInfo) throws PhrescoException;

	/**
	 * 
	 * @param appInfo
	 * @throws PhrescoException
	 */
	void postUpdate(ApplicationInfo appInfo, List<ArtifactGroup> artifactGroup) throws PhrescoException;
	
	/**
	 * 
	 * @param appInfo
	 * @throws PhrescoException
	 */
	void postConfiguration(ApplicationInfo appInfo) throws PhrescoException;
	
	/**
	 * 
	 * @param appInfo
	 * @throws PhrescoException
	 */
	List<Configuration> preFeatureConfiguration(ApplicationInfo appInfo, String featureName) throws PhrescoException;
	/**
	 * 
	 * @param appInfo
	 * @throws PhrescoException
	 */
	void postFeatureConfiguration(ApplicationInfo appInfo, List<Configuration> configs, String featureName) throws PhrescoException;
	
	/**
	 * 
	 * @param appInfo
	 * @throws PhrescoException
	 */
	void preBuild(ApplicationInfo appInfo) throws PhrescoException;
	
	/**
	 * 
	 * @param appInfo
	 * @throws PhrescoException
	 */
	void postBuild(ApplicationInfo appInfo) throws PhrescoException;

}
