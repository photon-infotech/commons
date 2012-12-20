package com.photon.phresco.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.CoreOption;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class DrupalApplicationProcessor implements ApplicationProcessor{
	
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
		File path = new File(Utility.getProjectHome() + appInfo.getAppDirName());
		updateDrupalVersion(path, appInfo);
		
	}

	@Override
	public void postUpdate(ApplicationInfo appInfo,
			List<ArtifactGroup> artifactGroups) throws PhrescoException {
		File pomFile = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + Constants.POM_NAME);
		ProjectUtils projectUtils = new ProjectUtils();
		projectUtils.deletePluginExecutionFromPom(pomFile);
		if(CollectionUtils.isNotEmpty(artifactGroups)) {
			projectUtils.updatePOMWithPluginArtifact(pomFile, artifactGroups);
			excludeModule(appInfo, artifactGroups);
		}
	}
	
	private void updateDrupalVersion(File path, ApplicationInfo info) throws PhrescoException {
		try {
			File xmlFile = new File(path, Constants.POM_NAME);
			PomProcessor processor = new PomProcessor(xmlFile);
			String selectedVersion = info.getTechInfo().getVersion();
			processor.setProperty(Constants.DRUPAL_VERSION, selectedVersion);
			processor.save();
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}
	
	private void excludeModule(ApplicationInfo appInfo, List<ArtifactGroup> artifactGroups) throws PhrescoException {
		try {
			File projectPath = new File(Utility.getProjectHome()+ File.separator + appInfo.getAppDirName() + File.separator + Constants.POM_NAME);
			PomProcessor processor = new PomProcessor(projectPath);
			StringBuilder exclusionStringBuff = new StringBuilder();
			StringBuilder exclusionValueBuff = new StringBuilder();
			if (CollectionUtils.isEmpty(artifactGroups)) {
				return;
			}
			for (ArtifactGroup artifactGroup : artifactGroups) {
				List<CoreOption> appliesTo = artifactGroup.getAppliesTo();
				for (CoreOption coreOption : appliesTo) {
					if (coreOption.isCore()) {
						exclusionValueBuff.append(artifactGroup.getName().toLowerCase());
						exclusionValueBuff.append(",");
						exclusionStringBuff.append("**\\");
						exclusionStringBuff.append(artifactGroup.getName().toLowerCase());
						exclusionStringBuff.append("\\**");
						exclusionStringBuff.append(",");
					}
				}
			}
			String exclusionValue = exclusionStringBuff.toString();
			if (exclusionValue.lastIndexOf(',') != -1) {
				exclusionValue = exclusionValue.substring(0, exclusionValue.lastIndexOf(','));
			}
			
			String exclusiontoolValue = exclusionValueBuff.toString();
			if (exclusiontoolValue.lastIndexOf(',') != -1) {
				exclusiontoolValue = exclusiontoolValue.substring(0, exclusiontoolValue.lastIndexOf(','));
			}
			processor.setProperty("sonar.exclusions", exclusionValue);
			processor.setProperty("sonar.phpDepend.argumentLine", "--ignore=" + exclusiontoolValue);
			processor.setProperty("sonar.phpPmd.argumentLine", "--exclude" + exclusiontoolValue);
			processor.save();
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	@Override
	public void postConfiguration(ApplicationInfo appInfo)
			throws PhrescoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Configuration> preFeatureConfiguration(ApplicationInfo appInfo,
			String featureName) throws PhrescoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void postFeatureConfiguration(ApplicationInfo appInfo,
			List<Configuration> configs, String featureName)
			throws PhrescoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preBuild(ApplicationInfo appInfo) throws PhrescoException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void postBuild(ApplicationInfo appInfo) throws PhrescoException {
		// TODO Auto-generated method stub
		
	}
}
