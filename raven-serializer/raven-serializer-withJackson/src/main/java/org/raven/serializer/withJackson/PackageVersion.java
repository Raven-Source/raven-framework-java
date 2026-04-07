package org.raven.serializer.withJackson;


import tools.jackson.core.Version;
import tools.jackson.core.util.VersionUtil;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.06.29 01:27
 */
public class PackageVersion {

    public static final Version VERSION = VersionUtil.parseVersion("5.0.0", "io.github.raven-source", "raven-serializer-withJackson");

    public PackageVersion() {
    }

    public Version version() {
        return VERSION;
    }
}
