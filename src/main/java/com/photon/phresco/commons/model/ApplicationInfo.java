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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationInfo extends CustomerBasedElement {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
    private String appDirName;
    private TechnologyInfo techInfo;
    //Information about the pilot project which was used to create this application
    private Element pilotInfo;
    
    private String version;
    private String embedAppId;
    private List<String> selectedModules;
    private List<String> selectedJSLibs;
    private List<ArtifactGroup> selectedFrameworks;
    private List<String> selectedComponents;
    private List<ArtifactGroupInfo> selectedServers;
    private List<ArtifactGroupInfo> selectedDatabases;
    private List<String> selectedWebservices;
    private boolean emailSupported;
    private boolean phoneEnabled;
    private boolean tabletEnabled;
    private boolean pilot;
    private String pomFile;

    //Information about where the pilot project is stored in repository
    private ArtifactGroup pilotContent;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public TechnologyInfo getTechInfo() {
        return techInfo;
    }

    public void setTechInfo(TechnologyInfo techInfo) {
        this.techInfo = techInfo;
    }

    public Element getPilotInfo() {
        return pilotInfo;
    }

    public void setPilotInfo(Element pilotInfo) {
        this.pilotInfo = pilotInfo;
    }

    public List<String> getSelectedModules() {
        return selectedModules;
    }

    public void setSelectedModules(List<String> selectedModules) {
        this.selectedModules = selectedModules;
    }

    public List<String> getSelectedJSLibs() {
        return selectedJSLibs;
    }

    public void setSelectedJSLibs(List<String> selectedJSLibs) {
        this.selectedJSLibs = selectedJSLibs;
    }

    public List<ArtifactGroup> getSelectedFrameworks() {
        return selectedFrameworks;
    }

    public void setSelectedFrameworks(List<ArtifactGroup> selectedFrameworks) {
        this.selectedFrameworks = selectedFrameworks;
    }

    public List<String> getSelectedComponents() {
        return selectedComponents;
    }

    public void setSelectedComponents(List<String> selectedComponents) {
        this.selectedComponents = selectedComponents;
    }

    public List<ArtifactGroupInfo> getSelectedServers() {
        return selectedServers;
    }

    public void setSelectedServers(List<ArtifactGroupInfo> selectedServers) {
        this.selectedServers = selectedServers;
    }

    public List<ArtifactGroupInfo> getSelectedDatabases() {
        return selectedDatabases;
    }

    public void setSelectedDatabases(List<ArtifactGroupInfo> selectedDatabases) {
        this.selectedDatabases = selectedDatabases;
    }

    public List<String> getSelectedWebservices() {
        return selectedWebservices;
    }

    public void setSelectedWebservices(List<String> selectedWebservices) {
        this.selectedWebservices = selectedWebservices;
    }

    public boolean isEmailSupported() {
        return emailSupported;
    }

    public void setEmailSupported(boolean emailSupported) {
        this.emailSupported = emailSupported;
    }

    public ArtifactGroup getPilotContent() {
        return pilotContent;
    }

    public void setPilotContent(ArtifactGroup pilotContent) {
        this.pilotContent = pilotContent;
    }
    
    public String getAppDirName() {
		return appDirName;
	}

	public void setAppDirName(String appDirName) {
		this.appDirName = appDirName;
	}

    public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getEmbedAppId() {
        return embedAppId;
    }

    public void setEmbedAppId(String embedAppId) {
        this.embedAppId = embedAppId;
    }
	
	public boolean isPhoneEnabled() {
        return phoneEnabled;
    }

    public void setPhoneEnabled(boolean phoneSupported) {
        this.phoneEnabled = phoneSupported;
    }

    public boolean isTabletEnabled() {
        return tabletEnabled;
    }

    public void setTabletEnabled(boolean tabletSupported) {
        this.tabletEnabled = tabletSupported;
    }
    
    public boolean isPilot() {
        return pilot;
    }

    public void setPilot(boolean pilot) {
        this.pilot = pilot;
    }
    
    public void setPomFile(String pomFile) {
		this.pomFile = pomFile;
	}

	public String getPomFile() {
		return pomFile;
	}
	
	public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("code", getCode())
                .append("appDirName", getAppDirName())
                .append("techInfo", getTechInfo())
                .append("pilotInfo", getPilotInfo())
                .append("emailSupported", isEmailSupported())
                .append("selectedModules", getSelectedModules())
                .append("selectedJSLibs", getSelectedJSLibs())
                .append("selectedFrameworks", getSelectedFrameworks())
                .append("selectedComponents", getSelectedComponents())
                .append("selectedServers", getSelectedServers())
                .append("selectedDatabases", getSelectedDatabases())
                .append("selectedWebservices", getSelectedWebservices())
                .append("version", getVersion())
                .append("phoneEnabled", isPhoneEnabled())
                .append("tabletEnabled", isTabletEnabled())
                .append("pilot", isPilot())
                .append("embedAppId", getEmbedAppId())
                .append("pomFile", getPomFile())
                .toString();
    }
}