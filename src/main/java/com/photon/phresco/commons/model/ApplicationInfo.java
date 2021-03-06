/**
 * Phresco Commons
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
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
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
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
    private boolean created;
    private String phrescoPomFile;
    private String pomFile;
    private String functionalFramework;
    private List<String> dependentModules;
    private List<ModuleInfo> modules;
    private String rootModule;
    private Map<String, String> selectedFeatureMap;
    private boolean showServer;
    private boolean showDatabase;
    private boolean showWebservice;
    private boolean showTestingFramework;
    private boolean parentArchrtypeCreated;
	private String buildVersion;

	public Map<String, String> getSelectedFeatureMap() {
		return selectedFeatureMap;
	}

	public void setSelectedFeatureMap(Map<String, String> selectedFeatureMap) {
		this.selectedFeatureMap = selectedFeatureMap;
	}

	//Information about where the pilot project is stored in repository
    private ArtifactGroup pilotContent;
    private FunctionalFrameworkInfo functionalFrameworkInfo;
    

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
    
	public void setFunctionalFramework(String functionalFrameworkId) {
		this.functionalFramework = functionalFrameworkId;
	}

	public String getFunctionalFramework() {
		return functionalFramework;
	}
	
	public void setDependentModules(List<String> dependentModules) {
		this.dependentModules = dependentModules;
	}

	public List<String> getDependentModules() {
		return dependentModules;
	}
	
	public void setModules(List<ModuleInfo> modules) {
		this.modules = modules;
	}

	public List<ModuleInfo> getModules() {
		return modules;
	}
	
	public void setFunctionalFrameworkInfo(FunctionalFrameworkInfo functionalFrameworkInfo) {
		this.functionalFrameworkInfo = functionalFrameworkInfo;
	}

	public FunctionalFrameworkInfo getFunctionalFrameworkInfo() {
		return functionalFrameworkInfo;
	}
	
	public void setCreated(boolean created) {
		this.created = created;
	}

	public boolean isCreated() {
		return created;
	}
	
	public void setPhrescoPomFile(String phrescoPomFile) {
		this.phrescoPomFile = phrescoPomFile;
	}

	public String getPhrescoPomFile() {
		return phrescoPomFile;
	}
	
	public boolean isShowServer() {
		return showServer;
	}

	public void setShowServer(boolean showServer) {
		this.showServer = showServer;
	}

	public boolean isShowDatabase() {
		return showDatabase;
	}

	public void setShowDatabase(boolean showDatabase) {
		this.showDatabase = showDatabase;
	}

	public boolean isShowWebservice() {
		return showWebservice;
	}

	public void setShowWebservice(boolean showWebservice) {
		this.showWebservice = showWebservice;
	}

	public boolean isShowTestingFramework() {
		return showTestingFramework;
	}

	public void setShowTestingFramework(boolean showTestingFramework) {
		this.showTestingFramework = showTestingFramework;
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
                .append("functionalFramework", getFunctionalFramework())
                .append("dependentModules", getDependentModules())
                .append("phrescoPomFile", getPhrescoPomFile())
                .append("pomFile", getPomFile())
                .append("rootModule", getRootModule())
                .append("modules", getModules())
                .toString();
    }

	public void setPomFile(String pomFile) {
		this.pomFile = pomFile;
	}

	public String getPomFile() {
		return pomFile;
	}

	public String getRootModule() {
		return rootModule;
	}

	public void setRootModule(String rootModule) {
		this.rootModule = rootModule;
	}

	public void setParentArchrtypeCreated(boolean parentArchrtypeCreated) {
		this.parentArchrtypeCreated = parentArchrtypeCreated;
	}

	public boolean isParentArchrtypeCreated() {
		return parentArchrtypeCreated;
	}

	public void setBuildVersion(String buildVersion) {
		this.buildVersion = buildVersion;
	}

	public String getBuildVersion() {
		return buildVersion;
	}
}