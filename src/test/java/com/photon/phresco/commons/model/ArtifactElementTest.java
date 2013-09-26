package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArtifactElementTest {
	ArtifactElement applicationType = new ArtifactElement();
	String licenseId = "test";
	String artifactGroupId = "test123";

	@Test
	public void testSetLicenseId() {
		applicationType.setLicenseId(licenseId);
	}

	@Test
	public void testGetLicenseId() {
		applicationType.setLicenseId(licenseId);
		licenseId = applicationType.getLicenseId();
		assertNotNull(licenseId);
	}
	
	@Test
	public void testSetArtifactGroupId() {
		applicationType.setArtifactGroupId(artifactGroupId);
	}

	@Test
	public void testGetArtifactGroupId() {
		applicationType.setArtifactGroupId(artifactGroupId);
		artifactGroupId = applicationType.getArtifactGroupId();
		assertNotNull(artifactGroupId);
	}
}
