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
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;

/**
 * Default Application Processor for MultiModule
 * @author syed_ah
 *
 */

public class DefaultApplicationProcessor extends AbstractApplicationProcessor {

	@Override
	public void postUpdate(ApplicationInfo appInfo,
			List<ArtifactGroup> artifactGroups, List<ArtifactGroup> deletedFeatures) throws PhrescoException {
			
			System.out.println(" Inside post update in Default Application Processor ");
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
			
			System.out.println(" sourceFolderLocation.getPath() : " + sourceFolderLocation.getPath());
			
			File pomFile = new File(sourceFolderLocation.getPath() + File.separator + appInfo.getPomFile());
			ProjectUtils projectUtils = new ProjectUtils();
			
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
	}
}
