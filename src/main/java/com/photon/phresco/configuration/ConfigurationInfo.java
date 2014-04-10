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
package com.photon.phresco.configuration;

public class ConfigurationInfo {
	
	private String environment;

	private String serverPort;
	
	private String context;
	
	private String moduleName;

	public ConfigurationInfo() {
		super();
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironmentName(String environment) {
		this.environment = environment;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Override
	public String toString() {
		return "configurationInfo [environmentName=" + environment
				+ ", serverPort=" + serverPort + ", context=" + context
				+ ", moduleName=" + moduleName + "]";
	}
}
