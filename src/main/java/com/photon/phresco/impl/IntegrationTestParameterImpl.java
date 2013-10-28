package com.photon.phresco.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;

public class IntegrationTestParameterImpl implements DynamicParameter, Constants {

	@Override
	public PossibleValues getValues(Map<String, Object> paramsMap) throws IOException, ParserConfigurationException, SAXException, ConfigurationException, PhrescoException {
		PossibleValues possibleValues = new PossibleValues();
		MojoProcessor mojo = (MojoProcessor) paramsMap.get(KEY_MOJO);
		String goal = (String) paramsMap.get(KEY_GOAL);
		Parameter parameter = mojo.getParameter(goal, KEY_ENVIRONMENT);
		String updateDefaultEnv = "";
		if (paramsMap != null) {
			String projectCode = (String) paramsMap.get(KEY_PROJECT_CODE);
			String settingsPath = getSettingsPath(projectCode);
			ConfigManager configManager = new ConfigManagerImpl(new File(settingsPath)); 
			List<Environment> environments = configManager.getEnvironments();
			for (Environment environment : environments) {
				Value value = new Value();
				value.setValue(environment.getName());
				possibleValues.getValue().add(value);
				if(environment.isDefaultEnv()) {
					updateDefaultEnv = environment.getName();
				}
			}
		}
		if (parameter != null && StringUtils.isEmpty(parameter.getValue())) {
			parameter.setValue(updateDefaultEnv);
			mojo.save();
		}
		return possibleValues;
	}
	
	private String getSettingsPath(String projectCode) {
    	return Utility.getProjectHome() + projectCode +"-settings.xml";
    }

}
