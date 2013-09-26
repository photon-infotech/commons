package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class SettingsTemplateTest {
	
	SettingsTemplate settingsTemplate = new SettingsTemplate();

	@Test
	public void testGetAppliesToTechs() {
		List<Element> appliesTo = new ArrayList<Element>();
		Element element = new Element("testId");
		appliesTo.add(element);
		settingsTemplate.setAppliesToTechs(appliesTo);
		List<Element> test = settingsTemplate.getAppliesToTechs();
		assertNotNull(test);
	}
	
	@Test
	public void testGetCreationDate() {
		Date date = new Date(System.currentTimeMillis());
		settingsTemplate.setCreationDate(date);
		Date test = settingsTemplate.getCreationDate();
		assertNotNull(test);
	}
	
	@Test
	public void testGetCustomerIds() {
		List<String> customerIds = new ArrayList<String>();
		customerIds.add("testCustomer");
		settingsTemplate.setCustomerIds(customerIds);
		List<String> test = settingsTemplate.getCustomerIds();
		assertNotNull(test);
	}
	
	@Test
	public void testIsCustomProp() {
		settingsTemplate.setCustomProp(false);
		Boolean test = settingsTemplate.isCustomProp();
		assertNotNull(test);
	}
	
	@Test
	public void testGetDescription() {
		settingsTemplate.setDescription("testdescription");
		String test = settingsTemplate.getDescription();
		assertNotNull(test);
	}
	
	@Test
	public void testGetDisplayName() {
		settingsTemplate.setDisplayName("testDisplayName");
		String test = settingsTemplate.getDisplayName();
		assertNotNull(test);
	}
	
	@Test
	public void testIsEnvSpecific() {
		settingsTemplate.setEnvSpecific(false);
		Boolean test = settingsTemplate.isEnvSpecific();
		assertNotNull(test);
	}
	
	@Test
	public void testIsFavourite() {
		settingsTemplate.setFavourite(false);
		Boolean test = settingsTemplate.isFavourite();
		assertNotNull(test);
	}
	
	@Test
	public void testGetHelpText() {
		settingsTemplate.setHelpText("testhelpText");
		String test = settingsTemplate.getHelpText();
		assertNotNull(test);
	}
	
	@Test
	public void testGetId() {
		settingsTemplate.setId("testId");
		String test = settingsTemplate.getId();
		assertNotNull(test);
	}
	
	@Test
	public void testGetName() {
		settingsTemplate.setName("testName");
		String test = settingsTemplate.getName();
		assertNotNull(test);
	}
	
	@Test
	public void testGetPossibleTypes() {
		List<Element> types = new ArrayList<Element>();
		Element element = new Element("testId");
		types.add(element);
		settingsTemplate.setPossibleTypes(types);
		List<Element> test = settingsTemplate.getPossibleTypes();
		assertNotNull(test);
	}
	
	@Test
	public void testGetProperties() {
		List<PropertyTemplate> properties = new ArrayList<PropertyTemplate>();
		PropertyTemplate propTemp = new PropertyTemplate("testkey", "testType");
		properties.add(propTemp);
		settingsTemplate.setProperties(properties);
		List<PropertyTemplate> test = settingsTemplate.getProperties();
		assertNotNull(test);
	}
	
	@Test
	public void testGetStatus() {
		Status status = Status.NEW;
		settingsTemplate.setStatus(status);
		Status test = settingsTemplate.getStatus();
		assertNotNull(test);
	}
	
	@Test
	public void testIsSystem() {
		settingsTemplate.setSystem(false);
		Boolean test = settingsTemplate.isSystem();
		assertNotNull(test);
	}
	
	@Test
	public void testGetType() {
		settingsTemplate.setType("testType");
		String test = settingsTemplate.getType();
		assertNotNull(test);
	}
	
	@Test
	public void testIsUsed() {
		settingsTemplate.setUsed(false);
		Boolean test = settingsTemplate.isUsed();
		assertNotNull(test);
	}
	
	@Test
	public void testToString() {
		String test = settingsTemplate.toString();
		assertNotNull(test);
	}
}