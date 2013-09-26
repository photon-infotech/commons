package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class SelectedFeatureTest {
	
	SelectedFeature selectedFeature = new SelectedFeature();

	@Test
	public void testGetArtifactGroupId() {
		selectedFeature.setArtifactGroupId("testArtifactGroupId");
		String test = selectedFeature.getArtifactGroupId();
		assertNotNull(test);
	}
	
	@Test
	public void testIsCanConfigure() {
		selectedFeature.setCanConfigure(false);
		Boolean test = selectedFeature.isCanConfigure();
		assertNotNull(test);
	}
	
	@Test
	public void testIsDefaultModule() {
		selectedFeature.setDefaultModule(false);
		Boolean test = selectedFeature.isDefaultModule();
		assertNotNull(test);
	}
	
	@Test
	public void testGetDispName() {
		selectedFeature.setDispName("testDispName");
		String test = selectedFeature.getDispName();
		assertNotNull(test);
	}
	
	@Test
	public void testGetDisValue() {
		selectedFeature.setDispValue("testDispValue");
		String test = selectedFeature.getDispValue();
		assertNotNull(test);
	}
	
	@Test
	public void testGetModuleId() {
		selectedFeature.setModuleId("testModuleId");
		String test = selectedFeature.getModuleId();
		assertNotNull(test);
	}
	
	@Test
	public void testGetName() {
		selectedFeature.setName("testName");
		String test = selectedFeature.getName();
		assertNotNull(test);
	}
	
	@Test
	public void testGetPackaging() {
		selectedFeature.setPackaging("testPackaging");
		String test = selectedFeature.getPackaging();
		assertNotNull(test);
	}
	
	@Test
	public void testGetScope() {
		selectedFeature.setScope("testScope");
		String test = selectedFeature.getScope();
		assertNotNull(test);
	}
	
	@Test
	public void testGetType() {
		selectedFeature.setType("testType");
		String test = selectedFeature.getType();
		assertNotNull(test);
	}
	
	@Test
	public void testGetVersionId() {
		selectedFeature.setVersionID("testVersionId");
		String test = selectedFeature.getVersionID();
		assertNotNull(test);
	}
	
	@Test
	public void testToString() {
		String test = selectedFeature.toString();
		assertNotNull(test);
	}
}