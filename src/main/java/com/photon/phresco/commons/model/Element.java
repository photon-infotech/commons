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


/**
 * Basic Phresco element in the platform.
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Element implements Serializable {

    private static final long serialVersionUID = 2573493968812398251L;
    
    /**
     * id
     */
    @NotEmpty(message = ServiceConstants.VAL_ID_MSG)
    private String id;
    
    /**
     * name of the element
     */
    @NotEmpty(message = ServiceConstants.VAL_NAME_MSG)
	private String name;
    
    /**
     * Display name of the element
     */
    private String displayName;
	
    /**
	 * Optional description
	 */
	private String description;
	
	/**
	 * optional help text
	 */
	private String helpText;
	
	/**
	 * Creation date of the element, usually system generated as now.
	 */
	private Date creationDate;
	
	/**
	 * flag to know phresco internal element
	 */
	private boolean system;
	
	/**
	 * Phresco status
	 */
	private Status status;

	/**
	 * 
	 */
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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the helpText
	 */
	public String getHelpText() {
		return helpText;
	}

	/**
	 * @param helpText the helpText to set
	 */
	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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

    @Override
	public String toString() {
		return "Element [id=" + id + ", name=" + name + ", displayName="
				+ displayName + ", description=" + description + ", helpText="
				+ helpText + ", creationDate=" + creationDate + ", system="
				+ system + ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((displayName == null) ? 0 : displayName.hashCode());
		result = prime * result
				+ ((helpText == null) ? 0 : helpText.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + (system ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Element other = (Element) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		if (helpText == null) {
			if (other.helpText != null)
				return false;
		} else if (!helpText.equals(other.helpText))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			System.out.println("Name is null  " + name);
			if (other.name != null)
				System.out.println(" Other Name is null  " + other.name);
				return false;
		} else if (!name.equalsIgnoreCase(other.name)){
			System.out.println("  Name is not null  " + name + "other name = " + other.name);
			return false;
		}
		if (status != other.status)
			return false;
		if (system != other.system)
			return false;
		return true;
	}	
}