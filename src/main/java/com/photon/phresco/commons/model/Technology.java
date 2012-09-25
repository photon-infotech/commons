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

    public enum Option {
    	BUILD_SUPPORTED, CODE_QULAITY_SUPPORTED, DEPLOY_SUPPORTED, 
    	UNIT_TEST_SUPPORTED, FUNCTIONAL_TEST_SUPPORTED, PERFORMANCE_TEST_SUPPORTED, 
    	LOAD_TEST_SUPPORTED, REPORTS_SUPPORTED, CI_SUPPORTED
	}

	private static final long serialVersionUID = 1L;
    
    private String appTypeId;
	private List<String> techVersions;
	private ArtifactGroup archetypeInfo;
	
	//Admin Options - Is build enabled, is Code Quality enabled, etc
	private List<Option> options;
	
	public Technology() {
        super();
    }

    public Technology(String id) {
        super(id);
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

    public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("appTypeId", getAppTypeId())
                .append("archetypeInfo", getArchetypeInfo())
                .append("techVersions", getTechVersions())
                .toString();
    }

}