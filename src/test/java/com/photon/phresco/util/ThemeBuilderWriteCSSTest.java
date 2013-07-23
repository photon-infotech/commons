package com.photon.phresco.util;

import java.io.File;
import java.net.URL;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.phloc.commons.state.ESuccess;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSExpression;
import com.phloc.css.decl.CSSSelector;
import com.phloc.css.decl.CSSSelectorSimpleMember;
import com.phloc.css.decl.CSSStyleRule;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.ICSSSelectorMember;
import com.phloc.css.decl.ICSSTopLevelRule;

public class ThemeBuilderWriteCSSTest {
	private static final String SUCCESS = "SUCCESS";
	private static final String WRITE_CSS = "src/test/resources/write.css";
	ThemeBuilderWriteCSS themeBuilderWriteCSS = new ThemeBuilderWriteCSS();
	@Test
	@SuppressWarnings("static-access")
	public void cssWriterTest() {
		try {
			File cssFile = new File(WRITE_CSS);
			if (!cssFile.exists()) {
				cssFile.createNewFile();
			}
			CascadingStyleSheet css = ThemeBuilderReadCSS.readCSS30(cssFile);
			if (css != null) {
				List<ICSSTopLevelRule> allRules = css.getAllRules();
				for (ICSSTopLevelRule icssTopLevelRule : allRules) {
					css.removeRule(icssTopLevelRule);
				}
			}
			getCssObject(css);
			ESuccess status = themeBuilderWriteCSS.writeCSS30(css, cssFile);
			
			Assert.assertEquals(SUCCESS, status.toString());
		} catch (Exception e) {
		}
	}
	
	private void getCssObject(CascadingStyleSheet css) {
		CSSStyleRule styleRule = new CSSStyleRule();
		CSSSelector aSelector = new CSSSelector();
		String selector = ".widget-login";
		String property = "height";
		String value = "100px";
		ICSSSelectorMember icssmm = new CSSSelectorSimpleMember(selector);
		aSelector.addMember(icssmm);
		styleRule.addSelector(aSelector);
		CSSExpression aExpression = new CSSExpression();
		aExpression.addTermSimple(value);
		CSSDeclaration aDeclaration = new CSSDeclaration(property, aExpression, false);
		styleRule.addDeclaration(aDeclaration);
		css.addRule(styleRule);
	}
}
