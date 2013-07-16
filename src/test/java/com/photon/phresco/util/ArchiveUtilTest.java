package com.photon.phresco.util;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.ArchiveUtil.ArchiveType;

public class ArchiveUtilTest {
	private static final String FOLDER_TEST = "FolderTest";
	private static final String EXTRACT = "extract";
	private static final String TEMP_FOLDER_TEST = "tempFolderTest";
	private static final String NEW = "new";
	private static final String SAMPLE_TXT = "sample.txt";
	private static final String EXTRACT_ZIP = "extract_zip";
	private static final String EXTRACT_TAR_GZ = "extract_tar_gz";
	private static final String EXTRACT_TAR = "extract_tar";
	private static final String DESTINATION_TAR = "destination.tar";
	private static final String DESTINATION_TAR_GZ = "destination.tar.gz";
	private static final String DESTINATION_ZIP = "destination.zip";
	private static final String SOURCE = "source";
	private static final String ARCHIVE_TEST = "archive_util_test";
	
	ArchiveUtil archiveUtil = new ArchiveUtil();
	
	static String phrescoTemp = Utility.getPhrescoTemp();
	static File archiveFolder = new File(phrescoTemp + File.separator + ARCHIVE_TEST);
	static File sourceFolder = new File(archiveFolder.getPath() + File.separator + SOURCE);
	static File textFile = new File(sourceFolder.getPath() + File.separator + SAMPLE_TXT);
	
	static File zipFile = new File(archiveFolder.getPath() + File.separator + DESTINATION_ZIP);
	static File tarFile = new File(archiveFolder.getPath() + File.separator + DESTINATION_TAR);
	static File targzFile = new File(archiveFolder.getPath() + File.separator + DESTINATION_TAR_GZ);
	
	File extractedZip = new File(archiveFolder.getPath() + File.separator + EXTRACT_ZIP);
	File extractedTar = new File(archiveFolder.getPath() + File.separator + EXTRACT_TAR);
	File extractedTarGz = new File(archiveFolder.getPath() + File.separator + EXTRACT_TAR_GZ);
	
	@BeforeClass
	public static void createFiles() {
		try {
			createFiles(zipFile, tarFile, targzFile, sourceFolder, textFile);
		} catch (IOException e) {
		}
	}
	
	@Test
	public void createZipTest() throws PhrescoException {
		try {
			ArchiveUtil.createArchive(sourceFolder.getPath(), zipFile.getPath(), ArchiveType.ZIP);
			Assert.assertTrue(zipFile.length() > 0);
		} catch (Exception e) {
			throw new PhrescoException(e);
		} 
	}
	
	@Test
	public void extractZipTest() throws PhrescoException {
		try {
			ArchiveUtil.extractArchive(zipFile.getPath(), extractedZip.getPath(), ArchiveType.ZIP);
			Assert.assertTrue(extractedZip.listFiles().length == 1);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	@Test
	public void unzipTest() throws PhrescoException {
		try {
			ArchiveUtil.unzip(zipFile.getPath(), extractedZip.getPath(), "");
			File extractedFile = new File (extractedZip.getPath() + File.separator + EXTRACT); 
			Assert.assertTrue(extractedFile.listFiles().length == 1);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	@Test
	public void unzipWithFolderNameTest() throws PhrescoException {
		try {
			ArchiveUtil.unzip(zipFile.getPath(), extractedZip.getPath(), FOLDER_TEST);
			File extractedFile = new File (extractedZip.getPath() + File.separator + TEMP_FOLDER_TEST); 
			Assert.assertTrue(extractedFile.listFiles().length == 1);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	@Test
	public void unzipWithCreateTempDirTest() throws PhrescoException {
		try {
			File createDestination = new File(extractedZip.getPath() + File.separator + NEW);
			ArchiveUtil.unzip(zipFile.getPath(), createDestination.getPath(), "");
			File extractedFile = new File (createDestination.getPath() + File.separator + EXTRACT); 
			Assert.assertTrue(extractedFile.listFiles().length == 1);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	@Test
	public void extractZipFolderNameTest() throws PhrescoException {
		try {
			ArchiveUtil.extractArchive(zipFile.getPath(), extractedZip.getPath(), "test", ArchiveType.ZIP);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	
	@Test
	public void createTarTest() throws PhrescoException {
		try {
			ArchiveUtil.createArchive(sourceFolder.getPath(), tarFile.getPath(), ArchiveType.TAR);
			Assert.assertTrue(tarFile.length() > 0);
		} catch (Exception e) {
			throw new PhrescoException(e);
		} 
	}
	
	@Test
	public void extractTarTest() throws PhrescoException {
		try {
			ArchiveUtil.extractArchive(tarFile.getPath(), extractedTar.getPath(), ArchiveType.TAR);
			Assert.assertTrue(extractedTar.listFiles().length == 1);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	@AfterClass
	public static void deleteArchveFolder () {
		FileUtil.delete(archiveFolder);
	}
	private static void createFiles(File zipFile, File tarFile, File targzFile,
			File sourceFolder, File textFile) throws IOException {
		if (!archiveFolder.exists()) {
			archiveFolder.mkdir();
		}
		
		if (!sourceFolder.exists()) {
			sourceFolder.mkdir();
		}
		if (!zipFile.exists()) {
			zipFile.createNewFile();
		}
		
		if (!tarFile.exists()) {
			tarFile.createNewFile();
		}
//		if (!targzFile.exists()) {
//			targzFile.createNewFile();
//		}
		if (!textFile.exists()) {
			textFile.createNewFile();
		}
	}
}
