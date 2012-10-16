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
 * 
 */
package com.photon.phresco.commons.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

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
 * 
 */
@XmlRootElement
public class TechnologyInfo extends Element {
    
    private static final long serialVersionUID = 2573493968812398251L;
    
    private String appTypeId;
    private String version;
    private List<String> techVersions;

    public TechnologyInfo() {
        super();
    }

    public TechnologyInfo(String appTypeId, String version) {
        super();
        this.appTypeId = appTypeId;
        this.version = version;
    }

    public String getAppTypeId() {
        return appTypeId;
    }

    public void setAppTypeId(String appTypeId) {
        this.appTypeId = appTypeId;
    }
    
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

	public List<String> getTechVersions() {
		return techVersions;
	}

	public void setTechVersions(List<String> techVersions) {
		this.techVersions = techVersions;
	}

	public String toString() {
	    return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
	            .append("appTypeId", getAppTypeId())
                .append("techVersion", getVersion())
                .append("techVersions", getTechVersions())
                .toString();
	}
}