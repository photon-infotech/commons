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
public class Role extends Element {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8632062848943337990L;

	private List<String> permissionIds;
    
    private String appliesTo;

	/**
	 * 
	 */
	public Role() {
		super();
	}

	/**
	 * @param id
	 * @param name
	 * @param description
	 */
	public Role(String id, String name, String description) {
		super();
	}

	/**
	 * @return
	 */
	public List<String> getPermissionIds() {
		return permissionIds;
	}

	/**
	 * @param roles
	 */
	public void setPermissionIds(List<String> permissionIds) {
		this.permissionIds = permissionIds;
	}
	
	public void setAppliesTo(String appliesTo) {
		this.appliesTo = appliesTo;
	}

	public String getAppliesTo() {
		return appliesTo;
	}

	public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("permissions", getPermissionIds())
                .append("appliesTo", getAppliesTo())
                .toString();
    }
}