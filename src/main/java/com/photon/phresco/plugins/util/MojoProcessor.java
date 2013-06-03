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
package com.photon.phresco.plugins.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos;
import com.photon.phresco.plugins.model.Mojos.ApplicationHandler;
import com.photon.phresco.plugins.model.Mojos.Mojo;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter;
import com.photon.phresco.plugins.model.Mojos.Mojo.Implementation;

/**
 * 
 * @author suresh_ma
 *
 */
public class MojoProcessor {

	private Mojos mojos;
	
	private File file;
	
	/**
	 * TO initialize the file
	 * @param infoFile
	 * @throws PhrescoException
	 */
	public MojoProcessor(File infoFile) throws PhrescoException {
        try {
    		if(infoFile.exists()){
    			JAXBContext jaxbContext = JAXBContext.newInstance(Mojos.class);
    			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    			mojos = (Mojos) jaxbUnmarshaller.unmarshal(infoFile);
    		} else {
    			infoFile.createNewFile();
    			mojos = new Mojos();
    		}
    		file = infoFile;
        } catch (JAXBException e) {
            throw new PhrescoException(e);
        } catch (IOException e) {
            throw new PhrescoException(e);
        }
	}
	
	public ApplicationHandler getApplicationHandler() {
		return mojos.getApplicationHandler();
		}
	
	/**
	 * get the configuration from the file
	 * @param goal
	 * @return
	 */
	public Configuration getConfiguration(String goal) {
		if(mojos.getMojo() != null) {
			List<Mojo> mojoList = mojos.getMojo();
			for (Mojo mojo : mojoList) {
					if(mojo.getGoal().equals(goal)) {
						return mojo.getConfiguration();
					}
			}
		}
	    return null;
	}
	
	/**
	 * get the implementation to the particular goal.
	 * @param goal
	 * @return
	 */
	public Implementation getImplementationDependency(String goal) {
		if(mojos.getMojo() != null) {
			List<Mojo> mojoList = mojos.getMojo();
			for (Mojo mojo : mojoList) {
				if(mojo.getGoal().equals(goal)) {
				return mojo.getImplementation();
				}
			}
		}
		return null;
	}
	
	public List<Parameter> getParameters(String goal) {
        Configuration configuration = getConfiguration(goal);
        if (configuration != null) {
            return configuration.getParameters().getParameter();
        }
        return null;
    }
	
	/**
	 * 
	 * @param goal
	 * @param key
	 * @return
	 */
	public Parameter getParameter(String goal, String key) {
		Configuration configuration = getConfiguration(goal);
		if(configuration != null) {
			List<Parameter> parameters = configuration.getParameters().getParameter();
			for (Parameter parameter : parameters) {
					if(parameter.getKey().equals(key)) {
						return parameter;
					}
			}
		}
		return null; 
	}
	
	/**
	 * To check weather the goal is available or not
	 * @param goal
	 * @return
	 */
	public boolean isGoalAvailable(String goal) {
		List<Mojo> mojoList = mojos.getMojo();
		if(mojoList != null) {
			for (Mojo mojo : mojoList) {
				if(mojo.getGoal().equals(goal)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @throws PhrescoException
	 */
	public void save() throws PhrescoException {
        try {
    		JAXBContext jaxbContext = JAXBContext.newInstance(Mojos.class);
    		Marshaller marshal = jaxbContext.createMarshaller();
    		marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    		marshal.marshal(mojos, file);
        } catch (JAXBException e) {
            throw new PhrescoException(e);
        }
	}
}
