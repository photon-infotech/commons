package com.photon.phresco.commons.model;

public class CoreOption {
    
    String techId;
    boolean core;
    
    boolean required;
    
    public CoreOption(String techId, boolean core, boolean required) {
        super();
        this.techId = techId;
        this.core = core;
        this.required = required;
    }

    public String getTechId() {
        return techId;
    }
    
    public void setTechId(String techId) {
        this.techId = techId;
    }
    
    public boolean isCore() {
        return core;
    }
    
    public void setCore(boolean core) {
        this.core = core;
    }
    
    public boolean isRequired() {
        return required;
    }
    
    public void setRequired(boolean required) {
        this.required = required;
    }

    
}
