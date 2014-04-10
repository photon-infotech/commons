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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TechnologyGroup extends CustomerBasedElement {

	private static final long serialVersionUID = 1L;
	private String appTypeId;
	private List<TechnologyInfo> techInfos;
	
	public TechnologyGroup() {
	}
	
	public List<TechnologyInfo> getTechInfos() {
		return techInfos;
	}

	public void setTechInfos(List<TechnologyInfo> techIds) {
		this.techInfos = techIds;
	}

	public String getAppTypeId() {
		return appTypeId;
	}

	public void setAppTypeId(String appTypeId) {
		this.appTypeId = appTypeId;
	}

	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("id", getId())
                .append("name", getName())
                .append("techInfos", getTechInfos())
                .append("appTypeId", getAppTypeId())
                .toString();
    }
}
