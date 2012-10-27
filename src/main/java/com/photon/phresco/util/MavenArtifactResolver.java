package com.photon.phresco.util;

import java.net.URL;
import java.util.List;

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
	
	public static final URL[] resolve(String url, String username,
			String password, List<Artifact> artifacts) throws Exception {
		RepositorySystem repoSystem = new DefaultPlexusContainer().lookup(RepositorySystem.class);
		MavenRepositorySystemSession session = new MavenRepositorySystemSession();

		LocalRepository localRepo = new LocalRepository(Utility.getLocalRepoPath());
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
