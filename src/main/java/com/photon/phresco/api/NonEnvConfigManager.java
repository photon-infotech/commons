package com.photon.phresco.api;

import java.util.List;

import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.exception.PhrescoException;

public interface NonEnvConfigManager {

	/**
	 * To get all the non-env specific configurations
	 * @return
	 * @throws PhrescoException
	 */
	List<Configuration> getConfigurations() throws PhrescoException;
	
	/**
	 * To get all the configurations of the given type 
	 * @param type
	 * @return
	 * @throws PhrescoException
	 */
	List<Configuration> getConfigurations(String type) throws PhrescoException;
	
	/**
	 * To get the non-env specific configuration of the given name
	 * @param configName
	 * @return
	 * @throws PhrescoException
	 */
	Configuration getConfiguration(String configName) throws PhrescoException;
	
	/**
	 * To reade the non-env specific configuration
	 * @param configuration
	 * @throws PhrescoException
	 */
	void createConfiguration(Configuration configuration) throws PhrescoException;
	
	/**
	 * delete the given configuration
	 * @param configName
	 * @throws PhrescoException
	 */
	void deleteConfiguration(String configName) throws PhrescoException;
	
	/**
	 * update the existing configuration
	 * @param oldConfigName
	 * @param configuration
	 * @throws PhrescoException
	 */
	void updateConfiguration(String oldConfigName, Configuration configuration) throws PhrescoException;
}
