# Flysoloing Plugins

[![Build Status](https://secure.travis-ci.org/shardingjdbc/sharding-jdbc.png?branch=master)](https://travis-ci.org/shardingjdbc/sharding-jdbc)
[![Maven Status](https://maven-badges.herokuapp.com/maven-central/io.shardingjdbc/sharding-jdbc/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.shardingjdbc/sharding-jdbc)
[![Coverage Status](https://coveralls.io/repos/shardingjdbc/sharding-jdbc/badge.svg?branch=master&service=github)](https://coveralls.io/github/shardingjdbc/sharding-jdbc?branch=master)
[![GitHub release](https://img.shields.io/github/release/shardingjdbc/sharding-jdbc.svg)](https://github.com/shardingjdbc/sharding-jdbc/releases)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Skywalking Tracing](https://img.shields.io/badge/Skywalking%20Tracing-enable-brightgreen.svg)](https://github.com/OpenSkywalking/skywalking)

## 准备工作

1. 引入第三方远程仓库设置，有两种方式

* 在`~/MVN_HOME/conf/settings.xml`中，找到`<profiles>...</profiles>`，作为profile添加
* 在需要使用插件的工程中，找到`pom.xml`，引入如下第三方远程仓库

```xml
<!-- private remote libs repository -->
<repositories>
    <repository>
        <id>flysoloing-maven-libs-repo</id>
        <name>Flysoloing Maven Libs Repository</name>
        <url>http://flysoloing.github.io/repo/libs</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>
```

```xml
<!-- private remote plugins repository -->
<pluginRepositories>
    <pluginRepository>
        <id>flysoloing-maven-plugins-repo</id>
        <name>Flysoloing Maven Plugins Repository</name>
        <url>http://flysoloing.github.io/repo/plugins</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </pluginRepository>
</pluginRepositories>
```

2. 在需要使用插件的工程中，找到`pom.xml`，引入如下plugin设置

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.flysoloing.plugins.codegen</groupId>
            <artifactId>codegen-maven-plugin</artifactId>
            <version>1.0-SNAPSHOT</version>
        </plugin>
    </plugins>
</build>
```

## 如何使用

~~[TOC]

* mvn compile:compile 
* mvn plugin:descriptor 
* mvn install

[Servlet web app xsd](http://www.oracle.com/webfolder/technetwork/jsc/xml/ns/javaee/index.html)

Servlet 2.5
```xml
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee 
            http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
         version="2.5">
</web-app>
```

Servlet 3.0
```xml
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee 
            http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
         version="3.0">
</web-app>
```

Servlet 3.1
```xml
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee 
            http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
</web-app>
```

Servlet 4.0

Maven标准工程结构：

    project
    |-- pom.xml
    |-- src
    |   |-- main
    |   |   |-- java
    |   |   |   |-- com
    |   |   |       |-- flysoloing
    |   |   |           |-- App.java
    |   |   |-- resources
    |   |   |-- webapp
    |   |       |-- WEB-INF]
    |   |           |-- web.xml
    |   |-- test
    |       |-- java
    |       |   |-- com
    |       |       |-- flysoloing
    |       |           |-- AppTest.java
    |       |-- resources

## 贡献

1. 首先fork该项目
2. 创建你自己的特性分支 (`git checkout -b my-new-feature`)
3. 提交你的修改 (`git commit -am 'Add some feature'`)
4. 推送修改到origin分支 (`git push origin my-new-feature`)
5. 最后创建一个新的pull request，通过审核后，合并到master分支