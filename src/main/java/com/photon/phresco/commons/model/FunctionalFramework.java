package com.photon.phresco.commons.model;

import java.util.List;


public class FunctionalFramework extends Element {

	private static final long serialVersionUID = 1L;

	public FunctionalFramework() {
		super();
	}
	
	private List<FunctionalFrameworkProperties> funcFrameworkProperties;
	
	public void setFuncFrameworkProperties(List<FunctionalFrameworkProperties> funcFrameworkProperties) {
		this.funcFrameworkProperties = funcFrameworkProperties;
	}

	public List<FunctionalFrameworkProperties> getFuncFrameworkProperties() {
		return funcFrameworkProperties;
	}
	
	@Override
	public String toString() {
		return "FunctionalFramework [funcFrameworkProperties="
				+ funcFrameworkProperties + "]";
	}
}