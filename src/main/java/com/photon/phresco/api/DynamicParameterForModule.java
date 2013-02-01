package com.photon.phresco.api;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Module;


public interface DynamicParameterForModule {
	
	String KEY_APP_INFO = "applicationInfo";
	String KEY_ENVIRONMENT = "environmentName";
	String KEY_SHOW_SETTINGS = "showSettings";
	String KEY_CUSTOMER_ID = "customerId";

    public Module.Configurations.Configuration.Parameter.PossibleValues getValues(Map<String, Object> map) throws IOException, ParserConfigurationException, SAXException, ConfigurationException, PhrescoException;
}