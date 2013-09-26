package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ApplicationTypeTest {
	
	ApplicationType applicationType = new ApplicationType();
	List<TechnologyGroup> techGroups = new ArrayList<TechnologyGroup>();
	
	@Test
	public void testSetTechGroups() {
		TechnologyGroup techGroup = new TechnologyGroup();
		techGroup.setAppTypeId("sjdhfvj");
		techGroup.setDescription("sadsad");
		techGroup.setDisplayName("test");
		techGroup.setId("213hg213417");
		techGroup.setName("test");
		techGroup.setCreationDate(null);
		techGroup.setCustomerIds(null);
		techGroup.setHelpText("help");
		techGroup.setStatus(null);
		techGroup.setSystem(false);
		techGroup.setTechInfos(null);
		techGroup.setUsed(false);
		techGroups.add(techGroup);
		applicationType.setTechGroups(techGroups);
	}
	
	@Test
	public void testGetTechGroups() {
		TechnologyGroup techGroup = new TechnologyGroup();
		techGroup.setAppTypeId("sjdhfvj");
		techGroup.setDescription("sadsad");
		techGroup.setDisplayName("test");
		techGroup.setId("213hg213417");
		techGroup.setName("test");
		techGroup.setCreationDate(null);
		techGroup.setCustomerIds(null);
		techGroup.setHelpText("help");
		techGroup.setStatus(null);
		techGroup.setSystem(false);
		techGroup.setTechInfos(null);
		techGroup.setUsed(false);
		techGroups.add(techGroup);
		applicationType.setTechGroups(techGroups);
		List<TechnologyGroup> techGroups1 = applicationType.getTechGroups();
		assertNotNull(techGroups1);
	}
	
	@Test
	public void testToString() {
		String test = applicationType.toString();
		assertNotNull(test);
	}
}
