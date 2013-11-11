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

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Technology extends CustomerBasedElement implements Cloneable {

    public enum Option {
    	BUILD_SUPPORTED, CODE_QULAITY_SUPPORTED, DEPLOY_SUPPORTED, 
    	UNIT_TEST_SUPPORTED, FUNCTIONAL_TEST_SUPPORTED, PERFORMANCE_TEST_SUPPORTED, 
    	LOAD_TEST_SUPPORTED, REPORTS_SUPPORTED, CI_SUPPORTED, EXE_DOWNLOAD
	}

	private static final long serialVersionUID = 1L;
    
    private String appTypeId;
	private List<String> techVersions;
	private ArtifactGroup archetypeInfo;
	private List<ArtifactGroup> plugins;
	
	//Admin Options - Is build enabled, is Code Quality enabled, etc
	private List<String> options;
	private String techGroupId;
	private List<String> reports;
	private List<String> archetypeFeatures;
	private List<String> applicableEmbedTechnology;
	private List<FunctionalFrameworkGroup> functionalFrameworks;
	private List<FunctionalFrameworkInfo> functionalFrameworksInfo;
	private boolean multiModule;
	private List<String> subModules;
	
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

    public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

    public List<ArtifactGroup> getPlugins() {
		return plugins;
	}

	public void setPlugins(List<ArtifactGroup> plugins) {
		this.plugins = plugins;
	}

	public String getTechGroupId() {
		return techGroupId;
	}

	public void setTechGroupId(String techGroupId) {
		this.techGroupId = techGroupId;
	}
	
	public void setReports(List<String> reports) {
		this.reports = reports;
	}

	public List<String> getReports() {
		return reports;
	}
	
	public List<String> getArchetypeFeatures() {
		return archetypeFeatures;
	}

	public void setArchetypeFeatures(List<String> archetypeFeatures) {
		this.archetypeFeatures = archetypeFeatures;
	}
	
	public void setFunctionalFrameworks(List<FunctionalFrameworkGroup> functionalFrameworks) {
		this.functionalFrameworks = functionalFrameworks;
	}

	public List<FunctionalFrameworkGroup> getFunctionalFrameworks() {
		return functionalFrameworks;
	}
	
	public boolean isMultiModule() {
		return multiModule;
	}

	public void setMultiModule(boolean multiModule) {
		this.multiModule = multiModule;
	}

	public List<String> getSubModules() {
		return subModules;
	}

	public void setSubModules(List<String> subModules) {
		this.subModules = subModules;
	}

	public void setApplicableEmbedTechnology(
			List<String> applicableEmbedTechnology) {
		this.applicableEmbedTechnology = applicableEmbedTechnology;
	}

	public List<String> getApplicableEmbedTechnology() {
		return applicableEmbedTechnology;
	}

	public void setFunctionalFrameworksInfo(List<FunctionalFrameworkInfo> functionalFrameworksInfo) {
		this.functionalFrameworksInfo = functionalFrameworksInfo;
	}

	public List<FunctionalFrameworkInfo> getFunctionalFrameworksInfo() {
		return functionalFrameworksInfo;
	}

	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("appTypeId", getAppTypeId())
                .append("archetypeInfo", getArchetypeInfo())
                .append("techVersions", getTechVersions())
                .append("reports", getReports())
                .append("plugins", getPlugins())
                .append("options", getOptions())
                .append("techGroupId", getTechGroupId())
                .append("archetypeFeatures", getArchetypeFeatures())
                .append("functionalFrameworks", getFunctionalFrameworks())
                .append("multiModule", isMultiModule())
                .append("subModules", getSubModules())
                .toString();
    }
}