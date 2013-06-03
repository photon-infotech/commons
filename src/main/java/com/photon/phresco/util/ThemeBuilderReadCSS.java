/**
 * Phresco Commons
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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