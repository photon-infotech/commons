package com.photon.phresco.configuration;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.AfterClass;
import org.junit.Test;
import org.mockito.Mockito;
import com.photon.phresco.exception.ConfigurationException;

public class ConfigWriterTest {
	
	private File file = new File("src/test/resources/phresco-env-config-write.xml");
	private static File wrongFile = new File("src/test/resources/phresco-env-config-wrong.xml");
	
	@Test
	public void testConfigWriter() throws ConfigurationException {
		ConfigReader configReader = new ConfigReader(file);
		ConfigWriter configWriter = new ConfigWriter(configReader, false);
		ConfigWriter configWriter2 = new ConfigWriter(configReader, true);
		assertNotNull(configWriter);
		assertNotNull(configWriter2);
	}
	
	@Test
	public void testSaveXml() throws ConfigurationException, FileNotFoundException {
		ConfigReader configReader = new ConfigReader(file);
		ConfigWriter configWriter = new ConfigWriter(configReader, true);
		configWriter.saveXml(configReader, "Testing");
		configWriter.saveXml(file, "Testing");
		FileOutputStream fos = new FileOutputStream(file);
		configWriter.saveXml(fos, "Production,Development,Testing, NewEnvironment");
		assertNotNull(configWriter);
	}
	
	@Test
	public void testGetDocument() throws ConfigurationException {
		ConfigReader configReader = new ConfigReader(file);
		ConfigWriter configWriter = new ConfigWriter(configReader, true);
		assertNotNull(configWriter.getDocument());
	}
	
	@Test
	public void testGetRootElement() throws ConfigurationException {
		ConfigReader configReader = new ConfigReader(file);
		ConfigWriter configWriter = new ConfigWriter(configReader, true);
		assertNotNull(configWriter.getRootElement());
	}
	
	@Test(expected=ConfigurationException.class)
	public void testSaveXmlError1() throws ConfigurationException, FileNotFoundException {
		ConfigReader configReader = new ConfigReader(file);
		ConfigWriter configWriter = new ConfigWriter(configReader, true);
		ConfigReader mock = org.mockito.Mockito.mock(ConfigReader.class);
		Mockito.when(mock.getDocument()).thenReturn(configReader.getDocument());
		Mockito.when(mock.getConfigFile()).thenThrow(FileNotFoundException.class);
		configWriter.saveXml(mock, "Testing");
	}
	
	@Test(expected=ConfigurationException.class)
	public void testSaveXmlError2() throws ConfigurationException, FileNotFoundException {
		ConfigReader configReader = new ConfigReader(file);
		ConfigWriter configWriter = new ConfigWriter(configReader, true);
		File mock = org.mockito.Mockito.mock(File.class);
		Mockito.when(mock.getPath()).thenThrow(FileNotFoundException.class);
		configWriter.saveXml(mock, "Testing");
	}
	
	@AfterClass
	public static void deleteFile() {
		wrongFile.delete();
	}
}
