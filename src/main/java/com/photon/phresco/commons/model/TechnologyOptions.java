package com.photon.phresco.commons.model;

public class TechnologyOptions extends Element {
	
	private static final long serialVersionUID = 1L;
	private String option;
	
	public TechnologyOptions() {
		super();
	}
	
	public TechnologyOptions(String option) {
		this.option = option;
	}
	
	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
	
}	
