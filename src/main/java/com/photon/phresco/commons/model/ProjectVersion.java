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

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectVersion {

	private int major;
	private int minor;
	private int fix;
	private String buildType;
	private int weekStart;

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public int getFix() {
		return fix;
	}

	public void setFix(int fix) {
		this.fix = fix;
	}

	public String getBuildType() {
		return buildType;
	}

	public void setBuildType(String iteration) {
		this.buildType = iteration;
	}

	public void setWeekStart(int weekStart) {
		this.weekStart = weekStart;
	}

	public int getWeekStart() {
		return weekStart;
	}

	public String toString() {
		return new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE)
		.append(super.toString())
		.append("major", getMajor())
		.append("minor", getMinor())
		.append("fix", getFix())
		.append("buildType", getBuildType())
		.append("weekStart", getWeekStart())
		.toString();
	}
}