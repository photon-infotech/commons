package com.photon.phresco.commons.model;

public class FunctionalFrameworkProperties {

	public FunctionalFrameworkProperties() {
		
	}

	private String techId = "";
	private String testDir = "";
	private String testReportDir = "";
	private String testsuiteXpathPath = "";
	private String testcasePath = "";
	private String adaptConfigPath = "";
	
	public String getTestDir() {
		return testDir;
	}
	
	public void setTestDir(String testDir) {
		this.testDir = testDir;
	}
	
	public String getTestReportDir() {
		return testReportDir;
	}
	
	public void setTestReportDir(String testReportDir) {
		this.testReportDir = testReportDir;
	}
	
	public String getTestsuiteXpathPath() {
		return testsuiteXpathPath;
	}
	
	public void setTestsuiteXpathPath(String testsuiteXpathPath) {
		this.testsuiteXpathPath = testsuiteXpathPath;
	}
	
	public String getTestcasePath() {
		return testcasePath;
	}
	
	public void setTestcasePath(String testcasePath) {
		this.testcasePath = testcasePath;
	}
	
	public String getAdaptConfigPath() {
		return adaptConfigPath;
	}
	
	public void setAdaptConfigPath(String adaptConfigPath) {
		this.adaptConfigPath = adaptConfigPath;
	}
	
	public void setTechId(String techId) {
		this.techId = techId;
	}

	public String getTechId() {
		return techId;
	}

	@Override
	public String toString() {
		return "FunctionalFramework [testDir=" + testDir + ", testReportDir="
				+ testReportDir + ", testsuiteXpathPath=" + testsuiteXpathPath
				+ ", testcasePath=" + testcasePath + ", adaptConfigPath="
				+ adaptConfigPath + "]";
	}
}