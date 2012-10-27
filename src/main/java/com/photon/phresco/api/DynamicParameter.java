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
	String KEY_SETTINGS_ENV = "environmentName";

    public Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues getValues(Map<String, Object> map) throws IOException, ParserConfigurationException, SAXException, ConfigurationException, PhrescoException;
}
