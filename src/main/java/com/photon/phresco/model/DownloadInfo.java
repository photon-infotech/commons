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

@XmlRootElement
public class DownloadInfo implements Serializable {

	private static final long serialVersionUID = -9197395975210628328L;
	private String id;
	private String name;
	private String description;
	private List<String> versions;
	private String downloadURL;
	private String type;
	private String fileName;
	private String fileSize;
	private String customerId;
	private List<String> appliesTo;
	private List<String> platform;
	private boolean system;
	private ArchetypeInfo archetypeInfo;
	
	public DownloadInfo() {
	}

	public DownloadInfo(String id, String name, String description, List<String> versions,
			String downloadURL, String type, List<String> appliesTo,
			List<String> platform) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.versions = versions;
		this.downloadURL = downloadURL;
		this.type = type;
		this.appliesTo = appliesTo;
		this.platform = platform;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getVersion() {
		return versions;
	}

	public void setVersion(List<String> versions) {
		this.versions = versions;
	}

	public String getDownloadURL() {
		return downloadURL;
	}

	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
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

	public List<String> getAppliesTo() {
		return appliesTo;
	}

	public void setAppliesTo(List<String> appliesTo) {
		this.appliesTo = appliesTo;
	}

	public List<String> getPlatform() {
		return platform;
	}

	public void setPlatform(List<String> platform) {
		this.platform = platform;
	}

	public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

    public ArchetypeInfo getArchetypeInfo() {
        return archetypeInfo;
    }

    public void setArchetypeInfo(ArchetypeInfo archetypeInfo) {
        this.archetypeInfo = archetypeInfo;
    }

    @Override
	public String toString() {
		return "DownloadInfo [id=" + id + ", name=" + name + ", version="
				+ versions + ", downloadURL=" + downloadURL + ", type=" + type
				+ ", fileName=" + fileName + ", fileSize=" + fileSize
				+ ", appliesTo=" + appliesTo + ", platform="
				+ platform + ", customerId=" + customerId
				+ ", getId()=" + getId() + ", getName()=" + getName()
				+ ", getVersion()=" + getVersion() + ", getDownloadURL()="
				+ getDownloadURL() + ", getType()=" + getType()
				+ ", getAppliesTo()=" + getAppliesTo()
				+ ", getPlatform()=" + getPlatform()
				+ ", getFileName()=" + getFileName() + ", getFileSize()="
				+ getFileSize() + ", getCustomerId()=" + getCustomerId() + "]";
	}

}
