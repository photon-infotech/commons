/*
 * ###
 * Phresco Commons
 *
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.photon.phresco.commons.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement
public class Technology extends CustomerBasedElement implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    
    private String appTypeId;
	private List<String> techVersions;
	private ArtifactGroup archetypeInfo;
	
	//Plugins and Dependencies
	private List<ArtifactGroup> dependencies;
	
	//Admin Options - Is build enabled, is Code Quality enabled, etc
//	private List<Option> options;
	
    public Technology() {
        super();
    }

    public Technology(String id, String name) {
        super(id, name);
    }

    public String getAppTypeId() {
		return appTypeId;
	}

	public void setAppTypeId(String appTypeId) {
		this.appTypeId = appTypeId;
	}

    public List<String> getTechVersions() {
        return techVersions;
    }

    public void setTechVersions(List<String> techVersions) {
        this.techVersions = techVersions;
    }

    public ArtifactGroup getArchetypeInfo() {
        return archetypeInfo;
    }

    public void setArchetypeInfo(ArtifactGroup archetypeInfo) {
        this.archetypeInfo = archetypeInfo;
    }

    public List<ArtifactGroup> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<ArtifactGroup> dependencies) {
        this.dependencies = dependencies;
    }

    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("appTypeId", getAppTypeId())
                .append("archetypeInfo", getArchetypeInfo())
                .append("techVersions", getTechVersions())
                .append("dependencies", getDependencies())
                .toString();
    }

}