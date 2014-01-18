package com.photon.phresco.commons.api;


import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.impl.BuildInfoParameterImpl;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.util.Utility;

public class BuildInfoParameterImplTest {
	
	Map<String, Object> map = new HashMap<String, Object>();
	BuildInfoParameterImpl buildInfoParmas = new BuildInfoParameterImpl();
	
	@BeforeClass
    public static void before() throws IOException {
		File projectFile = new File("src/test/resources/wp1-wordpress3.4.2");
		File destDirectory = new File(Utility.getProjectHome());
		FileUtils.copyDirectoryToDirectory(projectFile, destDirectory);
    }
	
	@Test
	public void buildParamsTest() throws PhrescoException {
		map.put("applicationInfo",getApplicationInfo());
		map.put("rootModule", "");
		PossibleValues values = buildInfoParmas.getValues(map);
		Assert.assertEquals(4, values.getValue().size());
		
	}
	
	private static ApplicationInfo getApplicationInfo() {
		ApplicationInfo info = new ApplicationInfo();
		info.setAppDirName("wp1-wordpress3.4.2");
		info.setCode("wp1-wordpress3.4.2");
		info.setId("6d009721-2d99-45a9-b37b-8f1060fcfb48");
		info.setCustomerIds(Collections.singletonList("photon"));
		info.setEmailSupported(false);
		info.setPhoneEnabled(false);
		info.setTabletEnabled(false);
		info.setDescription("wordpress project");
		info.setHelpText("Help");
		info.setName("wp-wp1-wordpress3.4.2");
		info.setPilot(false);
		info.setUsed(false);
		info.setDisplayName("TestProject");
		info.setPomFile("pom.xml");
		return info;
	}
}