package com.photon.phresco.commons.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SelectedFeature {

	private String dispName;
	private String dispValue;
	private String versionID;
	private String type;
	private String moduleId;
	private boolean canConfigure = false;
	private boolean defaultModule = false;
	
	public String getDispName() {
		return dispName;
	}

	public void setDispName(String dispName) {
		this.dispName = dispName;
	}

	public String getDispValue() {
		return dispValue;
	}

	public void setDispValue(String dispValue) {
		this.dispValue = dispValue;
	}

	public String getVersionID() {
		return versionID;
	}

	public void setVersionID(String versionID) {
		this.versionID = versionID;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	public boolean isCanConfigure() {
        return canConfigure;
    }

    public void setCanConfigure(boolean canConfigure) {
        this.canConfigure = canConfigure;
    }

    public boolean isDefaultModule() {
		return defaultModule;
	}

	public void setDefaultModule(boolean defaultModule) {
		this.defaultModule = defaultModule;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("dispName", getDispName())
                .append("dispValue", getDispValue())
                .append("versionID", getVersionID())
                .append("type", getType())
                .append("moduleId", getModuleId())
                .append("canConfigure", isCanConfigure())
                .append("defaultModule", isDefaultModule())
                .toString();
    }
}
