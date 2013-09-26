package com.photon.phresco.logger;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Test;

public class CRMHtmlFormatterTest {
	
	CRMHtmlFormatter crmFormatter = new CRMHtmlFormatter();
	String result = "";
	
	@Test
	public void testFormatLevelAll() {
		Level level = Level.ALL;
		LogRecord rec = new LogRecord(level, "test");
		result = crmFormatter.format(rec);
		assertNotNull(result);
	}
	
	@Test
	public void testFormatLevelWarning() {
		Level level = Level.WARNING;
		LogRecord rec = new LogRecord(level, "test");
		result = crmFormatter.format(rec);
		assertNotNull(result);
	}
	
	@Test
	public void testGetHead() {
		result = crmFormatter.getHead(null);
		assertNotNull(result);
	}
	
	@Test
	public void testGetTail() {
		result = crmFormatter.getTail(null);
		assertNotNull(result);
	}

}
