package com.photon.phresco.commons.model;

import com.photon.phresco.util.PlatformTypes;

public enum PlatformType {
    
    WIN32(PlatformTypes.WINDOWS, 32), WIN64(PlatformTypes.WINDOWS, 64),
    LINUX32(PlatformTypes.LINUX, 32), LINUX64(PlatformTypes.LINUX, 64),
    MAC32(PlatformTypes.MAC, 32), MAC64(PlatformTypes.MAC, 64);
    
    String type;
    int bit;
    
    private PlatformType(String type, int bit) {
        this.type = type;
        this.bit = bit;
    }

    public int getBit() {
        return bit;
    }

    public String getType() {
        return type;
    }
    
}
