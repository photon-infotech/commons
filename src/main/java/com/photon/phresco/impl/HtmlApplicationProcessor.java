package com.photon.phresco.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Assembly.FileSets.FileSet;
import com.photon.phresco.plugins.model.Assembly.FileSets.FileSet.Includes;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.plugins.util.WarConfigProcessor;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class HtmlApplicationProcessor implements ApplicationProcessor {

	@Override
	public void preCreate(ApplicationInfo appInfo) throws PhrescoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preUpdate(ApplicationInfo appInfo) throws PhrescoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postCreate(ApplicationInfo appInfo) throws PhrescoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postUpdate(ApplicationInfo appInfo, List<ArtifactGroup> artifactGroups) throws PhrescoException {
		File pomFile = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator
				+ Constants.POM_NAME);
		ProjectUtils projectUtils = new ProjectUtils();
		projectUtils.deletePluginExecutionFromPom(pomFile);
		if (CollectionUtils.isNotEmpty(artifactGroups)) {
			try {
				projectUtils.updatePOMWithPluginArtifact(pomFile, artifactGroups);
				projectUtils.deletePluginFromPom(pomFile);
				projectUtils.addServerPlugin(appInfo, pomFile);
				BufferedReader breader = projectUtils.ExtractFeature(appInfo);
			String line = "";
				while ((line = breader.readLine()) != null) {
					if (line.startsWith("[INFO] BUILD SUCCESS")) {
						readConfigJson(appInfo);
					}
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}

	@Override
	public void postConfiguration(ApplicationInfo appInfo)
			throws PhrescoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Configuration> preFeatureConfiguration(ApplicationInfo appInfo,
			String featureName) throws PhrescoException {
		try {
			File fXmlFile = new File("C:\\Documents and Settings\\loheswaran_g\\Desktop\\sample.xml");
			if(!fXmlFile.exists()) {
				return null;
			}
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getDocumentElement().getChildNodes();
			return getConfigurations(nodeList);
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		} catch (SAXException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}

	private List<Configuration> getConfigurations(NodeList nList) {
		List<Configuration> configurations = new ArrayList<Configuration>();
		for (int root = 0; root < nList.getLength(); root++) {
			Node nNode = nList.item(root);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) nNode;
				NodeList childNodes = element.getChildNodes();
				for (int child = 0; child	< childNodes.getLength(); child++) {
					Node ChildNode = childNodes.item(child);
					if (ChildNode.getNodeType() == Node.ELEMENT_NODE) {
						Element childElement = (Element) ChildNode;
						NodeList subChildNodes = childElement.getChildNodes();
						Configuration configuration = new Configuration();
						Properties properties = new Properties();
						String configType = childElement.getNodeName();
						for (int subChild = 0; subChild	< subChildNodes.getLength(); subChild++) {
							Node subChildNode = subChildNodes.item(subChild);
							if (subChildNode.getNodeType() == Node.ELEMENT_NODE) {
								Element subChildElement = (Element) subChildNode;
								String tagName = subChildElement.getTagName();
								configuration.setType(configType);
								properties.setProperty(tagName, subChildElement.getTextContent());
								configuration.setProperties(properties);
							}
						}
						configurations.add(configuration);
					}
				}
			}
		}
		return configurations;
	}
	
	private void getConfiguration() throws PhrescoException {
		FileReader reader = null;
		try {
			File jsonFile = new File("C:\\Documents and Settings\\suresh_ma\\workspace\\projects\\HTML5-html5multichanneljquerywidget\\src\\main\\webapp\\json\\config.json");
			reader = new FileReader(jsonFile);
			JsonParser parser = new JsonParser();
			Object obj = parser.parse(reader);
			JsonObject jsonObject =  (JsonObject) obj;
			Set<Entry<String,JsonElement>> entrySet = jsonObject.entrySet();
			Configuration configuration = new Configuration();
			Properties properties = new Properties();
			for (Entry<String, JsonElement> entry : entrySet) {
				String key = entry.getKey();
				JsonElement value = entry.getValue();
				JsonObject asJsonObj = value.getAsJsonObject();
				Set<Entry<String,JsonElement>> entrySet2 = asJsonObj.entrySet();
				for (Entry<String, JsonElement> entry2 : entrySet2) {
					String key2 = entry2.getKey();
					JsonElement value2 = entry2.getValue();
					properties.setProperty(key + "." + key2, value2.toString());
				}
			}
			configuration.setProperties(properties);
			Properties properties2 = configuration.getProperties();
			Set<Entry<Object,Object>> entrySet2 = properties2.entrySet();
			for (Entry<Object, Object> entry : entrySet2) {
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				System.out.println("key:::" + key);
				System.out.println("value:::" + value);
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	private void readConfigJson(ApplicationInfo appInfo) throws PhrescoException {
		FileReader reader;
		try {
			File componentDir = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + getFeaturePath(appInfo) + File.separator);
			File[] listFiles = componentDir.listFiles();
			List<JsonElement> jsonElements = new ArrayList<JsonElement>();
			if(listFiles.length > 0) {
				for (File file : listFiles) {
					String jsonFile = file.getPath() + File.separator + "config" + File.separator + Constants.CONFIG_JSON;
					reader = new FileReader(jsonFile);
					JsonParser parser = new JsonParser();
					Object obj = parser.parse(reader);
					JsonObject jsonObject =  (JsonObject) obj;
					JsonElement jsonElement = jsonObject.get(Constants.COMPONENTS);
					jsonElements.add(jsonElement);
				}
				writeJson(appInfo, jsonElements);
			}
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}
	
	private void writeJson(ApplicationInfo appInfo, List<JsonElement> compJsonElements) throws PhrescoException {
		
		File jsonDir = new File(Utility.getProjectHome() + 
				appInfo.getAppDirName() + File.separator + "src/main/webapp/json");
		if(!jsonDir.exists()) {
			return;
		}
		File configFile = new File(Utility.getProjectHome() + 
				appInfo.getAppDirName() + File.separator + "src/main/webapp/json" + File.separator + 
				Constants.CONFIG_JSON);

		JsonParser parser = new JsonParser();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonObject jsonObject = new JsonObject();
		JsonObject envObject = new JsonObject();

		if (!configFile.exists()) {
			jsonObject.addProperty("name", "Production");
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			for (JsonElement jsonElement : compJsonElements) {
				String jsonString = jsonElement.toString();
				sb.append(jsonString.substring(1, jsonString.length() - 1));
				sb.append(",");
			}
			String compConfig = sb.toString();
			compConfig = compConfig.substring(0, compConfig.length() - 1) + "}";
			jsonObject.add(Constants.COMPONENTS, parser.parse(compConfig));
			envObject.add("environments", parser.parse(Collections.singletonList(jsonObject).toString()));
		} else {
			FileReader reader = null;
			try {
				reader = new FileReader(configFile);
				Object obj = parser.parse(reader);
				envObject =  (JsonObject) obj;
				JsonArray environments = (JsonArray) envObject.get("environments");
				jsonObject = getProductionEnv(environments);
				JsonElement components = jsonObject.get(Constants.COMPONENTS);
				
				for (JsonElement compJsonElement : compJsonElements) {
					JsonObject allComponents = components.getAsJsonObject();
					Set<Entry<String,JsonElement>> entrySet = compJsonElement.getAsJsonObject().entrySet();
					Entry<String, JsonElement> entry = entrySet.iterator().next();
					String key = entry.getKey();
					if (allComponents.get(key) == null) {
						allComponents.add(key, entry.getValue());
					}
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		FileWriter writer = null;
		String json = gson.toJson(envObject);
		try {
			writer = new FileWriter(configFile);
			writer.write(json);
			writer.flush();
		} catch (IOException e) {
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}
	
	private JsonObject getProductionEnv(JsonArray environments) {
		for (JsonElement jsonElement : environments) {
			JsonElement envName = ((JsonObject)jsonElement).get("name");
			if ("Production".equals(envName.getAsString())) {
				return (JsonObject) jsonElement;
			}
		}
		return null;
	}

	@Override
	public void postFeatureConfiguration(ApplicationInfo appInfo, List<Configuration> configs, String featureName)
	    throws PhrescoException {
	    StringBuilder sb = new StringBuilder(Utility.getProjectHome())
	    .append(appInfo.getAppDirName())
	    .append(File.separator)
	    .append(getFeaturePath(appInfo))
	    .append(File.separator)
	    .append(featureName)
	    .append(File.separator)
	    .append("config/");
	    Gson gson = new Gson();
	    java.lang.reflect.Type jsonType = new TypeToken<Collection<Configuration>>(){}.getType();
	    String json = gson.toJson(configs, jsonType);
	    if(new File(sb.toString()).exists()) {
	        try {
	            //write converted json data to a file named "info.json"
	            FileWriter writer = new FileWriter(sb.toString() + "config.json");
	            writer.write(json);
	            writer.close();
	        } catch (IOException e) {
	            throw new PhrescoException(e);
	        }
	    }
	}
	
	private String getFeaturePath(ApplicationInfo appInfo) throws PhrescoException {
		String pomPath = Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + Constants.POM_NAME;
		try {
			PomProcessor processor = new PomProcessor(new File(pomPath));
			return processor.getProperty(Constants.POM_PROP_KEY_COMPONENTS_SOURCE_DIR);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	@Override
	public void preBuild(ApplicationInfo appInfo) throws PhrescoException {
	    try {
	        String baseDir = Utility.getProjectHome() + appInfo.getAppDirName();    
	        File pluginInfoFile = new File(baseDir + File.separator + Constants.PACKAGE_INFO_FILE);
	        MojoProcessor mojoProcessor = new MojoProcessor(pluginInfoFile);
	        Parameter defaultThemeParameter = mojoProcessor.getParameter(Constants.MVN_GOAL_PACKAGE, Constants.MOJO_KEY_DEFAULT_THEME);
	        if (defaultThemeParameter != null) {
	            StringBuilder json = new StringBuilder();
	            if (defaultThemeParameter != null) {
	                json.append("{\"theme\"")
	                .append(":")
	                .append("{")
	                .append("\"deafultTheme\"")
	                .append(":")
	                .append("\""+defaultThemeParameter.getValue()+"\"")
	                .append("}}");
	            }
	            StringBuilder sb = new StringBuilder(baseDir)
	            .append(File.separator)
	            .append("src")
	            .append(File.separator)
	            .append("config.json");
	            storeConfigObjAsJson(json.toString(), sb.toString());
	        }
	        
	        Parameter themesParameter = mojoProcessor.getParameter(Constants.MVN_GOAL_PACKAGE, Constants.MOJO_KEY_THEMES);
	        if (themesParameter != null) {
	            StringBuilder warConfigFilePath = new StringBuilder(baseDir)
	            .append(File.separator)
	            .append(".phresco")
	            .append(File.separator)
	            .append("war-config.xml");
	            File warConfigFile = new File(warConfigFilePath.toString());
	            WarConfigProcessor warConfigProcessor = new WarConfigProcessor(warConfigFile);
	            List<String> includes = new ArrayList<String>();
	            String value = themesParameter.getValue();
	            if (StringUtils.isNotEmpty(value)) {
	                includes.addAll(Arrays.asList(value.split(","))); 
	            }
	            setFileSetIncludes(warConfigProcessor, "themesIncludeFile", includes);
	        }
        } catch (PhrescoException e) {
            throw new PhrescoException(e);
        } catch (JAXBException e) {
            throw new PhrescoException(e);
        } catch (IOException e) {
            throw new PhrescoException(e);
        }
	}
	
	private void setFileSetIncludes(WarConfigProcessor warConfigProcessor, String fileSetId, List<String> toBeIncluded) throws PhrescoException {
        try {
            FileSet fileSet = warConfigProcessor.getFileSet(fileSetId);
            if (fileSet == null) {
                fileSet = new FileSet();
                fileSet.setDirectory("src/main/webapp/themes");
                fileSet.setOutputDirectory("src/main/webapp/themes");
                fileSet.setId(fileSetId);
            }
            
            if (fileSet.getIncludes() == null) {
                Includes includes = new Includes();
                fileSet.setIncludes(includes);
            }
            
            if (CollectionUtils.isNotEmpty(toBeIncluded)) {
            	fileSet.getIncludes().getInclude().clear();
                for (String include : toBeIncluded) {
                    fileSet.getIncludes().getInclude().add(include);
                }
            }
            warConfigProcessor.createFileSet(fileSet);
            warConfigProcessor.save();
        } catch (JAXBException e) {
            throw new PhrescoException();
        } 
    }
	
	@Override
	public void postBuild(ApplicationInfo appInfo) throws PhrescoException {
		// TODO Auto-generated method stub
		
	}
	
	private void storeConfigObjAsJson(String json, String jsonFile) throws PhrescoException {
	    try {
	        Gson gson = new Gson();
	        FileWriter writer = new FileWriter(jsonFile);
	        writer.write(json);
	        writer.close();
	    } catch (Exception e) {
	        throw new PhrescoException(e);
	    }
	}
}