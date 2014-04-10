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

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class DownloadInfo extends CustomerBasedElement {

    /**
	 * generated serial number 
	 */
	private static final long serialVersionUID = -1229697958518922019L;

	/**
	 * list of applications
	 */
	private List<String> appliesToTechIds;
	
	/**
	 * list of supported platforms
	 */
	private List<String> platformTypeIds;
	
	/**
	 * 
	 */
	private Category category;
	
	/**
	 * 
	 */
	private ArtifactGroup artifactGroup;
	
	/**
	 * 
	 */
	public DownloadInfo() {
	    super();
	}

	/**
	 * @param id
	 */
	public DownloadInfo(String id) {
	    super(id);
	}
	
    /**
     * @return
     */
    public List<String> getAppliesToTechIds() {
        return appliesToTechIds;
    }

    /**
     * @param appliesToTechs
     */
    public void setAppliesToTechIds(List<String> appliesToTechs) {
        this.appliesToTechIds = appliesToTechs;
    }

    /**
     * @return
     */
    public List<String> getPlatformTypeIds() {
        return platformTypeIds;
    }

    /**
     * @param platformTypeIds
     */
    public void setPlatformTypeIds(List<String> platformTypeIds) {
        this.platformTypeIds = platformTypeIds;
    }

    /**
     * @return
     */
    public Category getCategory() {
		return category;
	}

	/**
	 * @param category
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return
	 */
	public ArtifactGroup getArtifactGroup() {
		return artifactGroup;
	}

	/**
	 * @param artifactGroup
	 */
	public void setArtifactGroup(ArtifactGroup artifactGroup) {
		this.artifactGroup = artifactGroup;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("appliesToTechs", getAppliesToTechIds())
                .append("platformTypeIds", getPlatformTypeIds())
                .toString();
    }

}