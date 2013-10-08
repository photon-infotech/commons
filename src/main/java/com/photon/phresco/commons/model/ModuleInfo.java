package com.photon.phresco.commons.model;

import java.util.List;

public class ModuleInfo extends Element {

	private static final long serialVersionUID = 1L;
	private List<String> dependentApps;
	private List<String> dependentModules;
	private TechnologyInfo techInfo;
	private String code;
	
	public List<String> getDependentModules() {
		return dependentModules;
	}
	
	public void setDependentModules(List<String> dependentModules) {
		this.dependentModules = dependentModules;
	}
	
	public List<String> getDependentApps() {
		return dependentApps;
	}
	
	public void setDependentApps(List<String> dependentApps) {
		this.dependentApps = dependentApps;
	}
	
	public void setTechInfo(TechnologyInfo techInfo) {
		this.techInfo = techInfo;
	}

	public TechnologyInfo getTechInfo() {
		return techInfo;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	@Override
	public String toString() {
		return "ModuleInfo [dependentApps=" + dependentApps
				+ ", dependentModules=" + dependentModules + "]";
	}
}
