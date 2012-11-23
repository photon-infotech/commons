package com.photon.phresco.impl;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import org.xml.sax.*;

import com.photon.phresco.api.*;
import com.photon.phresco.exception.*;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;

public class AndroidDevicesParameterImpl implements DynamicParameter {

	private static final String CMD_ADB_DEVICES = "adb devices";

	public PossibleValues getValues(Map<String, Object> map) throws IOException, ParserConfigurationException, SAXException, ConfigurationException, PhrescoException {
		
		ArrayList<String> devices = new ArrayList<String>();
		try {
			String s = null;
			Process p = Runtime.getRuntime().exec(CMD_ADB_DEVICES);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			int cnt = 0;
			while ((s = stdInput.readLine()) != null) {
				if (cnt > 0) {
					s = s.substring(0, s.indexOf("\t") + 1);
					devices.add(s.trim());
				}
				cnt++;
			}
			stdInput.close();
			cnt = 0;
			while ((s = stdError.readLine()) != null) {
				if (cnt > 0) {
					s = s.substring(0, s.indexOf("\t") + 1);
					devices.add(s.trim());
				}
				cnt++;
			}
			stdError.close();
		} catch (Exception e) {
			throw new PhrescoException("Error in retriving connected devices ");
		}
		devices.remove(devices.size() - 1);
		
		PossibleValues possibleValues = new PossibleValues();
		for (String device : devices) {
				Value value = new Value();
				value.setValue(device);
				possibleValues.getValue().add(value);
		}
		return possibleValues;
	}

}

