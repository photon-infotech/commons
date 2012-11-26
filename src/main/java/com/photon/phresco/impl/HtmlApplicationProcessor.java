package com.photon.phresco.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactGroup.Type;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;

public class HtmlApplicationProcessor implements ApplicationProcessor {

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
	public void postUpdate(ApplicationInfo appInfo, List<ArtifactGroup> artifactGroups) throws PhrescoException {
		File pomFile = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator
				+ Constants.POM_NAME);
		ProjectUtils projectUtils = new ProjectUtils();
		List<ArtifactGroup> featuresArtifact = new ArrayList<ArtifactGroup>();
		List<ArtifactGroup> jsArtifact = new ArrayList<ArtifactGroup>();
		if (CollectionUtils.isNotEmpty(artifactGroups)) {
			for (ArtifactGroup artifactGroup : artifactGroups) {
				if (artifactGroup.getType().name().equals(Type.FEATURE.name())) {
					featuresArtifact.add(artifactGroup);
				} else if (artifactGroup.getType().name().equals(Type.JAVASCRIPT.name())
						|| artifactGroup.getType().name().equals(Type.COMPONENT.name())) {
					jsArtifact.add(artifactGroup);
				}
			}
			projectUtils.updatePOMWithModules(pomFile, featuresArtifact);
			projectUtils.updatePOMWithPluginArtifact(pomFile, jsArtifact);
			 //TODO: Need to handle the way of getting the servers
			 //TODO: move ServerPluginUtil class from framework to commons
//			ServerPluginUtil spUtil = new ServerPluginUtil();
//			spUtil.addServerPlugin(info, pomPath);
		}
	}
}
