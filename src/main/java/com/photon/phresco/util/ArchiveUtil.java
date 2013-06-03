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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang.StringUtils;
import org.codehaus.plexus.archiver.AbstractUnArchiver;
import org.codehaus.plexus.archiver.ArchiverException;
import org.codehaus.plexus.archiver.UnArchiver;
import org.codehaus.plexus.archiver.tar.TarArchiver;
import org.codehaus.plexus.archiver.tar.TarGZipUnArchiver;
import org.codehaus.plexus.archiver.tar.TarUnArchiver;
import org.codehaus.plexus.archiver.zip.ZipArchiver;
import org.codehaus.plexus.archiver.zip.ZipUnArchiver;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.codehaus.plexus.logging.console.ConsoleLoggerManager;
import org.codehaus.plexus.util.FileUtils;

import com.photon.phresco.exception.PhrescoException;

public class ArchiveUtil {
	private static final int BUFFER_SIZE = 4096;
	public enum ArchiveType {ZIP, TAR, TARGZ	}

	public static void createArchive(String sourcePath, String destPath, ArchiveType archiveType) throws PhrescoException {
		createArchive(new File(sourcePath), new File(destPath), archiveType);
	}

	public static void createArchive(File sourcePath, File destPath, ArchiveType archiveType) throws PhrescoException {
		if (archiveType.equals(ArchiveType.ZIP)) {
			createZip(sourcePath, destPath);
		} else if (archiveType.equals(ArchiveType.TAR)) {
			createTar(sourcePath, destPath);
		} else if (archiveType.equals(ArchiveType.TARGZ)) {
			createTarGZ(sourcePath, destPath);
		} else {
			throw new PhrescoException("Invalid ArchiveType");
		}
	}

	private static void createZip(File sourcePath, File destPath) throws PhrescoException {
		ZipArchiver zipArchiver = new ZipArchiver();
		zipArchiver.addDirectory(sourcePath);
		zipArchiver.setDestFile(destPath);
		try {
			zipArchiver.createArchive();
		} catch (ArchiverException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}

	private static void createTar(File sourcePath, File destPath) throws PhrescoException {
		TarArchiver tarArchiver = new TarArchiver();
		tarArchiver.addDirectory(sourcePath);
		tarArchiver.setDestFile(destPath);
		try {
			tarArchiver.createArchive();
		} catch (ArchiverException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}

	private static void createTarGZ(File sourcePath, File destPath) {

	}

	public static void extractArchive(String sourcePath, String destPath, ArchiveType archiveType) throws PhrescoException {
		extractArchive(new File(sourcePath), new File(destPath), null, archiveType);
	}

	public static void extractArchive(String sourcePath, String destPath, String folderNameInArchive, ArchiveType archiveType) throws PhrescoException {
		extractArchive(new File(sourcePath), new File(destPath), folderNameInArchive, archiveType);
	}

	public static void extractArchive(File sourcePath, File destPath, String folderNameInArchive, ArchiveType archiveType) throws PhrescoException {
		ConsoleLogger logger = new ConsoleLogger();
		if (archiveType.equals(ArchiveType.ZIP)) {
			extractZip(sourcePath, destPath, folderNameInArchive, logger);
		} else if (archiveType.equals(ArchiveType.TAR)) {
			extractTar(sourcePath, destPath, logger);
		} else if (archiveType.equals(ArchiveType.TARGZ)) {
			extractTarGZip(sourcePath, destPath, logger);
		} else {
			throw new PhrescoException("Invalid ArchiveType");
		}
	}

	private static void extractZip(File sourcePath, File destPath, String folderNameInArchive, Logger logger) {
		ZipUnArchiver zipUnArchiver = new ZipUnArchiver();
		zipUnArchiver.enableLogging(logger);
		zipUnArchiver.setSourceFile(sourcePath);
		if (!destPath.exists()) {
			FileUtils.mkdir(destPath.getAbsolutePath());
		}
		zipUnArchiver.setDestDirectory(destPath);
		if(StringUtils.isEmpty(folderNameInArchive)){
			zipUnArchiver.extract();
		} else {
			zipUnArchiver.extract(folderNameInArchive,destPath);
		}
	}

	private static void extractTar(File sourcePath, File destPath, Logger logger) {
		TarUnArchiver tarUnArchiver = new TarUnArchiver();
		tarUnArchiver.enableLogging(logger);
		tarUnArchiver.setSourceFile(sourcePath);
		if (!destPath.exists()) {
			FileUtils.mkdir(destPath.getAbsolutePath());
		}
		tarUnArchiver.setDestDirectory(destPath);
		tarUnArchiver.extract();
	}

	private static void extractTarGZip(File sourcePath, File destPath, Logger logger) {
		TarGZipUnArchiver tarGZipUnArchiver = new TarGZipUnArchiver();
		tarGZipUnArchiver.enableLogging(logger);
		tarGZipUnArchiver.setSourceFile(sourcePath);
		if (!destPath.exists()) {
			FileUtils.mkdir(destPath.getAbsolutePath());
		}
		tarGZipUnArchiver.setDestDirectory(destPath);
		tarGZipUnArchiver.extract();
	}

	public void say() {
		ConsoleLoggerManager clm = new ConsoleLoggerManager();
		   String className = clm.getClass().getName().replace('.', '/');
		   String classJar =  clm.getClass().getResource("/" + className + ".class").toString();
		 }

	public static void main(String[] args) throws PhrescoException {
//		ArchiveUtil.createArchive("c:/temp", "c:/temp/test.zip", ArchiveType.ZIP);
//		ArchiveUtil.extractArchive("c:/temp/test.zip", "c:/temp/test", ArchiveType.ZIP);
		ArchiveUtil au = new ArchiveUtil();
		au.say();
	}
	
	public static void unzip(String zipFilePath, String destDirectory) throws IOException {
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
		ZipEntry entry = zipIn.getNextEntry();
		// iterates over entries in the zip file
		while (entry != null) {
			String filePath = destDirectory + File.separator + entry.getName();
			if (!entry.isDirectory()) {
				// if the entry is a file, extracts it
				extractFile(zipIn, filePath);
			} else {
				// if the entry is a directory, make the directory
				File dir = new File(filePath);
				dir.mkdir();
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		zipIn.close();
	}
	 
	 private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		 BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		 byte[] bytesIn = new byte[BUFFER_SIZE];
		 int read = 0;
		 while ((read = zipIn.read(bytesIn)) != -1) {
			 bos.write(bytesIn, 0, read);
		 }
		 bos.close();
	 }

}
