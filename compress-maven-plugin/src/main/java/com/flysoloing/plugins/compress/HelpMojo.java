package com.flysoloing.plugins.compress;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.ReaderFactory;
import org.codehaus.plexus.util.StringUtils;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Compress Maven Plugin, Help Mojo
 * @author laitao
 * @date 2017-11-08 14:35:01
 */
@Mojo(name = "help", requiresProject = false, threadSafe = true)
public class HelpMojo extends AbstractMojo {

    private static final String PLUGIN_HELP_PATH = "/META-INF/maven/com.flysoloing.plugins/compress-maven-plugin/plugin-help.xml";

    /**
     * 是否展示参数详情，默认不展示
     */
    @Parameter
    private boolean detail;

    /**
     * 是否指定goal，默认全部
     */
    @Parameter
    private String goal;

    /**
     * 每行长度，默认80个字符
     */
    @Parameter(defaultValue = "80")
    private int lineLength;

    /**
     * 行首缩进长度，默认4个字符
     */
    @Parameter(defaultValue = "4")
    private int indentSize;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Xpp3Dom pluginElement = build();

        StringBuilder sb = new StringBuilder();
        String name = pluginElement.getChild("name").getValue();
        String version = pluginElement.getChild("version").getValue();
        String id = pluginElement.getChild("groupId").getValue() + ":" + pluginElement.getChild("artifactId").getValue() + ":" + version;

        if ((StringUtils.isNotEmpty(name)) && (!(name.contains(id)))) {
            append(sb, name + " " + version, 0);
        } else if (StringUtils.isNotEmpty(name)) {
            append(sb, name, 0);
        } else {
            append(sb, id, 0);
        }

        append(sb, pluginElement.getChild("description").getValue(), 1);
        append(sb, "", 0);

        String goalPrefix = pluginElement.getChild("goalPrefix").getValue();

        Xpp3Dom[] mojos = pluginElement.getChild("mojos").getChildren("mojo");

        if ((this.goal == null) || (this.goal.length() <= 0)) {
            append(sb, "This plugin has " + mojos.length + ((mojos.length > 1) ? " goals:" : " goal:"), 0);
            append(sb, "", 0);
        }

        for (Xpp3Dom mojo : mojos) {
            writeGoal(sb, goalPrefix, mojo);
        }

        if (!(getLog().isInfoEnabled())) {
            return;
        }
        getLog().info(sb.toString());
    }

    private Xpp3Dom build() throws MojoExecutionException {
        getLog().debug("load plugin-help.xml: " + PLUGIN_HELP_PATH);
        InputStream is = super.getClass().getResourceAsStream(PLUGIN_HELP_PATH);
        try {
            return Xpp3DomBuilder.build(ReaderFactory.newXmlReader(is));
        } catch (XmlPullParserException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    private String getValue(Xpp3Dom mojo, String child) {
        Xpp3Dom elt = mojo.getChild(child);
        return ((elt == null) ? "" : elt.getValue());
    }

    private void writeGoal(StringBuilder sb, String goalPrefix, Xpp3Dom mojo) {
        String mojoGoal = mojo.getChild("goal").getValue();
        Xpp3Dom configurationElement = mojo.getChild("configuration");

        if ((this.goal != null) && (this.goal.length() > 0) && (!(mojoGoal.equals(this.goal)))) {
            return;
        }
        append(sb, goalPrefix + ":" + mojoGoal, 0);
        Xpp3Dom deprecated = mojo.getChild("deprecated");
        if ((deprecated != null) && (StringUtils.isNotEmpty(deprecated.getValue()))) {
            append(sb, "Deprecated. " + deprecated, 1);
            if (this.detail) {
                append(sb, "", 0);
                append(sb, getValue(mojo, "description"), 1);
            }
        } else {
            append(sb, getValue(mojo, "description"), 1);
        }
        append(sb, "", 0);

        if (!(this.detail)) {
            return;
        }

        Xpp3Dom[] parameters = mojo.getChild("parameters").getChildren("parameter");
        append(sb, "Available parameters:", 1);
        append(sb, "", 0);
        for (Xpp3Dom parameter : parameters) {
            writeParameter(sb, parameter, configurationElement);
        }
    }

    private void writeParameter(StringBuilder sb, Xpp3Dom parameter, Xpp3Dom configurationElement) {
        String parameterName = parameter.getChild("name").getValue();
        String parameterDescription = parameter.getChild("description").getValue();

        Xpp3Dom fieldConfigurationElement = configurationElement.getChild(parameterName);

        String parameterDefaultValue = "";
        if ((fieldConfigurationElement != null) && (fieldConfigurationElement.getValue() != null)) {
            parameterDefaultValue = " (Default: " + fieldConfigurationElement.getAttribute("default-value") + ")";
        }
        append(sb, parameterName + parameterDefaultValue, 2);
        Xpp3Dom deprecated = parameter.getChild("deprecated");
        if ((deprecated != null) && (StringUtils.isNotEmpty(deprecated.getValue()))) {
            append(sb, "Deprecated. " + deprecated.getValue(), 3);
            append(sb, "", 0);
        }
        append(sb, parameterDescription, 3);
        if ("true".equals(parameter.getChild("required").getValue())) {
            append(sb, "Required: Yes", 3);
        }
        Xpp3Dom expression = parameter.getChild("expression");
        if ((expression != null) && (StringUtils.isNotEmpty(expression.getValue()))) {
            append(sb, "Expression: " + expression.getValue(), 3);
        }

        append(sb, "", 0);
    }

    private static String repeat(String str, int repeat) {
        StringBuilder buffer = new StringBuilder(repeat * str.length());

        for (int i = 0; i < repeat; ++i) {
            buffer.append(str);
        }

        return buffer.toString();
    }

    private void append(StringBuilder sb, String description, int indent) {
        for (String line : toLines(description, indent, this.indentSize, this.lineLength)) {
            sb.append(line).append('\n');
        }
    }

    private static List<String> toLines(String text, int indent, int indentSize, int lineLength) {
        List<String> lines = new ArrayList<String>();

        String ind = repeat("\t", indent);

        String[] plainLines = text.split("(\r\n)|(\r)|(\n)");

        for (String plainLine : plainLines) {
            toLines(lines, ind + plainLine, indentSize, lineLength);
        }

        return lines;
    }

    private static void toLines(List<String> lines, String line, int indentSize, int lineLength) {
        int lineIndent = getIndentLevel(line);
        StringBuilder buf = new StringBuilder(256);

        String[] tokens = line.split(" +");

        for (String token : tokens) {
            if (buf.length() > 0) {
                if (buf.length() + token.length() >= lineLength) {
                    lines.add(buf.toString());
                    buf.setLength(0);
                    buf.append(repeat(" ", lineIndent * indentSize));
                } else {
                    buf.append(' ');
                }
            }

            for (int j = 0; j < token.length(); ++j) {
                char c = token.charAt(j);
                if (c == '\t') {
                    buf.append(repeat(" ", indentSize - (buf.length() % indentSize)));
                } else if (c == 160) {
                    buf.append(' ');
                } else {
                    buf.append(c);
                }
            }
        }
        lines.add(buf.toString());
    }

    private static int getIndentLevel(String line) {
        int level = 0;
        for (int i = 0; (i < line.length()) && (line.charAt(i) == '\t'); ++i) {
            ++level;
        }
        for (int i = level + 1; (i <= level + 4) && (i < line.length()); ++i) {
            if (line.charAt(i) != '\t') {
                continue;
            }
            ++level;
            break;
        }

        return level;
    }
}
