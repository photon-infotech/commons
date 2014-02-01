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

package com.photon.phresco.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.RestClientUtility;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse;


public class TestSuitesImpl implements DynamicParameter, Constants {

	private static final String TAW_WS = "TAW";
	private static final String PASSWORD = "password";
	private static final String USER_NAME = "username";
	private static final String ADDITIONAL_CONTEXT = "additional_context";
	private static final String SUITE_NAMES = "suiteNames";
	private static final String REST_API_SERVICE_LISTSUITES = "/rest/api/service/listsuites?environment=";

	@Override
	public PossibleValues getValues(Map<String, Object> paramsMap) throws IOException, ParserConfigurationException, SAXException,
	ConfigurationException, PhrescoException {
		PossibleValues possibleValues = new PossibleValues();
		String rootModulePath = "";
		String subModuleName = "";
		ApplicationInfo applicationInfo = (ApplicationInfo) paramsMap.get(KEY_APP_INFO);
		String rootModule = (String) paramsMap.get(KEY_ROOT_MODULE);
		String envName = (String) paramsMap.get(KEY_ENVIRONMENT);

		if (StringUtils.isNotEmpty(rootModule)) {
			rootModulePath = Utility.getProjectHome() + rootModule;
			subModuleName = applicationInfo.getAppDirName();
		} else {
			rootModulePath = Utility.getProjectHome() + applicationInfo.getAppDirName();
		}
		Configuration configuration = null;
		Map<String, String> queryParamMap = new HashMap<String, String>();
		if (paramsMap != null) {
			String showSettings = (String) paramsMap.get(KEY_SHOW_SETTINGS);
			if (Boolean.parseBoolean(showSettings)) {
				String projectCode = (String) paramsMap.get(KEY_PROJECT_CODE);
				String settingsPath = getSettingsPath(projectCode);
				ConfigManager configManager = new ConfigManagerImpl(new File(settingsPath)); 
				configuration = configManager.getConfiguration(envName, SETTINGS_TEMPLATE_WEBSERVICE, TAW_WS);
			}
		}

		if (applicationInfo != null) {
			String configPath = getConfigurationPath(subModuleName, rootModulePath).toString();
			ConfigManager configManager = new ConfigManagerImpl(new File(configPath));
			configuration = configManager.getConfiguration(envName, SETTINGS_TEMPLATE_WEBSERVICE, TAW_WS);
		}

		if (configuration != null) {
			Properties properties = configuration.getProperties();
			String restServiceUrl = getRestServiceUrl(properties);
			String additional_context = properties.getProperty(ADDITIONAL_CONTEXT);
			String username = properties.getProperty(USER_NAME);
			String password = properties.getProperty(PASSWORD);
			if (StringUtils.isNotEmpty(username)) {
				queryParamMap.put(USER_NAME, username);
			}
			if (StringUtils.isNotEmpty(password)) {
				queryParamMap.put(PASSWORD, password);
			}
			if (StringUtils.isNotEmpty(restServiceUrl)) {
				restServiceUrl = StringUtils.isNotEmpty(additional_context) ? restServiceUrl.concat(additional_context) : restServiceUrl;
				restServiceUrl = restServiceUrl.concat(REST_API_SERVICE_LISTSUITES).concat(envName);
				ClientResponse clientResponse = RestClientUtility.getRestServiceResponse(restServiceUrl, queryParamMap);
				if (clientResponse.getStatus() == 200) {
					JSONObject resultJson = clientResponse.getEntity(JSONObject.class);
					List<String> suiteNames = (List<String>) resultJson.get(SUITE_NAMES);
					if(CollectionUtils.isNotEmpty(suiteNames)) {
						for (String suiteName : suiteNames) {
							Value value = new Value();
							value.setValue(suiteName);
							value.setKey(suiteName);
							possibleValues.getValue().add(value);
						}
					}
				}
			}
		}

		return possibleValues;
	}

	private StringBuilder getConfigurationPath(String subModuleName, String rootModulePath) throws PhrescoException {
		String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, subModuleName);
		StringBuilder builder = new StringBuilder(dotPhrescoFolderPath);
		builder.append(File.separator);
		builder.append(CONFIGURATION_INFO_FILE);
		return builder;
	}

	private String getSettingsPath(String projectCode) {
		return Utility.getProjectHome() + projectCode + SETTINGS_XML;
	}

	private String getRestServiceUrl(Properties properties) throws ConfigurationException {
		String protocol = properties.getProperty("protocol");
		String host = properties.getProperty("host");
		String port = properties.getProperty("port");
		String context = properties.getProperty("context");
		return String.format("%s://%s:%s/%s/", protocol, host, port, context);
	}
}
