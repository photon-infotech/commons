package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TechnologyOptionsTest {
	
	TechnologyOptions techOptions = new TechnologyOptions();
	TechnologyOptions techOptions2 = new TechnologyOptions("testOption");

	@Test
	public void testGetOption() {
		techOptions.setOption("testOption");
		String test = techOptions.getOption();
		assertNotNull(test);
	}
	
	@Test
	public void testToString() {
		String test = techOptions.toString();
		assertNotNull(test);
	}
}
