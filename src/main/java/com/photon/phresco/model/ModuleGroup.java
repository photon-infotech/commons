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
import com.photon.phresco.util.SizeConstants;

@XmlRootElement
public class ModuleGroup extends Element implements Serializable {

	private static final long serialVersionUID = 1L;

	//String id of the module. this will be groupId:artifactId:
	private String type;
	private boolean core;
	private String techId;
	private List<Module> versions = new ArrayList<Module>(SizeConstants.SIZE_VERSIONS_MAP);
	private String imageURL;
	private boolean system;
	private String customerId;
	
	public ModuleGroup() {
	}

	public ModuleGroup(String id, String name, String groupId, String artifactId, String type, boolean core, List<Module> modules) {
		super(id, name);
		this.type = type;
		this.core = core;
		this.versions = modules;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isCore() {
		return core;
	}

	public void setCore(boolean core) {
		this.core = core;
	}

	public List<Module> getVersions() {
		return versions;
	}

	public Module getVersion(String version) {
		List<Module> moduleVersions = getVersions();
		for (Module moduleVersion : moduleVersions) {
			if (moduleVersion.getVersion().equals(version)) {
				return moduleVersion;
			}
		}
		return null;
	}

	public void setVersions(List<Module> versions) {
		this.versions = versions;
	}

	public String getTechId() {
		return techId;
	}

	public void setTechId(String techId) {
		this.techId = techId;
	}

	public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

    @Override
    public String toString() {
        return "ModuleGroup [type=" + type + ", core=" + core + ", techId=" + techId + ", versions="
                + versions + ", imageURL=" + imageURL + ", system=" + system
                + ", customerId=" + customerId + "]";
    }
}