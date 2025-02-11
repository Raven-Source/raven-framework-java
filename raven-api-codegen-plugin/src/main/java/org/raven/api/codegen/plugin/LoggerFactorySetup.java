package org.raven.api.codegen.plugin;

import org.apache.maven.plugin.logging.Log;

public class LoggerFactorySetup {


    public static void initializeMavenLoggerFactory(Log mavenLog) {
        // 使用自定义的 LoggerFactory 初始化
//        MavenLoggerFactory customLoggerFactory = new MavenLoggerFactory(mavenLog);

        MavenPluginLogging.setMavenLog(mavenLog);

        // 将自定义的 LoggerFactory 设置到 SLF4J
        // 假设通过反射或其他机制替换默认的 LoggerFactory
        System.setProperty("slf4j.loggerFactory", MavenLoggerFactory.class.getName());
    }
}
