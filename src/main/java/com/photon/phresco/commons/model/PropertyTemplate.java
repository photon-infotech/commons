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

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertyTemplate extends Element {

	private static final long serialVersionUID = 1L;
	
	private String key;
	private String type;
	private boolean required;
	private boolean multiple;
	private String defaultValue;
	private List<String> possibleValues;
	private List<PropertyTemplate> propertyTemplates;
	private List<String> appliesTo;
	private String settingsTemplateId;
	
	public PropertyTemplate() {
		super();
	}

	public PropertyTemplate(String key, String type) {
		super();
		this.key = key;
		this.type = type;
	}

	public PropertyTemplate(String key, String type, boolean projectSpecific,
			boolean required) {
		super();
		this.key = key;
		this.type = type;
		this.required = required;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public List<String> getPossibleValues() {
		return possibleValues;
	}

	public void setPossibleValues(List<String> possibleValues) {
		this.possibleValues = possibleValues;
	}

	public List<PropertyTemplate> getPropertyTemplates() {
		return propertyTemplates;
	}

	public void setPropertyTemplates(List<PropertyTemplate> propertyTemplates) {
		this.propertyTemplates = propertyTemplates;
	}
	
	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
	
	public void setAppliesTo(List<String> appliesTo) {
		this.appliesTo = appliesTo;
	}

	public List<String> getAppliesTo() {
		return appliesTo;
	}
	
	public void setSettingsTemplateId(String settingsTemplateId) {
		this.settingsTemplateId = settingsTemplateId;
	}

	public String getSettingsTemplateId() {
		return settingsTemplateId;
	}
	
	@Override
	public String toString() {
		return "PropertyTemplate [key=" + key + ", type=" + type
				+ ", required=" + required + ", multiple=" + multiple
				+ ", defaultValue=" + defaultValue + ", possibleValues="
				+ possibleValues + ", propertyTemplates=" + propertyTemplates
				+ ", appliesTo=" + appliesTo + ", settingsTemplateId="
				+ settingsTemplateId + "]";
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((appliesTo == null) ? 0 : appliesTo.hashCode());
		result = prime * result
				+ ((defaultValue == null) ? 0 : defaultValue.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + (multiple ? 1231 : 1237);
		result = prime * result
				+ ((possibleValues == null) ? 0 : possibleValues.hashCode());
		result = prime
				* result
				+ ((propertyTemplates == null) ? 0 : propertyTemplates
						.hashCode());
		result = prime * result + (required ? 1231 : 1237);
		result = prime
				* result
				+ ((settingsTemplateId == null) ? 0 : settingsTemplateId
						.hashCode());
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
		PropertyTemplate other = (PropertyTemplate) obj;
		if (appliesTo == null) {
			if (other.appliesTo != null)
				return false;
		} else if (!appliesTo.equals(other.appliesTo))
			return false;
		if (defaultValue == null) {
			if (other.defaultValue != null)
				return false;
		} else if (!defaultValue.equals(other.defaultValue))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (multiple != other.multiple)
			return false;
		if (possibleValues == null) {
			if (other.possibleValues != null)
				return false;
		} else if (!possibleValues.equals(other.possibleValues))
			return false;
		if (propertyTemplates == null) {
			if (other.propertyTemplates != null)
				return false;
		} else if (!propertyTemplates.equals(other.propertyTemplates))
			return false;
		if (required != other.required)
			return false;
		if (settingsTemplateId == null) {
			if (other.settingsTemplateId != null)
				return false;
		} else if (!settingsTemplateId.equals(other.settingsTemplateId))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}