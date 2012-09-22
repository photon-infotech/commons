package com.photon.phresco.commons.model;

public class CoreOption {
    
    String techId;
    boolean core;
    
    public CoreOption(String techId, boolean core) {
        super();
        this.techId = techId;
        this.core = core;
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
}