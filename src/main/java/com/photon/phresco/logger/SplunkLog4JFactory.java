package com.photon.phresco.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

public class SplunkLog4JFactory implements LoggerFactory{
	
	
	public Logger makeNewLoggerInstance(String arg0) {
		return new SplunkLogger(arg0);
	}  
    
}
