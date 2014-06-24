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
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.ArtifactGroup.Type;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.ComponentMerge;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;


public class CommonApplicationProcessor extends AbstractApplicationProcessor {

	@Override
	public void postUpdate(ApplicationInfo appInfo,
			List<ArtifactGroup> artifactGroups,
			List<ArtifactGroup> deletedFeatures) throws PhrescoException {
		System.out.println("****************** inside the Commmon application processor **********");

		String rootModulePath = "";
		String subModuleName = "";
		if (StringUtils.isNotEmpty(appInfo.getRootModule())) {
			rootModulePath = Utility.getProjectHome() + appInfo.getRootModule();
			subModuleName = appInfo.getAppDirName();
		} else {
			rootModulePath = Utility.getProjectHome() + appInfo.getAppDirName();
		}
		File phrescoPomFile = Utility.getPomFileLocation(rootModulePath,subModuleName);

		ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
		File sourceFolderLocation = Utility.getSourceFolderLocation( projectInfo, rootModulePath, subModuleName);
		File pomFile = new File(sourceFolderLocation.getPath() + File.separator + appInfo.getPomFile());
	    PomProcessor processor;
		ProjectUtils projectUtils = new ProjectUtils();
        
		projectUtils.deletePluginExecutionFromPom(phrescoPomFile);
		
		if (CollectionUtils.isNotEmpty(artifactGroups)) {
			projectUtils.updatePOMWithPluginArtifact(pomFile, phrescoPomFile,artifactGroups);
		}

		try {
			processor = new PomProcessor(pomFile);
			String isMerge = processor.getPropertyValue(Constants.POM_PROP_KEY_COMPONENT_MERGE).trim();
			if (CollectionUtils.isNotEmpty(artifactGroups) && isMerge.equalsIgnoreCase("true")) {
				projectUtils.deleteResourcePluginExecutionFromPom(phrescoPomFile);
				projectUtils.deleteCleanPluginExecutionFromPom(phrescoPomFile);
				projectUtils.updatePOMWithMergePlugins(pomFile, phrescoPomFile, artifactGroups);
			}
		

		if (CollectionUtils.isNotEmpty(artifactGroups)) {
			projectUtils.updatePOMWithPluginDependencies(pomFile,artifactGroups);
		}

		if (CollectionUtils.isNotEmpty(deletedFeatures)) {
			projectUtils.removeExtractedFeatures(phrescoPomFile,sourceFolderLocation, deletedFeatures);
			projectUtils.deleteFeatureDependencies(pomFile, deletedFeatures);
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
		
		if(isMerge.equalsIgnoreCase("true")){
			ComponentMerge.mergeAndUnMerge(pomFile,sourceFolderLocation,artifactGroups,deletedFeatures);
			
	    	List<ArtifactGroup> artifacts = new ArrayList<ArtifactGroup>();	
		    for (ArtifactGroup artifactGroup : artifactGroups) {			
			
			if(artifactGroup.getType().name().equals(Type.COMPONENT.name())) {
		
			  artifacts.add(artifactGroup);
			  }
		  }
		
		 if(CollectionUtils.isNotEmpty(artifacts) && phrescoPomFile.exists()) {
		    projectUtils.updateToCleanPlugin(phrescoPomFile, artifacts);

		  } else {
         
		    projectUtils.updateToCleanPlugin(pomFile, artifacts);

		}
		 
	    if(isMerge.equalsIgnoreCase("true")){ 
	   BufferedReader bufferreader = ComponentMerge.cleanComponents(phrescoPomFile);
		 
		 try {
				String line = "";
				while ((line = bufferreader.readLine()) != null) {
					if (line.startsWith("[ERROR]")) {
						System.err.println(line);
					}
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		} 
		 
		}
		} catch (PhrescoPomException e1) {
			throw new PhrescoException(e1);
		}
		
	}
	
   
}
