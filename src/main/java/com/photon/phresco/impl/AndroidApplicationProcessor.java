package com.photon.phresco.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;
import com.phresco.pom.util.PomProcessor;

public class AndroidApplicationProcessor implements ApplicationProcessor {

	private static final String UNIT_TEST_POM_XML = "/test/unit/pom.xml";
    private static final String PERFORMANCE_TEST_POM_XML = "/test/performance/pom.xml";
    private static final String FUNCTIONAL_TEST_POM_XML = "/test/functional/pom.xml";
	private String ANDROID_VERSION = "android.version";
	
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
		String projectHome = Utility.getProjectHome() + appInfo.getAppDirName();
		File path = new File(projectHome);
		updateAndroidVersion(path, appInfo);
		
	}

	@Override
	public void postUpdate(ApplicationInfo appInfo, List<ArtifactGroup> artifactGroups) throws PhrescoException {
//		extractPilots(info, path, technology);
		File pomFile = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + Constants.POM_NAME);
		ProjectUtils projectUtils = new ProjectUtils();
		if(CollectionUtils.isNotEmpty(artifactGroups)) {
			projectUtils.updatePOMWithModules(pomFile, artifactGroups);
		}
	}
	
//	private void extractPilots(ApplicationInfo appInfo, File path, Technology technology) throws PhrescoException {
//		if (StringUtils.isNotBlank(appInfo.getPilotProjectName())) {
//			List<ProjectInfo> pilotProjects = getRepositoryManager().getPilotProjects(technology.getId());
//			if (CollectionUtils.isEmpty(pilotProjects)) {
//				return;
//			}
//			for (ProjectInfo projectInfo : pilotProjects) {
//				String urls[] = projectInfo.getPilotProjectUrls();
//				if (urls != null) {
//					for (String url : urls) {
//						DependencyUtils.extractFiles(url, path);
//					}
//				}
//			}
//		}
//	}
	
	public void updateAndroidVersion(File path, ApplicationInfo appInfo) throws PhrescoException {
		File pomPath = new File(path ,Constants.POM_NAME);
		if(!pomPath.exists()) {
			return;
		}
		PomProcessor processor;
		List<File> fileList = new ArrayList<File>();
		fileList.add(new File(path, Constants.POM_NAME));
		fileList.add(new File(path, FUNCTIONAL_TEST_POM_XML));
		fileList.add(new File(path, PERFORMANCE_TEST_POM_XML));
		fileList.add(new File(path, UNIT_TEST_POM_XML));
		for (File pomFile : fileList) {
			if(pomFile.exists()) {
				try {
					processor = new PomProcessor(pomFile);
					String selectedVersion = appInfo.getTechInfo().getVersion();
					processor.setProperty(ANDROID_VERSION, selectedVersion);
					processor.save();
				} catch (Exception e) {
					e.printStackTrace();
					throw new PhrescoException(e);
				}
            }
		}
	}

@Override
public void postConfiguration(ApplicationInfo appInfo) throws PhrescoException {
	// TODO Auto-generated method stub
	
}

@Override
public void preFeatureConfiguration(ApplicationInfo appInfo)
		throws PhrescoException {
	// TODO Auto-generated method stub
	
}

@Override
public void postFeatureConfiguration(ApplicationInfo appInfo)
		throws PhrescoException {
	// TODO Auto-generated method stub
	
}
}
