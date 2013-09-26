package com.photon.phresco.configuration;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class EnvironmentTest {
	
	Environment env = new Environment();

	@Test
	public void testCanDelete() {
		env.setDelete(false);
		assertNotNull(env.canDelete());
		assertNotNull(env.isDelete());
	}
	
	@Test
	public void testGetAppliesTo() {
		List<String> appliesTo = new ArrayList<String>();
		appliesTo.add("test");
		env.setAppliesTo(appliesTo);
		assertNotNull(env.getAppliesTo());
	}
	
	@Test
	public void testToString() {
		assertNotNull(env.toString());
	}
}
