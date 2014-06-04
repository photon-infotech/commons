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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.plexus.util.StringUtils;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactGroup.Type;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Model.Modules;
import com.phresco.pom.util.PomProcessor;

public class ATGApplicationProcessor extends AbstractApplicationProcessor {

	@Override
	public void postUpdate(ApplicationInfo appInfo,
		List<ArtifactGroup> artifactGroups, List<ArtifactGroup> deletedFeatures) throws PhrescoException {
		
		String rootModulePath = "";
		String subModuleName = "";
		if (StringUtils.isNotEmpty(appInfo.getRootModule())) {
			rootModulePath = Utility.getProjectHome() + appInfo.getRootModule();
			subModuleName = appInfo.getAppDirName();
		} else {
			rootModulePath = Utility.getProjectHome() + appInfo.getAppDirName();
		}
		File phrescoPomFile = Utility.getPomFileLocation(rootModulePath, subModuleName);
		ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
		File sourceFolderLocation = Utility.getSourceFolderLocation(projectInfo, rootModulePath, subModuleName);
		File pomFile = new File(sourceFolderLocation.getPath() + File.separator + appInfo.getPomFile());
		ProjectUtils projectUtils = new ProjectUtils();
		
		projectUtils.deletePluginExecutionFromPom(phrescoPomFile);
		if(CollectionUtils.isNotEmpty(artifactGroups)) {
			projectUtils.updatePOMWithPluginArtifact(pomFile, phrescoPomFile, artifactGroups);
		}
		
		if(CollectionUtils.isNotEmpty(deletedFeatures)) {
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
		
		//Update Components In Build Properties
		File buildProperties = new File(rootModulePath, "build.properties");
		if(CollectionUtils.isNotEmpty(artifactGroups)) {
			for (ArtifactGroup artifactGroup : artifactGroups) {
				if(artifactGroup.getType().name().equals(Type.COMPONENT.name())) {
					updateBuildProperties(buildProperties, artifactGroup.getName());
				}
			}
		}
	}
	
	@Override
	public void postCreate(ApplicationInfo appInfo) throws PhrescoException {
		String rootModulePath = "";
		String subModuleName = "";
		if (StringUtils.isNotEmpty(appInfo.getRootModule())) {
			rootModulePath = Utility.getProjectHome() + appInfo.getRootModule();
			subModuleName = appInfo.getAppDirName();
		} else {
			rootModulePath = Utility.getProjectHome() + appInfo.getAppDirName();
		}
		File buildProperties = new File(rootModulePath, "build.properties");
		updateBuildProperties(buildProperties, subModuleName);
		try {
			PomProcessor processor = new PomProcessor(new File(rootModulePath, "pom.xml"));
			Modules modules = processor.getModel().getModules();
			if(modules != null) {
				List<String> moduless = modules.getModule();
				for (String mod : moduless) {
					processor.removeModule(mod);
					processor.save();
				}
			} 
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}
	
	private void updateBuildProperties(File propertyFile, String moduleName) throws PhrescoException {
		Properties properties = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream(propertyFile);
			properties.load(fis);
			String moduleBuildOrder = properties.getProperty("modules.build.order");
			String module = moduleName + "/build.xml";
			if(StringUtils.isEmpty(moduleBuildOrder)) {
				properties.put("modules.build.order", module);
				
			} else {
				if(!moduleBuildOrder.contains(module)) {
					properties.put("modules.build.order", moduleBuildOrder.concat(",").concat(module));
				}
			}
			
			
			String moduleOrder = properties.getProperty("module.order");
			if(StringUtils.isEmpty(moduleOrder)) {
				properties.put("module.order", moduleName);
			} else {
				if(!moduleOrder.contains(moduleName)) {
					properties.put("module.order", moduleOrder + " " + moduleName);
				}
			}
			properties.save(new FileOutputStream(propertyFile), "");
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}
}
