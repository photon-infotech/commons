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
package com.photon.phresco.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.apache.tools.ant.AntClassLoader;
import org.codehaus.plexus.DefaultContainerConfiguration;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusConstants;
import org.codehaus.plexus.classworlds.ClassWorld;
import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactResult;

import com.photon.phresco.exception.PhrescoException;

public class MavenEclipseAetherArtifactResolver {
	
	private static final Logger S_LOGGER= Logger.getLogger(MavenEclipseAetherArtifactResolver.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
	public static final URL[] resolve(String url, String username,
			String password, List<Artifact> artifacts) throws Exception {
		if (isDebugEnabled) {
	        S_LOGGER.debug("Entered into MavenArtifactResolver.resolve()");
	        S_LOGGER.debug("Url Is" + url + " " + "Username Is " + username + "Password Is " + password);
	    }
		
//		RepositorySystem repoSystem = new DefaultPlexusContainer().lookup(RepositorySystem.class);
////		MavenRepositorySystemSession session = new MavenRepositorySystemSession();
//		org.eclipse.aether.DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();
//		LocalRepository localRepo = new LocalRepository(Utility.getLocalRepoPath());
//		if (isDebugEnabled) {
//	        S_LOGGER.debug("Local Repository Is" + Utility.getLocalRepoPath());
//	        S_LOGGER.debug("Local Repository Is" + localRepo);
//	    }
//		session.setLocalRepositoryManager(repoSystem.newLocalRepositoryManager( session, localRepo));
//		
//		String repoName = "phresco"; //TODO: Should be repoInfo.getName()
//		RemoteRepository remoteRepo = new RemoteRepository(repoName, "default", url);
//		
//        DependencyFilter classpathFlter = DependencyFilterUtils.classpathFilter(JavaScopes.COMPILE);
        List<URL> urls = new ArrayList<URL>();
//        for (Artifact artifact : artifacts) {
//            CollectRequest collectRequest = new CollectRequest();
//            collectRequest.setRoot( new Dependency( artifact, JavaScopes.COMPILE ) );
//            collectRequest.addRepository( remoteRepo );
//            DependencyRequest dependencyRequest = new DependencyRequest( collectRequest, classpathFlter );
//            List<ArtifactResult> artifactResults =
//            	repoSystem.resolveDependencies( session, dependencyRequest ).getArtifactResults();
//        	
//	        for (ArtifactResult artifactResult : artifactResults ) {
//	        	urls.add(artifactResult.getArtifact().getFile().toURI().toURL());
//	        }
//        }
        URL[] urlArr = new URL[0];
        return urls.toArray(urlArr);
	}

	
	public static final URL resolveSingleArtifact(String url, String username,
			String password, List<Artifact> artifacts) throws Exception {
		if (isDebugEnabled) {
	        S_LOGGER.debug("Entered into MavenArtifactResolver.resolve()");
	        S_LOGGER.debug("Url Is" + url + " " + "Username Is " + username + "Password Is " + password);
	    }
		Artifact artifact = artifacts.get(0);
		ClassWorld world = new ClassWorld("plexus.core", Thread.currentThread()
				.getContextClassLoader());
		ClassRealm classRealm = buildClassRealm(
				new File(System.getenv("MAVEN_HOME")), world, Thread
						.currentThread().getContextClassLoader());
		DefaultContainerConfiguration conf = new DefaultContainerConfiguration();
		conf.setContainerConfiguration(PlexusConstants.SCANNING_INDEX)
				.setRealm(classRealm).setClassWorld(world)
				.setClassPathScanning(PlexusConstants.SCANNING_INDEX)
				.setComponentVisibility(PlexusConstants.REALM_VISIBILITY);
		DefaultPlexusContainer plexusContainer = new DefaultPlexusContainer(conf);
//		RepositorySystem repoSystem = newRepositorySystem();
		RepositorySystem repoSystem = plexusContainer.lookup(org.eclipse.aether.RepositorySystem.class);
        RepositorySystemSession session = newSession( repoSystem );
        RemoteRepository central = new RemoteRepository.Builder( "phresco", "default", url ).build();
 
        CollectRequest collectRequest = new CollectRequest();
        org.eclipse.aether.artifact.Artifact eclipseArtifact = new 
        	DefaultArtifact(artifact.getGroupId(), artifact.getArtifactId(), artifact.getType(), artifact.getVersion());
		org.eclipse.aether.graph.Dependency dependency = new org.eclipse.aether.graph.Dependency(eclipseArtifact, "compile");
		collectRequest.setRoot( dependency  );
        collectRequest.addRepository( central );
        
        org.eclipse.aether.resolution.ArtifactRequest req = new org.eclipse.aether.resolution.ArtifactRequest();
        req.addRepository(central);
        req.setArtifact(eclipseArtifact);
		ArtifactResult resolveArtifact = repoSystem.resolveArtifact(session, req );
        return resolveArtifact.getArtifact().getFile().toURI().toURL();
	}
	
	public static ClassRealm buildClassRealm(File mavenHome, ClassWorld world,
			ClassLoader parentClassLoader) throws PhrescoException {
		if (mavenHome == null) {
			throw new IllegalArgumentException("mavenHome cannot be null");
		}
		if (!mavenHome.exists()) {
			throw new IllegalArgumentException(
					"mavenHome '"
							+ mavenHome.getPath()
							+ "' doesn't seem to exist on this node (or you don't have sufficient rights to access it)");
		}
		File libDirectory = new File(mavenHome, "lib");
		if (!libDirectory.exists()) {
			throw new IllegalArgumentException(
					mavenHome.getPath()
							+ " doesn't have a 'lib' subdirectory - thus cannot be a valid maven installation!");
		}
		File[] jarFiles = libDirectory.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar");
			}
		});
		AntClassLoader antClassLoader = new AntClassLoader(Thread
				.currentThread().getContextClassLoader(), false);
		for (File jarFile : jarFiles) {
			antClassLoader.addPathComponent(jarFile);
		}
		if (world == null) {
			world = new ClassWorld();
		}
		ClassRealm classRealm = new ClassRealm(world, "plexus.core",
				parentClassLoader == null ? antClassLoader : parentClassLoader);
		for (File jarFile : jarFiles) {
			try {
				classRealm.addURL(jarFile.toURI().toURL());
			} catch (MalformedURLException e) {
				throw new PhrescoException(e);
			}
		}
		return classRealm;
	}
	
	private static RepositorySystem newRepositorySystem()
    {
        DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
//        locator.addService( RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class );
//        locator.addService( TransporterFactory.class, FileTransporterFactory.class );
//        locator.addService( TransporterFactory.class, HttpTransporterFactory.class );
// 
        return locator.getService( RepositorySystem.class );
    }
	
	private static RepositorySystemSession newSession( RepositorySystem system )
    {
		DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();
        LocalRepository localRepo = new LocalRepository( Utility.getLocalRepoPath());
        session.setLocalRepositoryManager( system.newLocalRepositoryManager( session, localRepo ) );
        return session;
    }
	
	public static URL[] resolveUsingTemp(String repoURL, List<Artifact> artifacts) throws PhrescoException {
		URL[] urls = new URL[10];
		for (Artifact artifact : artifacts) {
			InputStream in = null;
			FileOutputStream fos = null;
			try {
				URL url = new URL(repoURL + artifact.getGroupId().replace(".", "/") + "/" + artifact.getArtifactId() + "/" + artifact.getVersion() + "/" + artifact.getArtifactId() + "-" + artifact.getVersion() + ".jar");
				URLConnection connection = url.openConnection();
				in = connection.getInputStream();
				File filePath = new File(Utility.getPhrescoTemp() + artifact.getArtifactId() + ".jar");
				filePath.createNewFile();
				int i = 0;
				urls[i++] = filePath.toURI().toURL();
				fos = new FileOutputStream(filePath);
				byte[] buf = new byte[1024];
				while (true) {
					int len;
					len = in.read(buf);
					if (len == -1) {
						break;
					}
					fos.write(buf, 0, len);
				}
			} catch (Exception e) {
				throw new PhrescoException(e);
			} finally {
				Utility.closeStream(in);
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						throw new PhrescoException(e);
					}
				}
			}
		}
		return urls;
	}
	
}
