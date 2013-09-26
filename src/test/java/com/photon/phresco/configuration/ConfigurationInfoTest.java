package com.photon.phresco.configuration;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigurationInfoTest {
	
	ConfigurationInfo confInfo = new ConfigurationInfo();
	
	@Test
	public void testGetEnvironment() {
		confInfo.setEnvironmentName("testEnv");
		assertNotNull(confInfo.getEnvironment());
	}
	
	@Test
	public void testGetContext() {
		confInfo.setContext("testContext");
		assertNotNull(confInfo.getContext());
	}
	
	@Test
	public void testGetModule() {
		confInfo.setModuleName("testModule");
		assertNotNull(confInfo.getModuleName());
	}
	
	@Test
	public void testGetServerPort() {
		confInfo.setServerPort("testPort");
		assertNotNull(confInfo.getServerPort());
	}
	
	@Test
	public void testToString() {
		assertNotNull(confInfo.toString());
	}
}
