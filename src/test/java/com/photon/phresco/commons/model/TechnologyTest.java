package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class TechnologyTest {
	
	Technology technology = new Technology();
	Technology technology2 = new Technology("testId");

	@Test
	public void testGetApplicableTechnologies() {
		List<String> applEmbedTechs = new ArrayList<String>();
		applEmbedTechs.add("testEmbed");
		technology.setApplicableEmbedTechnology(applEmbedTechs);
		List<String> test = technology.getApplicableEmbedTechnology();
		assertNotNull(test);
	}
	
	@Test
	public void testGetAppTypeId() {
		technology.setAppTypeId("testAppTypeId");
		String test = technology.getAppTypeId();
		assertNotNull(test);
	}
	
	@Test
	public void testGetArchetypeFeatures() {
		List<String> archetypeFeatures = new ArrayList<String>();
		archetypeFeatures.add("testArchfeature");
		technology.setArchetypeFeatures(archetypeFeatures);
		List<String> test = technology.getArchetypeFeatures();
		assertNotNull(test);
	}
	
	@Test
	public void testGetArchetypeInfo() {
		ArtifactGroup artGroup = new ArtifactGroup();
		artGroup.setId("testId");
		artGroup.setName("testName");
		technology.setArchetypeInfo(artGroup);
		ArtifactGroup test = technology.getArchetypeInfo();
		assertNotNull(test);
	}
	
	@Test
	public void testGetCreationDate() {
		Date date = new Date(System.currentTimeMillis());
		technology.setCreationDate(date);
		Date test = technology.getCreationDate();
		assertNotNull(test);
	}
	
	@Test
	public void testGetCustomerIds() {
		List<String> customerIds = new ArrayList<String>();
		customerIds.add("testCustomer");
		technology.setCustomerIds(customerIds);
		List<String> test = technology.getCustomerIds();
		assertNotNull(test);
	}
	
	@Test
	public void testGetDescription() {
		technology.setDescription("testDescription");
		String test = technology.getDescription();
		assertNotNull(test);
	}
	
	@Test
	public void testGetDisplayName() {
		technology.setDisplayName("testDisplayName");
		String test = technology.getDisplayName();
		assertNotNull(test);
	}
	
	@Test
	public void testGetFunctionalFrameworks() {
		List<FunctionalFrameworkGroup> functionalFrameworks = new ArrayList<FunctionalFrameworkGroup>();
		FunctionalFrameworkGroup ffGroup = new FunctionalFrameworkGroup();
		ffGroup.setId("testId");
		ffGroup.setName("testName");
		functionalFrameworks.add(ffGroup);
		technology.setFunctionalFrameworks(functionalFrameworks);
		List<FunctionalFrameworkGroup> test = technology.getFunctionalFrameworks();
		assertNotNull(test);
	}
	
	@Test
	public void testGetFunctionalFrameworksInfo() {
		List<FunctionalFrameworkInfo> functionalFrameworksInfo = new ArrayList<FunctionalFrameworkInfo>();
		FunctionalFrameworkInfo ffInfo = new FunctionalFrameworkInfo();
		ffInfo.setId("testId");
		ffInfo.setName("testName");
		functionalFrameworksInfo.add(ffInfo);
		technology.setFunctionalFrameworksInfo(functionalFrameworksInfo);
		List<FunctionalFrameworkInfo> test = technology.getFunctionalFrameworksInfo();
		assertNotNull(test);
	}
	
	@Test
	public void testGetHelpText() {
		technology.setHelpText("testHelpText");
		String test = technology.getHelpText();
		assertNotNull(test);
	}
	
	@Test
	public void testGetId() {
		technology.setId("testId");
		String test = technology.getId();
		assertNotNull(test);
	}
	
	@Test
	public void testGetName() {
		technology.setName("testName");
		String test = technology.getName();
		assertNotNull(test);
	}
	
	@Test
	public void testGetOptions() {
		List<String> options = new ArrayList<String>();
		options.add("testOption");
		technology.setOptions(options);
		List<String> test = technology.getOptions();
		assertNotNull(test);
	}
	
	@Test
	public void testGetPlugins() {
		List<ArtifactGroup> plugins = new ArrayList<ArtifactGroup>();
		ArtifactGroup artGroup = new ArtifactGroup("testId");
		plugins.add(artGroup);
		technology.setPlugins(plugins);
		List<ArtifactGroup> test = technology.getPlugins();
		assertNotNull(test);
	}
	
	@Test
	public void testGetReports() {
		List<String> reports = new ArrayList<String>();
		reports.add("testReport");
		technology.setReports(reports);
		List<String> test = technology.getReports();
		assertNotNull(test);
	}
	
	@Test
	public void testGetStatus() {
		Status status = Status.NEW;
		technology.setStatus(status);
		Status test = technology.getStatus();
		assertNotNull(test);
	}
	
	@Test
	public void testIsSystem() {
		technology.setSystem(false);
		Boolean test = technology.isSystem();
		assertNotNull(test);
	}
	
	@Test
	public void testGetTechGroupId() {
		technology.setTechGroupId("testTechGroupId");
		String test = technology.getTechGroupId();
		assertNotNull(test);
	}
	
	@Test
	public void testGetTechVersions() {
		List<String> techVersions = new ArrayList<String>();
		techVersions.add("1.0");
		technology.setTechVersions(techVersions);
		List<String> test = technology.getTechVersions();
		assertNotNull(test);
	}
	
	@Test
	public void testIsUsed() {
		technology.setUsed(false);
		Boolean test = technology.isUsed();
		assertNotNull(test);
	}
	
	@Test
	public void testToString() {
		String test = technology.toString();
		assertNotNull(test);
	}
}