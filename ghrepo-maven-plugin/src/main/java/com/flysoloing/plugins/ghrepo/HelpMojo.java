package com.flysoloing.plugins.ghrepo;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ghrepo-maven-plugin的帮助Mojo类<br>
 * User: laitao<br>
 * Date: 2015/11/21<br>
 * Time: 0:40<br>
 */
@Mojo(name = "help", requiresProject = false, threadSafe = true)
public class HelpMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    @Parameter(defaultValue = "${project.modules}")
    private List<String> moduleList;

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("");
        List<String> modules = project.getModules();
        for (String moduleName : modules) {
            getLog().info(" project.getModules(); = " + moduleName);
        }

        for (String moduleName : moduleList) {
            getLog().info(" project.modules = " + moduleName);
        }

//        Map<String, Artifact> map = project.getArtifactMap();
//        for (String key : map.keySet()) {
//            getLog().info(" project.getArtifactMap(); = " + map.get(key).getArtifactId());
//        }
//
//        List<Artifact> artifacts = project.getAttachedArtifacts();
//        for (Artifact artifact : artifacts) {
//            getLog().info(" project.getAttachedArtifacts(); = " + artifact.getArtifactId());
//        }
//
//        Set<Artifact> artifactSet = project.getArtifacts();
//        for (Artifact artifact : artifactSet) {
//            getLog().info(" project.getArtifacts(); = " + artifact.getArtifactId());
//        }
    }
}
