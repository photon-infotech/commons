package com.photon.phresco.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;

public class FileUtilTest {
	private static final String FILEUTIL_TEST_DIR = "file_util_test";
	private static final String FILEUTIL_TEMP_DIR = "file_util_temp";
	private static final String FILEUTIL_ZIP = "file_util.zip";
	private static final String FILEUTIL_DESTINATION_DIR = "file_util_destination";
	private static final String FILEUTIL_TEST_TXT = "file_util_test.txt";
	private static final String ZIP = "src/test/resources/sample.zip";
	private static String phrescoTemp = Utility.getPhrescoTemp();
	private static File fileUtilFolder = new File(phrescoTemp + File.separator + FILEUTIL_TEST_DIR);
	private static File tempFolder = new File(phrescoTemp + File.separator + FILEUTIL_TEST_DIR + File.separator + FILEUTIL_TEMP_DIR);
	private static File tempFile = new File(phrescoTemp + File.separator + FILEUTIL_TEST_DIR + File.separator + FILEUTIL_TEMP_DIR + File.separator + FILEUTIL_TEST_TXT);
	private static File destinationFolder = new File(phrescoTemp + File.separator + FILEUTIL_TEST_DIR + File.separator + FILEUTIL_DESTINATION_DIR);
	private static File fileUtilZip = new File(phrescoTemp + File.separator + FILEUTIL_TEST_DIR + File.separator + FILEUTIL_ZIP);
	
	@BeforeClass 
	public static void createFiles() throws PhrescoException {
		try {
			if (!fileUtilFolder.exists()) {
				fileUtilFolder.mkdir();
			}
			if (!tempFolder.exists()) {
				tempFolder.mkdir();
			}
			if (!tempFile.exists()) {
				tempFile.createNewFile();
			}
			File projectFile = new File("src/test/resources/wp1-wordpress3.4.2");
			File destDirectory = new File(Utility.getProjectHome());
			FileUtils.copyDirectoryToDirectory(projectFile, destDirectory);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	@Test 
	public void copyFolderTest() throws PhrescoException {
		try {
			FileUtil.copyFolder(tempFolder, destinationFolder);
			Assert.assertEquals(1, destinationFolder.list().length);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	@Test
	public void deleteFileTest() {
		boolean stauts = FileUtil.delete(tempFile);
		Assert.assertEquals(true, stauts);
	}
	
	@Test
	public void deleteDirectoryTest() {
		boolean status = FileUtil.delete(tempFolder);
		Assert.assertEquals(true, status);
	}
	
	@Test 
	public void writeFileFromInputStreamTest() throws PhrescoException {
		try {
			File zip = new File(ZIP);
			InputStream inputStream = new FileInputStream(zip.getPath());
			File destinationZip = FileUtil.writeFileFromInputStream(inputStream, fileUtilZip.getPath());
			Assert.assertEquals(true, destinationZip.exists());
		} catch (Exception e) {
			throw new PhrescoException(e);
		} 
	}
	
	@Test 
	public void moveUploadedThemeBundleTest() throws PhrescoException {
		try {
			ApplicationInfo appInfo = getApplicationInfo("wp1-wordpress3.4.2");
			Assert.assertNotNull(FileUtil.moveUploadedThemeBundle(tempFile, appInfo));
		} catch (Exception e) {
			throw new PhrescoException(e);
		} 
	}
	
	@AfterClass
	public static void clearDirectory() {
		FileUtil.delete(fileUtilFolder);
	}
	
	private static ApplicationInfo getApplicationInfo(String appDirName) throws PhrescoException {
		try {
			ProjectInfo projectInfo = getProjectInfo(appDirName);
			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			return applicationInfo;
		} catch (JsonIOException e) {
			throw new PhrescoException(e);
		}
	}
	
	private static ProjectInfo getProjectInfo(String appDirName) throws PhrescoException {
		StringBuilder builder  = new StringBuilder();
		builder.append(Utility.getProjectHome())
		.append(appDirName)
		.append(File.separatorChar)
		.append(".phresco")
		.append(File.separatorChar)
		.append("project.info");
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(builder.toString()));
			Gson gson = new Gson();
			ProjectInfo projectInfo = gson.fromJson(bufferedReader, ProjectInfo.class);
			return projectInfo;
		} catch (JsonSyntaxException e) {
			throw new PhrescoException(e);
		} catch (JsonIOException e) {
			throw new PhrescoException(e);
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		}
	}
}
