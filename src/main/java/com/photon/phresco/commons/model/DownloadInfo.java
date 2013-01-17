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


@XmlRootElement
public class DownloadInfo extends ArtifactGroup {

	private List<Element> appliesToTechs;
	private List<PlatformType> platform;
	
	public DownloadInfo() {
	    super();
	}
	
	public DownloadInfo(String id, String name, String description) {
        super(id, name, description);
    }

    public DownloadInfo(String name, String description) {
        super(name, description);
    }

    public List<Element> getAppliesToTechs() {
        return appliesToTechs;
    }

    public void setAppliesToTechs(List<Element> appliesToTechs) {
        this.appliesToTechs = appliesToTechs;
    }

    public List<PlatformType> getPlatform() {
        return platform;
    }

    public void setPlatform(List<PlatformType> platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("appliesToTechs", getAppliesToTechs())
                .append("platform", getPlatform())
                .toString();
    }

}
