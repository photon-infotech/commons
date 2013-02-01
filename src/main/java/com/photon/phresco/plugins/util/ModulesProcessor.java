package com.photon.phresco.plugins.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Module;
import com.photon.phresco.plugins.model.Module.Configurations;
import com.photon.phresco.plugins.model.Module.Configurations.Configuration;
import com.photon.phresco.plugins.model.Module.Configurations.Configuration.Parameter;

/**
 * 
 * @author santhosh_ja
 *
 */
public class ModulesProcessor {

	private Module module;
	
	private File file;
	
	/**
	 * TO initialize the file
	 * @param infoFile
	 * @throws PhrescoException
	 */
	public ModulesProcessor(File infoFile) throws PhrescoException {
        try {
    		if(infoFile.exists()){
    			JAXBContext jaxbContext = JAXBContext.newInstance(Module.class);
    			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    			module = (Module) jaxbUnmarshaller.unmarshal(infoFile);
    		} else {
    			infoFile.createNewFile();
    			module = new Module();
    		}
    		file = infoFile;
        } catch (JAXBException e) {
            throw new PhrescoException(e);
        } catch (IOException e) {
            throw new PhrescoException(e);
        }
	}	
	
	/**
	 * get the configuration from the file
	 * @return
	 */
	public Configurations getConfiguration() {
		if(module.getConfigurations() != null) {
			return module.getConfigurations();
		}
		return null;
	}
	
	/**
	 * get the configurations from the file
	 * @return
	 */
	public List<Configuration> getConfigurations() {
		List<Configuration> configuration = new ArrayList<Configuration>();
		if(module.getConfigurations() != null) {
			configuration = module.getConfigurations().getConfiguration();
			return configuration;
		}
		return null;
	}
	
	/**
	 * get the parameters from the file
	 * @return
	 */
	public List<Parameter> getParameters() {
		List<Parameter> Parameters = new ArrayList<Parameter>();
		if(module.getConfigurations() != null) {
			Configurations configurations = module.getConfigurations();
			List<Configuration> configurationList = configurations.getConfiguration();
			for (Configuration configuration : configurationList) {
				Parameters.add(configuration.getParameter());
			}
			return Parameters;
		}
		return null;
	}
	
	/**
	 * 
	 * @param configName
	 * @return
	 */
	public Parameter getParameter(String configName) {
		Configurations configurations = module.getConfigurations();
		if(configurations != null) {
			List<Configuration> configurationList = configurations.getConfiguration();
			for (Configuration configuration : configurationList) {
				if(configuration.getName().equals(configName)) {
					return configuration.getParameter();
				}
			}
		}
		return null; 
	}
	
	/**
	 * To check whether the parameter is available or not
	 * @param parameter
	 * @return
	 */
	public boolean isParameterAvailable(String paramName) {
		
		Configurations configurations = module.getConfigurations();
		if(configurations != null) {
			List<Configuration> configurationList = configurations.getConfiguration();
			for (Configuration configuration : configurationList) {
				if((configuration.getParameter().getName()).equals(paramName)) {
					return true;
				}
			}
		}
		return false; 
	}
	
	/**
	 * To get the configName given the paramName
	 * @param paramName
	 * @return
	 */
	public String getConfigName(String paramName) {
		
		Configurations configurations = module.getConfigurations();
		if(configurations != null) {
			List<Configuration> configurationList = configurations.getConfiguration();
			for (Configuration configuration : configurationList) {
				if((configuration.getParameter().getName().getValue().getValue()).equals(paramName)) {
					return configuration.getName();
				}
			}
		}
		return ""; 
	}
	
	/**
	 * 
	 * @throws PhrescoException
	 */
	public void save() throws PhrescoException {
        try {
    		JAXBContext jaxbContext = JAXBContext.newInstance(Module.class);
    		Marshaller marshal = jaxbContext.createMarshaller();
    		marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    		marshal.marshal(module, file);
        } catch (JAXBException e) {
            throw new PhrescoException(e);
        }
	}
}
