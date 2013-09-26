package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class CoreOptionTest {
	
	CoreOption coreOption = new CoreOption();
	CoreOption coreOption2 = new CoreOption("testTechId", false);

	@Test
	public void testGetTechId() {
		coreOption.setTechId("testTechId");
		String test = coreOption.getTechId();
		assertNotNull(test);
	}

	@Test
	public void testCore() {
		coreOption.setCore(false);
		Boolean test = coreOption.isCore();
		assertNotNull(test);
	}
}
