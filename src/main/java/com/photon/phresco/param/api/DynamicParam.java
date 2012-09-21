package com.photon.phresco.param.api;

import java.util.Collection;

import com.photon.phresco.plugins.model.Mojos;


public interface DynamicParam {

    Collection<Mojos.Mojo.Configuration.Parameters.Parameter.Possiblevalues> getValues();
}
