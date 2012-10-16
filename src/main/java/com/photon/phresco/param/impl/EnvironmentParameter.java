package com.photon.phresco.param.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.photon.phresco.commons.api.ConfigManager;
import com.photon.phresco.commons.impl.ConfigManagerImpl;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.param.api.DynamicParameter;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;

public class EnvironmentParameter implements DynamicParameter, Constants {
	
	public PossibleValues getValues(ApplicationInfo applicationInfo) throws IOException, ParserConfigurationException, SAXException, ConfigurationException {
    	PossibleValues values = new PossibleValues();
    	String projectDirectory = applicationInfo.getAppDirName();
    	String configPath = getConfigurationPath(projectDirectory).toString();
    	ConfigManager configManager = new ConfigManagerImpl(new File(configPath)); 
    	List<Environment> environments = configManager.getEnvironments();
    	for (Environment environment : environments) {
    		values.getValue().add(environment.getName());
		}

    	return values;
    }
    
    private StringBuilder getConfigurationPath(String projectDirectory) {
		 StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		 builder.append(projectDirectory);
		 builder.append(File.separator);
		 builder.append(DOT_PHRESCO_FOLDER);
		 builder.append(File.separator);
		 builder.append(CONFIGURATION_INFO_FILE);
		 
		 return builder;
	 }
}
