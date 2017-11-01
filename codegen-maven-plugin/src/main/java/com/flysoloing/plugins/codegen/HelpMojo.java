package com.flysoloing.plugins.codegen;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * codegen-maven-plugin的帮助Mojo类<br>
 * User: laitao<br>
 * Date: 2015/12/7<br>
 * Time: 23:58<br>
 */
@Mojo(name = "help", requiresProject = false, threadSafe = true)
public class HelpMojo extends AbstractMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("This goal is running...");
    }
}
