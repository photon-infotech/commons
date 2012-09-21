package com.photon.phresco.param.impl;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.param.api.DynamicParameter;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;

public class EnvironmentParameter implements DynamicParameter {

	public PossibleValues getValues(ApplicationInfo applicationInfo) {
		return null;
	}
}
