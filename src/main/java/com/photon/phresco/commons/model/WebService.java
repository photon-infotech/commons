/**
 * Phresco Commons
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
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
public class WebService extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3812647520371818960L;
	
	private String version;
	private List<String> appliesToTechs;
	
	public WebService() {
		super();
	}

	public WebService(String id, String name, String version, String description) {
	    super();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

    public List<String> getAppliesToTechs() {
        return appliesToTechs;
    }

    public void setAppliesToTechs(List<String> appliesToTechs) {
        this.appliesToTechs = appliesToTechs;
    }
	
    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("version", getVersion())
                .append("appliesToTechs", getAppliesToTechs())
                .toString();
    }
	
}
