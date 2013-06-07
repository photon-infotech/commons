/**
 * 
 */
package com.photon.phresco.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.util.Utility;


public class SplunkLogger extends Logger{
	
	private static final String SPLUNK_DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss.SSSz";
	
	private String instanceName = null;
	
	/**
	 * constructor that takes the instance name
	 * @param name
	 */
	protected SplunkLogger(String name) {
		super(name);
		instanceName = System.getProperty("instance.name");
	}
	
	
	/**
	 * gets the instance of Splunk Logger with the provided instance name
	 * @param name
	 * @return
	 */
	public static SplunkLogger getSplunkLogger(String name){
		return (SplunkLogger)Logger.getLogger(name, new SplunkLog4JFactory());
	}
	
	
	
 	/**
	 * Method that converts the data and time in the format yyyy-MM-dd'T'hh:mm:ss.SSSz
	 * @return
	 */
	private String getCurrentTimeWithDate()  {
		String currentDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(SPLUNK_DATE_FORMAT);  
		currentDate = sdf.format(new Date());
		return currentDate;
	}
	
	/**
	 * constructs the message in the splunk format from the data received
	 * @param instanceName
	 * @param action
	 * @param message
	 * @param logLevel
	 * @return
	 */
	private String getMessage(String action,String logLevel,String... messages){
        StringBuffer sb = new StringBuffer();
        String messageInfo = "";
        if(messages != null && messages.length > 0){
        	messageInfo = Utility.convertToCommaDelimited(messages).replace(",,", ",");
        }
        sb.append("time="+getCurrentTimeWithDate()+",").append("logLevel="+logLevel+",").append("action="+action+",").
        	append("instanceName="+instanceName+",");
        if(StringUtils.isNotEmpty(messageInfo)) {
        	sb.append(messageInfo).toString();
        }
        return commaDelimatedString(sb.toString());
    }
	
	private String commaDelimatedString(String message) {
		if (message.length() > 0 && message.charAt(message.length()-1)==',') {
			message = message.substring(0, message.length()-1);
		}
		return message;
	}
	
	/**
	 * Log Debug method
	 * @param instanceName
	 * @param action
	 * @param message
	 */
    public void debug(String action,String... messages) {
        super.debug(getMessage(action,"Debug",messages));
    }
    
    
    /**
     * Log error method
     * @param instanceName
     * @param action
     * @param message
     */
    public void error(String action,String... messages) {
        super.error(getMessage(action,"Error",messages));
    }
    
    
    /**
     * Log fatal method
     * @param instanceName
     * @param action
     * @param message
     */
    public void fatal(String action,String... messages) {
        super.fatal(getMessage(action,"Fatal",messages));
    }
    
    
    /**
     * log info method
     * @param instanceName
     * @param action
     * @param message
     */
    public void info(String action,String... messages) {
        super.info(getMessage(action,"Info",messages));
    }
    
    
    /**
     * log warn method
     * @param instanceName
     * @param action
     * @param message
     */
    public void warn(String action,String... messages) {
        super.warn(getMessage(action,"Warn",messages));
    }
    
    /**
	 * Log Debug method
	 * @param instanceName
	 * @param action
	 * @param message
	 */
    public void debug(String action,Throwable t,String... messages) {
        super.debug(getMessage(action,"Debug",messages),t);
    }
    
    
    /**
     * Log error method
     * @param instanceName
     * @param action
     * @param message
     */
    public void error(String action,Throwable t,String... messages) {
        super.error(getMessage(action,"Error",messages),t);
    }
    
    
    /**
     * Log fatal method
     * @param instanceName
     * @param action
     * @param message
     */
    public void fatal(String action,Throwable t,String... messages) {
        super.fatal(getMessage(action,"Fatal",messages),t);
    }
    
    
    /**
     * log info method
     * @param instanceName
     * @param action
     * @param message
     */
    public void info(String action,Throwable t,String... messages) {
        super.info(getMessage(action,"Info",messages),t);
    }
    
    
    /**
     * log warn method
     * @param instanceName
     * @param action
     * @param message
     */
    public void warn(String action,Throwable t,String... messages) {
        super.warn(getMessage(action,"Warn",messages),t);
    }
   
}
