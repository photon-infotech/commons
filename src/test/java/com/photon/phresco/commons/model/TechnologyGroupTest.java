package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TechnologyGroupTest {
	
	TechnologyGroup techGroup =  new TechnologyGroup();

	@Test
	public void testGetTechInfos() {
		List<TechnologyInfo> techInfos = new ArrayList<TechnologyInfo>();
		TechnologyInfo techInfo = new TechnologyInfo();
		techInfo.setDescription("sadsad");
		techInfo.setDisplayName("test");
		techInfo.setId("213hg213417");
		techInfo.setName("test");
		techInfo.setCreationDate(null);
		techInfo.setHelpText("help");
		techInfo.setStatus(null);
		techInfo.setSystem(false);
		techInfo.setUsed(false);
		techInfo.setAppTypeId("testAppTypeId");
		techInfo.setVersion("1.0");
		techInfo.setCustomerIds(null);
		techInfo.setTechGroupId("testTechGroupId");
		techInfo.setTechVersions(null);
		techInfos.add(techInfo);
		techGroup.setTechInfos(techInfos);
		List<TechnologyInfo> test = techGroup.getTechInfos();
		assertNotNull(test);
	}
	
	@Test
	public void testGetAppTypeId() {
		techGroup.setAppTypeId("testAppTypeId");
		String test = techGroup.getAppTypeId();
		assertNotNull(test);
	}
	
	@Test
	public void testToString() {
		String test = techGroup.toString();
		assertNotNull(test);
	}
}
