/**
 * Phresco Commons
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
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
package com.photon.phresco.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactGroup.Type;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.Utility;

/**
 * @author suresh_ma
 *
 */
public class WindowsApplicationProcessor extends AbstractApplicationProcessor implements Constants {

	@Override
	public void postUpdate(ApplicationInfo appInfo,
			List<ArtifactGroup> artifactGroups, List<ArtifactGroup> deletedFeatures) throws PhrescoException {
		File pomFile = new File(Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + Utility.getPomFileName(appInfo));
		ProjectUtils projectUtils = new ProjectUtils();
		File path = new File(Utility.getProjectHome() + appInfo.getAppDirName());
		projectUtils.deletePluginExecutionFromPom(pomFile);
		
		if (CollectionUtils.isNotEmpty(deletedFeatures)) {
			deleteFeature(appInfo, deletedFeatures);
		}
		
		if(CollectionUtils.isNotEmpty(artifactGroups)) {
			projectUtils.updatePOMWithPluginArtifact(pomFile, artifactGroups);
			updateItemGroup(appInfo, path, artifactGroups);
			BufferedReader breader = projectUtils.ExtractFeature(appInfo);
			try {
				String line = "";
				while ((line = breader.readLine()) != null) {
					if (line.startsWith("[ERROR]")) {
						System.err.println(line);
					}
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}	
	}

	private static void updateItemGroup(ApplicationInfo appInfo, File path, List<ArtifactGroup> artifactGroups) throws PhrescoException {
		try {
			File sourceDir = new File(path + File.separator + SOURCE_DIR);
			String solutionFilename = getSolutionFileName(sourceDir);
			
			path = new File(path + File.separator + SOURCE_DIR + File.separator + SRC_DIR + File.separator + solutionFilename + File.separator + solutionFilename + CSPROJ_FILE);
			if(!path.exists() && artifactGroups == null) {
				return;
			}
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(false);
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(path);
			boolean referenceCheck = referenceCheck(doc);
			if (referenceCheck) {
				updateItemGroups(doc,artifactGroups);
			} else {
				createNewItemGroup(doc, artifactGroups);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(path.toURI().getPath());
			transformer.transform(source, result);
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		} catch (SAXException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (TransformerConfigurationException e) {
			throw new PhrescoException(e);
		} catch (TransformerException e) {
			throw new PhrescoException(e);
		} 
	}

	private static String getSolutionFileName(File sourceDir) {
		File[] solutionFile = sourceDir.listFiles(new FilenameFilter() { 
			public boolean accept(File dir, String name) { 
				return name.endsWith(".sln");
			}
		});	
		String solutionFilename = solutionFile[0].getName().substring(0, (solutionFile[0].getName().length())-4);
		return solutionFilename;
	}

	private static void updateItemGroups(Document doc, List<ArtifactGroup> artifactGroups) {
		List<Node> itemGroup = getItemGroup(doc);
		updateContent(doc, artifactGroups, itemGroup, REFERENCE);
	}

	private static void createNewItemGroup(Document doc, List<ArtifactGroup> artifactGroups) {
		Element project = doc.getDocumentElement();
		Element itemGroup = doc.createElement(ITEMGROUP);
		for (ArtifactGroup artifactGroup : artifactGroups) {
			if(artifactGroup.getType().name().equals(Type.FEATURE.name())) {
				Element reference = doc.createElement(REFERENCE);
				reference.setAttribute(INCLUDE , artifactGroup.getName());
				Element hintPath = doc.createElement(HINTPATH);
				hintPath.setTextContent(DOUBLE_DOT + COMMON + File.separator + artifactGroup.getName()+ DLL);
				reference.appendChild(hintPath);
				itemGroup.appendChild(reference);
			}
		}
		project.appendChild(itemGroup);
	}

	private static List<Node> getItemGroup(Document doc) {
		NodeList projects = doc.getElementsByTagName(PROJECT);
		List<Node> itemGroupList = new ArrayList<Node>();
		for (int i = 0; i < projects.getLength(); i++) {
			Element project = (Element) projects.item(i);
			NodeList itemGroups = project.getElementsByTagName(ITEMGROUP);
			for (int j = 0; j < itemGroups.getLength(); j++) {
				Element itemGroup = (Element) itemGroups.item(j);
				itemGroupList.add(itemGroup);
			}
		}
		return itemGroupList;
	}

	private static void updateContent(Document doc, List<ArtifactGroup> artifactGroups,	List<Node> itemGroup, String elementName) {
		for (Node node : itemGroup) {
			NodeList childNodes = node.getChildNodes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				Node item = childNodes.item(j);
				if (item.getNodeName().equals(elementName)) {
					Node parentNode = item.getParentNode();
					for (ArtifactGroup artifactGroup : artifactGroups) {
						if(artifactGroup.getType().name().equals(Type.FEATURE.name())) {
							Element content = doc.createElement(elementName);
							if (elementName.equalsIgnoreCase(REFERENCE)) {
								content.setAttribute(INCLUDE, artifactGroup.getName()+ DLL);
								Element hintPath = doc.createElement(HINTPATH);
								hintPath.setTextContent(DOUBLE_DOT + COMMON + File.separator + artifactGroup.getName()+ DLL);
								content.appendChild(hintPath);
							} else {
								content.setAttribute(INCLUDE, artifactGroup.getName()+ DLL);
							}
							parentNode.appendChild(content);
						}
					}
					break;
				}
			}
		}
	}

	private static boolean referenceCheck(Document doc) {
		NodeList project = doc.getElementsByTagName(PROJECT);
		Boolean flag = false;
		for (int i = 0; i < project.getLength(); i++) {
			Element PROEJCT = (Element) project.item(i);
			NodeList ITEMGROUPs = PROEJCT.getElementsByTagName(ITEMGROUP);
			for (int j = 0; j < ITEMGROUPs.getLength(); j++) {
				Element ITEMGROUP = (Element) ITEMGROUPs.item(j);
				NodeList references = ITEMGROUP.getElementsByTagName(REFERENCE);
				for (int k = 0; k < references.getLength(); k++) {
					Element reference = (Element) references.item(k);
					if (reference.getTagName().equalsIgnoreCase(REFERENCE)) {
						flag = true;
					} else {
						flag = false;
					}
				}
			}
		}
		return flag;
	}
	
	private static void deleteFeature(ApplicationInfo appInfo , List<ArtifactGroup> deletedFeatures) throws PhrescoException {
		try {
			File sourceDir = new File(Utility.getProjectHome() + File.separator + appInfo.getAppDirName() + File.separator + SOURCE_DIR);
			String solutionFileName = getSolutionFileName(sourceDir);
			File path = new File(sourceDir + File.separator + SRC_DIR + File.separator + solutionFileName + File.separator + solutionFileName + CSPROJ_FILE);
			if(!path.exists() && CollectionUtils.isNotEmpty(deletedFeatures)) {
				return;
			}
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(false);
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(path);
			for (ArtifactGroup deleteFeature : deletedFeatures) {
				String feature = deleteFeature.getName();
				feature = "//Reference[@Include='" + feature + "']";
				XPath xpath = XPathFactory.newInstance().newXPath();
				javax.xml.xpath.XPathExpression expr = xpath.compile(feature);
				Object result = expr.evaluate(doc, XPathConstants.NODESET);
				NodeList nodes = (NodeList) result;
				for (int i = 0; i < nodes.getLength(); i++) {
					Node item = nodes.item(i).getParentNode();
					item.getParentNode().removeChild(item);
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(path.toURI().getPath());
			transformer.transform(source, result);

		} catch (XPathExpressionException e) {
			throw new PhrescoException(e);
		} catch (DOMException e) {
			throw new PhrescoException(e);
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		} catch (SAXException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (TransformerConfigurationException e) {
			throw new PhrescoException(e);
		} catch (TransformerException e) {
			throw new PhrescoException(e);
		}
	}
}
