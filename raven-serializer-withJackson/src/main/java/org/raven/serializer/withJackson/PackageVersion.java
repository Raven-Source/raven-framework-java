package org.raven.serializer.withJackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.VersionUtil;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.06.29 01:27
 */
public class PackageVersion {

    public static final Version VERSION = VersionUtil.parseVersion("4.0.7", "io.github.raven-source", "raven-serializer-withJackson");

    public PackageVersion() {
    }

    public Version version() {
        return VERSION;
    }
}
