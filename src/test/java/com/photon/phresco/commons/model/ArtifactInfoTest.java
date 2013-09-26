package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ArtifactInfoTest {
	
	ArtifactInfo artInfo = new ArtifactInfo();

	@Test
	public void testGetArtifactGroupId() {
		artInfo.setArtifactGroupId("testArtifactGroupId");
		String test = artInfo.getArtifactGroupId();
		assertNotNull(test);
	}

	@Test
	public void testGetVersion() {
		artInfo.setVersion("1.0");
		String test = artInfo.getVersion();
		assertNotNull(test);
	}
	
	@Test
	public void testIsUsed() {
		artInfo.setUsed(false);
		Boolean test = artInfo.isUsed();
		assertNotNull(test);
	}
	
	@Test
	public void testgetDependencyIds() {
		List<String> dependencies = new ArrayList<String>();
		dependencies.add("test");
		artInfo.setDependencyIds(dependencies);
		List<String> test = artInfo.getDependencyIds();
		assertNotNull(test);
	}
	
	@Test
	public void testGetAppliesTo() {
		List<RequiredOption> appliesTo = new ArrayList<RequiredOption>();
		RequiredOption req = new RequiredOption();
		req.setRequired(false);
		req.setTechId("test");
		appliesTo.add(req);
		artInfo.setAppliesTo(appliesTo);
		List<RequiredOption> test = artInfo.getAppliesTo();
		assertNotNull(test);
	}
	
	@Test
	public void testGetFileSize() {
		artInfo.setFileSize(1024);
		Long test = artInfo.getFileSize();
		assertNotNull(test);
	}
	
	@Test
	public void testGetDownloadUrl() {
		artInfo.setDownloadURL("testURL");
		String test = artInfo.getDownloadURL();
		assertNotNull(test);
	}
	
	@Test
	public void testGetScope() {
		artInfo.setScope("testScope");
		String test = artInfo.getScope();
		assertNotNull(test);
	}
	
	@Test
	public void testToString() {
		String test = artInfo.toString();
		assertNotNull(test);
	}
	
	@Test
	public void testEnum() {
		assertNotNull(ArtifactInfo.Scope.COMPILE);
		assertNotNull(ArtifactInfo.Scope.PROVIDED);
		assertNotNull(ArtifactInfo.Scope.RUNTIME);
		assertNotNull(ArtifactInfo.Scope.TEST);
	}
}