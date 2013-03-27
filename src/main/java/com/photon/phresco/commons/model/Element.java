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

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts2.json.annotations.JSON;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

import com.photon.phresco.util.ServiceConstants;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Element implements Serializable {

    private static final long serialVersionUID = 2573493968812398251L;
    
    @NotEmpty(message = ServiceConstants.VAL_ID_MSG)
    private String id;
    @NotEmpty(message = ServiceConstants.VAL_NAME_MSG)
	private String name;
    private String displayName;
	private String description;
	private String helpText;
	private Date creationDate;
	private boolean system;
	private Status status;

	public Element() {
		super();
		this.id = UUID.randomUUID().toString();
		this.creationDate = new Date();
	}

    public Element(String id) {
        super();
        this.id = id;
    }

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public String getHelpText() {
        return helpText;
    }

    /**
	 * @return
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 */
    @JSON(format="MMM dd, yyyy HH:mm:ss a")
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

	public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String toString() {
	    return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("displayName", getDisplayName())
                .append("description", getDescription())
                .append("helpText", getHelpText())
                .append("creationDate", getCreationDate())
                .append("system", isSystem())
                .append("status", getStatus())
                .toString();
	}	
}