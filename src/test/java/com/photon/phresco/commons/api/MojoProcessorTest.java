package com.photon.phresco.commons.api;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.ApplicationHandler;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter;
import com.photon.phresco.plugins.model.Mojos.Mojo.Implementation;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.util.Utility;

public class MojoProcessorTest {
	MojoProcessor mojo = null;
	MojoProcessor mojoPro = null;
	
	@Before
	public void setUp() throws PhrescoException {
	File infoFile = FileUtils.toFile(this.getClass().getResource("/phresco-deploy-info.xml"));
	File appHandlerFile = FileUtils.toFile(this.getClass().getResource("/phresco-application-handler-info.xml"));
	mojo = new MojoProcessor(infoFile);
	mojoPro = new MojoProcessor(appHandlerFile);
	}
	
	@Test
	public void getConfigurationTest() throws PhrescoException {
		Configuration configuration = mojo.getConfiguration("deploy");
		Assert.assertEquals(6, configuration.getParameters().getParameter().size());
		Configuration config = mojoPro.getConfiguration("build");
		Assert.assertEquals(null, config);
	}
	
	@Test
	public void getapplicationHandlerTest() {
		ApplicationHandler applicationHandler = mojoPro.getApplicationHandler();
		Assert.assertEquals("com.photon.phresco.commons",applicationHandler.getGroupId());
	}
	
	@Test
	public void getImplDependencyTest() throws PhrescoException {
		Implementation impl = mojo.getImplementationDependency("deploy");
		Assert.assertEquals("com.photon.phresco.plugins", impl.getDependency().getGroupId());
		Implementation implementationDependency = mojo.getImplementationDependency("build");
		Assert.assertEquals(null, implementationDependency);
		Implementation implementationDep = mojoPro.getImplementationDependency("build");
		Assert.assertEquals(null, implementationDep);
	}
	
	@Test
	public void getParamtersTest() throws PhrescoException {
		List<Parameter> paramList = mojo.getParameters("deploy");
		Assert.assertEquals(6, paramList.size());
		List<Parameter> paramnone = mojo.getParameters("build");
		Assert.assertEquals(null, paramnone);
	}
	
	@Test
	public void getParamterTest() throws PhrescoException {
		Parameter param = mojo.getParameter("deploy", "executeSql");
		Assert.assertEquals("false", param.getRequired());
		Parameter paramnone = mojo.getParameter("build", "executeSql");
		Assert.assertEquals(null, paramnone);
	}
	
	@Test
	public void isGoalAvailableTest() throws PhrescoException {
		boolean goal = mojo.isGoalAvailable("deploy");
		Assert.assertEquals(true, goal);
		boolean goalnone = mojo.isGoalAvailable("deploy2");
		Assert.assertEquals(false, goalnone);
		boolean goalNone = mojoPro.isGoalAvailable("build");
		Assert.assertEquals(false, goalNone);
	}
	
//	@Test(expected=PhrescoException.class)
//	public void ioExceptionTest() throws PhrescoException {
//		File mock = org.mockito.Mockito.mock(File.class);
//		when(mock.exists()).thenThrow(PhrescoException.class);
//		MojoProcessor mojos = new MojoProcessor(mock);
//	}
	
	@Test
	public void saveTest() {
		try {
			mojo.save();
			Assert.assertEquals(true, true);
		} catch (PhrescoException e) {
			assertEquals("Expected Message", e.getMessage());
		}
	}
}
