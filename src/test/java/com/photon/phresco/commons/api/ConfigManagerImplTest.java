package com.photon.phresco.commons.api;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.impl.ConfigManagerImpl;

public class ConfigManagerImplTest {
	ConfigManagerImpl configmanagerImpl = null;

	@Before
	public void setUp() throws ConfigurationException {
		File configFile = FileUtils.toFile(this.getClass().getResource("/phresco-unit.xml"));
		configmanagerImpl = new ConfigManagerImpl(configFile);
	}

	@Test
	public void getEnvironmentsTest() throws ConfigurationException {
		List<Environment> environments = configmanagerImpl.getEnvironments(Arrays.asList("dev"));
		Assert.assertEquals(1, environments.size());
	}

	
	@Test
	public void getEnvironmentTest() throws ConfigurationException {
		List<Environment> environments = configmanagerImpl.getEnvironments();
		Assert.assertEquals(3, environments.size());
		List<Environment> environmentsAlone = configmanagerImpl.getEnvironmentsAlone();
		Assert.assertEquals(3, environmentsAlone.size());
	}
	
	@Test
	public void deleleconfigbyMap() throws ConfigurationException {
		Map<String,List<String>> map = new HashMap<String, List<String>>();
		configmanagerImpl.deleteConfigurations(map);
		map.put("testing", Arrays.asList("Database"));
		Assert.assertEquals(1, configmanagerImpl.getConfigurations("testing", "Database").size());
	}
	
	@Test
	public void getconfigurationsTest() throws ConfigurationException, PhrescoException {
		List<Configuration> configuration = configmanagerImpl.getConfigurations("Production", "Server");
		Assert.assertEquals(1, configuration.size());
		List<Configuration> configurations = configmanagerImpl.getConfigurations("Production");
		Assert.assertEquals(2, configurations.size());
		Configuration config = configmanagerImpl.getConfiguration("Production", "Server", "serverconfig");
		Assert.assertEquals("Production", config.getEnvName());
	}
	
	@Test
	public void createConfigurationbyName() throws ConfigurationException {
		Properties propertiesEmail = new Properties();
		propertiesEmail.setProperty("port", "8596");
		propertiesEmail.setProperty("emailid", "test@gmail.com");
		propertiesEmail.setProperty("password", "test");
		propertiesEmail.setProperty("incoming_mail_server", "test");
		propertiesEmail.setProperty("incoming_mail_port", "test");
		propertiesEmail.setProperty("host", "test");
		propertiesEmail.setProperty("username", "test");
		Configuration config = new Configuration();
		config.setEnvName("Production");
		config.setName("testemail");
		config.setType("Email");
		config.setDesc("email added");
		config.setProperties(propertiesEmail);
		configmanagerImpl.createConfiguration("Production", config);
		Assert.assertEquals(1, configmanagerImpl.getConfigurations("Production", "Email").size());
		configmanagerImpl.createConfiguration("dev", Arrays.asList(config));
		Assert.assertEquals(2, configmanagerImpl.getConfigurations("dev", "Email").size());
	}
	
	@Test
	public void updateConfiguration() throws ConfigurationException {
		Properties propertiesEmail = new Properties();
		propertiesEmail.setProperty("port", "8596");
		propertiesEmail.setProperty("emailid", "testprod@gmail.com");
		propertiesEmail.setProperty("password", "prodtest");
		propertiesEmail.setProperty("incoming_mail_server", "tcp://");
		propertiesEmail.setProperty("incoming_mail_port", "2658");
		propertiesEmail.setProperty("host", "test");
		propertiesEmail.setProperty("username", "test");
		Configuration config = new Configuration();
		config.setEnvName("Production");
		config.setName("testemail");
		config.setType("Email");
		config.setDesc("email updated");
		config.setProperties(propertiesEmail);
		configmanagerImpl.updateConfiguration("Production", "testemail", config);
		Assert.assertEquals(1, configmanagerImpl.getConfigurations("Production", "Email").size());
	}
	
