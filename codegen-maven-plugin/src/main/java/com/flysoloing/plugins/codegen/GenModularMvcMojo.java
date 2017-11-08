package com.flysoloing.plugins.codegen;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * 该Mojo作用是生成多模块Spring MVC结构WEB工程<br>
 * User: laitao<br>
 * Date: 2015/11/23<br>
 * Time: 23:38<br>
 */
@Mojo(name = "gen-modular-mvc", threadSafe = true)
public class GenModularMvcMojo extends AbstractMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("This goal is running...");
    }
}
