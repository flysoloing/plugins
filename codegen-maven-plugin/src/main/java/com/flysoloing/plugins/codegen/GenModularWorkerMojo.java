package com.flysoloing.plugins.codegen;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * 该Mojo作用是生成多模块Worker工程<br>
 *
 * @author laitao
 * @date 2016-10-26 18:17:01
 */
@Mojo(name = "gen-modular-worker", threadSafe = true)
public class GenModularWorkerMojo extends AbstractMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("This goal is running...");
    }
}
