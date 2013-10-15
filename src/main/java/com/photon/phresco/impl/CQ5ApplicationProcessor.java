package com.photon.phresco.impl;

import java.util.List;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import org.apache.commons.collections.CollectionUtils;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;

public class CQ5ApplicationProcessor extends AbstractApplicationProcessor {
	@Override
	public void postUpdate(ApplicationInfo appInfo, List<ArtifactGroup> artifactGroup, List<ArtifactGroup> deletedFeatures) throws PhrescoException {
		String pomFileName = Utility.getPhrescoPomFile(appInfo);
		File pomFile = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + pomFileName);
		ProjectUtils projectUtils = new ProjectUtils();
		if(CollectionUtils.isNotEmpty(artifactGroup)) {
			projectUtils.updatePOMWithPluginArtifact(pomFile, artifactGroup);
		}
		if(CollectionUtils.isNotEmpty(deletedFeatures)) {
			projectUtils.deleteFeatureDependencies(appInfo, deletedFeatures);
		}
		File phrescoPom = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + pomFileName);
		
		projectUtils.deletePluginFromPom(phrescoPom);
		projectUtils.addServerPlugin(appInfo, phrescoPom);
		
		BufferedReader breader = projectUtils.ExtractFeature(appInfo);
		try {
			String line = "";
			while ((line = breader.readLine()) != null) {
				System.out.println("breader.readLine() ? " + breader.readLine());
				if (line.startsWith("[ERROR]")) {
					System.err.println(line);
				}
			}
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}
}
