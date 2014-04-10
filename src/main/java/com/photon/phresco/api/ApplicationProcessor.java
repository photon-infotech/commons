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
package com.photon.phresco.api;

import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONObject;

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
	void postUpdate(ApplicationInfo appInfo, List<ArtifactGroup> artifactGroup, List<ArtifactGroup> deletedFeatures) throws PhrescoException;
	
	/**
     * 
     * @param appInfo
     * @throws PhrescoException
     */
	List<Configuration> preConfiguration(ApplicationInfo appInfo, String featureName, String envName) throws PhrescoException;
	
	/**
	 * 
	 * @param appInfo
	 * @throws PhrescoException
	 */
	void postConfiguration(ApplicationInfo appInfo, List<Configuration> configurations) throws PhrescoException;
	
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
	

	/**
	 * 
	 * @param appInfo
	 * @throws PhrescoException
	 */
	Map<String, String> themeBuilderList(ApplicationInfo appInfo) throws PhrescoException;

	JSONObject themeBuilderEdit(ApplicationInfo appInfo, String file) throws PhrescoException;
	
	boolean themeBuilderSave(ApplicationInfo appInfo, String cssJson) throws PhrescoException;
	
	Map<Boolean, String> themeBundleUpload(ApplicationInfo appInfo, byte[] byteArray, String fileName) throws PhrescoException;
	
	void adoptApplication(ApplicationInfo appInfo) throws PhrescoException;
}
