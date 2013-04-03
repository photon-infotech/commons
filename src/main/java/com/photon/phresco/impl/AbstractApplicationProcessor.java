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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONObject;

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
	
	@Override
	public Map<String, String> themeBuilderList(ApplicationInfo appInfo)  throws PhrescoException {
		
		return new HashMap<String, String>();
	}
	
	@Override
	public JSONObject themeBuilderEdit(ApplicationInfo appInfo, String file) throws PhrescoException {
		return new JSONObject();
	}
	
	@Override
	public boolean themeBuilderSave(ApplicationInfo appInfo, String cssJson) throws PhrescoException {
		return true;
	}
	@Override
	public Map<Boolean, String>  themeBundleUpload(ApplicationInfo appInfo, byte[] byteArray, String fileName) throws PhrescoException {
		return new HashMap<Boolean, String>();
	}
	
	
}
