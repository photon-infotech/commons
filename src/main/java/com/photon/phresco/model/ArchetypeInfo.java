package com.photon.phresco.model;

import com.photon.phresco.commons.model.Element;

public class ArchetypeInfo extends Element{
    
	private String groupId;
    private String artifactId;
    private String version;
    private String projectGroupId;
    //for file upload
    private boolean mavenJar;
	private boolean success;
    
    public ArchetypeInfo() {
    }
    
    public ArchetypeInfo(String groupId, String artifactId, String version) {
		super();
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
	}

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProjectGroupId() {
        return projectGroupId;
    }

    public void setProjectGroupId(String projectGroupId) {
        this.projectGroupId = projectGroupId;
    }
    
    public boolean isMavenJar() {
		return mavenJar;
	}

	public void setMavenJar(boolean mavenJar) {
		this.mavenJar = mavenJar;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
    public String toString() {
        return "ArchetypeInfo [groupId=" + groupId + ", artifactId="
                + artifactId + ", version=" + version + ", projectGroupId="
                + projectGroupId  + "]";
    }
}
