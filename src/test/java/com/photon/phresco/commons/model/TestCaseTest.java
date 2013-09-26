package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCaseTest {
	
	TestCase testCase = new TestCase();

	@Test
	public void testGetActualResult() {
		testCase.setActualResult("testActualResult");
		String test = testCase.getActualResult();
		assertNotNull(test);
	}
	
	@Test
	public void testGetBugComment() {
		testCase.setBugComment("testBugComment");
		String test = testCase.getBugComment();
		assertNotNull(test);
	}
	
	@Test
	public void testGetDesc() {
		testCase.setDescription("testDesc");
		String test = testCase.getDescription();
		assertNotNull(test);
	}
	
	@Test
	public void testGetExpectedResult() {
		testCase.setExpectedResult("testExpectedResult");
		String test = testCase.getExpectedResult();
		assertNotNull(test);
	}
	
	@Test
	public void testGetFeatureId() {
		testCase.setFeatureId("testfeatureId");
		String test = testCase.getFeatureId();
		assertNotNull(test);
	}
	
	@Test
	public void testGetPriority() {
		testCase.setPriority("testPriority");
		String test = testCase.getPriority();
		assertNotNull(test);
	}
	
	@Test
	public void testGetRequirementId() {
		testCase.setRequirementId("testRequirementId");
		String test = testCase.getRequirementId();
		assertNotNull(test);
	}
	
	@Test
	public void testGetSeverity() {
		testCase.setSeverity("testSeverity");
		String test = testCase.getSeverity();
		assertNotNull(test);
	}
	
	@Test
	public void testGetStatus() {
		testCase.setStatus("testStaus");
		String test = testCase.getStatus();
		assertNotNull(test);
	}
	
	@Test
	public void testGetSteps() {
		testCase.setSteps("testSteps");
		String test = testCase.getSteps();
		assertNotNull(test);
	}
	
	@Test
	public void testGetTestCaseId() {
		testCase.setTestCaseId("testTestCaseId");
		String test = testCase.getTestCaseId();
		assertNotNull(test);
	}
	
	@Test
	public void testGetTestCaseType() {
		testCase.setTestCaseype("testTestCaseType");
		String test = testCase.getTestCaseype();
		assertNotNull(test);
	}
	
	@Test
	public void testGetTestInput() {
		testCase.setTestInput("testTestInput");
		String test = testCase.getTestInput();
		assertNotNull(test);
	}
}