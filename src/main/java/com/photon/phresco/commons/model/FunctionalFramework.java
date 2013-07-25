package com.photon.phresco.commons.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class FunctionalFramework extends Element {

	private static final long serialVersionUID = 1L;
	
	private List<String> versions;
	private List<FunctionalFrameworkProperties> funcFrameworkProperties;
	
	public FunctionalFramework() {
		super();
	}
	
	public void setFuncFrameworkProperties(List<FunctionalFrameworkProperties> funcFrameworkProperties) {
		this.funcFrameworkProperties = funcFrameworkProperties;
	}

	public List<FunctionalFrameworkProperties> getFuncFrameworkProperties() {
		return funcFrameworkProperties;
	}
	
	public void setVersions(List<String> versions) {
		this.versions = versions;
	}

	public List<String> getVersions() {
		return versions;
	}

	@Override
	public String toString() {
		return "FunctionalFramework [versions=" + versions
				+ ", funcFrameworkProperties=" + funcFrameworkProperties + "]";
	}
}