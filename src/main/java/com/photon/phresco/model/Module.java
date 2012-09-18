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
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.photon.phresco.model.Documentation.DocumentationType;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Module implements Serializable {

	private String id;
    private String contentType;
    private String contentURL;
    private List<ModuleGroup> dependentModules;
    private String name;
    private String version;
    private Boolean required;
    private String groupId;
    private String artifactId;
    private List<Documentation> docs;
    private boolean used;

    public Module() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType
     *            the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return the contentURL
     */
    public String getContentURL() {
        return contentURL;
    }

    /**
     * @param contentURL
     *            the contentURL to set
     */
    public void setContentURL(String contentURL) {
        this.contentURL = contentURL;
    }

    /**
     * @return the dependentModules
     */
    public List<ModuleGroup> getDependentModules() {
        return dependentModules;
    }

    /**
     * @param dependentModules
     *            the dependentModules to set
     */
    public void setDependentModules(List<ModuleGroup> dependentModules) {
        this.dependentModules = dependentModules;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the required
     */
    public Boolean getRequired() {
        return required;
    }

    /**
     * @param required
     *            the required to set
     */
    public void setRequired(Boolean required) {
        this.required = required;
    }

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     *            the groupId to set
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the artifactId
     */
    public String getArtifactId() {
        return artifactId;
    }

    /**
     * @param artifactId
     *            the artifactId to set
     */
    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }
    
	public List<Documentation> getDocs() {
		return docs;
	}

	public void setDocs(List<Documentation> docs) {
		this.docs = docs;
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
	
	public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
	public String toString() {
		return "Module [id=" + id + ", contentType=" + contentType
				+ ", contentURL=" + contentURL + ", dependentModules="
				+ dependentModules + ", name=" + name + ", version=" + version
				+ ", required=" + required + ", groupId=" + groupId
				+ ", artifactId=" + artifactId + ", docs=" + docs + "]";
	}
}
