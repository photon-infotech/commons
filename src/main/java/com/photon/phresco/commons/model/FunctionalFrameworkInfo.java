package com.photon.phresco.commons.model;

import java.util.List;


public class FunctionalFrameworkInfo extends Element {

	private static final long serialVersionUID = 1L;
	
	private String frameworkGroupId;
	private String frameworkIds;
	private String version;
	private String iframeUrl;
	private List<String> functionalFrameworkIds;
	
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
	
	public void setFunctionalFrameworkIds(List<String> functionalFrameworkIds) {
		this.functionalFrameworkIds = functionalFrameworkIds;
	}

	public List<String> getFunctionalFrameworkIds() {
		return functionalFrameworkIds;
	}

	public void setIframeUrl(String iframeUrl) {
		this.iframeUrl = iframeUrl;
	}

	public String getIframeUrl() {
		return iframeUrl;
	}

	@Override
	public String toString() {
		return "FunctionalFrameworkInfo [frameworkGroupId=" + frameworkGroupId
				+ ", frameworkIds=" + frameworkIds + ", version=" + version
				+ "]";
	}
}
