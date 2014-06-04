package com.photon.phresco.util;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.exception.PhrescoException;
import com.phresco.pom.util.PomProcessor;

public class ComponentMerge {
	
	
	
	public static void mergeAndUnMerge(File pomFile,File sourceFolderLocation,List<ArtifactGroup> artifactGroups,List<ArtifactGroup> deletedFeatures)
	{
		try {
			PomProcessor processor1 = new PomProcessor(pomFile);
			String componentDirName = "";
			componentDirName = processor1.getProperty(Constants.POM_PROP_KEY_COMPONENTS_SOURCE_DIR);			

			
			//XML File Path specifying 
			String finalxmlDirName = sourceFolderLocation.getPath() + File.separator + Constants.DOT_PHRESCO_FOLDER;
			
			
			//markers DIR Path specifying
			String markersDirName=sourceFolderLocation.getPath() + File.separator + Constants.DO_NOT_CHECKIN_DIRY + File.separator + Constants.MARKERS_DIR;
				
			
			String componentDir = sourceFolderLocation.getPath() + File.separator + Constants.SOURCE_DIR + componentDirName;
			for (ArtifactGroup artifactGroup : artifactGroups) {				
				String componentPath = componentDir + File.separator + artifactGroup.getArtifactId();	
				File component = new File(componentPath);
				if(component.exists()){
					
				   // reading All Files for create XML files
				   ArrayList<String> files = readAllFiles( componentPath, new ArrayList<String>());				
				 
				   // creating XML files
				   createXMLFile(files, artifactGroup.getArtifactId(), finalxmlDirName);		
				
				  }
			    }
			
						
			for(ArtifactGroup deleteArtifact : deletedFeatures)
			{
				//reading XML files for deleting
				ArrayList<String> filesList=readXMLFiles(finalxmlDirName+File.separator+deleteArtifact.getArtifactId()+".xml", new ArrayList<String>());
				
				File xmlFilePath=new File(finalxmlDirName+File.separator+deleteArtifact.getArtifactId()+".xml");
				if(xmlFilePath.exists())
				{
					xmlFilePath.delete();
				}				
				
				//deleteing files				
				deleteFiles(filesList, componentDir);
				
				//delete markers files
				deleteMarkers(markersDirName,deleteArtifact);
				
			}
			deleteEmptyFolders(componentDir);
			
            } catch (Exception ex) {
	        ex.printStackTrace();
         }		
	}
	
 
	public static ArrayList<String> readAllFiles(String dComponentPath, ArrayList<String> list) {

		
		File file = new File(dComponentPath);		
		File[] files = file.listFiles();

		if (files != null) {
			for (File insidefile : files) {
				if (insidefile.isDirectory()) {
					readAllFiles(insidefile.getAbsolutePath(), list);
				} else {
					list.add(insidefile.getName());
				}
			}
		}
		return list;
	}
	
	public static void createXMLFile(ArrayList<String> list,String xmlFileName, String dirPath) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root element
			Document doc = docBuilder.newDocument();

			Element rootElement = doc.createElement("configurations");
			doc.appendChild(rootElement);			

			// sub Root Element
			Element subRootElement = doc.createElement("configuration");
			rootElement.appendChild(subRootElement);			

			// child nodes
			for (String e : list) {
				Element childNode = doc.createElement("file-name");
				childNode.appendChild(doc.createTextNode(e));
				subRootElement.appendChild(childNode);
			}
			

			// write content to XML file
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();			
			DOMSource source = new DOMSource(doc);

			File xmlFile = new File(dirPath);
			if (!xmlFile.exists()) {			
				xmlFile.mkdir();
			}

			
			StreamResult result = new StreamResult(dirPath + File.separator
					+ xmlFileName + ".xml");
			transformer.transform(source, result);

			

		} catch (Exception ex) {

		}

	}
	
	public static  ArrayList<String> readXMLFiles(String xmlFilesPath,ArrayList<String> filesList) {

		File file = new File(xmlFilesPath);
				
			if(file.exists()){			
				if (file.getName().endsWith(".xml")) {
					try {

						DocumentBuilderFactory dbFactory = DocumentBuilderFactory
								.newInstance();
						DocumentBuilder builder = dbFactory
								.newDocumentBuilder();
						Document doc = builder.parse(file.getPath());

						doc.getDocumentElement().normalize();

						NodeList list = doc.getElementsByTagName("file-name");

						for (int i = 0; i < list.getLength(); i++) {
							Element ele = (Element) list.item(i);
							filesList.add(ele.getTextContent());
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}

				}
			}
		return filesList;

	}
	
	
	public static void deleteFiles(ArrayList<String> filesList,String filePath)
	{
		String tempFilePath="";
		
		File[] filelocation=new File(filePath).listFiles();
		File checkFile=new File(filePath);
		if(checkFile.exists())
		{
		for(File file : filelocation)
		{			
			if(file.isDirectory() && file.list().length>0 && file.exists())
			{				
				tempFilePath = filePath + File.separator + file.getName();
				deleteFiles(filesList,tempFilePath);				
			}else{
				if(filesList.contains(file.getName()) && !file.isDirectory() && file.exists())
				{
					file.delete();					
				}				
			}
			
			if(file.isDirectory() && file.list().length==0){
				if(filesList.contains(file.getName()) && file.exists())
				{
					
					file.delete();
				
				}
			}
		  }
		}
	}
	
	public static void deleteMarkers(String markersDirName,ArtifactGroup artifactGroup)
	{		
		
		File[] markersFiles=new File(markersDirName).listFiles();
		
		if(markersFiles!=null)
		{
			for(File file : markersFiles)
			{				
				if(file.getName().contains(artifactGroup.getArtifactId()))
				{
					file.delete();
				}				
			}			
		}		
	}
		
	 public static BufferedReader cleanComponents(File phrescoPomFile) throws PhrescoException {
			BufferedReader breader = null;
			StringBuilder sb = new StringBuilder();			
			sb.append(Constants.MVN_COMMAND);
			sb.append(Constants.STR_BLANK_SPACE);
			sb.append(Constants.CLEAN__PHASE);
			if(!Constants.POM_NAME.equals(phrescoPomFile.getName())) {
				sb.append(Constants.STR_BLANK_SPACE);
				sb.append(Constants.HYPHEN_F);
				sb.append(Constants.STR_BLANK_SPACE);
				sb.append(phrescoPomFile.getName());
			}
	       breader = Utility.executeCommand(sb.toString(), phrescoPomFile.getParent());
        return breader;
	
   }
	 
	public static void deleteEmptyFolders(String dirPath)
	{		
		File insideFile=new File(dirPath);
		
		File[] allFiles=insideFile.listFiles();
		
		if(allFiles!=null)
		{
			for(File file : allFiles)
			{
				if(file.isDirectory() && file.list().length==0 && !file.getName().equalsIgnoreCase(Constants.SOURCE_DIR)){
					file.delete();
				}else{
					deleteEmptyFolders(dirPath+File.separator+file.getName());
				}
				
				File againCheck=new File(dirPath);
				if(againCheck.list().length==0 && !againCheck.getName().equalsIgnoreCase(Constants.SOURCE_DIR)){
					againCheck.delete();
				}
				
			}			
		}	
	}		
	
	
	
}