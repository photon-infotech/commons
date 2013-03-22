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
package com.photon.phresco.commons.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@SuppressWarnings("restriction")
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertyTemplate extends Element {

	private static final long serialVersionUID = 1L;
	
	private String key;
	private String type;
	private boolean required;
	private boolean multiple;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("type", getType())
                .append("key", getKey())
                .append("possibleValues", getPossibleValues())
                .append("required", isRequired())
                .append("multiple", isMultiple())
                .toString();
    }
    
}