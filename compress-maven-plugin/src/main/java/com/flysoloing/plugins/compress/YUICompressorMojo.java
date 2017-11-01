package com.flysoloing.plugins.compress;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * 基于YUICompressor进行静态资源文件压缩<br>
 * User: laitao<br>
 * Date: 2015/11/24<br>
 * Time: 23:25<br>
 */
@Mojo(name = "YUICompressor", threadSafe = true)
public class YUICompressorMojo extends AbstractMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("This goal is running...");
    }
}
