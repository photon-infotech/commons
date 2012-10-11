package com.photon.phresco.param.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.photon.phresco.param.api.DynamicParameter;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.Name.Value;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.util.Utility;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.configuration.ConfigReader;
import com.photon.phresco.configuration.Environment;

public class EnvironmentParameter implements DynamicParameter {
	private static final String CONFIGURATION_INFO_FILE_NAME = "phresco-env-config.xml";
	private static final String FOLDER_DOT_PHRESCO = ".phresco";
	
    public PossibleValues getValues(ApplicationInfo projectInfo) throws IOException, ParserConfigurationException, SAXException {
    	PossibleValues values = new PossibleValues();
    	String projectCode = projectInfo.getCode();
    	String configPath = getConfigurationPath(projectCode).toString();
//    	ConfigReader reader = new ConfigReader(new File(configPath));
//    	List<Environment> environments = reader.getEnvironments();
//    	for (Environment environment : environments) {
//    		values.getValue().add(environment.getName());
//		}
//
    	return values;
    }
    
    private StringBuilder getConfigurationPath(String projectCode) {
		 StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		 builder.append(projectCode);
		 builder.append(File.separator);
		 builder.append(FOLDER_DOT_PHRESCO);
		 builder.append(File.separator);
		 builder.append(CONFIGURATION_INFO_FILE_NAME);
		 
		 return builder;
	 }
}
