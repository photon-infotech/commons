/**
 * Phresco Commons
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photon.phresco.commons.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectedFeature implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String dispName;
	private String dispValue;
	private String versionID;
	private String type;
	private String moduleId;
	private boolean canConfigure = false;
	private boolean defaultModule = false;
	private String artifactGroupId;
	private String packaging;
	private String scope;
	
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

	public String getArtifactGroupId() {
		return artifactGroupId;
	}

	public void setArtifactGroupId(String artifactGroupId) {
		this.artifactGroupId = artifactGroupId;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("name", getName())
                .append("dispName", getDispName())
                .append("dispValue", getDispValue())
                .append("versionID", getVersionID())
                .append("type", getType())
                .append("artifactGroupId", getArtifactGroupId())
                .append("moduleId", getModuleId())
                .append("canConfigure", isCanConfigure())
                .append("defaultModule", isDefaultModule())
                .toString();
    }
}
