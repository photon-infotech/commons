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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

import com.photon.phresco.util.ServiceConstants;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerBasedElement extends Element {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@NotEmpty(message = ServiceConstants.VAL_CUSID_MSG)
	private List<String> customerIds;
    
	/**
     * 
     */
    private boolean used;

	/**
	 * 
	 */
	protected CustomerBasedElement() {
		super();
	}

    /**
     * @param id
     */
    protected CustomerBasedElement(String id) {
        super(id);
    }

    /**
     * @return
     */
    public List<String> getCustomerIds() {
        return customerIds;
    }

    /**
     * @param customerIds
     */
    public void setCustomerIds(List<String> customerIds) {
        this.customerIds = customerIds;
    }
    
    /**
     * @return
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * @param used
     */
    public void setUsed(boolean used) {
        this.used = used;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("customerIds", getCustomerIds())
                .append("isUsed", isUsed())
                .toString();
    }
}