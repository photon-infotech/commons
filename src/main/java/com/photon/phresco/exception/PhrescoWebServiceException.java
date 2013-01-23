/*
 * ###
 * Phresco Commons
 *
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.photon.phresco.exception;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;


public class PhrescoWebServiceException extends WebApplicationException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static InputStream is = null;
	private static Properties exceptionProp = null;
	private static final Logger S_LOGGER  = Logger.getLogger(PhrescoWebServiceException.class);
    private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();

	static {
		is = PhrescoWebServiceException.class.getClassLoader().getResourceAsStream("exception-messages.properties");
		exceptionProp = new Properties();
		try {
			exceptionProp.load(is);
		} catch (IOException e) {
		    if (isDebugEnabled) {
	         S_LOGGER.debug("Error in property loading");
	        }
		}
	}
	
	public PhrescoWebServiceException(Throwable e) {
		super(e);
	}
	
	public PhrescoWebServiceException(String errorNum, String type) {
        this(null, errorNum, type);
    }
	
	public PhrescoWebServiceException(Response response) {
		super(response);
	}
	
	public PhrescoWebServiceException(Throwable cause, String errorNum, String type) {
        super(cause, Response.status(Response.Status.NOT_FOUND).
        		type(MediaType.TEXT_PLAIN).entity(MessageFormat.
        		format(exceptionProp.getProperty(errorNum), type )).build());
        if (isDebugEnabled) {
            S_LOGGER.debug("************************************");
            S_LOGGER.debug("MessageFormat.format(exceptionProp.getProperty(errorNum), type )");
            S_LOGGER.debug("************************************");
        }
        
    }
}
