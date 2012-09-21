package com.photon.phresco.param.api;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;


public interface DynamicParameter {

	public PossibleValues getValues(ApplicationInfo applicationInfo);
}
