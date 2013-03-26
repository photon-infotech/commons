package com.photon.phresco.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.opensymphony.xwork2.util.ArrayUtils;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSWriterSettings;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSExpression;
import com.phloc.css.decl.CSSSelector;
import com.phloc.css.decl.CSSSelectorSimpleMember;
import com.phloc.css.decl.CSSStyleRule;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.ICSSExpressionMember;
import com.phloc.css.decl.ICSSSelectorMember;
import com.phloc.css.decl.ICSSTopLevelRule;
import com.phloc.css.writer.CSSWriterSettings;
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
import com.photon.phresco.util.ThemeBuilderReadCSS;
import com.photon.phresco.util.ThemeBuilderWriteCSS;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class HtmlApplicationProcessor extends AbstractApplicationProcessor {

	@Override
	public void postUpdate(ApplicationInfo appInfo, List<ArtifactGroup> artifactGroups, List<ArtifactGroup> deletedFeatures) throws PhrescoException {
		File pomFile = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator
				+ Constants.POM_NAME);
		ProjectUtils projectUtils = new ProjectUtils();
		projectUtils.deletePluginExecutionFromPom(pomFile);
		projectUtils.deletePluginFromPom(pomFile);
		projectUtils.addServerPlugin(appInfo, pomFile);
		if (CollectionUtils.isNotEmpty(artifactGroups)) {
			BufferedReader breader = null;
			try {
				projectUtils.updatePOMWithPluginArtifact(pomFile, artifactGroups);
				breader = projectUtils.ExtractFeature(appInfo);
				String line = "";
				while ((line = breader.readLine()) != null) {
					if (line.startsWith("[INFO] BUILD SUCCESS")) {
						readConfigJson(appInfo);
					}
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			} finally {
				try {
					if (breader != null) {
						breader.close();
					}
				} catch (IOException e) {
					throw new PhrescoException(e);
				}
			}
		}
	}

	@Override
	public List<Configuration> preConfiguration(ApplicationInfo appInfo, String componentName, String envName) throws PhrescoException {
		StringBuilder sb = new StringBuilder(Utility.getProjectHome())
		.append(appInfo.getAppDirName())
		.append(File.separator)
		.append("src")
		.append(File.separator)
		.append("main")
		.append(File.separator)
		.append("webapp")
		.append(File.separator)
		.append("json")
		.append(File.separator)
		.append(Constants.CONFIG_JSON);

		File jsonFile = new File(sb.toString());
		if(!jsonFile.exists()) {
			return null;
		}
		return getConfiguration(jsonFile, envName, componentName);
	}

	@Override
	public void postConfiguration(ApplicationInfo appInfo, List<Configuration> configurations)
	throws PhrescoException {
		Configuration configuration = configurations.get(0);
		JsonParser parser = new JsonParser();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<JsonElement> jsonElements = new ArrayList<JsonElement>();
		String envName = configuration.getEnvName();
		String configType = configuration.getType().toLowerCase();
		String configName = "";
		if(configuration.getType().equalsIgnoreCase("components")) {
			configName = configuration.getProperties().getProperty("featureName");
		} else {
			configName = configuration.getName();
		}
		String json = gson.toJson(configuration.getProperties());
		JsonElement propertyJsonElement = parser.parse(json);
		JsonObject propJsonObject = new JsonObject();
		propJsonObject.add(configName, propertyJsonElement);
		JsonObject typeJsonObject = new JsonObject();
		typeJsonObject.add(configType, propJsonObject);
		jsonElements.add(propJsonObject);
		writeJson(appInfo, jsonElements, envName, configType);
	}

	@Override
	public List<Configuration> preFeatureConfiguration(ApplicationInfo appInfo,
			String componentName) throws PhrescoException {
		try {
			StringBuilder sb = new StringBuilder(Utility.getProjectHome())
			.append(appInfo.getAppDirName())
			.append(getFeaturePath(appInfo))
			.append(File.separator)
			.append(componentName)
			.append(File.separator)
			.append("config")
			.append(File.separator)
			.append(Constants.CONFIG_JSON);

			File jsonFile = new File(sb.toString());
			if(!jsonFile.exists()) {
				return null;
			}
			return getConfiguration(jsonFile, componentName);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	private List<Configuration> getConfiguration(File jsonFile, String componentName) throws PhrescoException {
		FileReader reader = null;
		try {
			reader = new FileReader(jsonFile);
			JsonParser parser = new JsonParser();
			Object obj = parser.parse(reader);
			JsonObject jsonObject =  (JsonObject) obj;
			List<Configuration> configurations = new ArrayList<Configuration>();
			Configuration configuration = new Configuration();
			Properties properties = new Properties();
			Set<Entry<String,JsonElement>> entrySet = jsonObject.entrySet();
			for (Entry<String, JsonElement> entry : entrySet) {
				JsonElement value = entry.getValue();
				JsonObject asJsonObject = value.getAsJsonObject();
				Set<Entry<String,JsonElement>> entrySet2 = asJsonObject.entrySet();
				for (Entry<String, JsonElement> entry2 : entrySet2) {
					JsonElement value2 = entry2.getValue();
					JsonObject asJsonObject1 = value2.getAsJsonObject();
					Set<Entry<String,JsonElement>> entrySet3 = asJsonObject1.entrySet();
					for (Entry<String, JsonElement> entry3 : entrySet3) {
						String key = entry3.getKey();
						JsonElement value3 = entry3.getValue();
						properties.setProperty(key, value3.getAsString());
					}
				}
				configuration.setProperties(properties);
				configurations.add(configuration);
				return configurations;
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}

		return null;
	}

	private List<Configuration> getConfiguration(File jsonFile, String envName, String componentName) throws PhrescoException {
		FileReader reader = null;
		try {
			reader = new FileReader(jsonFile);
			JsonParser parser = new JsonParser();
			Object obj = parser.parse(reader);
			JsonObject jsonObject =  (JsonObject) obj;
			List<Configuration> configurations = new ArrayList<Configuration>();
			Configuration configuration = new Configuration();
			Properties properties = new Properties();
			Set<Entry<String,JsonElement>> entrySet = jsonObject.entrySet();
			for (Entry<String, JsonElement> entry : entrySet) {
				JsonElement value = entry.getValue();
				JsonArray jsonArray = value.getAsJsonArray();
				for (JsonElement jsonElement : jsonArray) {
					JsonObject asJsonObject = jsonElement.getAsJsonObject();
					JsonElement name = asJsonObject.get(Constants.NAME);
					if (name.getAsString().equals(envName)) {
						JsonElement components = asJsonObject.get(Constants.COMPONENTS);
						JsonObject asJsonObj = components.getAsJsonObject();
						Set<Entry<String, JsonElement>> parentEntrySet = asJsonObj.entrySet();
						for (Entry<String, JsonElement> entry1 : parentEntrySet) {
							String key = entry1.getKey();
							if (key.equalsIgnoreCase(componentName)) {
								JsonObject valueJsonObj = entry1.getValue().getAsJsonObject();
								Set<Entry<String,JsonElement>> valueEntrySet = valueJsonObj.entrySet();
								for (Entry<String, JsonElement> valueEntry : valueEntrySet) {
									String key1 = valueEntry.getKey();
									JsonElement value1 = valueEntry.getValue();
									properties.setProperty(key1, value1.getAsString());
								}
							}
						}
						configuration.setProperties(properties);
						configurations.add(configuration);
						return configurations;
					}
				}
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}

		return null;
	}

	private void readConfigJson(ApplicationInfo appInfo) throws PhrescoException {
		FileReader reader = null;
		try {
			File componentDir = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + getFeaturePath(appInfo) + File.separator);
			File[] listFiles = componentDir.listFiles();
			if(ArrayUtils.isEmpty(listFiles)) {
				return;
			}
			List<JsonElement> jsonElements = new ArrayList<JsonElement>();
			if(listFiles.length > 0) {
				for (File file : listFiles) {
					String jsonFile = file.getPath() + File.separator + "config" + File.separator + Constants.CONFIG_JSON;
					if (new File(jsonFile).exists()) {
						reader = new FileReader(jsonFile);
						JsonParser parser = new JsonParser();
						Object obj = parser.parse(reader);
						JsonObject jsonObject =  (JsonObject) obj;
						JsonElement jsonElement = jsonObject.get(Constants.COMPONENTS);
						jsonElements.add(jsonElement);
					}
				}
				writeJson(appInfo, jsonElements, "Production", Constants.COMPONENTS);
			}
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}

	private void writeJson(ApplicationInfo appInfo, List<JsonElement> compJsonElements, String environment, String type) throws PhrescoException {
		File jsonDir = new File(Utility.getProjectHome() + 
				appInfo.getAppDirName() + File.separator + "src/main/webapp/json");
		if (!jsonDir.exists()) {
			return;
		}
		
		if(CollectionUtils.isEmpty(compJsonElements)) {
			return;
		}
		
		File configFile = new File(getAppLevelConfigJson(appInfo.getAppDirName()));

		JsonParser parser = new JsonParser();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonObject jsonObject = new JsonObject();
		JsonObject envObject = new JsonObject();
		if (!configFile.exists()) {
			jsonObject.addProperty(Constants.NAME, environment);
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			for (JsonElement jsonElement : compJsonElements) {
				String jsonString = jsonElement.toString();
				sb.append(jsonString.substring(1, jsonString.length() - 1));
				sb.append(",");
			}
			String compConfig = sb.toString();
			compConfig = compConfig.substring(0, compConfig.length() - 1) + "}";
			jsonObject.add(type, parser.parse(compConfig));
			envObject.add(Constants.ENVIRONMENTS, parser.parse(Collections.singletonList(jsonObject).toString()));
		} else {
			FileReader reader = null;
			try {
				reader = new FileReader(configFile);
				Object obj = parser.parse(reader);
				envObject =  (JsonObject) obj;
				JsonArray environments = (JsonArray) envObject.get(Constants.ENVIRONMENTS);
				jsonObject = getProductionEnv(environments, environment);
				JsonElement components = null;
				for (JsonElement compJsonElement : compJsonElements) {
					JsonObject allComponents = null;
					if(jsonObject == null) {
						jsonObject = new JsonObject();
						JsonElement jsonElement = envObject.get(Constants.ENVIRONMENTS);
						String oldObj = jsonElement.toString().substring(1, jsonElement.toString().length()-1).concat(",");
						jsonObject.addProperty(Constants.NAME, environment);
						jsonObject.add(type, compJsonElement);
						String newObj = jsonObject.toString();
						envObject.add(Constants.ENVIRONMENTS, parser.parse(Collections.singletonList(oldObj + newObj).toString()));
					} else {
						components = jsonObject.get(type);
					}
					if (components == null) {
						jsonObject.add(type, compJsonElement);
					} else {
						allComponents = components.getAsJsonObject();
						Set<Entry<String,JsonElement>> entrySet = compJsonElement.getAsJsonObject().entrySet();
						Entry<String, JsonElement> entry = entrySet.iterator().next();
						String key = entry.getKey();
						//						if (allComponents.get(key) == null) {
						allComponents.add(key, entry.getValue());
						//						}
					}
				}

			} catch (FileNotFoundException e) {
				throw new PhrescoException(e);
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						throw new PhrescoException(e);
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

	private JsonObject getProductionEnv(JsonArray environments, String environment) {
		for (JsonElement jsonElement : environments) {
			JsonElement envName = ((JsonObject)jsonElement).get(Constants.NAME);
			if (environment.equals(envName.getAsString())) {
				return (JsonObject) jsonElement;
			}
		}
		return null;
	}

	@Override
	public void postFeatureConfiguration(ApplicationInfo appInfo, List<Configuration> configs, String featureName)
	throws PhrescoException {
		FileWriter writer = null;
		StringBuilder sb = new StringBuilder(Utility.getProjectHome())
		.append(appInfo.getAppDirName())
		.append(getFeaturePath(appInfo))
		.append(File.separator)
		.append(featureName)
		.append(File.separator)
		.append("config");
		if (new File(sb.toString()).exists()) {
			try {
				Gson gson = new Gson();
				String jsonFile = sb.toString() + File.separator + Constants.CONFIG_JSON;
				String json = gson.toJson(configs.get(0).getProperties());
				JsonParser parser = new JsonParser();
				JsonElement propertyJsonElement = parser.parse(json);
				JsonObject propJsonObject = new JsonObject();
				propJsonObject.add(featureName, propertyJsonElement);
				JsonObject jsonObject = new JsonObject();
				jsonObject.add(Constants.COMPONENTS, propJsonObject);
				writer = new FileWriter(jsonFile);
				json = gson.toJson(jsonObject);
				writer.write(json);
				writer.flush();
			} catch (IOException e) {
				throw new PhrescoException(e);
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
		FileReader reader = null;
		FileWriter writer = null;
		try {
			String baseDir = Utility.getProjectHome() + appInfo.getAppDirName();    
			File pluginInfoFile = new File(baseDir + File.separator + Constants.PACKAGE_INFO_FILE);
			MojoProcessor mojoProcessor = new MojoProcessor(pluginInfoFile);
			Parameter defaultThemeParameter = mojoProcessor.getParameter(Constants.MVN_GOAL_PACKAGE, Constants.MOJO_KEY_DEFAULT_THEME);
			String appLevelConfigJson = getAppLevelConfigJson(appInfo.getAppDirName());
			if (defaultThemeParameter != null && new File(appLevelConfigJson).exists()) {
				reader = new FileReader(appLevelConfigJson);
				JsonParser parser = new JsonParser();
				Object obj = parser.parse(reader);
				JsonObject jsonObject =  (JsonObject) obj;
				jsonObject.addProperty(Constants.MOJO_KEY_DEFAULT_THEME, defaultThemeParameter.getValue());
				Gson gson = new Gson();
				String json = gson.toJson(jsonObject);
				writer = new FileWriter(appLevelConfigJson);
				writer.write(json);
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
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}

	private String getAppLevelConfigJson(String appDirName) {
		StringBuilder sb =  new StringBuilder(Utility.getProjectHome())
		.append(appDirName)
		.append(File.separator)
		.append("src")
		.append(File.separator)
		.append("main")
		.append(File.separator)
		.append("webapp")
		.append(File.separator)
		.append("json")
		.append(File.separator)
		.append(Constants.CONFIG_JSON);

		return sb.toString();
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
	public List<String> themeBuilderList(ApplicationInfo appInfo)  throws PhrescoException {
		List<String> files = new ArrayList<String>();
		String cssFilesPath = getThemeBuilderPath(appInfo);
		File file = new File(cssFilesPath);
        File[] cssFiles = file.listFiles(new CSSFileFilter("css"));
        if(cssFiles != null && cssFiles.length > 0) {
        	for (File cssFile : cssFiles) {
        		files.add(cssFile.getName());
			}
        } 
		
		return files;

	}
	
	@Override
	public JSONObject themeBuilderEdit(ApplicationInfo appInfo, String file) throws PhrescoException {
		JSONObject finalObj = new JSONObject();
		try {
			String cssFilePath = getThemeBuilderPath(appInfo);
			StringBuilder filePath = new StringBuilder(cssFilePath);
			filePath.append(File.separator)
			.append(file);
			
			File cssFile = new File(filePath.toString());
			CascadingStyleSheet css = ThemeBuilderReadCSS.readCSS30(cssFile);
			JSONArray baseArray = new JSONArray();
			String element = "";
			if (css != null) {
				List<ICSSTopLevelRule> allRules = css.getAllRules();
				for (ICSSTopLevelRule icssTopLevelRule : allRules) {
					JSONObject baseObj = new JSONObject();
					JSONArray jsonarray = new JSONArray();
					 if (icssTopLevelRule instanceof  CSSStyleRule) {
						 CSSStyleRule styleRule = (CSSStyleRule) icssTopLevelRule;
						 List<CSSDeclaration> allDeclarations = styleRule.getAllDeclarations();
						 ICSSWriterSettings icssWriter = new CSSWriterSettings(ECSSVersion.CSS30);
						 element = styleRule.getSelectorAtIndex(0).getAsCSSString(icssWriter, 0);
				    	for (CSSDeclaration cssDeclaration : allDeclarations) {
				    		JSONObject json = new JSONObject();
							String property = cssDeclaration.getProperty();
							StringBuilder sb = new StringBuilder();
							CSSExpression expression = cssDeclaration.getExpression();
							List<ICSSExpressionMember> allMembers = expression.getAllMembers();
							String space = "";
							for (ICSSExpressionMember icssExpressionMember : allMembers) {
								sb.append(space);
								sb.append(icssExpressionMember.getAsCSSString(icssWriter, 0));
								space = " ";
							}
							json.put("property", property);
							json.put("value", sb.toString().replace("\"", ""));
							jsonarray.put(json);
						}
					 }
					 baseObj.put("selector", element);
					 baseObj.put("properties", jsonarray);
					 baseArray.put(baseObj);
				}
				finalObj.put("css", baseArray);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalObj;
	}
	
	private String getThemeBuilderPath (ApplicationInfo appInfo) throws PhrescoException {
		StringBuilder sb =  new StringBuilder(Utility.getProjectHome());
		try {
			sb.append(appInfo.getAppDirName());
			String themeBuilderPathFromPom = getThemeBuilderPathFromPom(appInfo);
			sb.append(themeBuilderPathFromPom);
		} catch (PhrescoPomException e) {
		}
		
		return sb.toString();
	}
	
	 public String getThemeBuilderPathFromPom(ApplicationInfo appinfo) throws PhrescoException, PhrescoPomException {
	        return Utility.getPomProcessor(appinfo.getAppDirName()).getProperty(Constants.POM_PROP_KEY_THEME_BUILDER);
	 }
	 
	 @Override
	 public boolean themeBuilderSave(ApplicationInfo appInfo, String jsonString) throws PhrescoException {
		 boolean success = true;
		 try {
			 StringBuilder sb =  new StringBuilder(Utility.getProjectHome());
			 sb.append(appInfo.getAppDirName());
			 String themeBuilderPathFromPom = getThemeBuilderPathFromPom(appInfo);
			 sb.append(themeBuilderPathFromPom)
			 .append(File.separator)
			 .append("style.css");
			 
			 
			 File cssFile = new File(sb.toString());
			 if (!cssFile.exists()) {
				 cssFile.createNewFile();
			 }

			 CascadingStyleSheet css = ThemeBuilderReadCSS.readCSS30(cssFile);

			 if (css != null) {
				List<ICSSTopLevelRule> allRules = css.getAllRules();
				for (ICSSTopLevelRule icssTopLevelRule : allRules) {
					css.removeRule(icssTopLevelRule);
				}
			 }		
			 
			 org.codehaus.jettison.json.JSONObject jsonObj = new org.codehaus.jettison.json.JSONObject(jsonString);
			 JSONArray jsonArray = jsonObj.getJSONArray("css");

			 for (int i=0; i < jsonArray.length(); i++) {
				 CSSStyleRule styleRule = new CSSStyleRule();
				 CSSSelector aSelector = new CSSSelector();

				 org.codehaus.jettison.json.JSONObject item = jsonArray.getJSONObject(i);
				 String selector = item.getString("selector");
				 ICSSSelectorMember icssmm = new CSSSelectorSimpleMember(selector);
				 aSelector.addMember(icssmm);
				 styleRule.addSelector(aSelector);

				 JSONArray propertiesArray = item.getJSONArray("properties");
				 for (int j=0; j < propertiesArray.length() ; j++) {
					 org.codehaus.jettison.json.JSONObject properties = propertiesArray.getJSONObject(j);
					 String property = properties.getString("property");
					 String value = properties.getString("value");
					 CSSExpression aExpression = new CSSExpression();
					 CSSDeclaration aDeclaration = new CSSDeclaration(property, aExpression, true);
					 aExpression.addString(value);
					 styleRule.addDeclaration(aDeclaration);
				 }
				 css.addRule(styleRule);
			 }
			 ThemeBuilderWriteCSS.writeCSS30(css, cssFile);
		 } catch (Exception e) {
			 success = false;
		}
		 return success;
	 }
	 
	 public class CSSFileFilter implements FilenameFilter {
	        private String filter_;
	        public CSSFileFilter(String filter) {
	            filter_ = filter;
	        }

	        public boolean accept(File dir, String name) {
	            return name.endsWith(filter_);
	        }
	    }
}