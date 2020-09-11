package org.raven.web.spi.identity;

public interface Identity<T> {

    T getId();

    String getName();

}
