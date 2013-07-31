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
package com.photon.phresco.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.BuildInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;

public class BuildInfoParameterImpl implements DynamicParameter, Constants {

    @Override
    public PossibleValues getValues(Map<String, Object> map) throws PhrescoException {
        try {
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
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
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