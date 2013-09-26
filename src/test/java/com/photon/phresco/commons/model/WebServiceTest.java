package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class WebServiceTest {
	
	WebService webService = new WebService();
	WebService webService2 = new WebService("testId", "testName", "testVersion", "testDescription");

	@Test
	public void testGetVersion() {
		webService.setVersion("testVersion");
		String test = webService.getVersion();
		assertNotNull(test);
	}
	
	@Test
	public void testGetAppliesToTech() {
		List<String> appliesToTech = new ArrayList<String>();
		appliesToTech.add("testTech");
		webService.setAppliesToTechs(appliesToTech);
		List<String> test = webService.getAppliesToTechs();
		assertNotNull(test);
	}
	
	@Test
	public void testToString() {
		String test = webService.toString();
		assertNotNull(test);
	}
}
