package com.photon.phresco.util;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement
public class DashboardSearchInfo {
	
	private String username;
    private String password;
    private String host;
    private int port;
    private String url;
    private String query;
    private String dashboardname;
    private String widgetname;
    private String applicationname;
    private String datatype;

    
	public String getDashboardname() {
		return dashboardname;
	}
	public void setDashboardname(String dashboardname) {
		this.dashboardname = dashboardname;
	}
	public String getWidgetname() {
		return widgetname;
	}
	public void setWidgetname(String widgetname) {
		this.widgetname = widgetname;
	}
	public String getApplicationname() {
		return applicationname;
	}
	public void setApplicationname(String applicationname) {
		this.applicationname = applicationname;
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

	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
    
}
