package com.photon.phresco.logger;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public class PhrescoLoggerTest {

	@Test
	public void init() throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Constructor<PhrescoLogger> reflected = PhrescoLogger.class.getDeclaredConstructor();
		reflected.setAccessible(true);
		PhrescoLogger initialize = reflected.newInstance();
	}
	
	@Test
	public void testSetup() throws IOException {
		PhrescoLogger.setup();
	}
}
