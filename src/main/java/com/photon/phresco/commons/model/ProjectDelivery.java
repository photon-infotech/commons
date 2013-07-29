package com.photon.phresco.commons.model;

import java.util.List;


public class ProjectDelivery {
	
	private String id = "";
	private List<ContinuousDelivery> continuousDeliveries;
	
	public ProjectDelivery() {
		super();
	}

	public ProjectDelivery(String id, List<ContinuousDelivery> continuousDeliveries) {
		super();
		this.setId(id);
		this.setContinuousDeliveries(continuousDeliveries);	       
	}


	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setContinuousDeliveries(List<ContinuousDelivery> continuousDeliveries) {
		this.continuousDeliveries = continuousDeliveries;
	}

	public List<ContinuousDelivery> getContinuousDeliveries() {
		return continuousDeliveries;
	}	
}
