package org.raven.api.codegen.plugin;

import lombok.Getter;
import org.apache.maven.plugin.logging.Log;

public class MavenPluginLogging {

    @Getter
    private static Log mavenLog;

    protected static void setMavenLog(Log mavenLog) {
        MavenPluginLogging.mavenLog = mavenLog;
    }
}
