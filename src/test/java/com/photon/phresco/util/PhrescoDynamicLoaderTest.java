package com.photon.phresco.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactInfo;
import com.photon.phresco.commons.model.RepoInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;

public class PhrescoDynamicLoaderTest {
	
	PhrescoDynamicLoader dynamic = new PhrescoDynamicLoader(null, null);
	
	@Before
	public void init() {
	RepoInfo repoInfo = new RepoInfo();
	repoInfo.setGroupRepoURL("testURL");
	repoInfo.setRepoUserName("admin");
	repoInfo.setRepoPassword("manage");
	List<ArtifactGroup> plugins = new ArrayList<ArtifactGroup>();
	ArtifactGroup group = new ArtifactGroup("testGroupId", "testArtifactId");
	group.setPackaging("jar");
	group.setGroupId("testGroupId");
	group.setArtifactId("testArtifactId");
	List<ArtifactInfo> artifactInfos = new ArrayList<ArtifactInfo>();
	ArtifactInfo artInfo = new ArtifactInfo();
	artInfo.setVersion("1.0");
	artInfo.setScope("testScope");
	artifactInfos.add(artInfo);
	group.setVersions(artifactInfos);
	plugins.add(group);
	dynamic = new PhrescoDynamicLoader(repoInfo, plugins);
	}
	
	@Test
	public void initTest() {
		assertNotNull(dynamic);
	}
}
