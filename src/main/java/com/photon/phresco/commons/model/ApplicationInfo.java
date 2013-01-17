/*
 * ###
\ * Phresco Commons
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
 * 
 */
package com.photon.phresco.commons.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class ApplicationInfo extends Element {
    
    private String code;
    private String version;
    private Element techInfo;
    //Information about the pilot project which was used to create this application
    private Element pilotInfo;
    
    private List<ArtifactGroup> selectedModules;
    private List<ArtifactGroup> selectedJSLibs;
    private List<ArtifactGroup> selectedFrameworks;
    private List<ArtifactGroup> selectedComponents;
    private List<DownloadInfo> selectedServers;
    private List<DownloadInfo> selectedDatabases;
    private List<WebService> selectedWebservices;
    private boolean emailSupported;

    //Information about where the pilot project is stored in repository
    private ArtifactGroup pilotContent;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Element getTechInfo() {
        return techInfo;
    }

    public void setTechInfo(Element techInfo) {
        this.techInfo = techInfo;
    }

    public Element getPilotInfo() {
        return pilotInfo;
    }

    public void setPilotInfo(Element pilotInfo) {
        this.pilotInfo = pilotInfo;
    }

    public List<ArtifactGroup> getSelectedModules() {
        return selectedModules;
    }

    public void setSelectedModules(List<ArtifactGroup> selectedModules) {
        this.selectedModules = selectedModules;
    }

    public List<ArtifactGroup> getSelectedJSLibs() {
        return selectedJSLibs;
    }

    public void setSelectedJSLibs(List<ArtifactGroup> selectedJSLibs) {
        this.selectedJSLibs = selectedJSLibs;
    }

    public List<ArtifactGroup> getSelectedFrameworks() {
        return selectedFrameworks;
    }

    public void setSelectedFrameworks(List<ArtifactGroup> selectedFrameworks) {
        this.selectedFrameworks = selectedFrameworks;
    }

    public List<ArtifactGroup> getSelectedComponents() {
        return selectedComponents;
    }

    public void setSelectedComponents(List<ArtifactGroup> selectedComponents) {
        this.selectedComponents = selectedComponents;
    }

    public List<DownloadInfo> getSelectedServers() {
        return selectedServers;
    }

    public void setSelectedServers(List<DownloadInfo> selectedServers) {
        this.selectedServers = selectedServers;
    }

    public List<DownloadInfo> getSelectedDatabases() {
        return selectedDatabases;
    }

    public void setSelectedDatabases(List<DownloadInfo> selectedDatabases) {
        this.selectedDatabases = selectedDatabases;
    }

    public List<WebService> getSelectedWebservices() {
        return selectedWebservices;
    }

    public void setSelectedWebservices(List<WebService> selectedWebservices) {
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

    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("code", getCode())
                .append("version", getVersion())
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
                .toString();
    }
        
}