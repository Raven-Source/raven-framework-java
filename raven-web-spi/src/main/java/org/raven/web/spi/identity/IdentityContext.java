package org.raven.web.spi.identity;

public interface IdentityContext {

    Identity currentIdentity();

    Object getAttribute(String key);

    void setAttribute(String key, Object val);
}
