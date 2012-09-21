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
package com.photon.phresco.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.photon.phresco.commons.model.Element;


@XmlRootElement
public class Technology extends Element implements Cloneable, Serializable {

	public enum DocumentationType {
		DESCRIPTION, HELP_TEXT, TOOL_TIP, DOCUMENT, ARCHITECHTURE, CODING_GUIDELINES
	}
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String appTypeId;
    private List<ModuleGroup> frameworks;
    private List<ModuleGroup> jsLibraries;
    private List<ModuleGroup> modules;
	private List<Server> servers;
	private List<Database> databases;
	private List<WebService> webservices;
	private boolean emailSupported;
	private String versionComment;
	private String techVersion;
	private List<String> appType;
	private String appJar;
	private String pluginJar;
	private List<String> versions;
	private boolean system;
	private String customerId;
	private ArchetypeInfo archetypeInfo;
	private List<Documentation> docs;
	private List<ArchetypeInfo> plugins;
	private boolean used;
	private boolean supportJs;
	
    public Technology() {
        super();
    }

    public Technology(String id, String name) {
        super(id, name);
    }

    public Technology(String name, String description, List<String> versions, List<String> appType) {
        super(name, description);
        this.versions = versions;
        this.appType = appType;
    }

    public String getAppTypeId() {
		return appTypeId;
	}

	public void setAppTypeId(String appTypeId) {
		this.appTypeId = appTypeId;
	}

    public List<ModuleGroup> getFrameworks() {
        return frameworks;
    }

    public void setFrameworks(List<ModuleGroup> frameworks) {
        this.frameworks = frameworks;
    }

    public List<ModuleGroup> getJsLibraries() {
        return jsLibraries;
    }

    public void setJsLibraries(List<ModuleGroup> jsLibraries) {
        this.jsLibraries = jsLibraries;
    }

    public List<ModuleGroup> getModules() {
        return modules;
    }

    public void setModules(List<ModuleGroup> modules) {
        this.modules = modules;
    }

	public List<Server> getServers() {
		return servers;
	}

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}

	public List<Database> getDatabases() {
		return databases;
	}

	public void setDatabases(List<Database> databases) {
		this.databases = databases;
	}

	public List<WebService> getWebservices() {
		return webservices;
	}

	public void setWebservices(List<WebService> webservices) {
		this.webservices = webservices;
	}

	public boolean isEmailSupported() {
		return emailSupported;
	}

	public void setEmailSupported(boolean emailSupported) {
		this.emailSupported = emailSupported;
	}

	public void setVersions(List<String> versions) {
		this.versions = versions;
	}

	public List<String> getVersions() {
		return versions;
	}

	public String getVersionComment() {
		return versionComment;
	}

	public void setVersionComment(String versionComment) {
		this.versionComment = versionComment;
	}

	public String getTechVersion() {
		return techVersion;
	}

	public void setTechVersion(String techVersion) {
		this.techVersion = techVersion;
	}

	public ArchetypeInfo getArchetypeInfo() {
        return archetypeInfo;
    }

    public void setArchetypeInfo(ArchetypeInfo archetypeInfo) {
        this.archetypeInfo = archetypeInfo;
    }

    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

    public List<ArchetypeInfo> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<ArchetypeInfo> plugins) {
        this.plugins = plugins;
    }

    public List<String> getAppType() {
		return appType;
	}

	public void setAppType(List<String> appType) {
		this.appType = appType;
	}

	public String getAppJar() {
		return appJar;
	}

	public void setAppJar(String appJar) {
		this.appJar = appJar;
	}

	public String getPluginJar() {
		return pluginJar;
	}

	public void setPluginJar(String pluginJar) {
		this.pluginJar = pluginJar;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public List<Documentation> getDocs() {
        return docs;
    }

    public void setDocs(List<Documentation> docs) {
        this.docs = docs;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isSupportJs() {
        return supportJs;
    }

    public void setSupportJs(boolean supportJs) {
        this.supportJs = supportJs;
    }

    @Override
	public String toString() {
		return "Technology [appTypeId=" + appTypeId + ", frameworks="
				+ frameworks + ", jsLibraries=" + jsLibraries + ", modules="
				+ modules + ", servers=" + servers + ", databases=" + databases
				+ ", webservices=" + webservices + ", emailSupported="
				+ emailSupported + ", versionComment=" + versionComment + ", appType=" + appType
				+ ", appJar=" + appJar + ", pluginJar=" + pluginJar
				+ ", versions=" + versions + ", system=" + system
				+ ", customerId=" + customerId  + "]";
	}

	@SuppressWarnings("unchecked")
    public Technology clone() throws CloneNotSupportedException{
    	Technology tech = new Technology();

    	tech.setId(getId());
    	tech.setName(getName());
    	tech.setAppTypeId(getAppTypeId());

		if (databases != null && !databases.isEmpty()) {
			tech.setDatabases((List<Database>) ((ArrayList<Database>) databases).clone());
		}
		if (frameworks != null && !frameworks.isEmpty()) {
			tech.setFrameworks((List<ModuleGroup>) ((ArrayList<ModuleGroup>) frameworks).clone());
		}
		if (jsLibraries != null && !jsLibraries.isEmpty()) {
			tech.setJsLibraries((List<ModuleGroup>) ((ArrayList<ModuleGroup>) jsLibraries).clone());
		}
		if (modules != null && !modules.isEmpty()) {
			tech.setModules((List<ModuleGroup>) ((ArrayList<ModuleGroup>) modules).clone());
		}
		if (servers != null && !servers.isEmpty()) {
			tech.setServers((List<Server>) ((ArrayList<Server>) servers).clone());
		}
		if (webservices != null && !webservices.isEmpty()) {
			tech.setWebservices((List<WebService>) ((ArrayList<WebService>) this.webservices).clone());
		}
		tech.setEmailSupported(emailSupported);
    	return tech;
    }

}