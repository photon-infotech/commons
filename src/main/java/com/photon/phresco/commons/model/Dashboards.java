package com.photon.phresco.commons.model;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dashboards {
	
	String projectid;
	String appname;
	String appcode;
	String appid;
	HashMap<String, Dashboard> dashboards;
	
	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getAppcode() {
		return appcode;
	}
	public void setAppcode(String appcode) {
		this.appcode = appcode;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public HashMap<String, Dashboard> getDashboards() {
		return dashboards;
	}
	public void setDashboards(HashMap<String, Dashboard> dashboards) {
		this.dashboards = dashboards;
	}
	
}
