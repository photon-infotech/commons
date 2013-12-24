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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.plexus.util.StringUtils;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class WordPressApplicationProcessor extends AbstractApplicationProcessor{

	@Override
	public void postCreate(ApplicationInfo appInfo) throws PhrescoException {
		File path = new File(Utility.getProjectHome() + appInfo.getAppDirName());
		updateWordpressVersion(path, appInfo);

	}

	@Override
	public void postUpdate(ApplicationInfo appInfo,
			List<ArtifactGroup> artifactGroups, List<ArtifactGroup> deletedFeatures) throws PhrescoException {
		ProjectUtils projectUtils = new ProjectUtils();
		
		String rootModulePath = "";
		String subModuleName = "";
		if (StringUtils.isNotEmpty(appInfo.getRootModule())) {
			rootModulePath = Utility.getProjectHome() + appInfo.getRootModule();
			subModuleName = appInfo.getAppDirName();
		} else {
			rootModulePath = Utility.getProjectHome() + appInfo.getAppDirName();
		}
		
		File phrescoPomFile = Utility.getPomFileLocation(rootModulePath, subModuleName);
		ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, subModuleName);
		File sourceFolderLocation = Utility.getSourceFolderLocation(projectInfo, rootModulePath, subModuleName);
		File pomFile = new File(sourceFolderLocation.getPath() + File.separator + appInfo.getPomFile());
		
		projectUtils.deletePluginExecutionFromPom(phrescoPomFile);
		if(CollectionUtils.isNotEmpty(deletedFeatures)) {
			projectUtils.removeExtractedFeatures(phrescoPomFile,sourceFolderLocation, deletedFeatures);
			projectUtils.deleteFeatureDependencies(pomFile, deletedFeatures);
		}
		if(CollectionUtils.isNotEmpty(artifactGroups)) {
			projectUtils.updatePOMWithPluginArtifact(pomFile,phrescoPomFile, artifactGroups);
		}
		BufferedReader breader = projectUtils.ExtractFeature(phrescoPomFile);
		try {
			String line = "";
			while ((line = breader.readLine()) != null) {
				if (line.startsWith("[ERROR]")) {
					System.err.println(line);
				}
			}
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}

	private void updateWordpressVersion(File path, ApplicationInfo info) throws PhrescoException {
		try {
			File xmlFile = new File(path, Utility.getPomFileName(info));
			PomProcessor processor = new PomProcessor(xmlFile);
			String selectedVersion = info.getTechInfo().getVersion();
			processor.setProperty(Constants.WORDPRESS_VERSION, selectedVersion);
			processor.save();
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

}
