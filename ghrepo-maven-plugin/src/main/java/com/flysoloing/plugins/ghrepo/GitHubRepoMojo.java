package com.flysoloing.plugins.ghrepo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 目标是在打包或者安装到本地仓库的同时，把相关包同步更新到本地的GitHub仓库
 * @author laitao
 * @date 2015-11-19 23:56:21
 */
@Mojo(name = "sync", threadSafe = true, defaultPhase = LifecyclePhase.INSTALL)
public class GitHubRepoMojo extends AbstractMojo {

    private static final char PATH_SEPARATOR = '\\';
    private static final char GROUP_SEPARATOR = '.';
    private static final char ARTIFACT_SEPARATOR = '_';

    private static final String PACKAGING_TYPE_POM = "pom";
    private static final String PACKAGING_TYPE_JAR = "jar";
    private static final String PACKAGING_TYPE_WAR = "war";
    private static final String PACKAGING_TYPE_MAVEN_PLUGIN = "maven-plugin";

    private static final String MAVEN_METADATA_LOCAL = "maven-metadata-local.xml";

    /**
     * 是否启用同步，默认不启用
     */
    @Parameter
    private boolean enable;

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

    /**
     * 当前project的父project的groupId
     */
    @Parameter(defaultValue = "${project.parent.groupId}")
    private String parentProjectGroupId;

    /**
     * 当前project的父project的artifactId
     */
    @Parameter(defaultValue = "${project.parent.artifactId}")
    private String parentProjectArtifactId;

    /**
     * 当前project的父project的version
     */
    @Parameter(defaultValue = "${project.parent.version}")
    private String parentProjectVersion;

    /**
     * 当前project的父project的packaging
     */
    @Parameter(defaultValue = "${project.parent.packaging}")
    private String parentProjectPackaging;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("enable = " + enable);
        getLog().info("localMavenRepoPath = " + localMavenRepoPath.getPath());
        getLog().info("localGhRepoLibsPath = " + localGhRepoLibsPath.getPath());
        getLog().info("localGhRepoPluginsPath = " + localGhRepoPluginsPath.getPath());
        getLog().info("");
        getLog().info("projectGroupId = " + projectGroupId);
        getLog().info("projectArtifactId = " + projectArtifactId);
        getLog().info("projectVersion = " + projectVersion);
        getLog().info("projectPackaging = " + projectPackaging);
        getLog().info("");
        getLog().info("parentProjectGroupId = " + parentProjectGroupId);
        getLog().info("parentProjectArtifactId = " + parentProjectArtifactId);
        getLog().info("parentProjectVersion = " + parentProjectVersion);
        getLog().info("parentProjectPackaging = " + parentProjectPackaging);
        getLog().info("");

        if (!enable) {
            getLog().info("The value of 'enable' is " + enable + ", not execute");
            return;
        }

        try {
            copyDispatch(projectGroupId, projectArtifactId, projectVersion, projectPackaging);

            if (StringUtils.isNotBlank(parentProjectGroupId) && StringUtils.isNotBlank(parentProjectArtifactId)
                    && StringUtils.isNotBlank(parentProjectPackaging) && StringUtils.isNotBlank(parentProjectVersion)) {
                copyDispatch(parentProjectGroupId, parentProjectArtifactId, parentProjectVersion, parentProjectPackaging);
            }
        } catch (IOException e) {
            getLog().error(e);
        }

    }

    /**
     * 根据packaging选择进行哪些拷贝操作
     * @param groupId
     * @param artifactId
     * @param packaging
     */
    private void copyDispatch(String groupId, String artifactId, String version, String packaging) throws IOException {
        //将projectGroupId转换为文件路径格式
        String basePath = formatRepoPath(localMavenRepoPath.getPath(), groupId, artifactId);

        String mavenMetadataLocalPath = basePath + PATH_SEPARATOR + MAVEN_METADATA_LOCAL;
        File mavenMetadataLocalFile = new File(mavenMetadataLocalPath);

        String srcPath = basePath + PATH_SEPARATOR + version;
        File srcDir = new File(srcPath);
        getLog().info("src path = " + srcPath);

        if (!srcDir.exists()) {
            getLog().info("src path = " + srcPath + " does not existed");
            return;
        }

        //把这个路径下面的所有文件复制到本地github仓库中，如果不存在则新建，否则直接覆盖
        if (PACKAGING_TYPE_POM.equals(packaging)) {
            try {
                copyRepoDirectory(srcDir, localMavenRepoPath.getPath(), localGhRepoLibsPath.getPath());
                FileUtils.copyFileIfModified(mavenMetadataLocalFile, genDestDir(mavenMetadataLocalFile, localMavenRepoPath.getPath(), localGhRepoLibsPath.getPath()));
                copyRepoDirectory(srcDir, localMavenRepoPath.getPath(), localGhRepoPluginsPath.getPath());
                FileUtils.copyFileIfModified(mavenMetadataLocalFile, genDestDir(mavenMetadataLocalFile, localMavenRepoPath.getPath(), localGhRepoPluginsPath.getPath()));
                getLog().info("");
            } catch (IOException e) {
                getLog().error(e);
            }
            return;
        }
        if (PACKAGING_TYPE_MAVEN_PLUGIN.equals(packaging)) {
            try {
                copyRepoDirectory(srcDir, localMavenRepoPath.getPath(), localGhRepoPluginsPath.getPath());
                FileUtils.copyFileIfModified(mavenMetadataLocalFile, genDestDir(mavenMetadataLocalFile, localMavenRepoPath.getPath(), localGhRepoPluginsPath.getPath()));
                getLog().info("");
            } catch (IOException e) {
                getLog().error(e);
            }
            return;
        }
        if (PACKAGING_TYPE_JAR.equals(packaging)) {
            try {
                copyRepoDirectory(srcDir, localMavenRepoPath.getPath(), localGhRepoLibsPath.getPath());
                FileUtils.copyFileIfModified(mavenMetadataLocalFile, genDestDir(mavenMetadataLocalFile, localMavenRepoPath.getPath(), localGhRepoLibsPath.getPath()));
                getLog().info("");
            } catch (IOException e) {
                getLog().error(e);
            }
        }
    }

    /**
     * 格式化仓库路径
     * @param groupId
     * @param artifactId
     * @return
     */
    private String formatRepoPath(String mavenRepoPath, String groupId, String artifactId) {
        String projectGroupIdPath = groupId.replace(GROUP_SEPARATOR, PATH_SEPARATOR);
        return mavenRepoPath + PATH_SEPARATOR + projectGroupIdPath + PATH_SEPARATOR + artifactId;
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
