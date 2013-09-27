package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class ApplicationInfoTest {
	
	ApplicationInfo appInfo = new ApplicationInfo();

	@Test
	public void testGetAppDirName() {
		appInfo.setAppDirName("testAppDirName");
		String test = appInfo.getAppDirName();
		assertNotNull(test);
	}
	
	@Test
	public void testGetCode() {
		appInfo.setCode("testCode");
		String test = appInfo.getCode();
		assertNotNull(test);
	}
	
	@Test
	public void testIsCreated() {
		appInfo.setCreated(false);
		Boolean test = appInfo.isCreated();
		assertNotNull(test);
	}
	
	@Test
	public void testGetDate() {
		Date date = new Date(System.currentTimeMillis());
		appInfo.setCreationDate(date);
		Date test = appInfo.getCreationDate();
		assertNotNull(test);
	}
	
	@Test
	public void testGetCustomerIds() {
		List<String> customerIds = new ArrayList<String>();
		customerIds.add("testCustomer");
		appInfo.setCustomerIds(customerIds);
		List<String> test = appInfo.getCustomerIds();
		assertNotNull(test);
	}
	
	@Test
	public void testGetDependentModules() {
		List<String> dependentModules = new ArrayList<String>();
		dependentModules.add("testModule");
		appInfo.setDependentModules(dependentModules);
		List<String> test = appInfo.getDependentModules();
		assertNotNull(test);
	}
	
	@Test
	public void testGetDesc() {
		appInfo.setDescription("testDesc");
		String test = appInfo.getDescription();
		assertNotNull(test);
	}
	
	@Test
	public void testGetDisplayName() {
		appInfo.setDisplayName("testName");
		String test = appInfo.getDisplayName();
		assertNotNull(test);
	}
	
	@Test
	public void testIsEmailSupported() {
		appInfo.setEmailSupported(false);
		Boolean test = appInfo.isEmailSupported();
		assertNotNull(test);
	}
	
	@Test
	public void testGetEmbedAppId() {
		appInfo.setEmbedAppId("embedId");
		String test = appInfo.getEmbedAppId();
		assertNotNull(test);
	}
	
	@Test
	public void testGetFunctionalFramework() {
		appInfo.setFunctionalFramework("testFramework");
		String test = appInfo.getFunctionalFramework();
		assertNotNull(test);
	}
	
	@Test
	public void testGetFunctionalFrameworkInfo() {
		FunctionalFrameworkInfo ffInfo = new FunctionalFrameworkInfo();
		ffInfo.setId("testid");
		ffInfo.setName("testName");
		appInfo.setFunctionalFrameworkInfo(ffInfo);
		FunctionalFrameworkInfo test = appInfo.getFunctionalFrameworkInfo();
		assertNotNull(test);
	}
	
	@Test
	public void testGetHelpText() {
		appInfo.setHelpText("TestHelpText");
		String test = appInfo.getHelpText();
		assertNotNull(test);
	}
	
	@Test
	public void testGetId() {
		appInfo.setId("testId");
		String test = appInfo.getId();
		assertNotNull(test);
	}
	
	@Test
	public void testGetModules() {
		List<ModuleInfo> modInfos = new ArrayList<ModuleInfo>();
		ModuleInfo modInfo = new ModuleInfo();
		modInfo.setId("testid");
		modInfo.setName("testName");
		modInfos.add(modInfo);
		appInfo.setModules(modInfos);
		List<ModuleInfo> test = appInfo.getModules();
		assertNotNull(test);
	}
	
	@Test
	public void testGetName() {
		appInfo.setName("testName");
		String test = appInfo.getName();
		assertNotNull(test);
	}
	
	@Test
	public void testIsPhoneEnabled() {
		appInfo.setPhoneEnabled(false);
		Boolean test = appInfo.isPhoneEnabled();
		assertNotNull(test);
	}
	
	@Test
	public void testIsPilot() {
		appInfo.setPilot(false);
		Boolean test = appInfo.isPilot();
		assertNotNull(test);
	}
	
	@Test
	public void testGetPilotContent() {
		ArtifactGroup artGroup = new ArtifactGroup("testGroup", "testArtifact");
		appInfo.setPilotContent(artGroup);
		ArtifactGroup test = appInfo.getPilotContent();
		assertNotNull(test);
	}
	
	@Test
	public void testGetPilotInfo() {
		Element pilotInfo = new Element("testId");
		appInfo.setPilotInfo(pilotInfo);
		Element test = appInfo.getPilotInfo();
		assertNotNull(test);
	}
	
	@Test
	public void testGetPomFile() {
		appInfo.setPomFile("pom.xml");
		String test = appInfo.getPomFile();
		assertNotNull(test);
	}
	
	@Test
	public void testGetSelectedComponents() {
		List<String> selectedComponents = new ArrayList<String>();
		selectedComponents.add("testComponent");
		appInfo.setSelectedComponents(selectedComponents);
		List<String> test = appInfo.getSelectedComponents();
		assertNotNull(test);
	}
	
	@Test
	public void testGetSelectedDatabases() {
		List<ArtifactGroupInfo> selectedDatabases = new ArrayList<ArtifactGroupInfo>();
		ArtifactGroupInfo info = new ArtifactGroupInfo();
		info.setId("testId");
		info.setName("testName");
		selectedDatabases.add(info);
		appInfo.setSelectedDatabases(selectedDatabases);
		List<ArtifactGroupInfo> test = appInfo.getSelectedDatabases();
		assertNotNull(test);
	}
	
	@Test
	public void testGetSelectedFrameworks() {
		List<ArtifactGroup> selectedFrameworks = new ArrayList<ArtifactGroup>();
		ArtifactGroup artfGroup = new ArtifactGroup("testId"); 
		selectedFrameworks.add(artfGroup);
		appInfo.setSelectedFrameworks(selectedFrameworks);
		List<ArtifactGroup> test = appInfo.getSelectedFrameworks();
		assertNotNull(test);
	}
	
	@Test
	public void testGetSelectedJSLibs() {
		List<String> selectedJSLibs = new ArrayList<String>();
		selectedJSLibs.add("testJSLib");
		appInfo.setSelectedJSLibs(selectedJSLibs);
		List<String> test = appInfo.getSelectedJSLibs();
		assertNotNull(test);
	}
	
	@Test
	public void testGetSelectedModules() {
		List<String> selectedModules = new ArrayList<String>();
		selectedModules.add("testModule");
		appInfo.setSelectedModules(selectedModules);
		List<String> test = appInfo.getSelectedModules();
		assertNotNull(test);
	}
	
	@Test
	public void testGetSelectedServers() {
		List<ArtifactGroupInfo> selectedServers = new ArrayList<ArtifactGroupInfo>();
		ArtifactGroupInfo info = new ArtifactGroupInfo();
		info.setId("testId");
		info.setName("testName");
		selectedServers.add(info);
		appInfo.setSelectedServers(selectedServers);
		List<ArtifactGroupInfo> test = appInfo.getSelectedServers();
		assertNotNull(test);
	}
	
	@Test
	public void testGetSelectedWebservices() {
		List<String> selectedWebservices = new ArrayList<String>();
		selectedWebservices.add("testWebservice");
		appInfo.setSelectedWebservices(selectedWebservices);
		List<String> test = appInfo.getSelectedWebservices();
		assertNotNull(test);
	}
	
	@Test
	public void testGetStatus() {
		Status status = Status.NEW;
		appInfo.setStatus(status);
		Status test = appInfo.getStatus();
		assertNotNull(test);
	}
	
	@Test
	public void testIsSystem() {
		appInfo.setSystem(false);
		Boolean test = appInfo.isSystem();
		assertNotNull(test);
	}
	
	@Test
	public void testIsTabletEnabled() {
		appInfo.setTabletEnabled(false);
		Boolean test = appInfo.isTabletEnabled();
		assertNotNull(test);
	}
	
	@Test
	public void testGetTechInfo() {
		TechnologyInfo techInfo = new TechnologyInfo("testAppTypeId", "1.0", "testTechGroupId"); 
		appInfo.setTechInfo(techInfo);
		String test = appInfo.getId();
		assertNotNull(test);
	}
	
	@Test
	public void testIsUsed() {
		appInfo.setUsed(false);
		Boolean test = appInfo.isUsed();
		assertNotNull(test);
	}
	
	@Test
	public void testGetVersion() {
		appInfo.setVersion("1.0");
		String test = appInfo.getVersion();
		assertNotNull(test);
	}
	
	@Test
	public void testToString() {
		String test = appInfo.toString();
		assertNotNull(test);
	}
}