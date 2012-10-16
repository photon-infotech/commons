package com.photon.phresco.commons.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TechnologyGroup extends Element {

	private static final long serialVersionUID = 1L;
	private String appTypeId;
	private List<TechnologyInfo> techInfos;
	
	public TechnologyGroup() {
	}
	
	public List<TechnologyInfo> getTechInfos() {
		return techInfos;
	}

	public void setTechInfos(List<TechnologyInfo> techIds) {
		this.techInfos = techIds;
	}

	public String getAppTypeId() {
		return appTypeId;
	}

	public void setAppTypeId(String appTypeId) {
		this.appTypeId = appTypeId;
	}

	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("id", getId())
                .append("name", getName())
                .append("techInfos", getTechInfos())
                .append("appTypeId", getAppTypeId())
                .toString();
    }
}
