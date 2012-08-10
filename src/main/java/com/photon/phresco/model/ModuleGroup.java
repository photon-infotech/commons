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
import com.photon.phresco.model.Documentation.DocumentationType;
import com.photon.phresco.util.SizeConstants;

@XmlRootElement
public class ModuleGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	//String id of the module. this will be groupId:artifactId:
	private String id;
	private String moduleId;
	private String groupId;
	private String artifactId;
	private String type;
	private String name;
	private String description;
	private String vendor;
	private boolean core;
	private boolean required;
	private List<Documentation> docs;
	private String techId;
	private List<Module> versions = new ArrayList<Module>(SizeConstants.SIZE_VERSIONS_MAP);
	private String imageURL;
	private boolean system;
	private String customerId;
	
	public ModuleGroup() {
	}

	public ModuleGroup(String id, String name,String groupId,String artifactId,String type,String vendor,boolean core,boolean required,List<Documentation> docs,List<Module> modules) {
		this.id = id;
		this.name = name;
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.type = type;
		this.vendor = vendor;
		this.core = core;
		this.required = required;
		this.docs = docs;
		this.versions = modules;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public boolean isCore() {
		return core;
	}

	public void setCore(boolean core) {
		this.core = core;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public List<Documentation> getDocs() {
		return docs;
	}

	public void setDocs(List<Documentation> docs) {
		this.docs = docs;
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

	public Documentation getDoc(DocumentationType type) {
		List<Documentation> docs2 = getDocs();
		if (docs2 == null || docs2.isEmpty()) {
			return null;
		}

		for (Documentation documentation : docs2) {
			if (type.equals(documentation.getType())) {
				return documentation;
			}
		}

		return null;
	}

	public String getTechId() {
		return techId;
	}

	public void setTechId(String techId) {
		this.techId = techId;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ModuleGroup [id=" + id + ", moduleId=" + moduleId
				+ ", groupId=" + groupId + ", artifactId=" + artifactId
				+ ", type=" + type + ", name=" + name + ",description=" +description +", vendor=" + vendor
				+ ", core=" + core + ", required=" + required + ", docs="
				+ docs + ", techId=" + techId + ", versions=" + versions
				+ ", imageURL=" + imageURL + ", system=" + system
				+ ", customerId=" + customerId + "]";
	}
    
    
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	

}
