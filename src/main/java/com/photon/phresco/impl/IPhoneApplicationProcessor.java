package com.photon.phresco.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class IPhoneApplicationProcessor implements ApplicationProcessor {

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
	public void postUpdate(ApplicationInfo appInfo, List<ArtifactGroup> artifactGroup) throws PhrescoException {
		File pomFile = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + "pom.xml");
//		List<ArtifactGroup> artifactgroup = getFeatures(custId, appInfo.getTechInfo().getId(), type);
//		updatePOMWithModules(pomFile, artifactGroup);
		if(CollectionUtils.isNotEmpty(artifactGroup)) { 
		updatePOMWithPluginArtifact(pomFile, artifactGroup);
		}
//		  updateTestPom(pomFile);
		
	}
	
	private void updatePOMWithModules(File pomFile, List<com.photon.phresco.commons.model.ArtifactGroup> modules) throws PhrescoException {
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

	protected void updatePOMWithPluginArtifact(File pomFile, List<com.photon.phresco.commons.model.ArtifactGroup> modules) throws PhrescoException {
		try {
		if(CollectionUtils.isEmpty(modules)) {
		return;
		}
		List<Element> configList = new ArrayList<Element>();
		PomProcessor processor = new PomProcessor(pomFile);
		String modulePath = processor.getProperty(Constants.POM_PROP_KEY_MODULE_SOURCE_DIR);
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		for (com.photon.phresco.commons.model.ArtifactGroup module : modules) {
		if (module != null) {
		configList = configList(modulePath, module.getGroupId(), module.getArtifactId(),  module.getVersions().get(0).getVersion(), doc);
		processor.addExecutionConfiguration("org.apache.maven.plugins", "maven-dependency-plugin", "unpack-module", "validate", "unpack", configList, false, doc);
		}
		}
		processor.save();
		} catch (PhrescoPomException e) {
		throw new PhrescoException(e);
		} catch (ParserConfigurationException e) {
		throw new PhrescoException(e);
		}
		}

	protected List<Element> configList(String modulePath, String moduleGroupId, String moduleArtifactId, String moduleVersion, Document doc) throws PhrescoException {
		List<Element> configList = new ArrayList<Element>();
		Element groupId = doc.createElement("groupId");
		groupId.setTextContent(moduleGroupId);
		Element artifactId = doc.createElement("artifactId");
		artifactId.setTextContent(moduleArtifactId);
		Element version = doc.createElement("version");
		version.setTextContent(moduleVersion);
		Element type = doc.createElement("type");
		type.setTextContent("zip");
		Element overWrite = doc.createElement("overWrite");
		overWrite.setTextContent("false");
		Element outputDirectory = doc.createElement("outputDirectory");
		outputDirectory.setTextContent("${project.basedir}" + modulePath);
		configList.add(groupId);
		configList.add(artifactId);
		configList.add(version);
		configList.add(type);
		configList.add(overWrite);
		configList.add(outputDirectory);
		return configList;
	}

//	protected static void updateTestPom(File sourcePom) throws PhrescoException {
//		try {
//			if (!sourcePom.exists()) {
//				return;
//			}
//			
//			PomProcessor processor;
//			processor = new PomProcessor(sourcePom);
//			String groupId = processor.getGroupId();
//			String artifactId = processor.getArtifactId();
//			String version = processor.getVersion();
//			String name = processor.getName();
//			Set<String> keySet = testPomFiles.keySet();
//			for (String string : keySet) {
//			    File testPomFile = new File(sourcePom.getParent() + testPomFiles.get(string));
//			    if (testPomFile.exists()) {
//                  processor = new PomProcessor(testPomFile);
//                  processor.setGroupId(groupId + "." + string);
//                  processor.setArtifactId(artifactId);
//                  processor.setVersion(version);
//                  if (name != null && !name.isEmpty()) {
//                      processor.setName(name);
//                  }
//                  processor.save();
//              }
//            }
//			
//		} catch (Exception e) {
//			throw new PhrescoException(e);
//		}
//	}

}
