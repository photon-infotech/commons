package com.photon.phresco.logger;

import static org.junit.Assert.*;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerRepository;
import org.junit.Test;
import org.mockito.*;

public class SplunkLoggerTest {
	SplunkLogger splunkLogger = new SplunkLogger("test");
	String[] test = {"test1", "test2", "test3"};
	@Test
	public void testGetSplunkLogger() {
		SplunkLogger splunk = new SplunkLogger("test");
		splunk = SplunkLogger.getSplunkLogger("test");
		assertNotNull(splunk);
	}
	
	@Test(expected=NullPointerException.class)
	public void testDebug() {
		splunkLogger.debug("test", test);
		
	}
	
	@Test(expected=NullPointerException.class)
	public void testError() {
		splunkLogger.error("test", test);
	}
	
	@Test(expected=NullPointerException.class)
	public void testFatal() {
		splunkLogger.fatal("test", test);
	}
	
	@Test(expected=NullPointerException.class)
	public void testInfo() {
		splunkLogger.info("test", test);
	}
	
	@Test(expected=NullPointerException.class)
	public void testWarn() {
		splunkLogger.warn("test", test);
	}
	
	@Test(expected=NullPointerException.class)
	public void testDebugWithThrowable() {
		Throwable t = new Throwable("testThrowable");
		splunkLogger.debug("test", t, test);
	}
	
	@Test(expected=NullPointerException.class)
	public void testErrorWithThrowable() {
		Throwable t = new Throwable("testThrowable");
		splunkLogger.error("test", t, test);
	}
	
	@Test(expected=NullPointerException.class)
	public void testFatalWithThrowable() {
		Throwable t = new Throwable("testThrowable");
		splunkLogger.fatal("test", t, test);
	}
	
	@Test(expected=NullPointerException.class)
	public void testInfoWithThrowable() {
		Throwable t = new Throwable("testThrowable");
		splunkLogger.info("test", t, test);
	}
	
	@Test(expected=NullPointerException.class)
	public void testWarnWithThrowable() {
		Throwable t = new Throwable("testThrowable");
		splunkLogger.warn("test", t, test);
	}
}
