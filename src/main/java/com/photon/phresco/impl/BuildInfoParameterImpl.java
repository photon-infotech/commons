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

public class BuildInfoParameterImpl implements DynamicParameter, Constants {

    @Override
    public PossibleValues getValues(Map<String, Object> map) throws IOException, ParserConfigurationException, SAXException, ConfigurationException, PhrescoException {
        PossibleValues possibleValues = new PossibleValues();
        ApplicationInfo applicationInfo = (ApplicationInfo) map.get(KEY_APP_INFO);
        String buildInfoPath = getBuildInfoPath(applicationInfo.getAppDirName()).toString();
        List<BuildInfo> buildInfos = Utility.getBuildInfos(new File(buildInfoPath));
        if (buildInfos != null) {
            for (BuildInfo buildInfo : buildInfos) {
                Value value = new Value();
                value.setValue(Integer.toString(buildInfo.getBuildNo()));
                possibleValues.getValue().add(value);
            }
        }

        return possibleValues;
    }

    private StringBuilder getBuildInfoPath(String projectDirectory) {
        StringBuilder builder = new StringBuilder(Utility.getProjectHome());
        builder.append(projectDirectory);
        builder.append(File.separator);
        builder.append("do_not_checkin");
        builder.append(File.separator);
        builder.append("build");
        builder.append(File.separator);
        builder.append("build.info");
        return builder;
    }
}