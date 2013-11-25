package com.photon.phresco.commons.model;

import java.util.List;

public class ModuleInfo extends Element {

	private static final long serialVersionUID = 1L;
	private List<String> dependentApps;
	private List<String> dependentModules;
	private TechnologyInfo techInfo;
	private String code;
	private boolean modified;
	private String rootModule;
	
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
	
	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public boolean isModified() {
		return modified;
	}
	
	public void setRootModule(String rootModule) {
		this.rootModule = rootModule;
	}

	public String getRootModule() {
		return rootModule;
	}

	@Override
	public String toString() {
		return "ModuleInfo [dependentApps=" + dependentApps
				+ ", dependentModules=" + dependentModules + ", techInfo="
				+ techInfo + ", code=" + code + ", modified=" + modified
				+ ", rootModule=" + rootModule + "]";
	}
}
