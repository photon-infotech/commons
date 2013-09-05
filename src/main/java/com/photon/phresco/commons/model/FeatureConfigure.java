package com.photon.phresco.commons.model;

import java.util.List;
import java.util.Properties;

public class FeatureConfigure {

	private List<PropertyTemplate> propertyTemplates;
	private Properties properties;
	private boolean hasCustomProperty;
	
	public List<PropertyTemplate> getPropertyTemplates() {
		return propertyTemplates;
	}
	public void setPropertyTemplates(List<PropertyTemplate> propertyTemplates) {
		this.propertyTemplates = propertyTemplates;
	}
	
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	public boolean isHasCustomProperty() {
		return hasCustomProperty;
	}
	public void setHasCustomProperty(boolean hasCustomProperty) {
		this.hasCustomProperty = hasCustomProperty;
	}
	
}
