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
public class ApplicationType extends CustomerBasedElement {

	private static final long serialVersionUID = 1L;
	
	private List<TechnologyGroup> techGroups;
	
	public ApplicationType() {
		super();
	}

    public List<TechnologyGroup> getTechGroups() {
		return techGroups;
	}

	public void setTechGroups(List<TechnologyGroup> techGroups) {
		this.techGroups = techGroups;
	}

	@Override
	public String toString() {
		return super.toString();
	}
    
}