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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;

public class WPDeployTargetImpl implements DynamicParameter{
	
	private static String EMULATOR = "Emulator";
	private static String DEVICE = "Device";
	private static String X86 = "86";
	private static String X64 = "64";
	private static String XD = "xd";
	private static String DE = "de";
	public PossibleValues getValues(Map<String, Object> paramsMap) throws IOException, ParserConfigurationException, SAXException, ConfigurationException, PhrescoException {
    	PossibleValues possibleValues = new PossibleValues();
    	String osArch = getOSArchitecture();
    	List<String> targets = new ArrayList<String>();
    	targets.add(EMULATOR);
    	targets.add(DEVICE);
    	if (osArch.contains(X86)) {
    		for (String target : targets) {
    			Value value = new Value();
    			value.setValue(target);
    			value.setKey(target.toLowerCase());
    			possibleValues.getValue().add(value);
			}
    	} else if (osArch.contains(X64)) {
    		for (String target : targets) {
    			Value value = new Value();
    			value.setValue(target);
    			if (EMULATOR.equals(target)) {
        			value.setKey(XD);
    			} else {
        			value.setKey(DE);
    			}
    			possibleValues.getValue().add(value);
			}

    	}
    	
    	return possibleValues;
    }
	public String getOSArchitecture() {
		String osArch = "";
		for (Map.Entry<Object, Object> e : System.getProperties().entrySet()) {

			if (e.getKey().toString().equalsIgnoreCase("os.arch")) {
				osArch = e.getValue().toString();
				break;
			}
		}
		return osArch;
	}
	


}
