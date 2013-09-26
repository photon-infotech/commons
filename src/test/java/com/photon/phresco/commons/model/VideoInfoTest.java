package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class VideoInfoTest {
	
	VideoInfo videoInfo = new VideoInfo();
	VideoInfo videoInfo2 = new VideoInfo("testId", "testName", "testDescription");

	@Test
	public void testGetImageUrl() {
		videoInfo.setImageurl("testURL");
		String test = videoInfo.getImageurl();
		assertNotNull(test);
	}
	
	@Test
	public void testGetVideoList() {
		List<VideoType> videoList = new ArrayList<VideoType>();
		VideoType type = new VideoType();
		type.setId("testId");
		type.setName("testName");
		videoList.add(type);
		videoInfo.setVideoList(videoList);
		List<VideoType> test = videoInfo.getVideoList();
		assertNotNull(test);
	}
	
	@Test
	public void testToString() {
		String test = videoInfo.toString();
		assertNotNull(test);
	}
}
