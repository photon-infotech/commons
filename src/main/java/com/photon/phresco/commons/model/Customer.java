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

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement
public class Customer extends Element {

	private String emailId;
    private String address;
    private String country;
    private String state;
    private String zipcode;
    private String contactNumber;
    private String fax;
	Date validFrom;
	Date validUpto;
	private LicenseType type;
	private RepoInfo repoInfo;
	
	public enum LicenseType {
        TYPE_GOLD, TYPE_SILVER, TYPE_BRONZE
    }
	
	/**
	 * 
	 */
	public Customer() {
		super();
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
                .toString();
    }
}