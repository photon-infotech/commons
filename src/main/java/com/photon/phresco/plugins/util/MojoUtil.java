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
package com.photon.phresco.plugins.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter;

public class MojoUtil {
    
    public static final Map<String, String> getAllValues(Configuration config) {
        List<Parameter> parameters = config.getParameters().getParameter(); 
        Map<String, String> configValues = new HashMap<String, String>(parameters.size() * 2);
        for (Parameter parameter : parameters) {
            String value = parameter.getValue();
            if (StringUtils.isNotEmpty(value)) {
                String key = parameter.getKey();
                configValues.put(key, value); 
            }
        }
        
        return configValues;
    }

}
