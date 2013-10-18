package com.photon.phresco.commons.model;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dashboard {
	
	String dashboardname;
	String datatype;
	String username;
	String password;
	String url;
	HashMap<String, Widget> widgets;
	
	public String getDashboardname() {
		return dashboardname;
	}
	public void setDashboardname(String dashboardname) {
		this.dashboardname = dashboardname;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public HashMap<String, Widget> getWidgets() {
		return widgets;
	}
	public void setWidgets(HashMap<String, Widget> widgets) {
		this.widgets = widgets;
	}
	
	
	
}