	@Test
	public void cloneConfiguration() throws PhrescoException, ConfigurationException {
		Environment env = new Environment();
		env.setDesc("sample env added");
		env.setName("cloneenv");

		Configuration config = new Configuration();
		config.setEnvName("testEnv");
		config.setName("cloneserver");
		config.setType("Server");
		config.setDesc("server added");

		Properties propProd = new Properties();
		propProd.setProperty("protocol", "http");
		propProd.setProperty("host", "localhost");
		propProd.setProperty("port", "8654");
		propProd.setProperty("admin_username", "");
		propProd.setProperty("admin_password", "");
		propProd.setProperty("remoteDeployment", "false");
		propProd.setProperty("certificate", "");
		propProd.setProperty("type", "Apache Tomcat");
		propProd.setProperty("version", "6.0.x");
		propProd.setProperty("deploy_dir", "C:\\apache-tomcat-7.0.14\\webapps");
		propProd.setProperty("context", "ser");
		configmanagerImpl.cloneEnvironment("Production", env);
		Assert.assertEquals(1, configmanagerImpl.getConfigurations("cloneenv", "Server").size());
	}
	
	@Test
	public void deleteConfiguration() throws ConfigurationException {
		Configuration config = new Configuration();
		config.setEnvName("Production");
		config.setName("db");
		config.setType("Database");
		config.setDesc("email updated");
		configmanagerImpl.deleteConfigurations(Arrays.asList(config));
		Assert.assertEquals(0, configmanagerImpl.getConfigurations("Production", "Database").size());
		
		configmanagerImpl.deleteConfigurations("dev", Arrays.asList("Email"));
		Assert.assertEquals(1, configmanagerImpl.getConfigurations("dev", "Email").size());
	}
	
	@Test
	public void addEnvironmentsTest() throws ConfigurationException {
			Environment env = new Environment();
			env.setDesc("sample env added");
			env.setName("testEnv");

			Configuration config = new Configuration();
			config.setEnvName("testEnv");
			config.setName("testserver");
			config.setType("Server");
			config.setDesc("server added");

			Properties propProd = new Properties();
			propProd.setProperty("protocol", "http");
			propProd.setProperty("host", "localhost");
			propProd.setProperty("port", "8654");
			propProd.setProperty("admin_username", "");
			propProd.setProperty("admin_password", "");
			propProd.setProperty("remoteDeployment", "false");
			propProd.setProperty("certificate", "");
			propProd.setProperty("type", "Apache Tomcat");
			propProd.setProperty("version", "6.0.x");
			propProd.setProperty("deploy_dir", "C:\\apache-tomcat-7.0.14\\webapps");
			propProd.setProperty("context", "serverprtod");
			config.setProperties(propProd);
			env.setConfigurations(Arrays.asList(config));
			configmanagerImpl.addEnvironments(Arrays.asList(env));
			Assert.assertEquals(1, configmanagerImpl.getEnvironments().size());
	}
	
	@Test
	public void updateEnvironmentTest() throws ConfigurationException {
		Environment env = new Environment();
		env.setDesc("sample env added");
		env.setName("testEnv");

		Configuration config = new Configuration();
		config.setEnvName("testEnv");
		config.setName("testserver");
		config.setType("Server");
		config.setDesc("server added");

		Properties propProd = new Properties();
		propProd.setProperty("protocol", "http");
		propProd.setProperty("host", "localhost");
		propProd.setProperty("port", "3030");
		propProd.setProperty("admin_username", "");
		propProd.setProperty("admin_password", "");
		propProd.setProperty("remoteDeployment", "false");
		propProd.setProperty("certificate", "");
		propProd.setProperty("type", "Apache Tomcat");
		propProd.setProperty("version", "7.0.x");
		propProd.setProperty("deploy_dir", "D:\\webapps");
		propProd.setProperty("context", "testserver");
		config.setProperties(propProd);
		env.setConfigurations(Arrays.asList(config));
		configmanagerImpl.updateEnvironment(env);
		Assert.assertEquals(1, configmanagerImpl.getEnvironments().size());
		
	}
	
	@Test
	public void deleteEnvironmentTest() throws ConfigurationException {
		configmanagerImpl.deleteEnvironment("testEnv");
		Assert.assertEquals(0, configmanagerImpl.getEnvironments().size());
	}
	
//	@Test(expected=ConfigurationException.class)
//	public void exceptionhandleTest()  {
//		File configFile = FileUtils.toFile(this.getClass().getResource("/phresco-unit1.xml"));
//		ConfigManagerImpl con = new ConfigManagerImpl(configFile);
//		Environment env = new Environment();
//		con.updateEnvironment(env);
//	}
}
