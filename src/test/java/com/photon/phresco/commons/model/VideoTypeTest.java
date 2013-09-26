package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class VideoTypeTest {
	
	VideoType videoType = new VideoType();

	@Test
	public void testGetVideoId() {
		videoType.setVideoInfoId("testVideoId");
		String test = videoType.getVideoInfoId();
		assertNotNull(test);
	}
	
	@Test
	public void testGetUrl() {
		videoType.setUrl("testURL");
		String test = videoType.getUrl();
		assertNotNull(test);
	}
	
	@Test
	public void testGetArtifactGroup() {
		ArtifactGroup artifactGroup = new ArtifactGroup("testId");
		videoType.setArtifactGroup(artifactGroup);
		ArtifactGroup test = videoType.getArtifactGroup();
		assertNotNull(test);
	}
	
	@Test
	public void testGetType() {
		videoType.setType("testType");
		String test = videoType.getType();
		assertNotNull(test);
	}
	
	@Test
	public void testGetCodec() {
		videoType.setCodec("testCodec");
		String test = videoType.getCodec();
		assertNotNull(test);
	}
	
	@Test
	public void testToString() {
		String test = videoType.toString();
		assertNotNull(test);
	}
}
