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

import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import com.photon.phresco.commons.model.CertificateInfo;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;


public interface ConfigManager {
	
	/**
	 * Returns the list of environments with the names provided
	 * @param names names of the environments to be returned
	 * @return returns the environments with the matching names
	 */
	List<Environment> getEnvironments(List<String> names) throws ConfigurationException;
	
	/**
	 * Adds the environments to the existing list of environments
	 * @param environments
	 * @throws ConfigurationException
	 */
	void addEnvironments(List<Environment> environments) throws ConfigurationException;

	/**
	 * Updates the environment
	 * @param environment
	 * @throws ConfigurationException
	 */
	void updateEnvironment(Environment environment) throws ConfigurationException;
	
	/**
	 * Deletes the environment provided by the name
	 * @param envName
	 * @throws ConfigurationException
	 */
	void deleteEnvironment(String envName) throws ConfigurationException;
	
	/**
	 * Get All The Environments
	 * @return
	 * @throws ConfigurationException
	 */
	List<Environment> getEnvironments() throws ConfigurationException;
	
	/**
	 * Get All The Environments with out configuration
	 * @return
	 * @throws ConfigurationException
	 */
	List<Environment> getEnvironmentsAlone() throws ConfigurationException;
	
	/**
	 * Returns the configuration list based on environment name and type
	 * @param envName
	 * @param type
	 * @return
	 * @throws ConfigurationException
	 */
	List<Configuration> getConfigurations(String envName, String type) throws ConfigurationException;
	
	/**
	 * Create configuration by given environment name and configuration
	 * @param envName
	 * @param configuration
	 * @throws ConfigurationException
	 */
	void createConfiguration(String envName, Configuration configuration) throws ConfigurationException;
	
	/**
	 * Updates configuration by given environment name and configuration
	 * @param envName
	 * @param oldConfigName
	 * @param configuration
	 * @throws ConfigurationException
	 */
	void updateConfiguration(String envName, String oldConfigName, Configuration configuration) throws ConfigurationException;
	
	/**
	 * Deletes the configuration by given environment name and configuration
	 * @param envName
	 * @param configuration
	 * @throws ConfigurationException
	 */
	void deleteConfiguration(String envName, Configuration configuration) throws ConfigurationException;
	
	/**
	 * Get the configuration using envName configtype and configName
	 * @param envName
	 * @param type
	 * @param configName
	 * @return
	 * @throws PhrescoException
	 */
	Configuration getConfiguration(String envName, String type, String configName) throws ConfigurationException;
	
	/**
	 * @param configuration
	 * @return
	 * @throws ConfigurationException
	 */
	Element createConfigElement(Configuration configuration) throws ConfigurationException;
	
	/**
	 * Deletes the list of configurations based on environment name
	 * @param envName
	 * @param configurations
	 * @throws ConfigurationException
	 */
	void deleteConfigurations(String envName, List<String> configurations) throws ConfigurationException;
	
	/**
	 * To delete the list of configurations
	 * @param configurations
	 * @throws ConfigurationException
	 */
	void deleteConfigurations(List<Configuration> configurations)throws ConfigurationException;
	
	/**
	 * Delete configurations based on given environment
	 * @param configurations
	 * @throws ConfigurationException
	 */
	void deleteConfigurations(Map<String, List<String>> configurations) throws ConfigurationException;
	
	/**
	 * Write xml.
	 *
	 * @param fos the fos
	 * @throws ConfigurationException the configuration exception
	 */
	void writeXml(OutputStream fos) throws ConfigurationException;

	List<CertificateInfo> getCertificate(String host, int port) throws PhrescoException;

	void addCertificate(CertificateInfo info, File file) throws PhrescoException;
}
