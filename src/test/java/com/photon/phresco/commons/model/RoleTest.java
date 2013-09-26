package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class RoleTest {
	
	Role role = new Role();
	Role role2 = new Role("testId", "testName", "testDescription");

	@Test
	public void testGetAppliesTo() {
		role.setAppliesTo("testAppliesTo");
		String test = role.getAppliesTo();
		assertNotNull(test);
	}
	
	@Test
	public void testGetPermissionIds() {
		List<String> permissionIds = new ArrayList<String>();
		permissionIds.add("testPermissionId");
		role.setPermissionIds(permissionIds);
		List<String> test = role.getPermissionIds();
		assertNotNull(test);
	}
	
	@Test
	public void testToString() {
		String test = role.toString();
		assertNotNull(test);
	}
}