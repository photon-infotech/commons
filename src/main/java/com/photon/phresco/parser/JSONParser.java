package com.photon.phresco.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

import com.photon.phresco.configuration.Configuration;

public class JSONParser {
	
	
	private String globalConfigFilePath = "",featureConfigFilePath = "", globalConfigJsonData = "",featureConfigJsonData = "";
	Properties properties;
	private HashMap<String, String> globalConfigMap  = new HashMap<String ,String>();
	private HashMap<String, String> featureConfigMap = new HashMap<String ,String>();
	
	private String featureName ;
	
	JSONObject globalJObj =null ,featureJObj=null;
	
	public JSONParser() {
		
	}
	/**
	 * JSONParser single instance created by configuration json input stream
	 * 
	 * @param jsonStream
	 * @return
	 * @throws Exception
	 */
	public JSONParser(Properties properties,String featureName ,String featureConfigFilePath ,String globalConfigFilePath) throws Exception {
		
		this.properties = properties;
		this.featureName = featureName;
		this.globalConfigFilePath = globalConfigFilePath;
		this.featureConfigFilePath = featureConfigFilePath;
		File globalConfigJSONFile = new File(globalConfigFilePath);
		File featureConfigJSONFile =new File(featureConfigFilePath);
		if (globalConfigJSONFile.exists()) {
			globalConfigJsonData = initConfigJson(new FileInputStream(globalConfigJSONFile),globalConfigFilePath);
		}
		if (featureConfigJSONFile.exists()) {
			featureConfigJsonData =initConfigJson(new FileInputStream(featureConfigJSONFile),featureConfigFilePath);
		}
		
	}

