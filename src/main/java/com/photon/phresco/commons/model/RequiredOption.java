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

public class RequiredOption {
    
    String techId;
    boolean required;
    
    public RequiredOption() {
		// TODO Auto-generated constructor stub
	}
    public RequiredOption(String techId, boolean required) {
        super();
        this.techId = techId;
        this.required = required;
    }

    public String getTechId() {
        return techId;
    }
    
    public void setTechId(String techId) {
        this.techId = techId;
    }
    
    public boolean isRequired() {
        return required;
    }
    
    public void setRequired(boolean required) {
        this.required = required;
    }
}
