package com.photon.phresco.param.api;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.plugins.model.Mojos;


public interface DynamicParameter {

    public Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues getValues(ApplicationInfo projectInfo) throws IOException, ParserConfigurationException, SAXException, ConfigurationException;
}
