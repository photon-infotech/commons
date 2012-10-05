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

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement
public class User extends Element {

    private static final long serialVersionUID = 1L;
    
    private String loginId;
	private String email;
	private String firstName;
	private String lastName;
	private List<String> roleIds;
	private boolean phrescoEnabled;
	private String displayName;
	private List<String> customerIds;
	private String token;
	private boolean validLogin;
	
	public User() {
		super();
	}

	public User(String id) {
		super(id);
	}
	
	/**
	 * @param id
	 * @param name
	 * @param description
	 */
	public User(String id, String name, String description) {
		super(id);
	}

	/**
	 * @return
	 */
	public String getLoginId() {
        return loginId;
    }

    /**
     * @param loginId
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    
    /**
     * @return
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * @param mailId
     */
    public void setEmail(String mailId) {
        this.email = mailId;
    }
    
    /**
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return
     */
    public List<String> getRoleIds() {
		return roleIds;
	}

	/**
	 * @param roles
	 */
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	/**
	 * @return
	 */
	public boolean isPhrescoEnabled() {
        return phrescoEnabled;
    }
	
	/**
	 * @param phrescoEnabled
	 */
	public void setPhrescoEnabled(boolean phrescoEnabled) {
        this.phrescoEnabled = phrescoEnabled;
    }
	
    /**
     * @return
     */
    public String getDisplayName() {
        return displayName;
    }
    
	/**
	 * @param displayName
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return
	 */
	public List<String> getCustomerIds() {
        return customerIds;
    }

    /**
     * @param customers
     */
    public void setCustomerIds(List<String> customerIds) {
        this.customerIds = customerIds;
    }

    /**
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }
	
	public boolean isValidLogin() {
		return validLogin;
	}

	public void setValidLogin(boolean validLogin) {
		this.validLogin = validLogin;
	}

	public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("loginId", loginId)
                .append("email", email)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("roleIds", roleIds)
                .append("phrescoEnabled", phrescoEnabled)
                .append("displayName", displayName)
                .append("customers", customerIds)
                .append("token", token)
                .toString();
    }
}