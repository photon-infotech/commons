/**
 * Phresco Commons
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
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
package com.photon.phresco.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class AbstractPhrescoException extends Exception implements
		AIException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int errorNumber;

	public AbstractPhrescoException() {
	}

	public AbstractPhrescoException(Throwable t) {
		super(t);
	}

	public AbstractPhrescoException(int pErrorNumber) {
		super("Unknown Error");
		this.errorNumber = pErrorNumber;
	}

	public AbstractPhrescoException(String pErrorMessage) {
		super(pErrorMessage);
		this.errorNumber = 0;
	}

	public AbstractPhrescoException(int pErrorNumber, String pErrorMessage) {
		super(pErrorMessage);
		this.errorNumber = pErrorNumber;
	}

	public int getErrorNumber() {
		return this.errorNumber;
	}

	public String getErrorMessage() {
		return super.getMessage();
	}

	public String getStackError() {
		return null;
	}

	public abstract String toString();

	public String printErrorStack() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		printWriter.write("\n************* Stack Trace ***********\n");
		super.printStackTrace(printWriter);
		printWriter.write("\n*************************************\n");
		StringBuffer error = stringWriter.getBuffer();
		return error.toString();
	}
}