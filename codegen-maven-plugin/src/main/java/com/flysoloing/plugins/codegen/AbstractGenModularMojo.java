package com.flysoloing.plugins.codegen;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @author laitao
 * @since 2016-10-26 17:01:11
 */
public abstract class AbstractGenModularMojo extends AbstractMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        generate();
    }

    public abstract void generate();
}
