package com.photon.phresco.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.xml.sax.SAXException;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.util.PomProcessor;

public class JmxFilesImpl implements DynamicParameter, Constants {

	private static final String JMX = ".jmx";
	private static final String SEP = "#SEP#";
	private static final String PATH = "#PATH#";

	@Override
	public PossibleValues getValues(Map<String, Object> paramsMap) throws IOException, ParserConfigurationException, SAXException, ConfigurationException, PhrescoException {
		PossibleValues possibleValues = new PossibleValues();
    	ApplicationInfo applicationInfo = (ApplicationInfo) paramsMap.get(KEY_APP_INFO);
    	String customTestAgainst = (String) paramsMap.get(KEY_CUSTOM_TEST_AGAINST);
    	String FORWARD_SLASH = "/";
    	
    	try {
			PomProcessor processor = new PomProcessor(getPOMFile(applicationInfo));
			String performDir = processor.getProperty(POM_PROP_KEY_PERFORMANCETEST_DIR);
			String jmxUploadDir = processor.getProperty(POM_PROP_KEY_PERFORMANCETEST_JMX_UPLOAD_DIR);
			
			
			StringBuilder againstTestDir = new StringBuilder(Utility.getProjectHome())
			.append(applicationInfo.getAppDirName())
			.append(performDir)
			.append(File.separator)
			.append(customTestAgainst)
			.append(File.separator);
			
			StringBuilder testsDir = new StringBuilder(Utility.getProjectHome())
			.append(applicationInfo.getAppDirName())
			.append(performDir)
			.append(File.separator)
			.append(customTestAgainst)
			.append(jmxUploadDir);
			
			File testDirectory = new File(testsDir.toString());
			List<String> filesList = new ArrayList<String>();
			if (testDirectory.exists()) {
				filesList = jmxFilter(testDirectory, filesList);
			}
			
			if (CollectionUtils.isNotEmpty(filesList)) {
				for (String file : filesList) {
		    		Value value = new Value();
		    		String[] split = file.split(PATH);
		    		String jmxName = split[0];
		    		String jmxPath = split[1].replace(File.separator, FORWARD_SLASH);
		    		jmxPath = jmxPath.replace(againstTestDir.toString().replace(File.separator, FORWARD_SLASH), "");
		    		jmxPath = jmxPath.substring(0, jmxPath.lastIndexOf(FORWARD_SLASH) + 1);
		    		String optionValue = jmxPath + SEP + jmxName;
		    		value.setValue(jmxName);
		    		value.setKey(optionValue);//key will be like (jmx path)#SEP#(jmx name)  eg.. tests/#SEP#test.jmx 
		    		possibleValues.getValue().add(value);
				}
			}
			
		} catch (Exception e) {
			
		}
    	
		return possibleValues;
	}

	private List<String> jmxFilter(File directory, List<String> files) {
		File[] childs = directory.listFiles();
		if (childs  != null && childs.length != 0) {
			for (File child : childs) {
				if (child.isDirectory()) {
					jmxFilter(child, files);//recursive call if the child is a directory
				} else if (child.getName().endsWith(JMX)) {
					files.add(child.getName() + PATH + child.getPath());
				}
			}
		}
		
		return files;
	}

	private File getPOMFile(ApplicationInfo appInfo) {
		StringBuilder builder = new StringBuilder(Utility.getProjectHome())
		.append(appInfo.getAppDirName())
		.append(File.separatorChar)
		.append(Utility.getPomFileName(appInfo));
		return new File(builder.toString());
	}
}
