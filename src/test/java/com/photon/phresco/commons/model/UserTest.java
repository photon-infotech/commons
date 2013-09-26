package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class UserTest {
	
	User user = new User();

	@Test
	public void testGetAuthType() {
		user.setAuthType(User.AuthType.LOCAL);
		User.AuthType test = user.getAuthType();
		assertNotNull(test);
	}
	
	@Test
	public void testGetCreationDate() {
		Date date = new Date(System.currentTimeMillis());
		user.setCreationDate(date);
		Date test = user.getCreationDate();
		assertNotNull(test);
	}
	
	@Test
	public void testGetCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer = new Customer("testId");
		customers.add(customer);
		user.setCustomers(customers);
		List<Customer> test = user.getCustomers();
		assertNotNull(test);
	}
	
	@Test
	public void testGetDesc() {
		user.setDescription("testDesc");
		String test = user.getDescription();
		assertNotNull(test);
	}
	
	@Test
	public void testGetDisplayName() {
		user.setDisplayName("testName");
		String test = user.getDisplayName();
		assertNotNull(test);
	}
	
	@Test
	public void testGetEmail() {
		user.setEmail("testmail");
		String test = user.getEmail();
		assertNotNull(test);
	}
	
	@Test
	public void testGetFirstName() {
		user.setFirstName("testFirstName");
		String test = user.getFirstName();
		assertNotNull(test);
	}
	
	@Test
	public void testGetHelpText() {
		user.setHelpText("helpText");
		String test = user.getHelpText();
		assertNotNull(test);
	}
	
	@Test
	public void testGetId() {
		user.setId("testId");
		String test = user.getId();
		assertNotNull(test);
	}
	
	@Test
	public void testGetLastName() {
		user.setLastName("testLastName");
		String test = user.getLastName();
		assertNotNull(test);
	}
	
	@Test
	public void testGetLoginId() {
		user.setLoginId("testLoginId");
		String test = user.getLoginId();
		assertNotNull(test);
	}
	
	@Test
	public void testGetName() {
		user.setName("testName");
		String test = user.getName();
		assertNotNull(test);
	}
	
	@Test
	public void testGetPassword() {
		user.setPassword("testPass");
		String test = user.getPassword();
		assertNotNull(test);
	}
	
	@Test
	public void testGetPermissions() {
		UserPermissions userPermissions = new UserPermissions();
		userPermissions.setManageBuilds(true);
		user.setPermissions(userPermissions);
		UserPermissions test = user.getPermissions();
		assertNotNull(test);
	}
	
	@Test
	public void testIsPhrescoEnabled() {
		user.setPhrescoEnabled(true);
		Boolean test = user.isPhrescoEnabled();
		assertNotNull(test);
	}
	
	@Test
	public void testGetRoleIds() {
		List<String> roleIds = new ArrayList<String>();
		roleIds.add("newRoleId");
		user.setRoleIds(roleIds);
		List<String> test = user.getRoleIds();
		assertNotNull(test);
	}
	
	@Test
	public void testGetStatus() {
		Status status = Status.NEW;
		user.setStatus(status);
		Status test = user.getStatus();
		assertNotNull(test);
	}
	
	@Test
	public void testIsSystem() {
		user.setSystem(false);
		Boolean test = user.isSystem();
		assertNotNull(test);
	}
	
	@Test
	public void testGetToken() {
		user.setToken("testToken");
		String test = user.getToken();
		assertNotNull(test);
	}
	
	@Test
	public void testIsValidLogin() {
		user.setValidLogin(false);
		Boolean test = user.isValidLogin();
		assertNotNull(test);
	}
	
	@Test
	public void testToString() {
		String test = user.toString();
		assertNotNull(test);
	}
	
	@Test
	public void testEnum() {
		assertNotNull(User.AuthType.AUTHSERVICE);
		assertNotNull(User.AuthType.LOCAL);
		assertNotNull(User.AuthType.OAUTH);
	}
}