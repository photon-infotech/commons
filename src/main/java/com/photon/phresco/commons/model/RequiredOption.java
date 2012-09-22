package com.photon.phresco.commons.model;

public class RequiredOption {
    
    String techId;
    boolean required;
    
    public RequiredOption(String techId, boolean required) {
        super();
        this.techId = techId;
        this.required = required;
    }

    public String getTechId() {
        return techId;
    }
    
    public void setTechId(String techId) {
        this.techId = techId;
    }
    
    public boolean isRequired() {
        return required;
    }
    
    public void setRequired(boolean required) {
        this.required = required;
    }
}
