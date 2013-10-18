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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.plexus.util.StringUtils;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;

public class JavaApplicationProcessor extends AbstractApplicationProcessor {

	@Override
	public void postUpdate(ApplicationInfo appInfo,
		List<ArtifactGroup> artifactGroups, List<ArtifactGroup> deletedFeatures) throws PhrescoException {
		StringBuilder sb = new StringBuilder(Utility.getProjectHome());
		if(StringUtils.isNotEmpty(appInfo.getRootModule())) {
			sb.append(appInfo.getRootModule())
			.append(File.separator);
		}
		sb.append(appInfo.getAppDirName());
		
		String phrescoPom = Utility.getPhrescoPomFromWorkingDirectory(appInfo, new File(sb.toString()));
		String pom = Utility.getPomFileNameFromRootModule(appInfo, appInfo.getRootModule());
		File phrescoPomFile = new File(sb.toString() + File.separator + phrescoPom);
		File pomFile = new File(sb.toString() + File.separator + pom);
		ProjectUtils projectUtils = new ProjectUtils();
		if(CollectionUtils.isNotEmpty(artifactGroups)) {
			//Need to refactore
			List<ArtifactGroup> dependencies = new ArrayList<ArtifactGroup>();
			List<ArtifactGroup> artifacts = new ArrayList<ArtifactGroup>();
			for (ArtifactGroup artifactGroup : artifactGroups) {
				if(artifactGroup.getPackaging().equals("zip") || artifactGroup.getPackaging().equals("war")) {
					artifacts.add(artifactGroup);
				} else {
					dependencies.add(artifactGroup);
				}
			}
			
			if(CollectionUtils.isNotEmpty(dependencies)) {
				projectUtils.updatePOMWithModules(pomFile, dependencies);
			}
			
			if(CollectionUtils.isNotEmpty(artifacts)) {
				projectUtils.updateToDependencyPlugin(phrescoPomFile, artifacts);
			}
			
		}
		if(CollectionUtils.isNotEmpty(deletedFeatures)) {
			projectUtils.deleteFeatureDependencies(appInfo, deletedFeatures);
		}
		BufferedReader breader = projectUtils.ExtractFeature(appInfo);
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
}
