package com.photon.phresco.commons.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FrameWorkTheme extends Element {
	
	/**
	 *  Apply FrameWork Theme based on customer
	 */
	
	private static final long serialVersionUID = 1L;
	private String brandingColor;
	private String bodyBackGroundColor;
	private String accordionBackGroundColor;
	private String menuBackGround;
	private String menufontColor;
	private String pageHeaderColor;
	private String labelColor;
	private String copyRightColor;
	private String buttonColor;
	
	public FrameWorkTheme() {
		super();
	}

	public String getBrandingColor() {
		return brandingColor;
	}

	public String getBodyBackGroundColor() {
		return bodyBackGroundColor;
	}

	public String getAccordionBackGroundColor() {
		return accordionBackGroundColor;
	}

	public String getMenuBackGround() {
		return menuBackGround;
	}

	public String getMenufontColor() {
		return menufontColor;
	}

	public String getLabelColor() {
		return labelColor;
	}

	public String getCopyRightColor() {
		return copyRightColor;
	}

	public String getButtonColor() {
		return buttonColor;
	}

	public void setBrandingColor(String brandingColor) {
		this.brandingColor = brandingColor;
	}

	public void setBodyBackGroundColor(String bodyBackGroundColor) {
		this.bodyBackGroundColor = bodyBackGroundColor;
	}

	public void setAccordionBackGroundColor(String accordionBackGroundColor) {
		this.accordionBackGroundColor = accordionBackGroundColor;
	}

	public void setMenuBackGround(String menuBackGround) {
		this.menuBackGround = menuBackGround;
	}

	public void setMenufontColor(String menufontColor) {
		this.menufontColor = menufontColor;
	}

	public void setLabelColor(String labelColor) {
		this.labelColor = labelColor;
	}

	public void setCopyRightColor(String copyRightColor) {
		this.copyRightColor = copyRightColor;
	}

	public void setButtonColor(String buttonColor) {
		this.buttonColor = buttonColor;
	}

	public String getPageHeaderColor() {
		return pageHeaderColor;
	}

	public void setPageHeaderColor(String pageHeaderColor) {
		this.pageHeaderColor = pageHeaderColor;
	}

	@Override
	public String toString() {
		return "FrameWorkTheme [brandingColor=" + brandingColor
				+ ", bodyBackGroundColor=" + bodyBackGroundColor
				+ ", accordionBackGroundColor=" + accordionBackGroundColor
				+ ", menuBackGround=" + menuBackGround + ", menufontColor="
				+ menufontColor + ", buttonColor=" + buttonColor
				+ ", pageHeaderColor=" + pageHeaderColor
				+ ", labelColor=" + labelColor + ", copyRightColor="
				+ copyRightColor + "]";
	}
}
