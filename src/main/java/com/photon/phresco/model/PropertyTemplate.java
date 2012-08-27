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

@SuppressWarnings("restriction")
@XmlRootElement
public class PropertyTemplate implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String key;
	private I18NString name;
	private I18NString description;
	private String type;
	private boolean isRequired;
	private List<String> possibleValues;
	private List<PropertyTemplate> propertyTemplates;
	
	public PropertyTemplate() {
		super();
	}

	public PropertyTemplate(String key, String type) {
		super();
		this.key = key;
		this.type = type;
	}

	public PropertyTemplate(String key, String type, boolean projectSpecific,
			boolean isRequired) {
		super();
		this.key = key;
		this.type = type;
		this.isRequired = isRequired;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public I18NString getName() {
		return name;
	}

	public void setName(I18NString name) {
		this.name = name;
	}

	public I18NString getDescription() {
		return description;
	}

	public void setDescription(I18NString description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
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
}