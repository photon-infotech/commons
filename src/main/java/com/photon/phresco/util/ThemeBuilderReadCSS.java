package com.photon.phresco.util;

import java.io.File;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.reader.CSSReader;

/**
 * This is example code to read a CSS declaration from a {@link File}.
 * 
 * @author philip
 */
public final class ThemeBuilderReadCSS {

  /**
   * Read a CSS 3.0 declaration from a file using UTF-8 encoding.
   * 
   * @param aFile
   *        The file to be read. May not be <code>null</code>.
   * @return <code>null</code> if the file has syntax errors.
   */
  public static CascadingStyleSheet readCSS30 (final File aFile) {
    final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, "utf-8", ECSSVersion.CSS30);
//    System.out.println("aCSS" + aCSS);
    if (aCSS == null) {
      // Most probably a syntax error
      return null;
    }
    return aCSS;
  }
}