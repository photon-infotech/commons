/*
 * ###
 * Phresco Commons
 *
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.photon.phresco.commons.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


@XmlRootElement
public class VideoInfo extends CustomerBasedElement {

	private String imageurl;
	private List<VideoType> videoList;

	public VideoInfo() {
	    super();
	}
	
	public VideoInfo(String id, String name, String description) {
        super(id, name, description);
    }

    public VideoInfo(String name, String description) {
        super(name, description);
    }

    public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public List<VideoType> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<VideoType> videoList) {
		this.videoList = videoList;
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("imageurl", getImageurl())
                .append("videoList", getVideoList())
                .toString();
    }
    
}
