package com.photon.phresco.commons.model;


public class FunctionalFrameworkInfo extends Element {

	private static final long serialVersionUID = 1L;
	
	private String frameworkGroupId;
	private String frameworkIds;
	private String version;
	
	public String getFrameworkGroupId() {
		return frameworkGroupId;
	}
	
	public void setFrameworkGroupId(String frameworkGroupId) {
		this.frameworkGroupId = frameworkGroupId;
	}
	
	public String getFrameworkIds() {
		return frameworkIds;
	}
	
	public void setFrameworkIds(String frameworkIds) {
		this.frameworkIds = frameworkIds;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		return "FunctionalFrameworkInfo [frameworkGroupId=" + frameworkGroupId
				+ ", frameworkIds=" + frameworkIds + ", version=" + version
				+ "]";
	}
}
