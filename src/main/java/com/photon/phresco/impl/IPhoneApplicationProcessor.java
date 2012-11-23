package com.photon.phresco.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;

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
//		updatePOMWithModules(pomFile, artifactGroup);
		ProjectUtils projectUtils = new ProjectUtils();
		if(CollectionUtils.isNotEmpty(artifactGroup)) { 
		projectUtils.updatePOMWithPluginArtifact(pomFile, artifactGroup);
		}
//		  updateTestPom(pomFile);
	}
	
//	private void updatePOMWithModules(File pomFile, List<com.photon.phresco.commons.model.ArtifactGroup> modules) throws PhrescoException {
//		if (CollectionUtils.isEmpty(modules)) {
//			return;
//		}
//		try {
//			PomProcessor processor = new PomProcessor(pomFile);
//			for (com.photon.phresco.commons.model.ArtifactGroup module : modules) {
//				if (module != null) {
//					processor.addDependency(module.getGroupId(), module.getArtifactId(), module.getVersions().get(0).getVersion(), "",
//							module.getPackaging(), "");
//				}
//			}
//			processor.save();
//		} catch (PhrescoPomException e) {
//			throw new PhrescoException(e);
//		}
//	}

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
