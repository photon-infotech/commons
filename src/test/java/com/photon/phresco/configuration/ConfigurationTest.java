package com.photon.phresco.configuration;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigurationTest {
	
	@Test
	public void testConstructor() {
		Configuration configuration = new Configuration("testName", "testType");
		assertNotNull(configuration.toString());
	}

}
