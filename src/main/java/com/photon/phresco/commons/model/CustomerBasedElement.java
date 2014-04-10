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

    @Override
	public String toString() {
		return "CustomerBasedElement [customerIds=" + customerIds + ", used="
				+ used + "]";
	}
    

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((customerIds == null) ? 0 : customerIds.hashCode());
		result = prime * result + (used ? 1231 : 1237);
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
		CustomerBasedElement other = (CustomerBasedElement) obj;
		if (customerIds == null) {
			if (other.customerIds != null)
				return false;
		} else if (!customerIds.equals(other.customerIds))
			return false;
		if (used != other.used)
			return false;
		return true;
	}
}