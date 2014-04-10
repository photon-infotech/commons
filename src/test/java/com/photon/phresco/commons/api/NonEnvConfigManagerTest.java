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
package com.photon.phresco.commons.api;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.api.NonEnvConfigManager;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.impl.NonEnvConfigManagerImpl;

public class NonEnvConfigManagerTest {
	
	private NonEnvConfigManager configManager = null;
	private File configFile = null;
	
	@Before
	public void initTest() throws ConfigurationException, PhrescoException {
		configFile  = new File("src/test/java/com/photon/phresco/commons/api/phresco-config.xml");
		configManager = new NonEnvConfigManagerImpl(configFile);
	}
	
	@Test
	public void getConfigurations() throws PhrescoException {
		try {
			List<Configuration> configurations = configManager.getConfigurations();
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}
	
	@Test
	public void getConfigurationsByType() throws PhrescoException {
		try {
			List<Configuration> configurations = configManager.getConfigurations("TestTheme");
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}
	
	@Test
	public void getConfiguration() throws PhrescoException {
		try {
			Configuration configuration = configManager.getConfiguration("Theme Configuration");
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}
	
	@Test
	public void createConfiguration() throws PhrescoException {
		try {
			Configuration configuration = new Configuration();
			configuration.setName("test");
			configuration.setType("test");
			configuration.setDesc("configuration of test");
			Properties properties = new Properties();
			properties.setProperty("prop1", "properties1");
			properties.setProperty("prop2", "properties2");
			properties.setProperty("prop3", "properties3");
			configuration.setProperties(properties);
			configManager.createConfiguration(configuration);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}
	
	@Test
	public void updateConfiguration() throws PhrescoException {
		try {
			Configuration configuration = new Configuration();
			configuration.setName("testUpdate");
			configuration.setType("test");
			configuration.setDesc("configuration of testUpdate");
			Properties properties = new Properties();
			properties.setProperty("prop1", "properties1Update");
			properties.setProperty("prop2", "properties2Update");
			properties.setProperty("prop3", "properties3Update");
			configuration.setProperties(properties);
			configManager.updateConfiguration("test", configuration);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}
	
	@Test
	public void deleteConfiguration() throws PhrescoException {
		try {
			configManager.deleteConfiguration("testUpdate");
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}
}
