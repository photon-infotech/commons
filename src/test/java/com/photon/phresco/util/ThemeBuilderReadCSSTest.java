package com.photon.phresco.util;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

import com.phloc.css.decl.CascadingStyleSheet;

public class ThemeBuilderReadCSSTest {
	
	private static final String READ_CSS = "src/test/resources/read.css";
	ThemeBuilderReadCSS themeBuilderReadCSS = new ThemeBuilderReadCSS();
	
	@Test
	@SuppressWarnings("static-access")
	public void readCssTest() {
		File cssFile = new File(READ_CSS);
		CascadingStyleSheet css = themeBuilderReadCSS.readCSS30(cssFile);
		Assert.assertTrue(css != null);
	}
	
	@Test 
	@SuppressWarnings("static-access")
	public void readEmptyCssTest() {
		CascadingStyleSheet emptpyCss = themeBuilderReadCSS.readCSS30(new File(""));
		Assert.assertTrue(emptpyCss == null);
	}
}
