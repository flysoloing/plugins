package com.flysoloing.plugins.ghrepo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 目标是在打包或者安装到本地仓库的同时，把相关包同步更新到本地的github仓库<br>
 * User: laitao<br>
 * Date: 15-11-19<br>
 * Time: 下午12:56<br>
 */
@Mojo(name = "sync-ghrepo", threadSafe = true, defaultPhase = LifecyclePhase.INSTALL)
public class GithubRepoMojo extends AbstractMojo {

    private static final char PATH_SEPARATOR = '\\';

    private static final char GROUP_SEPARATOR = '.';

    private static final char ARTIFACT_SEPARATOR = '_';

    /**
     * 本地maven仓库的路径
     */
    @Parameter(defaultValue = "${settings.localRepository}", readonly = true)
    private File localMavenRepoPath;

    /**
     * 本地github仓库的libs路径
     */
    @Parameter(required = true)
    private File localGhRepoLibsPath;

    /**
     * 本地github仓库的plugins路径
     */
    @Parameter(required = true)
    private File localGhRepoPluginsPath;

    /**
     * 当前project的groupId
     */
    @Parameter(defaultValue = "${project.groupId}")
    private String projectGroupId;

    /**
     * 当前project的artifactId
     */
    @Parameter(defaultValue = "${project.artifactId}")
    private String projectArtifactId;

    /**
     * 当前project的version
     */
    @Parameter(defaultValue = "${project.version}")
    private String projectVersion;

    /**
     * 当前project的packaging
     */
    @Parameter(defaultValue = "${project.packaging}")
    private String projectPackaging;

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("localMavenRepoPath = " + localMavenRepoPath.getPath());
        getLog().info("localGhRepoLibsPath = " + localGhRepoLibsPath.getPath());
        getLog().info("localGhRepoPluginsPath = " + localGhRepoPluginsPath.getPath());
        getLog().info("projectGroupId = " + projectGroupId);
        getLog().info("projectArtifactId = " + projectArtifactId);
        getLog().info("projectVersion = " + projectVersion);
        getLog().info("projectPackaging = " + projectPackaging);

        //将projectGroupId转换为文件路径格式
        String srcPath = formatRepoPath(localMavenRepoPath.getPath(), projectGroupId, projectArtifactId);
        File srcDir = new File(srcPath);
        getLog().info("base src path = " + srcPath);

        //把这个路径下面的所有文件复制到本地github仓库中，如果不存在则新建，否则直接覆盖
        if (projectPackaging.equals("pom")) {
            try {
                copyRepoDirectory(srcDir, localMavenRepoPath.getPath(), localGhRepoLibsPath.getPath());
                copyRepoDirectory(srcDir, localMavenRepoPath.getPath(), localGhRepoPluginsPath.getPath());
            } catch (IOException e) {
                getLog().error(e);
            }
        }
        if (projectPackaging.equals("jar")) {
            try {
                copyRepoDirectory(srcDir, localMavenRepoPath.getPath(), localGhRepoLibsPath.getPath());
            } catch (IOException e) {
                getLog().error(e);
            }
        }
        if (projectPackaging.equals("maven-plugin")) {
            try {
                copyRepoDirectory(srcDir, localMavenRepoPath.getPath(), localGhRepoPluginsPath.getPath());
            } catch (IOException e) {
                getLog().error(e);
            }
        }
    }

    /**
     * 格式化仓库路径
     * @param mavenRepoPath
     * @param projectGroupId
     * @return
     */
    private String formatRepoPath(String mavenRepoPath, String projectGroupId, String projectArtifactId) {
        String projectGroupIdPath = projectGroupId.replace(GROUP_SEPARATOR, PATH_SEPARATOR);
        return mavenRepoPath + PATH_SEPARATOR + projectGroupIdPath + PATH_SEPARATOR + projectArtifactId;
    }

    /**
     * 拷贝源路径下面的文件到目标路径
     * @param srcPath
     * @param baseMavenRepoPath
     * @param baseGhRepoPath
     * @throws IOException
     */
    private void copyRepoDirectory(String srcPath, String baseMavenRepoPath, String baseGhRepoPath) throws IOException {
        File srcDir = new File(srcPath);
        copyRepoDirectory(srcDir, baseMavenRepoPath, baseGhRepoPath);
    }

    /**
     * 拷贝源路径下面的文件到目标路径
     * @param srcDir
     * @param baseMavenRepoPath
     * @param baseGhRepoPath
     * @throws IOException
     */
    private void copyRepoDirectory(File srcDir, String baseMavenRepoPath, String baseGhRepoPath) throws IOException {
        List<File> fileList = FileUtils.getFiles(srcDir, null, null);
        for (File srcFile : fileList) {
            FileUtils.copyFileIfModified(srcFile, genDestDir(srcFile, baseMavenRepoPath, baseGhRepoPath));
        }
    }

    /**
     * 生成目标路径
     * @param srcFile
     * @param baseMavenRepoPath
     * @param baseGhRepoPath
     * @return
     */
    private File genDestDir(File srcFile, String baseMavenRepoPath, String baseGhRepoPath) {
        String srcFilePath = srcFile.getPath();
        getLog().info("src file = " + srcFilePath);
        String destFilePath = srcFilePath.replace(baseMavenRepoPath, baseGhRepoPath);
        getLog().info("dest file path = " + destFilePath);
        return new File(destFilePath);
    }
}
