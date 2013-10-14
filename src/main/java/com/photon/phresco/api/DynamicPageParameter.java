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
package com.photon.phresco.api;

import java.util.Map;

import com.photon.phresco.exception.PhrescoException;


public interface DynamicPageParameter {

    String KEY_APP_INFO = "applicationInfo";
    String KEY_TEST_AGAINST = "testAgainst";
    String KEY_TEST_RESULT_NAME = "testName";
    String KEY_ROOT_MODULE = "rootModule";
	String KEY_MULTI_MODULE = "multiModule";

    public Map<String, Object> getObjects(Map<String, Object> map) throws PhrescoException;
}