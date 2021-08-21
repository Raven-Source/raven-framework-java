package org.raven.commons.function;

@FunctionalInterface
public interface Function<A, R> {
    R apply(A a);
}
