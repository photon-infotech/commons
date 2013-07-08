package com.photon.phresco.util;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.IosSdkUtil.MacSdkType;

public class IosSdkUtilTest {
	private static final String MAC = "mac";
	private static final String OS_NAME = "os.name";
	private static final int MIN_SIZE = 0;
	boolean isMacOS;
	
	@Before
	public void setUp() throws Exception {
		String osType = System.getProperty(OS_NAME).toLowerCase();
		isMacOS = osType.indexOf(MAC) >= 0;
	}
	
	@After
	public void tearDown() throws Exception {
		isMacOS = false;
	}
	
	@Test
	public void testMacSdksOfSimulator() throws PhrescoException {
		try {
			if (isMacOS) {
				List<String> macSdks = IosSdkUtil.getMacSdks(MacSdkType.iphonesimulator);
				Assert.assertTrue(macSdks.size() > 0);
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	@Test
	public void testMacSdksOfIOS() throws PhrescoException {
		try {
			if (isMacOS) {
				List<String> macSdks = IosSdkUtil.getMacSdks(MacSdkType.iphoneos);
				Assert.assertTrue(macSdks.size() > 0);
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	@Test
	public void testMacSdksOfMacOs() throws PhrescoException {
		try {
			if (isMacOS) {
				List<String> macSdks = IosSdkUtil.getMacSdks(MacSdkType.macosx);
				Assert.assertTrue(macSdks.size() > 0);
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	@Test
	public void testMacSdksVersionsOfSimulator() throws PhrescoException {
		try {
			if (isMacOS) {
				List<String> macSdksVersions = IosSdkUtil.getMacSdksVersions(MacSdkType.iphonesimulator);
				Assert.assertTrue(macSdksVersions.size() > 0);
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	@Test
	public void testMacSdksVersionsOfMacOs() throws PhrescoException {
		try {
			if (isMacOS) {
				List<String> macSdksVersions = IosSdkUtil.getMacSdksVersions(MacSdkType.macosx);
				Assert.assertTrue(macSdksVersions.size() > 0);
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	@Test
	public void testMacSdksVersionsOfIOS() throws PhrescoException {
		try {
			if (isMacOS) {
				List<String> macSdksVersions = IosSdkUtil.getMacSdksVersions(MacSdkType.iphoneos);
				Assert.assertTrue(macSdksVersions.size() > MIN_SIZE);
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
}
