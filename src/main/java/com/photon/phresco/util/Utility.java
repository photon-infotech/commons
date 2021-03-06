/**
 * Phresco Commons
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photon.phresco.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.SequenceInputStream;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;

import java.util.Arrays;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.StreamConsumer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.BuildInfo;
import com.photon.phresco.commons.model.CIJob;
import com.photon.phresco.commons.model.ContinuousDelivery;
import com.photon.phresco.commons.model.ProjectDelivery;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.exception.PhrescoWebServiceException;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;


public final class Utility implements Constants {

    private static String systemTemp = null;
	private static final ArrayList<String> ERRORIDENTIFIERS = new ArrayList<String>();
    private static final Logger S_LOGGER  = Logger.getLogger(PhrescoWebServiceException.class);
    private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
    static boolean status = false;

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

	public static String getWorkingDirectoryPath(String directory) throws PhrescoException {
		return Utility.getProjectHome() + directory;
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
    
    public static PomProcessor getPomProcessor(ApplicationInfo appInfo) throws PhrescoException {
    	try {
    		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
    		builder.append(appInfo.getAppDirName());
    		builder.append(File.separatorChar);
    		builder.append(getPomFileName(appInfo));
    		S_LOGGER.debug("builder.toString() " + builder.toString());
    		File pomPath = new File(builder.toString());
    		S_LOGGER.debug("file exists " + pomPath.exists());
    		return new PomProcessor(pomPath);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
    }
    
    public static String getPomFileName(ApplicationInfo appInfo) {
    	StringBuilder path = new StringBuilder(Utility.getProjectHome());
		if (StringUtils.isNotEmpty(appInfo.getRootModule())) {
			path.append(appInfo.getRootModule()).append(File.separator);
		}
		path.append(appInfo.getAppDirName()).append(File.separator).append(appInfo.getPomFile());
    	File pomFile = new File(path.toString());
    	if(pomFile.exists()) {
    		return appInfo.getPomFile();
        } 
    	return Constants.POM_NAME;
    }
    
    public static String getPomFileNameFromRootModule(ApplicationInfo appInfo, String rootModule) {
    	StringBuilder path = new StringBuilder(Utility.getProjectHome());
		if (StringUtils.isNotEmpty(rootModule)) {
			path.append(rootModule).append(File.separator);
		}
		path.append(appInfo.getAppDirName()).append(File.separator).append(appInfo.getPomFile());
    	File pomFile = new File(path.toString());
    	if(pomFile.exists()) {
    		return appInfo.getPomFile();
        } 
    	return Constants.POM_NAME;
    }
    
    public static String getPhrescoPomFromWorkingDirectory(ApplicationInfo appInfo, File workingDirectory) {
    	File pomFile = new File(workingDirectory.getPath() +  File.separator + appInfo.getPhrescoPomFile());
    	if(pomFile.exists()) {
    		return appInfo.getPhrescoPomFile();
        } else {
        	pomFile = new File(workingDirectory.getPath() +  File.separator + appInfo.getPomFile());
			if (pomFile.exists()) {
				return appInfo.getPomFile();
			}
        }
    	return Constants.POM_NAME;
    }
    
    public static String getPhrescoPomFile(ApplicationInfo appInfo) {
    	StringBuilder path = new StringBuilder(Utility.getProjectHome());
		if (StringUtils.isNotEmpty(appInfo.getRootModule())) {
			path.append(appInfo.getRootModule()).append(File.separator);
		}
		path.append(appInfo.getAppDirName()).append(File.separator);
    	File pomFile = new File(path.toString() + appInfo.getPhrescoPomFile());
		if (pomFile.exists()) {
			return appInfo.getPhrescoPomFile();
		} else {
			pomFile = new File(path.toString() + appInfo.getPomFile());
			if (pomFile.exists()) {
				return appInfo.getPomFile();
			}
		}
    	return Constants.POM_NAME;
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
    	ResourcesPlugin plugin = ResourcesPlugin.getPlugin();
    	if(plugin != null) {
    		return getEclipseHome();
    	}
    	//String workspace = System.getProperty(PHRESCO_WORKSPACE);
    	//if (StringUtils.isNotEmpty(workspace)) {
    	//	return workspace;
    	//}
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
    
	private static String getEclipseHome() {
		IPath location = null ;
		String workingPath = "";
		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getSelection();
		if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
			Object[] selectedObjects = ((IStructuredSelection)selection).toArray();
			for (Object object : selectedObjects) {
				if(object instanceof IProject) {
					IProject iProject = (IProject) object;
					location = iProject.getLocation();
				} 
				if(object instanceof IJavaProject) {
					IJavaProject project = (IJavaProject) object;
					project.getJavaModel().getPath();
					location = project.getProject().getLocation();
				}
			} 
			String dir = location.toOSString();
			workingPath = StringUtils.removeEnd(dir, location.lastSegment());
		} else {
			String workingDir = ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString() + File.separatorChar + "projects";
			File filePath = new File(workingDir);
			if(!filePath.isDirectory()) {
				filePath.mkdir();
			}
			workingPath =  filePath.getPath() + File.separatorChar;
		}
		return workingPath;
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
    
    public static String getPythonHome() {
    	String phrescoHome = Utility.getPhrescoHome();
        StringBuilder builder = new StringBuilder(phrescoHome);
        builder.append(File.separator);
        builder.append(Constants.PROJECTS_WORKSPACE);
        builder.append(File.separator);
        builder.append(TOOLS_DIR);
        builder.append(File.separator);
        builder.append(PYTHON);
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
	
	public static boolean executeStreamconsumer(String command, String workingDir, String baseDir, String actionType){
		BufferedReader in = null;
		fillErrorIdentifiers();
		int ok = 0;
		try {
			final StringBuffer bufferErrBuffer = new StringBuffer();
			final StringBuffer bufferOutBuffer = new StringBuffer();
			Commandline commandLine = new Commandline(command);
			commandLine.setWorkingDirectory(workingDir);
			String processName = ManagementFactory.getRuntimeMXBean().getName();
    		String[] split = processName.split("@");
    		String processId = split[0].toString();
    		Utility.writeProcessid(baseDir, actionType, processId);
    		ok = CommandLineUtils.executeCommandLine(commandLine, new StreamConsumer() {
				public void consumeLine(String line) {
					System.out.println(line);
					status = true;
					if (isError(line)) {
						bufferErrBuffer.append(line);
					}
					if(line.startsWith("[ERROR]")) {
						status = false;
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
			if ( ok != 0 ) {
				status = false;
			}
			Utility.closeStream(in);
		}
		return status;
	}
	
	public static void writeProcessid(String baseDir, String key, String pid) {
		if(StringUtils.isEmpty(baseDir) || StringUtils.isEmpty(key)) {
			return;
		}
		File do_not_checkin = new File(baseDir + File.separator + DO_NOT_CHECKIN_DIRY);
		if(!do_not_checkin.exists()) {
			do_not_checkin.mkdirs();
		}
		File jsonFile = new File(do_not_checkin.getPath() + File.separator + "process.json");
		JSONObject jsonObject = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			if(jsonFile.exists()) {
				FileReader reader = new FileReader(jsonFile);
				jsonObject = (JSONObject)parser.parse(reader);
			}
			jsonObject.put(key, pid);
			FileWriter  writer = new FileWriter(jsonFile);
			writer.write(jsonObject.toString());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void killProcess(String baseDir, String actionType) throws PhrescoException {
		File do_not_checkin = new File(baseDir + File.separator + DO_NOT_CHECKIN_DIRY);
		File jsonFile = new File(do_not_checkin.getPath() + File.separator + "process.json");
		if(!jsonFile.exists()) {
			return;
		}
		try {
			JSONObject jsonObject = new JSONObject();
			JSONParser parser = new JSONParser();
			FileReader reader = new FileReader(jsonFile);
			jsonObject = (JSONObject)parser.parse(reader);
			Object processId = jsonObject.get(actionType);
			if (processId == null) {
				return;
			}
			if (System.getProperty(Constants.OS_NAME).startsWith(Constants.WINDOWS_PLATFORM)) {
				Runtime.getRuntime().exec("cmd /X /C taskkill /F /T /PID " + processId.toString());
			} else {
				Runtime.getRuntime().exec(Constants.JAVA_UNIX_PROCESS_KILL_CMD + processId.toString());
			}
			jsonObject.remove(actionType);
			FileWriter writer = new FileWriter(jsonFile);
			writer.write(jsonObject.toString());
			writer.close();
			reader.close();
			if(jsonObject.size() <= 0) {
				FileUtil.delete(jsonFile);
			}
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (ParseException e) {
			throw new PhrescoException(e);
		}
	}
	
	public static void executeStreamconsumer(String command, final FileOutputStream fos) {
		BufferedReader in = null;
		fillErrorIdentifiers();
		try {
			final StringBuffer bufferErrBuffer = new StringBuffer();
			final StringBuffer bufferOutBuffer = new StringBuffer();
			Commandline commandLine = new Commandline(command);
			CommandLineUtils.executeCommandLine(commandLine, new StreamConsumer() {
				public void consumeLine(String line) {
					System.out.println(line);
					try {
						fos.write(line.getBytes());
						fos.write("\n".getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (isError(line) == true) {
						bufferErrBuffer.append(line);
					}
					bufferOutBuffer.append(line);
				}
			}, new StreamConsumer() {
				public void consumeLine(String line) {
					System.out.println(line);
					try {
						fos.write(line.getBytes());
						fos.write("\n".getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
					bufferErrBuffer.append(line);
				}
			});
		} catch (CommandLineException e) {
			e.printStackTrace();
		} finally {
			Utility.closeStream(in);
		}
	}

	/**
	 * To write the console output only in the file. The console output will not be printed
	 * @param workingDir
	 * @param command
	 * @param fos
	 */
	public static void executeStreamconsumer(String workingDir, String command, final FileOutputStream fos) {
        BufferedReader in = null;
        fillErrorIdentifiers();
        try {
            final StringBuffer bufferErrBuffer = new StringBuffer();
            final StringBuffer bufferOutBuffer = new StringBuffer();
            Commandline commandLine = new Commandline(command);
            commandLine.setWorkingDirectory(workingDir);
            CommandLineUtils.executeCommandLine(commandLine, new StreamConsumer() {
                public void consumeLine(String line) {
//                    System.out.println(line);
                    if (fos != null) {
                        try {
                            fos.write(line.getBytes());
                            fos.write("\n".getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (isError(line) == true) {
                            bufferErrBuffer.append(line);
                        }
                        bufferOutBuffer.append(line);
                    }
                }
            }, new StreamConsumer() {
                public void consumeLine(String line) {
//                    System.out.println(line);
                    if (fos != null) {
                        try {
                            fos.write(line.getBytes());
                            fos.write("\n".getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        bufferErrBuffer.append(line);
                    }
                }
            });
        } catch (CommandLineException e) {
            e.printStackTrace();
        } finally {
            Utility.closeStream(in);
        }
    }
	
	public static void executeStreamconsumerFOS(String workingDir, String command, final FileOutputStream fos) {
        BufferedReader in = null;
        fillErrorIdentifiers();
        try {
            final StringBuffer bufferErrBuffer = new StringBuffer();
            final StringBuffer bufferOutBuffer = new StringBuffer();
            Commandline commandLine = new Commandline(command);
            commandLine.setWorkingDirectory(workingDir);
            CommandLineUtils.executeCommandLine(commandLine, new StreamConsumer() {
                public void consumeLine(String line) {
                    System.out.println(line);
                    if (fos != null) {
                        try {
                            fos.write(line.getBytes());
                            fos.write("\n".getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (isError(line) == true) {
                            bufferErrBuffer.append(line);
                        }
                        bufferOutBuffer.append(line);
                    }
                }
            }, new StreamConsumer() {
                public void consumeLine(String line) {
                    System.out.println(line);
                    if (fos != null) {
                        try {
                            fos.write(line.getBytes());
                            fos.write("\n".getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        bufferErrBuffer.append(line);
                    }
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
	
	public static void fillErrorIdentifiers() {
		ERRORIDENTIFIERS.add("Error");
		ERRORIDENTIFIERS.add("Parse error");
		ERRORIDENTIFIERS.add("Warning");
		ERRORIDENTIFIERS.add("Fatal error");
		ERRORIDENTIFIERS.add("Notice");
	}
	
	public static List<BuildInfo> getBuildInfos(File buildInfoFile) throws PhrescoException {
	    try {
	        return readBuildInfo(buildInfoFile);
	    } catch (IOException e) {
	        throw new PhrescoException(e);
	    }
	}

	private static List<BuildInfo> readBuildInfo(File path) throws IOException {
	    if (!path.exists()) {
	        return new ArrayList<BuildInfo>(1);
	    }

	    BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
	    Gson gson = new Gson();
	    Type type = new TypeToken<List<BuildInfo>>(){}.getType();

	    List<BuildInfo> buildInfos = gson.fromJson(bufferedReader, type);
	    bufferedReader.close();

	    return buildInfos;
	}

	public static BuildInfo getBuildInfo(int buildNumber, String buildInfoFileDirectory) throws PhrescoException {
	    List<BuildInfo> buildInfos = getBuildInfos(new File(buildInfoFileDirectory));
	    if (buildInfos != null) {
	        for (BuildInfo buildInfo : buildInfos) {
	            if (buildInfo.getBuildNo() == buildNumber) {
	                return buildInfo;
	            }
	        }
	    }

	    return null;
	}
	
	public static boolean isConnectionAlive(String protocol, String host, int port) {
        boolean isAlive = true;
        try {
            URL url = new URL(protocol, host, port, "");
            URLConnection connection = url.openConnection();
            connection.connect();
        } catch (Exception e) {
            isAlive = false;
        }
        return isAlive;
    }
	
	public static String convertToCommaDelimited(String[] list) {
        StringBuffer ret = new StringBuffer("");
        for (int i = 0; list != null && i < list.length; i++) {
            ret.append(list[i]);
            if (i < list.length - 1) {
                ret.append(',');
            }
        }
        return ret.toString();
    }
	
	public static void writeStreamAsFile(InputStream is, File file) throws PhrescoException {
		if(is == null) {
			return;
		}
		FileOutputStream fileOutStream = null;
		try {
            fileOutStream = new FileOutputStream(file);
            byte buf[] = new byte[1024];
            int len;
            while ((len = is.read(buf)) > 0) {
                fileOutStream.write(buf, 0, len);
            }
        } catch (IOException e) {
            throw new PhrescoException(e);
        } finally {
            Utility.closeStream(is);
            Utility.closeStream(fileOutStream);
        }
	}

	public static String getCiJobInfoPath(String appDir, String globalInfo, String status, String rootPath) throws PhrescoException {
		StringBuilder builder = new StringBuilder();
		String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootPath, "");
		if (StringUtils.isNotEmpty(appDir)) {
			builder.append(dotPhrescoFolderPath);
			builder.append(File.separator);
			builder.append(CI_INFO);
		} else if ((StringUtils.isEmpty(appDir)) && (WRITE.equals(status))) {
			builder.append(Utility.getProjectHome());
			builder.append(CI_INFO);
		} else if(StringUtils.isEmpty(appDir) && (StringUtils.isNotEmpty(globalInfo)) && (READ.equals(status))) {
			builder.append(dotPhrescoFolderPath);
			builder.append(File.separator);
			builder.append(CI_GLOBAL_INFO);
		}
		File ciJobInfoFile = new File(builder.toString());
		return ciJobInfoFile.getPath();
	}
	

public static String getCiJobInfoPath(String appDir, String globalInfo, String status) throws PhrescoException {
		String dotPhrescoFolderPath = "";
		StringBuilder builder = new StringBuilder();
		if (StringUtils.isNotEmpty(appDir)) {
			dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(Utility.getProjectHome().concat(appDir), "");
			builder.append(dotPhrescoFolderPath);
			builder.append(File.separator);
			builder.append(CI_INFO);
		} else if ((StringUtils.isEmpty(appDir)) && (WRITE.equals(status))) {
			builder.append(Utility.getProjectHome());
			builder.append(CI_INFO);
		} else if(StringUtils.isEmpty(appDir) && (!StringUtils.isEmpty(globalInfo)) && (READ.equals(status))) {
			dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(Utility.getProjectHome().concat(globalInfo), "");
			builder.append(dotPhrescoFolderPath);
			builder.append(File.separator);
			builder.append(CI_GLOBAL_INFO);
		}
		File ciJobInfoFile = new File(builder.toString());
		return ciJobInfoFile.getPath();
	}
	
	
	public static List<ProjectDelivery> getProjectDeliveries(File projectDeliveryFile) throws PhrescoException {
			FileReader ProjectDeliveryFileReader = null;
			BufferedReader br = null;
			List<ProjectDelivery> ProjectDelivery = null;
			try {
				if (!projectDeliveryFile.exists()) {
					return ProjectDelivery;
				}
				ProjectDeliveryFileReader = new FileReader(projectDeliveryFile);
				br = new BufferedReader(ProjectDeliveryFileReader);
				Type type = new TypeToken<List<ProjectDelivery>>() {
				}.getType();
				Gson gson = new Gson();
				ProjectDelivery = gson.fromJson(br, type);
			} catch (Exception e) {
				e.printStackTrace();
				throw new PhrescoException(e);
			} finally {
				Utility.closeStream(br);
				Utility.closeStream(ProjectDeliveryFileReader);
			}
			return ProjectDelivery;
		}
	 
	 public static List<CIJob> getJobs(String continuousName, String projectId, List<ProjectDelivery> ciJobInfo) {
			ContinuousDelivery specificContinuousDelivery = getContinuousDelivery(projectId, continuousName, ciJobInfo);
			if (specificContinuousDelivery != null) {
				return specificContinuousDelivery.getJobs();
			}
	 		return null;
	 	}
	 
	public static ContinuousDelivery getContinuousDelivery(String projectId, String name, List<ProjectDelivery> projectDeliveries) {
		ProjectDelivery projectDelivery = getProjectDelivery(projectId, projectDeliveries);
		if (projectDelivery != null) {
			List<ContinuousDelivery> continuousDeliveries = projectDelivery.getContinuousDeliveries();
			if (CollectionUtils.isNotEmpty(continuousDeliveries)) {
				return getContinuousDelivery(name, continuousDeliveries);
			}
		}
		return null;
	}
	
 	public static ProjectDelivery getProjectDelivery(String projId, List<ProjectDelivery> projectDeliveries) {
 		for(ProjectDelivery projectDelivery : projectDeliveries) {
 			if (StringUtils.isNotEmpty(projId) && projId.equals(projectDelivery.getId())) {
 				return projectDelivery;
 			}
 		}
 		return null;
 	}
 	
 	public static ContinuousDelivery getContinuousDelivery(String name, List<ContinuousDelivery> continuousDeliveries) {
 		for(ContinuousDelivery continuousDelivery : continuousDeliveries) {
 			if (StringUtils.isNotEmpty(name) && continuousDelivery.getName().equals(name)) {
 				return continuousDelivery;
 			}
 		}
 		return null;
 	}
 	
 	public static ProjectInfo getProjectInfo(String appDirPath, String module) throws PhrescoException {
 		BufferedReader reader = null;
		Gson gson = new Gson();
		ProjectInfo projectInfo = null;
		File[] split_phresco = null;
		File[] split_src = null;
		File[] dotPhrescoFolders = null;
		File dotAppDir = null;
		File srcAppDir = null;
 		try {
 			File appDir = new File(appDirPath);
 			String name = appDir.getName();
 			String[] split = appDirPath.split("###");
 			if(split.length>1) {
				for (int i = 0; i < split.length;) {
					appDir = new File(split[0]);
					name = split[1];
					break;
				}
 			}
 			
 			
 			if (StringUtils.isNotEmpty(module)) {
 				File appDirT = new File(appDir + File.separator + module);
 				dotPhrescoFolders = appDirT.listFiles(new PhrescoFileNameFilter(DOT_PHRESCO_FOLDER));
 			} else {
			dotPhrescoFolders = appDir.listFiles(new PhrescoFileNameFilter(DOT_PHRESCO_FOLDER));
 			}
			if (ArrayUtils.isEmpty(dotPhrescoFolders)) {
				if (StringUtils.isNotEmpty(module)) {
					dotAppDir = new File(appDir + File.separator + name + SUFFIX_PHRESCO + File.separator
							+ module);
				} else {
					dotAppDir = new File(appDir + File.separator + name + SUFFIX_PHRESCO);
				}
				split_phresco = dotAppDir.listFiles(new PhrescoFileNameFilter(DOT_PHRESCO_FOLDER));
				if (ArrayUtils.isEmpty(split_phresco)) {
					if (StringUtils.isNotEmpty(module)) {
						srcAppDir = new File(appDir + File.separator + name + File.separator
								+ module);
					} else {
						srcAppDir = new File(appDir + File.separator + name);
					}
					split_src = srcAppDir.listFiles(new PhrescoFileNameFilter(DOT_PHRESCO_FOLDER));
				}
			}

			if (!ArrayUtils.isEmpty(dotPhrescoFolders)) {
				File[] dotProjectFiles = dotPhrescoFolders[0].listFiles(new PhrescoFileNameFilter(PROJECT_INFO_FILE));
				if (!ArrayUtils.isEmpty(dotProjectFiles)) {
					reader = new BufferedReader(new FileReader(dotProjectFiles[0]));
					projectInfo = gson.fromJson(reader, ProjectInfo.class);
				}
			}
			if (!ArrayUtils.isEmpty(split_phresco)) {
				File[] splitDotProjectFiles = split_phresco[0].listFiles(new PhrescoFileNameFilter(PROJECT_INFO_FILE));
				if (!ArrayUtils.isEmpty(splitDotProjectFiles)) {
					reader = new BufferedReader(new FileReader(splitDotProjectFiles[0]));
					projectInfo = gson.fromJson(reader, ProjectInfo.class);
				}
			}
			if (!ArrayUtils.isEmpty(split_src)) {
				File[] splitSrcDotProjectFiles = split_src[0].listFiles(new PhrescoFileNameFilter(PROJECT_INFO_FILE));
				if (!ArrayUtils.isEmpty(splitSrcDotProjectFiles)) {
					reader = new BufferedReader(new FileReader(splitSrcDotProjectFiles[0]));
					projectInfo = gson.fromJson(reader, ProjectInfo.class);
				}
			}
			return projectInfo;
		} catch (JsonSyntaxException e) {
			throw new PhrescoException(e);
		} catch (JsonIOException e) {
			throw new PhrescoException(e);
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		} finally {
			closeReader(reader);
		}
	}
	
	public static String getProjectInfoPath(String appDirPath, String module) throws PhrescoException {
		try {
			File appDir = new File(appDirPath);
			File[] split_phresco = null;
			File[] split_src = null;
			File[] dotPhrescoFolders = null;
			File dotAppDir = null;
			File srcAppDir = null;
			String projInfoPath = "";
			
			if (StringUtils.isNotEmpty(module)) {
 				File appDirT = new File(appDir + File.separator + module);
 				dotPhrescoFolders = appDirT.listFiles(new PhrescoFileNameFilter(DOT_PHRESCO_FOLDER));
 			} else {
			dotPhrescoFolders = appDir.listFiles(new PhrescoFileNameFilter(DOT_PHRESCO_FOLDER));
 			}
			
			if (ArrayUtils.isEmpty(dotPhrescoFolders)) {
				if (StringUtils.isNotEmpty(module)) {
					dotAppDir = new File(appDir + File.separator + appDir.getName() + SUFFIX_PHRESCO + File.separator
							+ module);
				} else {
					dotAppDir = new File(appDir + File.separator + appDir.getName() + SUFFIX_PHRESCO);
				}
				split_phresco = dotAppDir.listFiles(new PhrescoFileNameFilter(DOT_PHRESCO_FOLDER));
				if (ArrayUtils.isEmpty(split_phresco)) {
					if (StringUtils.isNotEmpty(module)) {
						srcAppDir = new File(appDir + File.separator + appDir.getName() + File.separator
								+ module);
					} else {
						srcAppDir = new File(appDir + File.separator + appDir.getName());
					}
					split_src = srcAppDir.listFiles(new PhrescoFileNameFilter(DOT_PHRESCO_FOLDER));
				}
			}

			if (!ArrayUtils.isEmpty(dotPhrescoFolders)) {
				File[] dotProjectFiles = dotPhrescoFolders[0].listFiles(new PhrescoFileNameFilter(PROJECT_INFO_FILE));
				if (!ArrayUtils.isEmpty(dotProjectFiles)) {
					projInfoPath =  dotProjectFiles[0].getPath();
				}
			}
			if (!ArrayUtils.isEmpty(split_phresco)) {
				File[] splitDotProjectFiles = split_phresco[0].listFiles(new PhrescoFileNameFilter(PROJECT_INFO_FILE));
				if (!ArrayUtils.isEmpty(splitDotProjectFiles)) {
					projInfoPath =  splitDotProjectFiles[0].getPath();
				}
			}
			if (!ArrayUtils.isEmpty(split_src)) {
				File[] splitSrcDotProjectFiles = split_src[0].listFiles(new PhrescoFileNameFilter(PROJECT_INFO_FILE));
				if (!ArrayUtils.isEmpty(splitSrcDotProjectFiles)) {
					projInfoPath =  splitSrcDotProjectFiles[0].getPath();
				}
			}
			return projInfoPath;
		} catch (JsonSyntaxException e) {
			throw new PhrescoException(e);
		} catch (JsonIOException e) {
			throw new PhrescoException(e);
		}
	}
	
	public static String getDotPhrescoFolderPath(String appDirPath, String module) throws PhrescoException {
		try {
			File appDir = new File(appDirPath);
			File[] split_phresco = null;
			File[] split_src = null;
			File[] dotPhrescoFolders = null;
			File dotAppDir = null;
			File srcAppDir = null;
			String dotPhrescoPath = "";
			
			if (StringUtils.isNotEmpty(module)) {
 				File appDirT = new File(appDir + File.separator + module);
 				dotPhrescoFolders = appDirT.listFiles(new PhrescoFileNameFilter(DOT_PHRESCO_FOLDER));
 			} else {
			dotPhrescoFolders = appDir.listFiles(new PhrescoFileNameFilter(DOT_PHRESCO_FOLDER));
 			}
			if (ArrayUtils.isEmpty(dotPhrescoFolders)) {
				if (StringUtils.isNotEmpty(module)) {
					dotAppDir = new File(appDir + File.separator + appDir.getName() + SUFFIX_PHRESCO + File.separator
							+ module);
				} else {
					dotAppDir = new File(appDir + File.separator + appDir.getName() + SUFFIX_PHRESCO);
				}
				split_phresco = dotAppDir.listFiles(new PhrescoFileNameFilter(DOT_PHRESCO_FOLDER));
				if (ArrayUtils.isEmpty(split_phresco)) {
					if (StringUtils.isNotEmpty(module)) {
						srcAppDir = new File(appDir + File.separator + appDir.getName() + File.separator
								+ module);
					} else {
						srcAppDir = new File(appDir + File.separator + appDir.getName());
					}
					split_src = srcAppDir.listFiles(new PhrescoFileNameFilter(DOT_PHRESCO_FOLDER));
				}
			}

			if (!ArrayUtils.isEmpty(dotPhrescoFolders)) {
				dotPhrescoPath =  dotPhrescoFolders[0].getPath();
			}
			if (!ArrayUtils.isEmpty(split_phresco)) {
				dotPhrescoPath = split_phresco[0].getPath();
			}
			if (!ArrayUtils.isEmpty(split_src)) {
				dotPhrescoPath = split_src[0].getPath();
			}
			return dotPhrescoPath;
			
		} catch (JsonSyntaxException e) {
			throw new PhrescoException(e);
		} catch (JsonIOException e) {
			throw new PhrescoException(e);
		}
		
	}
	
	public static File getSourceFolderLocation(ProjectInfo projectinfo, String rootPath, String moduleName) throws PhrescoException {
		File docFile = null;
		try {
			File pomFile = Utility.getPomFileLocation(rootPath, moduleName);
			PomProcessor pomPro = new PomProcessor(pomFile);
			String src = pomPro.getProperty(POM_PROP_KEY_SPLIT_SRC_DIR);
			String[] split = rootPath.split("###");
 			if(split.length>1) {
				for (int i = 0; i < split.length;) {
					rootPath = split[0];
					break;
				}
 			}
			ApplicationInfo applicationInfo = projectinfo.getAppInfos().get(0);
			if(CollectionUtils.isNotEmpty(applicationInfo.getModules()) && StringUtils.isNotEmpty(src)) {
			 docFile = new File(rootPath + File.separator + src + File.separator + moduleName);
			} else if(CollectionUtils.isNotEmpty(applicationInfo.getModules()) && StringUtils.isEmpty(src)) {
				 docFile = new File(rootPath + File.separator + moduleName);
			} else if (CollectionUtils.isEmpty(applicationInfo.getModules()) && StringUtils.isNotEmpty(src)) {
				 docFile = new File(rootPath + File.separator + src);
			} else {
				 docFile = new File(rootPath);
			}
			return docFile;
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}
	
	public static File getTestFolderLocation(ProjectInfo projectinfo, String rootPath, String moduleName) throws PhrescoException {
		File testFile = null;
		try {
			ApplicationInfo applicationInfo = projectinfo.getAppInfos().get(0);
			File pomFile = Utility.getPomFileLocation(rootPath, moduleName);
			PomProcessor pomPro = new PomProcessor(pomFile);
			String test = pomPro.getProperty(POM_PROP_KEY_SPLIT_TEST_DIR);
			String src = pomPro.getProperty(POM_PROP_KEY_SPLIT_SRC_DIR);
			if(CollectionUtils.isNotEmpty(applicationInfo.getModules()) && StringUtils.isNotEmpty(test)) {
				testFile = new File(rootPath + File.separator + test + File.separator + moduleName);
			} else if(CollectionUtils.isNotEmpty(applicationInfo.getModules()) && StringUtils.isNotEmpty(src)) {
				testFile = new File(rootPath + File.separator + src + File.separator + moduleName);
			} else if(CollectionUtils.isNotEmpty(applicationInfo.getModules()) && StringUtils.isEmpty(src)) {
				testFile = new File(rootPath + File.separator + moduleName);
			} else if (CollectionUtils.isEmpty(applicationInfo.getModules()) && StringUtils.isNotEmpty(test)) {
				testFile = new File(rootPath + File.separator + test);
			}else if (CollectionUtils.isEmpty(applicationInfo.getModules()) && StringUtils.isNotEmpty(src)) {
				testFile = new File(rootPath + File.separator + src);
			} else {
				String applicationPath = StringUtils.isNotEmpty(moduleName) ? rootPath.concat(File.separator).concat(moduleName) : rootPath;
				testFile = new File(applicationPath);
			}
			return testFile;
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}
	
	
	public static File getPomFileLocation(String appDirPath, String module) throws PhrescoException {
		try {
			File[] split_phresco = null;
			File[] dotPhrescoFolders = null;
			File dotAppDir = null;
			File srcAppDir = null;
			File pomFile = null;
			ProjectInfo projectInfo = getProjectInfo(appDirPath, module);
			if (projectInfo == null) {
				return pomFile;
			}
			ApplicationInfo appInfo = projectInfo.getAppInfos().get(0);
			
			File appDir = new File(appDirPath);
 			String name = appDir.getName();
 			
 			String[] split = appDirPath.split("###");
 			if(split.length>1) {
				for (int i = 0; i < split.length;) {
					appDir = new File(split[0]);
					name = split[1];
					break;
				}
 			}
 			
			if (StringUtils.isNotEmpty(module)) {
 				File appDirT = new File(appDir + File.separator + module);
 				dotPhrescoFolders = appDirT.listFiles(new PhrescoFileNameFilter(DOT_PHRESCO_FOLDER));
 			} else {
			dotPhrescoFolders = appDir.listFiles(new PhrescoFileNameFilter(DOT_PHRESCO_FOLDER));
 			}
			
			if (!ArrayUtils.isEmpty(dotPhrescoFolders)) {
				File parentFile = dotPhrescoFolders[0].getParentFile();
				pomFile= new File(parentFile +  File.separator + appInfo.getPhrescoPomFile());
				if(pomFile.exists()) {
					return pomFile;
				} 
				pomFile = new File(parentFile +  File.separator + appInfo.getPomFile());
				 if(pomFile.exists()) {
						return pomFile;
				} 
			}
			
			if (ArrayUtils.isEmpty(dotPhrescoFolders)) {
				dotAppDir = new File(appDir + File.separator + name + SUFFIX_PHRESCO);
				if (StringUtils.isNotEmpty(module)) {
					dotAppDir = new File(dotAppDir.getPath() + File.separator + module);
				} 
				split_phresco = dotAppDir.listFiles(new PhrescoFileNameFilter(DOT_PHRESCO_FOLDER));
				
				if (!ArrayUtils.isEmpty(split_phresco)) {
					File parentFile = split_phresco[0].getParentFile();
					pomFile = new File(parentFile +  File.separator + appInfo.getPhrescoPomFile());
					if(pomFile.exists()) {
						return pomFile;
					} 
					pomFile = new File(parentFile +  File.separator + appInfo.getPomFile());
					 if(pomFile.exists()) {
							return pomFile;
					} 
				} else {
					dotAppDir = new File(appDir + File.separator + name);
					if (StringUtils.isNotEmpty(module)) {
						dotAppDir = new File(dotAppDir.getPath() + File.separator + module);
					} 
					split_phresco = dotAppDir.listFiles(new PhrescoFileNameFilter(DOT_PHRESCO_FOLDER));
					
					if (!ArrayUtils.isEmpty(split_phresco)) {
						File parentFile = split_phresco[0].getParentFile();
						pomFile = new File(parentFile +  File.separator + appInfo.getPhrescoPomFile());
						if(pomFile.exists()) {
							return pomFile;
						} 
						pomFile = new File(parentFile +  File.separator + appInfo.getPomFile());
						 if(pomFile.exists()) {
								return pomFile;
						} 
					}
				}
				
				if (!pomFile.exists()) {
					if (StringUtils.isNotEmpty(module)) {
						srcAppDir = new File(appDir + File.separator + name  + File.separator
								+ module);
					} else {
						srcAppDir = new File(appDir + File.separator + name);
					}
						pomFile = new File(srcAppDir +  File.separator + appInfo.getPhrescoPomFile());
						if(pomFile.exists()) {
							return pomFile;
						} 
						pomFile = new File(srcAppDir +  File.separator + appInfo.getPomFile());
						 if(pomFile.exists()) {
								return pomFile;
						} 
				}
			}

		} catch (JsonSyntaxException e) {
			throw new PhrescoException(e);
		} catch (JsonIOException e) {
			throw new PhrescoException(e);
		}
		return null;
		
	}
	
	public static PomProcessor getPomProcessor(String rootModulePath , String subModule) throws PhrescoException {
		try {
			File pomFile = getPomFileLocation(rootModulePath, subModule);
			return new PomProcessor(pomFile);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}
	
	public static void sendTemplateEmail(String[] toAddr, String fromAddr,String user, String subject, String body,  String username,  String password, String host,String screen,String build) throws PhrescoException {

		List<String> lists = Arrays.asList(toAddr);
		if (fromAddr == null) {
			fromAddr = "phresco.do.not.reply@gmail.com";
			username = "phresco.do.not.reply@gmail.com";
			password = "phresco123";
		}
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
	    Session session = Session.getDefaultInstance(props,new LoginAuthenticator(username,password));
	    try {
	    	Message message = new MimeMessage(session);
	    	message.setFrom(new InternetAddress(fromAddr));
	    	List<Address> emailsList = getEmailsList(lists);
	    	Address[] dsf = new Address[emailsList.size()];
	    	message.setRecipients(Message.RecipientType.BCC,
	    	emailsList.toArray(dsf));
	    	message.setSubject(subject);
	       	DataSource source = new FileDataSource(body);
	    	BodyPart messageBodyPart = new MimeBodyPart();
	    	messageBodyPart.setDataHandler(new DataHandler(source));
	    	messageBodyPart.setFileName("Error.txt");
	    	Multipart multipart = new MimeMultipart();
	    	multipart.addBodyPart(messageBodyPart);
	    	MimeBodyPart messageBodyPart2 = new MimeBodyPart(); 
	    	DataSource source2 = new FileDataSource(screen); 
	    	messageBodyPart2.setDataHandler( new DataHandler(source2)); 
	    	messageBodyPart2.setFileName("Error.jpg"); 
	    	multipart.addBodyPart(messageBodyPart2);
	    	MimeBodyPart mainPart = new MimeBodyPart();
            String content="<b>Phresco framework error report<br><br>User Name:&nbsp;&nbsp;"+user+"<br> Email Address:&nbsp;&nbsp;"+fromAddr+"<br>Build No:&nbsp;&nbsp;"+build;
            mainPart.setContent(content, "text/html");
            multipart.addBodyPart(mainPart);
	    	message.setContent(multipart);
	    	Transport.send(message);
	    } catch (MessagingException e) {
	    	throw new PhrescoException(e);
	    }
	}
	
	public static void sendNewPassword(String[] toAddr, String fromAddr,String user, String subject, String body,  String username,  String password, String host,String screen,String build) throws PhrescoException {

		List<String> lists = Arrays.asList(toAddr);
		if (fromAddr == null) {
			fromAddr = "phresco.do.not.reply@gmail.com";
			username = "phresco.do.not.reply@gmail.com";
			password = "phresco123";
		}
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
	    Session session = Session.getDefaultInstance(props,new LoginAuthenticator(username,password));
	    try {
	    	Message message = new MimeMessage(session);
	    	message.setFrom(new InternetAddress(fromAddr));
	    	List<Address> emailsList = getEmailsList(lists);
	    	Address[] dsf = new Address[emailsList.size()];
	    	message.setRecipients(Message.RecipientType.BCC,
	    	emailsList.toArray(dsf));
	    	message.setSubject(subject);
	       	message.setContent(body, "text/html");
           	Transport.send(message);
	    } catch (MessagingException e) {
	    	throw new PhrescoException(e);
	    }
	}
	private static List<Address> getEmailsList(List<String> items)throws PhrescoException {
		List<Address> adresses = new ArrayList<Address>();
		Address address = null;
		if (CollectionUtils.isNotEmpty(items)) {
			for (String emailId : items) {
				try {
					address = new InternetAddress(emailId);
					adresses.add(address);
				} catch (AddressException e) {
					throw new PhrescoException(e);
				}
			}
		}
		return adresses;
	}
		
	public static String splitPathConstruction(String appDirName) throws PhrescoException, PhrescoPomException {
		try {
			File pomFileLocation = Utility.getPomFileLocation(Utility.getProjectHome() + File.separator + appDirName, "");
			PomProcessor pom = new PomProcessor(pomFileLocation);
			String dotPhrescoDir = pom.getProperty(POM_PROP_KEY_SPLIT_PHRESCO_DIR);
			String srcDir = pom.getProperty(POM_PROP_KEY_SPLIT_SRC_DIR);
			if(StringUtils.isNotEmpty(dotPhrescoDir)) {
				appDirName = appDirName + File.separator + dotPhrescoDir;
			} else if (StringUtils.isNotEmpty(srcDir)) {
				appDirName = appDirName + File.separator + srcDir;
			}
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
		return appDirName;
	}
	
	public static String constructSubPath(String appDirName, boolean flag, String type) throws FileNotFoundException {
		File rootDir = new File(Utility.getProjectHome() + appDirName);
		File dotPhresco = new File(rootDir, ".phresco");
		ApplicationInfo applicationInfo;
		if (dotPhresco.exists() && dotPhresco.isDirectory()) {
			applicationInfo = returnAppInfo(rootDir);
			if (StringUtils.isNotEmpty(applicationInfo.getPhrescoPomFile())) {
				return applicationInfo.getPhrescoPomFile();
			}
			return applicationInfo.getPomFile();
		} 
		dotPhresco = new File(rootDir, appDirName+"-phresco");
		
		if (dotPhresco.exists() && dotPhresco.isDirectory()) {
			applicationInfo = returnAppInfo(dotPhresco);
			if (StringUtils.isNotEmpty(applicationInfo.getPhrescoPomFile())) {
				if (!flag || type.equalsIgnoreCase("git")) {
					return appDirName+"-phresco" + File.separator + applicationInfo.getPhrescoPomFile();
				}
				return applicationInfo.getPhrescoPomFile();
			}
			if (!flag || type.equalsIgnoreCase("git")) {
				return appDirName + File.separator + applicationInfo.getPomFile();
			}
			return applicationInfo.getPomFile();
		}
		
		dotPhresco = new File(rootDir, appDirName);
		if (dotPhresco.exists() && dotPhresco.isDirectory()) {
			applicationInfo = returnAppInfo(dotPhresco);
			if (!flag || type.equalsIgnoreCase("git")) {
				if (StringUtils.isNotEmpty(applicationInfo.getPhrescoPomFile())) {
					return appDirName + File.separator + applicationInfo.getPhrescoPomFile();
				}
				return appDirName + File.separator + applicationInfo.getPomFile();
			}
			
			if (StringUtils.isNotEmpty(applicationInfo.getPhrescoPomFile())) {
				return applicationInfo.getPhrescoPomFile();
			}
			return applicationInfo.getPomFile();
		}
		
		return POM_NAME;
	}
	
	public static DOMSource createXML(String browsePath, String fileType) throws PhrescoException {
		try {
			File inputPath = new File(browsePath);
			if (!inputPath.isDirectory() || inputPath.isFile()) {
				return null;
			}
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			Element rootElement = document.createElement("root");
			document.appendChild(rootElement);

			Element mainFolder = document.createElement("Item");
			mainFolder.setAttribute("name", inputPath.getName());
			mainFolder.setAttribute("path", inputPath.toString());
			mainFolder.setAttribute("type", "Folder");
			rootElement.appendChild(mainFolder);

			listDirectories(mainFolder, document, inputPath, fileType);

			DOMSource source = new DOMSource(document);
			return source;
		} catch (DOMException e) {
			throw new PhrescoException(e);
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		}
	}

	private static void listDirectories(Element rootElement, Document document, File dir, String fileType) {
		for (File childFile : dir.listFiles()) {
			String name = childFile.getName();
			String path = childFile.getPath();
			if (childFile.isDirectory()) {
				Element childElement = document.createElement("Item");
				childElement.setAttribute("name", name);
				childElement.setAttribute("path", path);
				childElement.setAttribute("type", "Folder");
				rootElement.appendChild(childElement);

				listDirectories(childElement, document, childFile.getAbsoluteFile(), fileType);
			} else { 
				String fileName = childFile.getName();
				fileName = fileName.substring(fileName.lastIndexOf('.')+1,fileName.length());
				if(StringUtils.isEmpty(fileType) || fileName.equals(fileType)) {
					Element childElement = document.createElement("Item");
					childElement.setAttribute("name", name);
					childElement.setAttribute("path", path);
					childElement.setAttribute("type", "File");
					rootElement.appendChild(childElement);
				}
			}
		}
	}
	
	private static ApplicationInfo returnAppInfo(File dotPhresco) throws FileNotFoundException {
		dotPhresco = new File(dotPhresco, ".phresco/project.info");
		Gson gson = new Gson();
		BufferedReader reader = new BufferedReader(new FileReader(dotPhresco));
		ProjectInfo projectInfo = gson.fromJson(reader, ProjectInfo.class);
		return projectInfo.getAppInfos().get(0);
	}
	
	private static class PhrescoFileNameFilter implements FilenameFilter {
		 private String filter_;
		 public PhrescoFileNameFilter(String filter) {
			 filter_ = filter;
		 }
		 public boolean accept(File dir, String name) {
			 return name.endsWith(filter_);
		 }
	 }
}

   class LoginAuthenticator extends Authenticator {
	 PasswordAuthentication authentication = null;
	 public LoginAuthenticator(String username, String password) {
	 authentication = new PasswordAuthentication(username,password);
	 }
	 
	 @Override
	 protected PasswordAuthentication getPasswordAuthentication() {
	  return authentication;
	 }
   }
	