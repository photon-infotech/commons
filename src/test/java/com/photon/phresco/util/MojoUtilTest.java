package com.photon.phresco.util;

import java.io.File;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.plugins.util.MojoUtil;

public class MojoUtilTest {

	private static final String PHASE_RUNGAINST_SRC_START = "functional-test-webdriver";
	private String file = "src/test/java/com/photon/phresco/util/phresco-functional-test-info.xml";
	private String ENVIRONMENT_NAME = "environmentName";
	
	@Test
	public void getAllValuesTest() throws PhrescoException {
		MojoProcessor mojo = new MojoProcessor(new File(file));
		com.photon.phresco.plugins.model.Mojos.Mojo.Configuration config = mojo.getConfiguration(PHASE_RUNGAINST_SRC_START);
		
		Map<String, String> configs = MojoUtil.getAllValues(config);
		String environmentName = configs.get(ENVIRONMENT_NAME);
		Assert.assertEquals("production", environmentName);
	}
}
