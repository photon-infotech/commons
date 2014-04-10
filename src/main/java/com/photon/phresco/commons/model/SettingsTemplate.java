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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.photon.phresco.util.SizeConstants;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class SettingsTemplate extends CustomerBasedElement {

	private static final long serialVersionUID = 1L;
    
    private boolean customProp;
    //Database, Server, Email
    private String type;
    
    //List of properties available for the template
    private List<PropertyTemplate> properties = new ArrayList<PropertyTemplate>(SizeConstants.SIZE_PROPERTIES_MAP);
    //List of technology ids
    private List<Element> appliesToTechs;
    private List<Element> possibleTypes;
    private String displayName;
    private boolean favourite;
    private boolean envSpecific;
    
    public SettingsTemplate() {
        super();
    }

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PropertyTemplate> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyTemplate> properties) {
        this.properties = properties;
    }

    public List<Element> getAppliesToTechs() {
        return appliesToTechs;
    }

    public void setAppliesToTechs(List<Element> appliesTo) {
        this.appliesToTechs = appliesTo;
    }
    
    public List<Element> getPossibleTypes() {
		return possibleTypes;
	}

	public void setPossibleTypes(List<Element> types) {
		this.possibleTypes = types;
	}

	public boolean isCustomProp() {
		return customProp;
	}

	public void setCustomProp(boolean customProp) {
		this.customProp = customProp;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}

	public boolean isFavourite() {
		return favourite;
	}
	
	public void setEnvSpecific(boolean envSpecific) {
		this.envSpecific = envSpecific;
	}

	public boolean isEnvSpecific() {
		return envSpecific;
	}

	 
    @Override
	public String toString() {
		return "SettingsTemplate [customProp=" + customProp + ", type=" + type
				+ ", properties=" + properties + ", appliesToTechs="
				+ appliesToTechs + ", possibleTypes=" + possibleTypes
				+ ", displayName=" + displayName + ", favourite=" + favourite
				+ ", envSpecific=" + envSpecific + "]";
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((appliesToTechs == null) ? 0 : appliesToTechs.hashCode());
		result = prime * result + (customProp ? 1231 : 1237);
		result = prime * result
				+ ((displayName == null) ? 0 : displayName.hashCode());
		result = prime * result + (envSpecific ? 1231 : 1237);
		result = prime * result + (favourite ? 1231 : 1237);
		result = prime * result
				+ ((possibleTypes == null) ? 0 : possibleTypes.hashCode());
		result = prime * result
				+ ((properties == null) ? 0 : properties.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		SettingsTemplate other = (SettingsTemplate) obj;
		if (appliesToTechs == null) {
			if (other.appliesToTechs != null)
				return false;
		} else if (!appliesToTechs.equals(other.appliesToTechs))
			return false;
		if (customProp != other.customProp)
			return false;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		if (envSpecific != other.envSpecific)
			return false;
		if (favourite != other.favourite)
			return false;
		if (possibleTypes == null) {
			if (other.possibleTypes != null)
				return false;
		} else if (!possibleTypes.equals(other.possibleTypes))
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	
}