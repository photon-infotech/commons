package com.photon.phresco.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.BuildInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;

public class BuildInfoEnvironmentsImpl implements DynamicParameter, Constants {
	private String BUILD_INFO_FILE_NAME = "build.info";
	private String DO_NOT_CHECKIN_DIR = "do_not_checkin";
	private String BUILD = "build";
	
	@SuppressWarnings("unchecked")
    @Override
	public PossibleValues getValues(Map<String, Object> paramsMap) throws IOException, ParserConfigurationException, SAXException, ConfigurationException, PhrescoException {
		PossibleValues possibleValues = new PossibleValues();;
		try {
            ApplicationInfo applicationInfo = (ApplicationInfo) paramsMap.get(KEY_APP_INFO);
            Object object = paramsMap.get("functionalTest");
            String buildNumber = "";
            if (object instanceof Map) {
                buildNumber = (String) ((Map<String, Object>) object).get(KEY_BUILD_NO);
            }
            System.out.println("buildNumber in BuildInfoEnvironmentsImpl:::" + buildNumber);
            BuildInfo buildInfo = Utility.getBuildInfo(Integer.parseInt(buildNumber), getBuildInfoPath(applicationInfo.getAppDirName()).toString());
            List<String> environments = buildInfo.getEnvironments();
            if (environments != null) {
                for (String environment : environments) {
                    Value value = new Value();
                    value.setValue(environment);
                    possibleValues.getValue().add(value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        
        return possibleValues;
	}
	
	private StringBuilder getBuildInfoPath(String projectDirectory) {
	    StringBuilder builder = new StringBuilder(Utility.getProjectHome());
	    builder.append(projectDirectory);
	    builder.append(File.separator);
	    builder.append(DO_NOT_CHECKIN_DIR);
	    builder.append(File.separator);
	    builder.append(BUILD);
	    builder.append(File.separator);
	    builder.append(BUILD_INFO_FILE_NAME);
	    return builder;
	}
}