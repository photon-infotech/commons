package com.photon.phresco.commons.model;

import java.util.Date;

public class Widget {
	
	String query;
	String name;
	String autorefresh;
	Date starttime;
	Date endtime;
	
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
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	
	
}
