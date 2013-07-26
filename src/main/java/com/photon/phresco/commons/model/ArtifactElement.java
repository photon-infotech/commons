package com.photon.phresco.commons.model;


public class ArtifactElement extends Element {

	private static final long serialVersionUID = 1L;
	
	public ArtifactElement() {
		// TODO Auto-generated constructor stub
	}
	
	private String licenseId;
	private String artifactGroupId;
	
	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}
	
	public String getLicenseId() {
		return licenseId;
	}

	public void setArtifactGroupId(String artifactGroupId) {
		this.artifactGroupId = artifactGroupId;
	}

	public String getArtifactGroupId() {
		return artifactGroupId;
	}
}
