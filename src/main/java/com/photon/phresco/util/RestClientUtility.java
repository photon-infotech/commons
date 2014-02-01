/**
 * Phresco Commons
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
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

package com.photon.phresco.util;

import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.MapUtils;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class RestClientUtility {
	public static ClientResponse getRestServiceResponse(String url, Map<String, String> queryParamMap) {
		ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(config);
		WebResource resource = client.resource(url);
		resource.accept(MediaType.APPLICATION_JSON);
		resource.header("Content-Type", MediaType.APPLICATION_JSON);
		
		if (MapUtils.isNotEmpty(queryParamMap)) {
			Set<String> queryKeys = queryParamMap.keySet();
			for (String queryKey : queryKeys) {
				resource.queryParam(queryKey, queryParamMap.get(queryKey));
			}
		}
		
		ClientResponse response = resource.get(ClientResponse.class);
		return response;
	}
}
