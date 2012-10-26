package com.photon.phresco.util;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.util.artifact.DefaultArtifact;

import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactInfo;
import com.photon.phresco.commons.model.RepoInfo;
import com.photon.phresco.exception.PhrescoException;

public class PhrescoDynamicLoader {
	RepoInfo repoInfo;
	List<ArtifactGroup> plugins;
	
	PhrescoDynamicLoader(RepoInfo repoInfo, List<ArtifactGroup> plugins) {
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
			throw new PhrescoException(e);
		}
		return applicationProcessor;
	}
	
	private URLClassLoader getURLClassLoader() throws PhrescoException {
		List<Artifact> artifacts = new ArrayList<Artifact>();
		for (ArtifactGroup plugin : plugins) {
			List<ArtifactInfo> versions = plugin.getVersions();
			for (ArtifactInfo artifactInfo : versions) {
				Artifact artifact = new DefaultArtifact(plugin.getGroupId(), plugin.getArtifactId(), "", artifactInfo.getVersion());
				artifacts.add(artifact);
			}
		}
		
		URL[] artifactURLs;
		try {
			artifactURLs = MavenArtifactResolver.resolve(repoInfo.getGroupRepoURL(), repoInfo.getRepoUserName(),
					repoInfo.getRepoPassword(), artifacts);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		URLClassLoader classLoader = new URLClassLoader(artifactURLs);
		return classLoader;
	}

}
