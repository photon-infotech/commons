package com.photon.phresco.commons.model;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class DashboardInfo {
	
String projectid; 
String appid; 
String appcode; 
String appname; 
String appdirname;
String dashboardid; 
String dashboardname; 
String datatype ; 
String username; 
String password; 
String url;
String widgetid;
String name;  
String query ; 
String autorefresh;
String starttime;
String endtime;
HashMap<String, String[]> properties;
HashMap<String, HashMap<String, String>> colorcodes;


public HashMap<String, String[]> getProperties() {
	return properties;
}
public void setProperties(HashMap<String, String[]> properties) {
	this.properties = properties;
}
public String getWidgetid() {
	return widgetid;
}
public void setWidgetid(String widgetid) {
	this.widgetid = widgetid;
}
public String getProjectid() {
	return projectid;
}
public void setProjectid(String projectid) {
	this.projectid = projectid;
}
public String getAppid() {
	return appid;
}
public void setAppid(String appid) {
	this.appid = appid;
}
public String getAppcode() {
	return appcode;
}
public void setAppcode(String appcode) {
	this.appcode = appcode;
}
public String getAppname() {
	return appname;
}
public void setAppname(String appname) {
	this.appname = appname;
}
public String getAppdirname() {
	return appdirname;
}
public void setAppdirname(String appdirname) {
	this.appdirname = appdirname;
}
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
public String getDashboardid() {
	return dashboardid;
}
public void setDashboardid(String dashboardid) {
	this.dashboardid = dashboardid;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getQuery() {
	return query;
}
public void setQuery(String query) {
	this.query = query;
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
public HashMap<String, HashMap<String, String>> getColorcodes() {
	return colorcodes;
}
public void setColorcodes(HashMap<String, HashMap<String, String>> colorcodes) {
	this.colorcodes = colorcodes;
}

}