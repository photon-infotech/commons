package com.photon.phresco.param.impl;

import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.param.api.DynamicParameter;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;

public class EnvironmentParameter implements DynamicParameter {

	public PossibleValues getValues(ProjectInfo projectInfo) {
		return null;
	}
}
