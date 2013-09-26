package com.photon.phresco.model;

import static org.junit.Assert.*;
import org.junit.Test;


public class FrameworkOptionsTest {
	FrameworkOptions frameworkOptions = new FrameworkOptions();
	
	@Test
	public void testGetDataFromPropertyFile() {
		String test = frameworkOptions.getDataFromPropertyFile("lbl.framework.options.run.against.source");
		assertNotNull(test);
	}
	
	@Test
	public void testGetEnum() {
		assertNotNull(FrameworkOptions.Options.BUILD);
		assertNotNull(FrameworkOptions.Options.CI);
		assertNotNull(FrameworkOptions.Options.CODEVALIDATION);
		assertNotNull(FrameworkOptions.Options.DEPLOY);
		assertNotNull(FrameworkOptions.Options.FUNCTIONAL);
		assertNotNull(FrameworkOptions.Options.JSLIBRARIES);
		assertNotNull(FrameworkOptions.Options.LOAD);
		assertNotNull(FrameworkOptions.Options.PERFORMANCE);
		assertNotNull(FrameworkOptions.Options.RUNAGAINSTSOURCE);
		assertNotNull(FrameworkOptions.Options.UNIT);
		assertNotNull(FrameworkOptions.Options.getEnum(FrameworkOptions.Options.UNIT.getValue()));
		assertNotNull(FrameworkOptions.Options.UNIT.getFlag());
	}
}
