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

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement
public class VersionInfo extends Element {
    
	private static final long serialVersionUID = 1L;
	private String serviceVersion;
	private String frameworkVersion;
	private String message;
	private boolean updateAvailable;


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isUpdateAvailable() {
		return updateAvailable;
	}

	public void setUpdateAvailable(boolean updateAvailable) {
		this.updateAvailable = updateAvailable;
	}

	public String getFrameworkVersion() {
		return frameworkVersion;
	}

	public void setFrameworkVersion(String frameworkversion) {
		this.frameworkVersion = frameworkversion;
	}
	public String getServiceVersion() {
		return serviceVersion;
	}

	public void setServiceVersion(String serviceversion) {
		this.serviceVersion = serviceversion;
	}

	@Override
	public String toString() {
		return "VersionInfo [serviceVersion=" + serviceVersion
				+ ", frameworkVersion=" + frameworkVersion + ", message="
				+ message + ", updateAvailable=" + updateAvailable + "]";
	}
}
