package org.raven.commons.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Slf4j
public final class NetUtils {

    private final NetProperties netProperties;

    public NetUtils(NetProperties netProperties) {
        this.netProperties = netProperties;
    }

    public InetAddress findFirstNonLoopbackAddress() {
        InetAddress result = null;
        try {
            for (Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                 networkInterfaces.hasMoreElements(); ) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface.isUp()) {
                    if (!ignoreInterface(networkInterface.getDisplayName())) {
                        for (Enumeration<InetAddress> addresses = networkInterface
                                .getInetAddresses(); addresses.hasMoreElements(); ) {
                            InetAddress address = addresses.nextElement();
                            if (address instanceof Inet4Address
                                    && !address.isLoopbackAddress()
                                    && isPreferredAddress(address)) {
                                result = address;
                            }
                        }
                    }
                }
            }
        } catch (IOException exception) {
            log.error("Cannot get first non-loopback address", exception);
        }
        if (result != null) {
            return result;
        }
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException("Unable to retrieve localhost");
        }
    }

    boolean isPreferredAddress(InetAddress address) {
        final List<String> preferredNetworks = this.netProperties.getPreferredNetworks();
        if (preferredNetworks.isEmpty()) {
            return true;
        }
        for (String regex : preferredNetworks) {
            final String hostAddress = address.getHostAddress();
            if (hostAddress.matches(regex) || hostAddress.startsWith(regex)) {
                return true;
            }
        }
        return false;
    }

    boolean ignoreInterface(String interfaceName) {
        List<String> ignoredInterfaces = this.netProperties.getIgnoredInterfaces();
        for (String regex : ignoredInterfaces) {
            if (interfaceName.matches(regex)) {
                return true;
            }
        }
        return false;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NetProperties {

        /**
         * 首选网络地址 (例如: 192.168.1,支持正则)
         */
        private List<String> preferredNetworks = new ArrayList<>();

        /**
         * 忽略网卡(例如:eth0,,支持正则)
         */
        private List<String> ignoredInterfaces = new ArrayList<>();

    }
}