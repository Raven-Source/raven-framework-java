package org.raven.api.codegen.plugin;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

public class MavenLoggerFactory implements ILoggerFactory {

//    public MavenLoggerFactory() {
//        System.out.println("MavenLoggerFactory");
//    }

    @Override
    public Logger getLogger(String name) {
        return new MavenLogger(MavenPluginLogging.getMavenLog());  // 使用自定义的 MavenLogger
    }
}
