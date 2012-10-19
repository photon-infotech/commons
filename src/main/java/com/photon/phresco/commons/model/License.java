package com.photon.phresco.commons.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class License extends Element {
	
	public License() {
		super();
	}
	
	@Override
    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .toString();
    }
	
}
