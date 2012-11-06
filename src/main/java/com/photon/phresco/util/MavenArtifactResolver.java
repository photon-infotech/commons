package com.photon.phresco.util;

import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.maven.repository.internal.MavenRepositorySystemSession;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.collection.CollectRequest;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.graph.DependencyFilter;
import org.sonatype.aether.repository.LocalRepository;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.resolution.ArtifactResult;
import org.sonatype.aether.resolution.DependencyRequest;
import org.sonatype.aether.util.artifact.JavaScopes;
import org.sonatype.aether.util.filter.DependencyFilterUtils;

public class MavenArtifactResolver {
	
	private static final Logger S_LOGGER= Logger.getLogger(MavenArtifactResolver.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
	public static final URL[] resolve(String url, String username,
			String password, List<Artifact> artifacts) throws Exception {
		if (isDebugEnabled) {
	        S_LOGGER.debug("Entered into MavenArtifactResolver.resolve()");
	        S_LOGGER.debug("Url Is" + url + " " + "Username Is " + username + "Password Is " + password);
	    }
		System.out.println("Entered To MavenArtifactResolver");
		System.out.println("Url Is" + url);
		System.out.println("UserName Is " + username);
		System.out.println("Password Is " + password);
		System.out.println("Artifacts Are " + artifacts);
		
		RepositorySystem repoSystem = new DefaultPlexusContainer().lookup(RepositorySystem.class);
		MavenRepositorySystemSession session = new MavenRepositorySystemSession();

		LocalRepository localRepo = new LocalRepository(Utility.getLocalRepoPath());
		if (isDebugEnabled) {
	        S_LOGGER.debug("Local Repository Is" + Utility.getLocalRepoPath());
	        S_LOGGER.debug("Local Repository Is" + localRepo);
	    }
		System.out.println("Local Repository Is  " + localRepo);
		session.setLocalRepositoryManager(repoSystem.newLocalRepositoryManager(localRepo));
		
		String repoName = "phresco"; //TODO: Should be repoInfo.getName()
		RemoteRepository remoteRepo = new RemoteRepository(repoName, "default", url);
		
        DependencyFilter classpathFlter = DependencyFilterUtils.classpathFilter(JavaScopes.COMPILE);
       
        URL[] urls = new URL[10];
        for (Artifact artifact : artifacts) {
	        CollectRequest collectRequest = new CollectRequest();
	        collectRequest.setRoot( new Dependency( artifact, JavaScopes.COMPILE ) );
	        collectRequest.addRepository( remoteRepo );
	
	        DependencyRequest dependencyRequest = new DependencyRequest(collectRequest, classpathFlter);
			List<ArtifactResult> artifactResults = repoSystem.resolveDependencies(
					session, dependencyRequest).getArtifactResults();
	
	        int i = 0;
	        for (ArtifactResult artifactResult : artifactResults ) {
	            urls[i++] =  artifactResult.getArtifact().getFile().toURI().toURL();
	        }
        }
        
        return urls;
	}
}
