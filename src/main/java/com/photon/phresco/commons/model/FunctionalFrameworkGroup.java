package com.photon.phresco.commons.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class FunctionalFrameworkGroup extends Element {

	private static final long serialVersionUID = 1L;

	private List<FunctionalFramework> functionalFrameworks;
	private List<String> techIds;
	
	public void setFunctionalFrameworks(List<FunctionalFramework> functionalFrameworks) {
		this.functionalFrameworks = functionalFrameworks;
	}

	public List<FunctionalFramework> getFunctionalFrameworks() {
		return functionalFrameworks;
	}
	
	public void setTechIds(List<String> techIds) {
		this.techIds = techIds;
	}

	public List<String> getTechIds() {
		return techIds;
	}

	@Override
	public String toString() {
		return "FunctionalFrameworkGroup [functionalFrameworks="
				+ functionalFrameworks + ", techIds=" + techIds + "]";
	}
	
}
