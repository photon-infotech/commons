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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement
public class LogInfo implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String message;
	private String trace;
	private String action;
	private String userId;
    private String customerId;
    private String appId;

	public LogInfo() {
	    super();
	}
	
	public LogInfo(String errorMessage, String trace, String action, String userid) {
		super();
		this.message = errorMessage;
		this.trace = trace;
		this.action = action;
		this.userId = userid;
	}
	
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String projectId) {
        this.appId = projectId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append("id", getId())
                .append("message", getMessage())
                .append("trace", getTrace())
                .append("action", getAction())
                .append("userId", getUserId())
                .append("customerId", getCustomerId())
                .append("appId", getAppId())
                .toString();
    }
    
}