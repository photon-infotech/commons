package com.photon.phresco.plugins.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;

import com.photon.phresco.plugins.model.Assembly;
import com.photon.phresco.plugins.model.Assembly.FileSets.FileSet;

public class WarConfigProcessor {
	
private Assembly assembly;
	
	private File file;
	
	public WarConfigProcessor(File configFile) throws JAXBException, IOException {
		if(configFile.exists()){
			JAXBContext jaxbContext = JAXBContext.newInstance(Assembly.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			assembly = (Assembly) jaxbUnmarshaller.unmarshal(configFile);
		} else {
			configFile.createNewFile();
			assembly = new Assembly();
		}
		file = configFile;
	}
	
	public void save() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Assembly.class);
		Marshaller marshal = jaxbContext.createMarshaller();
		marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshal.marshal(assembly, file);
	}
	
	public void createFileSet(FileSet fileSet) {
	    assembly.getFileSets().getFileSet().add(fileSet);
	}
	
	public FileSet getFileSet(String id) throws JAXBException {
		List<FileSet> fileSets = assembly.getFileSets().getFileSet();
		for (FileSet fileSet : fileSets) {
			if(StringUtils.isNotEmpty(fileSet.getId()) && fileSet.getId().equals(id)) {
				return fileSet;
			}
		}
		return null;
	}
}
