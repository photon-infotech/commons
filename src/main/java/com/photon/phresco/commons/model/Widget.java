package com.photon.phresco.commons.model;

import java.util.HashMap;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Widget {
	
	String query;
	String name;
	String autorefresh;
	String starttime;
	String endtime;
	HashMap<String, String[]> properties;
	
	public HashMap<String, String[]> getProperties() {
		return properties;
	}
	public void setProperties(HashMap<String, String[]> properties) {
		this.properties = properties;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAutorefresh() {
		return autorefresh;
	}
	public void setAutorefresh(String autorefresh) {
		this.autorefresh = autorefresh;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
}
