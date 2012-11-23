/*
 * ###
 * Phresco Commons
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.photon.phresco.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactGroup.Type;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class ProjectUtils implements Constants {
    
	public static void writeProjectInfo(ProjectInfo info, File phrescoFolder) throws PhrescoException {
		BufferedWriter out = null;
		FileWriter fstream = null;
		try {
			// create .project file inside the .phresco folder
			File projectFile = new File(phrescoFolder.getPath() + File.separator + PROJECT_INFO_FILE);
			if (!projectFile.exists()) {
				projectFile.createNewFile();
			}
			// make the .phresco folder as hidden for windows
			// for linux its enough to create the folder with '.' to make it as
			// hidden
			if (System.getProperty(OSNAME).startsWith(WINDOWS)) {
				Runtime.getRuntime().exec(
						"attrib +h " + STR_DOUBLE_QUOTES + phrescoFolder.getPath() + STR_DOUBLE_QUOTES);
			}

			// write the project info as json string into the .project file
			Gson gson = new Gson();
			String infoJSON = gson.toJson(info);
			fstream = new FileWriter(projectFile.getPath());
			out = new BufferedWriter(fstream);
			out.write(infoJSON);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (fstream != null) {
					fstream.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}
	
	/**
	 * To update the project.info file with the new info
	 * @param projectInfo
	 * @param projectInfoFile
	 * @throws PhrescoException
	 */
	public static void updateProjectInfo(ProjectInfo projectInfo, File projectInfoFile) throws PhrescoException {
		BufferedWriter out = null;
		FileWriter fstream = null;
		try {
			Gson gson = new Gson();
			String infoJSON = gson.toJson(projectInfo);
			fstream = new FileWriter(projectInfoFile.getPath());
			out = new BufferedWriter(fstream);
			out.write(infoJSON);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (fstream != null) {
					fstream.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}
	
	public ProjectInfo getProjectInfo(File baseDir) throws PhrescoException {
		ProjectInfo projectinfo = null;
		Gson gson = new Gson();
		BufferedReader reader = null;
		try {
			File[] dotPhrescoFolders = baseDir.listFiles(new PhrescoFileNameFilter(DOT_PHRESCO_FOLDER));
			if (!ArrayUtils.isEmpty(dotPhrescoFolders)) {
				for (File dotPhrescoFolder : dotPhrescoFolders) {
					File[] dotProjectFiles = dotPhrescoFolder.listFiles(new PhrescoFileNameFilter(PROJECT_INFO_FILE));
					for (File dotProjectFile : dotProjectFiles) {
						reader = new BufferedReader(new FileReader(dotProjectFile));
						projectinfo = gson.fromJson(reader, ProjectInfo.class);
					}
				}
			}
		} catch (JsonSyntaxException e) {
			throw new PhrescoException(e);
		} catch (JsonIOException e) {
			throw new PhrescoException(e);
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		}
		return projectinfo;
	}
	
	public void updatePOMWithPluginArtifact(File pomFile, List<ArtifactGroup> artifactGroups) throws PhrescoException {
		try {
			if(CollectionUtils.isEmpty(artifactGroups)) {
				return;
			}
			List<Element> configList = new ArrayList<Element>();
			PomProcessor processor = new PomProcessor(pomFile);
			String modulePath = "";
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			for (ArtifactGroup artifactGroup : artifactGroups) {
				if (artifactGroup != null) {
					if(artifactGroup.getType().name().equals(Type.FEATURE.name())) {
						modulePath = processor.getProperty(Constants.POM_PROP_KEY_MODULE_SOURCE_DIR);
					} else if(artifactGroup.getType().name().equals(Type.JAVASCRIPT.name())) {
						modulePath = processor.getProperty(Constants.POM_PROP_KEY_JSLIBS_SOURCE_DIR);
					} else if(artifactGroup.getType().name().equals(Type.COMPONENT.name())) {
						modulePath = processor.getProperty(Constants.POM_PROP_KEY_COMPONENTS_SOURCE_DIR);
					}
					configList = configList(modulePath, artifactGroup.getGroupId(), artifactGroup.getArtifactId(),  artifactGroup.getVersions().get(0).getVersion(), doc);
					processor.addExecutionConfiguration(DEPENDENCY_PLUGIN_GROUPID, DEPENDENCY_PLUGIN_ARTIFACTID, EXECUTION_ID, PHASE, GOAL, configList, false, doc);
				}
			}
			processor.save();
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		}
	}

	private List<Element> configList(String modulePath, String moduleGroupId, String moduleArtifactId, String moduleVersion, Document doc) throws PhrescoException {
		List<Element> configList = new ArrayList<Element>();
		Element groupId = doc.createElement(GROUP_ID);
		groupId.setTextContent(moduleGroupId);
		Element artifactId = doc.createElement(ARTIFACT_ID);
		artifactId.setTextContent(moduleArtifactId);
		Element version = doc.createElement(VERSION);
		version.setTextContent(moduleVersion);
		Element type = doc.createElement(TYPE);
		type.setTextContent(ZIP);
		Element overWrite = doc.createElement(OVER_WRITE);
		overWrite.setTextContent(OVER_WIRTE_VALUE);
		Element outputDirectory = doc.createElement(OUTPUT_DIR);
		outputDirectory.setTextContent("${project.basedir}" + modulePath);
		configList.add(groupId);
		configList.add(artifactId);
		configList.add(version);
		configList.add(type);
		configList.add(overWrite);
		configList.add(outputDirectory);
		return configList;
	}
	
	public void updatePOMWithModules(File pomFile, List<com.photon.phresco.commons.model.ArtifactGroup> modules) throws PhrescoException {
		if (CollectionUtils.isEmpty(modules)) {
			return;
		}
		try {
			PomProcessor processor = new PomProcessor(pomFile);
			for (com.photon.phresco.commons.model.ArtifactGroup module : modules) {
				if (module != null) {
					processor.addDependency(module.getGroupId(), module.getArtifactId(), module.getVersions().get(0).getVersion(), "",
							module.getPackaging(), "");
				}
			}
			processor.save();
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}
}

class PhrescoFileNameFilter implements FilenameFilter {
	private String filter_;

	public PhrescoFileNameFilter(String filter) {
		filter_ = filter;
	}

	public boolean accept(File dir, String name) {
		return name.endsWith(filter_);
	}
}
