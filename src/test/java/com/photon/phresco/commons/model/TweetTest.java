package com.photon.phresco.commons.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TweetTest {
	Tweet tweet = new Tweet();

	@Test
	public void testGetId() {
		tweet.setId("testId");
		String test = tweet.getId();
		assertNotNull(test);
	}
	
	@Test
	public void testGetMessage() {
		tweet.setMessage("testMessage");
		String test = tweet.getMessage();
		assertNotNull(test);
	}
	
	@Test
	public void testToString() {
		String test = tweet.toString();
		assertNotNull(test);
	}
}
