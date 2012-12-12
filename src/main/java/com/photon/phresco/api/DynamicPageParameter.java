package com.photon.phresco.api;

import java.util.List;
import java.util.Map;

import com.photon.phresco.exception.PhrescoException;


public interface DynamicPageParameter {

    String KEY_APP_INFO = "applicationInfo";
    String KEY_TEST_AGAINST = "testAgainst";
    String KEY_TEST_RESULT_NAME = "testName";

    public Map<String, Object> getObjects(Map<String, Object> map) throws PhrescoException;
}