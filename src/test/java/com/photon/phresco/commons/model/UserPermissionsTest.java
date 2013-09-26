package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserPermissionsTest {
	
	UserPermissions userPermissions = new UserPermissions();
	Boolean test = null;

	@Test
	public void testIsExecuteCIJobs() {
		userPermissions.setExecuteCIJobs(false);
		test = userPermissions.isExecuteCIJobs();
		assertNotNull(test);
	}
	
	@Test
	public void testIsImportApplication() {
		userPermissions.setImportApplication(false);
		test = userPermissions.isImportApplication();
		assertNotNull(test);
	}
	
	@Test
	public void testIsManageApplication() {
		userPermissions.setManageApplication(false);
		test = userPermissions.isManageApplication();
		assertNotNull(test);
	}
	
	@Test
	public void testIsManageBuilds() {
		userPermissions.setManageBuilds(false);
		test = userPermissions.isManageBuilds();
		assertNotNull(test);
	}
	
	@Test
	public void testIsManageCIJobs() {
		userPermissions.setManageCIJobs(false);
		test = userPermissions.isManageCIJobs();
		assertNotNull(test);
	}
	
	@Test
	public void testIsManageCodeValidation() {
		userPermissions.setManageCodeValidation(false);
		test = userPermissions.isManageCodeValidation();
		assertNotNull(test);
	}
	
	@Test
	public void testIsManageConfiguration() {
		userPermissions.setManageConfiguration(false);
		test = userPermissions.isManageConfiguration();
		assertNotNull(test);
	}
	
	@Test
	public void testIsManageMavenReports() {
		userPermissions.setManageMavenReports(false);
		test = userPermissions.isManageMavenReports();
		assertNotNull(test);
	}
	
	@Test
	public void testIsManagePdfReports() {
		userPermissions.setManagePdfReports(false);
		test = userPermissions.isManagePdfReports();
		assertNotNull(test);
	}
	
	@Test
	public void testIsManageRepo() {
		userPermissions.setManageRepo(false);
		test = userPermissions.isManageRepo();
		assertNotNull(test);
	}
	
	@Test
	public void testIsManageTests() {
		userPermissions.setManageTests(false);
		test = userPermissions.isManageTests();
		assertNotNull(test);
	}
	
	@Test
	public void testIsUpdateRepo() {
		userPermissions.setUpdateRepo(false);
		test = userPermissions.isUpdateRepo();
		assertNotNull(test);
	}
	
	@Test
	public void testToString() {
		String test = userPermissions.toString();
		assertNotNull(test);
	}
}
