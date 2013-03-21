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
package com.photon.phresco.commons.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer extends Element {

	private static final long serialVersionUID = -1276089034320060699L;
	
	private String emailId;
    private String address;
    private String country;
    private String state;
    private String zipcode;
    private String contactNumber;
    private String fax;
	private Date validFrom;
	private Date validUpto;
	private LicenseType type;
	private RepoInfo repoInfo;
	private String icon;
	private List<String> applicableTechnologies;
	private List<ApplicationType> applicableAppTypes;
	private Map<String, String> frameworkTheme;
	private List<String> options;
	
	public enum LicenseType {
        TYPE_GOLD, TYPE_SILVER, TYPE_BRONZE
    }
	
	/**
	 * 
	 */
	public Customer() {
		super();
	}

	public Customer(String id) {
		super(id);
	}
	
	/**
	 * @return
	 */
	public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    
	
	/**
	 * @return
	 */
	public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }
    
    /**
     * @return
     */
    public String getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }
    
    /**
     * @return
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * @param zipcode
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * @return
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * @param contactNumber
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * @return
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }
    
    /**
     * @return
     */
    public Date getValidFrom() {
        return validFrom;
    }

    /**
     * @param validFrom
     */
    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    /**
     * @return
     */
    public Date getValidUpto() {
        return validUpto;
    }

    /**
     * @param validUpto
     */
    public void setValidUpto(Date validUpto) {
        this.validUpto = validUpto;
    }
    
    
    /**
     * @return
     */
    public LicenseType getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(LicenseType type) {
        this.type = type;
    }

    /**
     * @return
     */
    public RepoInfo getRepoInfo() {
        return repoInfo;
    }

    /**
     * @param repoInfo
     */
    public void setRepoInfo(RepoInfo repoInfo) {
        this.repoInfo = repoInfo;
    }

    public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<ApplicationType> getApplicableAppTypes() {
		return applicableAppTypes;
	}

	public void setApplicableAppTypes(List<ApplicationType> applicableAppTypes) {
		this.applicableAppTypes = applicableAppTypes;
	}
	
	public List<String> getApplicableTechnologies() {
		return applicableTechnologies;
	}

	public void setApplicableTechnologies(List<String> applicableTechnologies) {
		this.applicableTechnologies = applicableTechnologies;
	}


	public Map<String, String> getFrameworkTheme() {
		return frameworkTheme;
	}

	public void setFrameworkTheme(Map<String, String> frameworkTheme) {
		this.frameworkTheme = frameworkTheme;
	}
	
	public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<String> getOptions() {
        return options;
    }

	public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("emailId", emailId)
                .append("address", address)
                .append("country", country)
                .append("state", state)
                .append("zipcode", zipcode)
                .append("contactNumber", contactNumber)
                .append("fax", fax)
                .append("validFrom", validFrom)
                .append("validUpto", validUpto)
                .append("type", type)
                .append("repoInfo", repoInfo)
                .append("applicableTechnologies", applicableTechnologies)
                .append("applicableAppTypes", applicableAppTypes)
                .append("options", options)
                .toString();
    }
}