	/**
	 * loads the configuration json as input stream
	 * 
	 * @param jsonStream
	 * @return 
	 * @throws Exception
	 */
	protected String initConfigJson(InputStream jsonStream ,String filePath) throws Exception {
		try {
			if (jsonStream == null) {
				jsonStream = new FileInputStream(filePath);
			}
//			jsonConfigData =  IOUtils.toString(jsonStream, "UTF-8");
			StringBuilder inputStringBuilder = new StringBuilder();
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(jsonStream, "UTF-8"));
	        String line = bufferedReader.readLine();
	        while(line != null){
	            inputStringBuilder.append(line);
	            inputStringBuilder.append('\n');
	            line = bufferedReader.readLine();
	        }
	        System.out.println("in initConfigJson returning the inputStringBuilder");
	       return inputStringBuilder.toString();

		} finally {
			try {
				jsonStream.close();
			} catch (IOException e) {
				throw e;
			}
		}
	}
	
	/**
	 * parse the configuration json
	 * 
	 * @param document
	 * @throws Exception 
	 */
	public HashMap<String, String> read() throws Exception {
		 System.out.println("entered in read() %%%%%%%%%");
		 System.out.println("globalConfigJsonData ============= :"+globalConfigJsonData);
	    globalJObj = new JSONObject(globalConfigJsonData);
	    System.out.println("globalJObj is assigned");
	    featureJObj= new JSONObject(featureConfigJsonData);
	    System.out.println("featureJObj is assigned"+featureJObj.toString());
	    //featureConfigMap = parseFeatureConfig(featureJObj);
	    if(!featureName.equalsIgnoreCase("application")){
		       featureConfigMap = parseFeatureConfig(featureJObj);
		       JSONObject component = globalJObj.getJSONObject("components");
			   JSONObject feature = (JSONObject) component.get(featureName);
			   globalConfigMap = parseGlobalConfig(feature);
			   
		    }else{
		    JSONObject config = globalJObj.getJSONObject("configuration");	
		    globalConfigMap = parseGlobalConfig(config);
		    }
	    //globalConfigMap = parseGlobalConfig(globalJObj);
		return globalConfigMap;
	}
	
	public HashMap<String,String> parseGlobalConfig(JSONObject json) throws JSONException, IOException{
		FileWriter file = new FileWriter(globalConfigFilePath);		
		
	    @SuppressWarnings("unchecked")
		Iterator<String> keys = json.keys();
	    while(keys.hasNext()){
	        String key = keys.next();
	        String val = null;
	        try{
	             JSONObject value = json.getJSONObject(key);
	             parseGlobalConfig(value);
	        }catch(Exception e){
	            val = json.getString(key);
	        }

	        if(val != null){
	        	globalConfigMap.put(key, val);
	        }
	    }
	    for(Entry<Object, Object> entry:properties.entrySet()){
	    	if(globalConfigMap.containsKey(entry.getKey())&& entry.getKey().equals("enabled")){
	   
	    		json.put((String) entry.getKey(), entry.getValue());
	    	 }else if(globalConfigMap.containsKey(entry.getKey())){
	    		 json.put((String) entry.getKey(), entry.getValue());
		        }
		    	
		    }if(!featureName.equals("application")){
		       globalJObj.remove(featureName);
		    }
		    
	    try {
			file.write(globalJObj.toString());					
    		
		} catch (IOException e) {
			e.printStackTrace();
		}
	    file.close();
	    return globalConfigMap;
	}
    
	public HashMap<String,String> parseFeatureConfig(JSONObject json) throws JSONException, IOException{
		FileWriter file = new FileWriter(featureConfigFilePath);
		System.out.println("The file value*********"+file.toString());
		JSONObject config = json.getJSONObject("configuration");
		System.out.println("The config value*********"+config.toString());
        @SuppressWarnings("unchecked")
		Iterator<String> keys = config.keys();
	    while(keys.hasNext()){
	        String key = keys.next();
	        String val = null;
	        try{
	             JSONObject value = config.getJSONObject(key);
	             parseFeatureConfig(value);
	        }catch(Exception e){
	            val = config.getString(key);
	        }

	        if(val != null){
	        	featureConfigMap.put(key, val);
	        }
	    }
	    for(Entry<Object, Object> entry:properties.entrySet()){
	    	if(featureConfigMap.containsKey(entry.getKey())){	   
	    		config.put((String) entry.getKey(), entry.getValue());
	    	 }
	    }	  
	    try {
			file.write(featureJObj.toString());					
    		
		} catch (IOException e) {
			e.printStackTrace();
		}
	    file.close();
	    return featureConfigMap;
	}
	
	// This method is using in preFeatureConfig of Android application processor
	  @SuppressWarnings("unchecked")
    public List<Configuration> getConfigFromJson(JSONObject jsonObject ,String jsonFilePath) throws IOException, JSONException 
			  {
	        	
	        	@SuppressWarnings("rawtypes")
				  List configs = new ArrayList();
			    
			      Configuration config = null;
			      File plistFile = new File(jsonFilePath);
			      if (plistFile.isFile())
			        config = new Configuration(plistFile.getName(), "features");
			      else {
			        return Collections.EMPTY_LIST;
			      }
	        	FileInputStream ins = new FileInputStream(jsonFilePath);
	   		    StringBuilder inputStringBuilder = new StringBuilder();
	   	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
	   	        String line = bufferedReader.readLine();
	   	        while(line != null){
	   	            inputStringBuilder.append(line);
	   	            inputStringBuilder.append('\n');
	   	            line = bufferedReader.readLine();
	   	        }
	   	        
	   	        Properties properties = new Properties();
	   	        String jsonConfigData = inputStringBuilder.toString(); 
	   	        JSONObject json = new JSONObject(jsonConfigData);
	   	        JSONObject configuration = json.getJSONObject("configuration");
	   	        System.out.println("parsing the json with configuration tag");
	   	        Iterator<String> keys =  configuration.keys();
	   	        while(keys.hasNext()){
	 	        String key = keys.next();
	 	        String val = null;
	 	         try{
	 	             JSONObject value = configuration.getJSONObject(key);
	 	             getConfigFromJson(value ,null);
	 	          }catch(Exception e){
	 	            val = configuration.getString(key);
	 	         }
	 	         System.out.println("Key=="+key + " " + "Value ==="+val);
	 	         properties.setProperty(key, val);
	 	         
	 	        
	   	        }
	   	        config.setProperties(properties);
	   	        configs.add(config);
				return configs;
	        	
			  }

}
