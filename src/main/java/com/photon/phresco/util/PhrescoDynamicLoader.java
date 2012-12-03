package com.photon.phresco.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.xml.sax.SAXException;

import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactInfo;
import com.photon.phresco.commons.model.RepoInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;

public class PhrescoDynamicLoader {
	RepoInfo repoInfo;
	List<ArtifactGroup> plugins;
	
	public PhrescoDynamicLoader(RepoInfo repoInfo, List<ArtifactGroup> plugins) {
		this.repoInfo = repoInfo;
		this.plugins = plugins;
	}
	
	public DynamicParameter getDynamicParameter(String className) throws PhrescoException {
		DynamicParameter dynamicParameter;
		try {
			Class<DynamicParameter> apiClass = (Class<DynamicParameter>) Class
					.forName(className, true, getURLClassLoader());
			dynamicParameter = apiClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PhrescoException(e);
		}
		return dynamicParameter;
	}
	
	public ApplicationProcessor getApplicationProcessor(String className) throws PhrescoException {
		ApplicationProcessor applicationProcessor;
		try {
			Class<ApplicationProcessor> apiClass = (Class<ApplicationProcessor>) Class
					.forName(className, true, getURLClassLoader());
			applicationProcessor = apiClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PhrescoException(e);
		}
		return applicationProcessor;
	}
	
	private URLClassLoader getURLClassLoader() throws PhrescoException {
		List<Artifact> artifacts = new ArrayList<Artifact>();
		for (ArtifactGroup plugin : plugins) {
			List<ArtifactInfo> versions = plugin.getVersions();
			for (ArtifactInfo artifactInfo : versions) {
				Artifact artifact = new DefaultArtifact(plugin.getGroupId(), plugin.getArtifactId(), "jar", artifactInfo.getVersion());
				artifacts.add(artifact);
			}
		}
		
		URL[] artifactURLs;
		try {
			artifactURLs = MavenArtifactResolver.resolve(repoInfo.getGroupRepoURL(), repoInfo.getRepoUserName(),
					repoInfo.getRepoPassword(), artifacts);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PhrescoException(e);
		}
		URLClassLoader classLoader = new URLClassLoader(artifactURLs);
		return classLoader;
	}
	
	public static void main(String[] args) throws PhrescoException, IOException, ParserConfigurationException, SAXException, ConfigurationException {
		RepoInfo repoinfo = new RepoInfo();
		repoinfo.setRepoName("releases");
		repoinfo.setRepoUserName("admin");
//		repoinfo.setRepoPassword("ZGV2cmVwbzI=");
		repoinfo.setRepoPassword("devrepo2");
		repoinfo.setGroupRepoURL("http://172.16.17.226:8080/repository/content/groups/public/");
		ArtifactGroup group = new ArtifactGroup();
		group.setGroupId("com.photon.phresco.commons");
		group.setArtifactId("phresco-commons");
		group.setPackaging("jar");
		ArtifactInfo info = new ArtifactInfo();
		info.setVersion("2.0.0.26001");
		group.setVersions(Arrays.asList(info));
		PhrescoDynamicLoader loader = new PhrescoDynamicLoader(repoinfo, Arrays.asList(group) );
		System.out.println("Loader Found..... " + loader);
		DynamicParameter applicationProcessor = loader.getDynamicParameter("com.photon.phresco.impl.EnvironmentsParameterImpl");
		applicationProcessor.getValues(null);
	}
	
}
