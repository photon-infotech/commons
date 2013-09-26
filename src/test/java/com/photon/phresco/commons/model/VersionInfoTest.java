package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class VersionInfoTest {
	
	VersionInfo versionInfo = new VersionInfo();

	@Test
	public void testGetMessage() {
		versionInfo.setMessage("testMessage");
		String test = versionInfo.getMessage();
		assertNotNull(test);
	}
	
	@Test
	public void testIsUpdateAvailable() {
		versionInfo.setUpdateAvailable(false);
		Boolean test = versionInfo.isUpdateAvailable();
		assertNotNull(test);
	}
	
	@Test
	public void testGetFrameworkVersion() {
		versionInfo.setFrameworkVersion("2.0");
		String test = versionInfo.getFrameworkVersion();
		assertNotNull(test);
	}
	
	@Test
	public void testGetServiceVersion() {
		versionInfo.setServiceVersion("2.0");
		String test = versionInfo.getServiceVersion();
		assertNotNull(test);
	}
	
	@Test
	public void testToString() {
		String test = versionInfo.toString();
		assertNotNull(test);
	}
}
