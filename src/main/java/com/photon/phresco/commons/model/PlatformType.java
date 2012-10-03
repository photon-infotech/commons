package com.photon.phresco.commons.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PlatformType extends Element {
    
	private static final long serialVersionUID = 1L;
	private String type;
    private int bit;
    
    public PlatformType() {
        super();
    }
    
    public PlatformType(String type, int bit) {
        this.type = type;
        this.bit = bit;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBit() {
        return bit;
    }

    public void setBit(int bit) {
        this.bit = bit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append("type", getType())
                .append("bit", getBit())
                .toString();
    }
}