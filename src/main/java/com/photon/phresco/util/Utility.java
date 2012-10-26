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
package com.photon.phresco.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.SequenceInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.StreamConsumer;

import com.photon.phresco.exception.PhrescoWebServiceException;

public final class Utility implements Constants {

    private static String systemTemp = null;
	private static final ArrayList<String> ERRORIDENTIFIERS = new ArrayList<String>();
    private static final Logger S_LOGGER  = Logger.getLogger(PhrescoWebServiceException.class);
    private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();


    private Utility(){
        //prevent instantiation outside
    }
    public static boolean isEmpty(String str) {
//		return (str == null || str.trim().length() == 0);
        return StringUtils.isEmpty(str);
    }

    /**
     * Closes inputsrteam.
     * @param inputStream
     */
    public static void closeStream(InputStream inputStream){
        try {
            if(inputStream!=null){
                inputStream.close();
            }
        } catch (IOException e) {

            if (isDebugEnabled) {
                S_LOGGER.debug("closeStream method execution fails");
            }
            //FIXME: should be logged.
        }
    }
	
	/**
     * Closes the SQL connection and logs the error message(TODO)
     * @param connection
     */
    public static void closeConnection(Connection connection) {
    	try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
            //FIXME: should be logged.
		}
    }


    /**
     * Closes inputsrteam.
     * @param inputStream
     */
    public static void closeStream(Reader inputStream){
        try {
            if(inputStream!=null){
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //FIXME: should be logged.
        }
    }

    /**
     * Closes output streams
     * @param outputStream
     */
    public static void closeStream(OutputStream outputStream){
        try {
            if(outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //FIXME: should be logged.
        }
    }

    public static String getPhrescoHome() {
        String phrescoHome = System.getenv(PHRESCO_HOME);
        if (phrescoHome == null) {
            phrescoHome = System.getProperty(USER_HOME);
        }
        StringBuilder sb = new StringBuilder();
		sb.append(File.separator);
		sb.append("bin");
		sb.append(File.separator);
		sb.append("..");
		int index = phrescoHome.lastIndexOf(sb.toString());
		if (index != -1) {
			phrescoHome = phrescoHome.substring(0, index);
		}
        FileUtils.mkdir(phrescoHome);
        return phrescoHome;
    }
    
    public static String getLocalRepoPath() {
        String phrescoHome = getPhrescoHome();
        StringBuilder builder = new StringBuilder(phrescoHome);
        builder.append(File.separator);
        builder.append(PROJECTS_WORKSPACE);
        builder.append(File.separator);
        builder.append("repo");
        builder.append(File.separator);
        FileUtils.mkdir(builder.toString());
        return builder.toString();
    }

    public static String getProjectHome() {
        String phrescoHome = getPhrescoHome();
        StringBuilder builder = new StringBuilder(phrescoHome);
        builder.append(File.separator);
        builder.append(PROJECTS_WORKSPACE);
        builder.append(File.separator);
        builder.append(PROJECTS_HOME);
        builder.append(File.separator);
        FileUtils.mkdir(builder.toString());
        return builder.toString();
    }

    public static String getPhrescoTemp() {
        String phrescoHome = getPhrescoHome();
        StringBuilder builder = new StringBuilder(phrescoHome);
        builder.append(File.separator);
        builder.append(PROJECTS_WORKSPACE);
        builder.append(File.separator);
        builder.append(PROJECTS_TEMP);
        builder.append(File.separator);
        FileUtils.mkdir(builder.toString());
        return builder.toString();
    }

    public static String getArchiveHome() {
        String phrescoHome = getPhrescoHome();
        StringBuilder builder = new StringBuilder(phrescoHome);
        builder.append(File.separator);
        builder.append(ARCHIVE_HOME);
        builder.append(File.separator);
        FileUtils.mkdir(builder.toString());
        return builder.toString();
    }

    public static String getSystemTemp() {
        if (systemTemp == null) {
            systemTemp = System.getProperty(JAVA_TMP_DIR);
        }

        return systemTemp;
    }

    public static String getJenkinsHome() {
    	String phrescoHome = Utility.getPhrescoHome();
        StringBuilder builder = new StringBuilder(phrescoHome);
        builder.append(File.separator);
        builder.append(Constants.PROJECTS_WORKSPACE);
        builder.append(File.separator);
        builder.append(TOOLS_DIR);
        builder.append(File.separator);
        builder.append(JENKINS_DIR);
        builder.append(File.separator);
        FileUtils.mkdir(builder.toString());
        return builder.toString();
    }

    public static String getJenkinsHomePluginDir() {
    	String jenkinsDataHome = System.getenv(JENKINS_HOME);
        StringBuilder builder = new StringBuilder(jenkinsDataHome);
        builder.append(File.separator);
        builder.append(PLUGIN_DIR);
        builder.append(File.separator);
        FileUtils.mkdir(builder.toString());
        return builder.toString();
    }
    
    public static String getJenkinsTemplateDir() {
    	String jenkinsDataHome = System.getenv(JENKINS_HOME);
        StringBuilder builder = new StringBuilder(jenkinsDataHome);
        builder.append(File.separator);
        builder.append(TEMPLATE_DIR);
        builder.append(File.separator);
        FileUtils.mkdir(builder.toString());
        return builder.toString();
    }

    public static void closeStream(FileWriter writer) {
    	try {
    		if (writer != null) {
    			writer.close();
    		}
    	} catch (IOException e) {
    		//FIXME : log exception
    	}
	}
	
	  public static void closeReader(BufferedReader reader) {
    	try {
    		if (reader != null) {
    			reader.close();
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
	}
    
    public static void closeWriter(BufferedWriter writer) {
    	try {
    		if (writer != null) {
    			writer.close();
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
	}
    
    public static BufferedReader executeCommand(String commandString, String workingDirectory) {
		InputStream inputStream = null;
		InputStream errorStream = null;
		SequenceInputStream sequenceInputStream = null;
		BufferedReader bufferedReader = null;
		try {
			Commandline cl = new Commandline(commandString);
			cl.setWorkingDirectory(workingDirectory);
			Process process = cl.execute();
			inputStream = process.getInputStream();
			errorStream = process.getErrorStream();
			sequenceInputStream = new SequenceInputStream(inputStream, errorStream);
			bufferedReader = new BufferedReader(new InputStreamReader(sequenceInputStream));
		} catch (CommandLineException e) {
			//FIXME : log exception
			e.printStackTrace();
		} 

		return bufferedReader;
	}
	
	  public static void executeStreamconsumer(String command) {
    	BufferedReader in = null;
    	fillErrorIdentifiers();
    	try {
    	final StringBuffer bufferErrBuffer = new StringBuffer();
		final StringBuffer bufferOutBuffer = new StringBuffer();
		Commandline commandLine = new Commandline(command);
		CommandLineUtils.executeCommandLine(commandLine, new StreamConsumer() {
			public void consumeLine(String line) {
				System.out.println(line);
				if (isError(line) == true) {
					bufferErrBuffer.append(line);
				}
				bufferOutBuffer.append(line);
			}
		}, new StreamConsumer() {
			public void consumeLine(String line) {
				System.out.println(line);
				bufferErrBuffer.append(line);
			}
		});
	} catch (CommandLineException e) {
		e.printStackTrace();
	} finally {
		Utility.closeStream(in);
	}
    }
    
	public static boolean isError(String line) {
		line = line.trim();
		for (int i = 0; i < ERRORIDENTIFIERS.size(); i++) {
			if (line.startsWith((String) ERRORIDENTIFIERS.get(i) + ":")
					|| line.startsWith("<b>" + (String) ERRORIDENTIFIERS.get(i)
							+ "</b>:")) {
				return true;
			}
		}
		return false;
	}
	
	private static void fillErrorIdentifiers() {
		ERRORIDENTIFIERS.add("Error");
		ERRORIDENTIFIERS.add("Parse error");
		ERRORIDENTIFIERS.add("Warning");
		ERRORIDENTIFIERS.add("Fatal error");
		ERRORIDENTIFIERS.add("Notice");
	}
    
}
