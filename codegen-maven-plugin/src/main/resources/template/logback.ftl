<?xml version="1.0" encoding="UTF-8"?>
<configuration>

    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder charset="UTF-8">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{10} [%L] - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="SYS_INFO" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${log.dir}/system_info.log</file>
        <append>true</append>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>INFO</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${log.dir}/system_info.%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>300MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <maxHistory>100</maxHistory>
        </rollingPolicy>
        <encoder charset="UTF-8">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{10} [%L] - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="SYS_ERROR" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${log.dir}/system_error.log</file>
        <append>true</append>
        <!-- Support multiple-JVM writing to the same log file -->
        <!--<prudent>true</prudent>-->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${log.dir}/system_error.%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>300MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <maxHistory>100</maxHistory>
        </rollingPolicy>
        <encoder charset="UTF-8">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{10} [%L] - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="APP_INFO" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${log.dir}/baoxianchannel_info.log</file>
        <append>true</append>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>INFO</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${log.dir}/baoxianchannel_info.%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>300MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <maxHistory>100</maxHistory>
        </rollingPolicy>
        <encoder charset="UTF-8">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{10} [%L] - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="APP_ERROR" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${log.dir}/baoxianchannel_error.log</file>
        <append>true</append>
        <!-- Support multiple-JVM writing to the same log file -->
        <!--<prudent>true</prudent>-->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${log.dir}/baoxianchannel_error.%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>300MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <maxHistory>100</maxHistory>
        </rollingPolicy>
        <encoder charset="UTF-8">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{10} [%L] - %msg%n</pattern>
        </encoder>
    </appender>

    <logger name="com.jd.baoxian.channel" level="INFO" additivity="false">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="APP_INFO"/>
        <appender-ref ref="APP_ERROR"/>
    </logger>

    <logger name="com.ibatis" level="DEBUG"/>
    <logger name="com.ibatis.common.jdbc.SimpleDataSource" level="DEBUG"/>
    <logger name="com.ibatis.common.jdbc.ScriptRunner" level="DEBUG"/>
    <logger name="com.ibatis.sqlmap.engine.impl.SqlMapClientDelegate" level="DEBUG"/>
    <logger name="java.sql.Connection" level="DEBUG"/>
    <logger name="java.sql.Statement" level="DEBUG"/>
    <logger name="java.sql.PreparedStatement" level="DEBUG">
        <appender-ref ref="CONSOLE"/>
    </logger>

    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="APP_INFO"/>
        <appender-ref ref="SYS_INFO"/>
        <appender-ref ref="SYS_ERROR"/>
    </root>
</configuration>