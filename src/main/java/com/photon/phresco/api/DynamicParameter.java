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
package com.photon.phresco.api;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos;


public interface DynamicParameter {
	
	String KEY_APP_INFO = "applicationInfo";
	String KEY_BUILD_NO = "buildNumber";
	String KEY_ENVIRONMENT = "environmentName";
	String KEY_SHOW_SETTINGS = "showSettings";
	String KEY_DATABASE = "dataBase";
	String KEY_SERVER = "server";
	String KEY_WEBSERVICE = "webService";
	String KEY_TEST_AGAINST = "testAgainst";
	String KEY_CUSTOMER_ID = "customerId";
	String KEY_MOJO =  "mojo";
	String KEY_GOAL = "goal";
	String KEY_DEVICE_TYPE = "deviceType";
	String KEY_TRIGGER_SIMULATOR = "triggerSimulator";
	String REQ_SERVICE_MANAGER = "serviceManager";
	String KEY_CUSTOM_TEST_AGAINST = "customTestAgainst";
	String KEY_ROOT_MODULE = "rootModule";
	String KEY_MULTI_MODULE = "multiModule";
	String KEY_PROJECT_CODE	= "projectCode";

    public Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues getValues(Map<String, Object> map) throws IOException, ParserConfigurationException, SAXException, ConfigurationException, PhrescoException;
}