package com.photon.phresco.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.photon.phresco.util.Constants;

public class FrameworkOptions implements Constants {
	
	private  Properties frameworkOptionsProp = new Properties();
    private  String fileName = "commons-messages.properties";
    static FrameworkOptions frmworkoption = new FrameworkOptions();
    
    public String getDataFromPropertyFile(String Key){ // Reading Data From Property File
    	InputStream stream = this.getClass().getClassLoader().getResourceAsStream(fileName);
    	try {
        	frameworkOptionsProp.load(stream);
        } catch (IOException e) {
        	
        }
        
    	return frameworkOptionsProp.getProperty(Key);
    }
    
	public enum Options {
		
		CODEVALIDATION(frmworkoption.getDataFromPropertyFile(ENABLE_CODE_VALIDATION), "codeValidationEnabled", true), 
		BUILD(frmworkoption.getDataFromPropertyFile(ENABLE_BUILD), "buildEnabled", true), 
		DEPLOY(frmworkoption.getDataFromPropertyFile(ENABLE_DEPLOY), "deployEnabled", false), 
		RUNAGAINSTSOURCE(frmworkoption.getDataFromPropertyFile(ENABLE_RUN_AGAINST_SOURCE),"runAgainstSourceEnabled", false), 
		UNIT(frmworkoption.getDataFromPropertyFile(ENABLE_UNIT_TEST),"unitTestEnabled", true), 
		FUNCTIONAL(frmworkoption.getDataFromPropertyFile(ENABLE_FUNCTIONAL_TEST),"functionalTestEnabled", true), 
		PERFORMANCE(frmworkoption.getDataFromPropertyFile(ENABLE_PERFORMANCE_TEST),"performanceTestEnabled", false), 
		LOAD(frmworkoption.getDataFromPropertyFile(ENABLE_LOAD_TEST),"loadTestEnabled", false), 
		CI(frmworkoption.getDataFromPropertyFile(ENABLE_CONTINUOUS_INTEGRATION),"continousIntegrationEnabled", true), 
		JSLIBRARIES(frmworkoption.getDataFromPropertyFile(ENABLE_JS_LIBRARIES),"JSLibrariesEnabled", false);
		
		private static final Map<String, Options> valueOptionsMap = new HashMap<String, FrameworkOptions.Options>(16);
		
		static {
			valueOptionsMap.put(CODEVALIDATION.getValue(), CODEVALIDATION);
			valueOptionsMap.put(BUILD.getValue(), BUILD);
			valueOptionsMap.put(DEPLOY.getValue(), DEPLOY);
			valueOptionsMap.put(RUNAGAINSTSOURCE.getValue(), RUNAGAINSTSOURCE);
			valueOptionsMap.put(UNIT.getValue(), UNIT);
			valueOptionsMap.put(FUNCTIONAL.getValue(), FUNCTIONAL);
			valueOptionsMap.put(PERFORMANCE.getValue(), PERFORMANCE);
			valueOptionsMap.put(LOAD.getValue(), LOAD);
			valueOptionsMap.put(CI.getValue(), CI);
			valueOptionsMap.put(JSLIBRARIES.getValue(), JSLIBRARIES);
		}
		
		String key;
		String value;
		Boolean flag;
		
		Options(String dispKey, String value, Boolean flag) {
			this.key = dispKey;
			this.value = value;
			this.flag = flag;
		}
		
		public String getKey() {
			return key;
		}
	
		public String getValue() {
			return value;
		}
		
		public Boolean getFlag() {
			return flag;
		}

		public static final String getEnum(String key) {
			Options value = valueOptionsMap.get(key);
			return value.getKey();
		}
	}
}