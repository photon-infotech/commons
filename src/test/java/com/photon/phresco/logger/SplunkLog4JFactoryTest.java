package com.photon.phresco.logger;

import static org.junit.Assert.*;

import org.junit.Test;

public class SplunkLog4JFactoryTest {

	@Test
	public void test() {
		SplunkLog4JFactory init = new SplunkLog4JFactory();
		init.makeNewLoggerInstance("test");
	}

}
