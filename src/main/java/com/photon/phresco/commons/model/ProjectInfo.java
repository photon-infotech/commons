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

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectInfo extends CustomerBasedElement implements Cloneable {

    private static final long serialVersionUID = 1L;
    
    private String projectCode;
    private String version;
	private List<ApplicationInfo> appInfos;
    private int noOfApps;
    private Date startDate;
    private Date endDate;
    private boolean preBuilt;
    private boolean multiModule;
    
	public String getProjectCode() {
        return projectCode;
    }
    
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
    
    public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
    
    public List<ApplicationInfo> getAppInfos() {
        return appInfos;
    }

    public void setAppInfos(List<ApplicationInfo> appInfos) {
        this.appInfos = appInfos;
    }
    
    public int getNoOfApps() {
		return noOfApps;
	}

	public void setNoOfApps(int noOfApps) {
		this.noOfApps = noOfApps;
	}
	

	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public void setPreBuilt(boolean preBuilt) {
		this.preBuilt = preBuilt;
	}

	public boolean isPreBuilt() {
		return preBuilt;
	}
	

	public void setMultiModule(boolean multiModule) {
		this.multiModule = multiModule;
	}

	public boolean isMultiModule() {
		return multiModule;
	}
	
	public ProjectInfo clone() {
		try {
			return (ProjectInfo)super.clone();
		} catch (CloneNotSupportedException e) {
			return this;
		}
	}

    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("projectCode", getProjectCode())
                .append("appInfos", getAppInfos())
                .append("noOfApps", getNoOfApps())
                .append("startDate",  getStartDate())
                .append("endDate", getStartDate())
                .append("preBuilt", isPreBuilt())
                .append("multiModule", isMultiModule())
                .toString();
    }
}