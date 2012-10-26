package com.photon.phresco.api;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.PhrescoException;

public interface ApplicationProcessor {
	
	/**
	 * 
	 * @param appInfo
	 * @throws PhrescoException
	 */
	void preCreate(ApplicationInfo appInfo) throws PhrescoException;

	/**
	 * 
	 * @param appInfo
	 * @throws PhrescoException
	 */
	void preUpdate(ApplicationInfo appInfo) throws PhrescoException;
	
	/**
	 * 
	 * @param appInfo
	 * @throws PhrescoException
	 */
	void postCreate(ApplicationInfo appInfo) throws PhrescoException;

	/**
	 * 
	 * @param appInfo
	 * @throws PhrescoException
	 */
	void postUpdate(ApplicationInfo appInfo) throws PhrescoException;

}
