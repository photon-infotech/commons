package com.photon.phresco.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.collections.CollectionUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.pom.POMConstants;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class AndroidApplicationProcessor implements ApplicationProcessor {

	private static final String UNIT_TEST_POM_XML = "/test/unit/pom.xml";
	private static final String PERFORMANCE_TEST_POM_XML = "/test/performance/pom.xml";
	private static final String FUNCTIONAL_TEST_POM_XML = "/test/functional/pom.xml";
	private static final String POM = "pom.xml";
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
		try {
			String projectHome = Utility.getProjectHome() + appInfo.getAppDirName();
			File path = new File(projectHome);
			updateAndroidVersion(path, appInfo);
			updatePOM( new File(projectHome));
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	@Override
	public void postUpdate(ApplicationInfo appInfo, List<ArtifactGroup> artifactGroups) throws PhrescoException {
		//		extractPilots(info, path, technology);
		File pomFile = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + Constants.POM_NAME);
		String projectHome = Utility.getProjectHome() + appInfo.getAppDirName();
		ProjectUtils projectUtils = new ProjectUtils();
		if(CollectionUtils.isNotEmpty(artifactGroups)) {
			projectUtils.updatePOMWithPluginArtifact(pomFile, artifactGroups);
		}
		BufferedReader breader = projectUtils.ExtractFeature(appInfo);
		try {
		String line = "";
			while ((line = breader.readLine()) != null) {
				if (line.startsWith("[ERROR]")) {
					System.out.println(line);
				}
			}
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
		updatePOM( new File(projectHome));
	}

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
	public void postConfiguration(ApplicationInfo appInfo, List<Configuration> configurations) throws PhrescoException {
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
	
	/**
	 * @param path
	 *            file path where the pom.xml present.
	 * @return 
	 * @throws PhrescoException
	 * @throws PhrescoPomException 
	 * @throws JAXBException 
	 */
	private static boolean updatePOM(File path) throws PhrescoException {
		File projectHome = new File(path, POM);
		File testFunctionalPom = new File(path, FUNCTIONAL_TEST_POM_XML);
		File testUnitPom = new File(path, UNIT_TEST_POM_XML);
		File testPerformancePom = new File(path, PERFORMANCE_TEST_POM_XML);
		boolean functionalTest = updateTestPom(testFunctionalPom, projectHome);
		boolean unitTest = updateTestPom(testUnitPom, projectHome);
		boolean performanceTest = updateTestPom(testPerformancePom, projectHome);
		if (Boolean.TRUE.equals(functionalTest) || Boolean.TRUE.equals(unitTest) || Boolean.TRUE.equals(performanceTest)  ) {
			return true;
		} else {
			return false;
		}
	}
		
	/**
	 * @param testPom
	 * @param projectPom
	 * @param projectHome 
	 * @return 
	 * @throws PhrescoException 
	 * @throws JDOMException
	 * @throws IOException
	 * @throws JAXBException 
	 * @throws PhrescoPomException 
	 */
	private static boolean updateTestPom(File projectPom, File projectHome) throws PhrescoException {
		SAXBuilder builder = new SAXBuilder();
		if(!projectPom.exists()) {
			return false;
		}
		try {
			Document projectDoc = builder.build(projectHome);
			Element projectRootNode = projectDoc.getRootElement();
			Element group = getNode(projectRootNode, POMConstants.GROUP_ID);
			Element artifact = getNode(projectRootNode, POMConstants.ARTIFACT_ID);
			Element version = getNode(projectRootNode, POMConstants.VERSION);
			PomProcessor processor = new PomProcessor(projectPom);
			processor.addDependency(group.getText(), artifact.getText(),  version.getText() , POMConstants.PROVIDED , POMConstants.JAR, "");
			processor.addDependency(group.getText(), artifact.getText(),  version.getText() , POMConstants.PROVIDED , POMConstants.APK, "");
			processor.save();
		
		} catch (JDOMException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
		return true;
	}

	/**
	 * @param rootNode
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static Element getNode(Element rootNode, String nodeName) {
		Element dependencies = rootNode.getChild(nodeName);
		// sometime, this doesn't work. So as workaround this stint.
		if (dependencies == null) {
			List children = rootNode.getChildren();
			for (Object object : children) {
				if ((object instanceof Element) && ((Element) object).getName().equals(nodeName)) {
					dependencies = (Element) object;
					break;
				}
			}
		}
		return dependencies;
	}
}
