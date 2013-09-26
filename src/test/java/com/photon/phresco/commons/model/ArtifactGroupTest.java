package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.photon.phresco.commons.model.ArtifactGroup.Type;

public class ArtifactGroupTest {
	ArtifactGroup artGroup = new ArtifactGroup();
	ArtifactGroup artGroup1 = new ArtifactGroup("test");
	ArtifactGroup artGroup2 = new ArtifactGroup("testGroup", "test");

	@Test
	public void testGetGroupId() {
		artGroup.setGroupId("testGroup");
		String test = artGroup.getGroupId();
		assertNotNull(test);		
	}
	
	@Test
	public void testGetArtifactId() {
		artGroup.setArtifactId("testArtifactId");
		String test = artGroup.getArtifactId();
		assertNotNull(test);		
	}
	
	@Test
	public void testGetPackaging() {
		artGroup.setPackaging("testPackage");
		String test = artGroup.getPackaging();
		assertNotNull(test);		
	}
	
	@Test
	public void testGetVersions() {
		List<ArtifactInfo> artInfos = new ArrayList<ArtifactInfo>();
		ArtifactInfo artInfo = new ArtifactInfo();
		artInfo.setDescription("sadsad");
		artInfo.setDisplayName("test");
		artInfo.setId("213hg213417");
		artInfo.setName("test");
		artInfo.setCreationDate(null);
		artInfo.setHelpText("help");
		artInfo.setStatus(null);
		artInfo.setSystem(false);
		artInfo.setUsed(false);
		artInfo.setAppliesTo(null);
		artInfo.setArtifactGroupId("testArtifact");
		artInfo.setDependencyIds(null);
		artInfo.setDownloadURL(null);
		artInfo.setFileSize(1024);
		artInfo.setScope("testScope");
		artInfo.setVersion("1.0");
		artInfos.add(artInfo);
		artGroup.setVersions(artInfos);
		List<ArtifactInfo> test = artGroup.getVersions();
		assertNotNull(test);		
	}
	
	@Test
	public void testGetType() {
		Type type = Type.ICON;
		artGroup.setType(type);
		Type test = artGroup.getType();
		assertNotNull(test);		
	}
	
	@Test
	public void testGetImageUrl() {
		artGroup.setImageURL("testURL");
		String test = artGroup.getImageURL();
		assertNotNull(test);
	}
	
	@Test
	public void testGetClassifier() {
		artGroup.setClassifier("testClassifier");
		String test = artGroup.getClassifier();
		assertNotNull(test);
	}
	
	@Test
	public void testGetAppliesTo() {
		List<CoreOption> coreOptions = new ArrayList<CoreOption>();
		CoreOption coreOption = new CoreOption();
		coreOption.setCore(false);
		coreOption.setTechId("testTechId");
		coreOptions.add(coreOption);
		artGroup.setAppliesTo(coreOptions);
		List<CoreOption> test = artGroup.getAppliesTo();
		assertNotNull(test);
	}
	
	@Test
	public void testGetLicenseId() {
		artGroup.setLicenseId("testLicenseId");
		String test = artGroup.getLicenseId();
		assertNotNull(test);
	}
	
	@Test
	public void testToString() {
		String test = artGroup.toString();
		assertNotNull(test);
	}
}
