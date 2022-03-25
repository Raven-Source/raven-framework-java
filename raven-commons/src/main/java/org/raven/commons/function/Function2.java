package org.raven.commons.function;

@FunctionalInterface
public interface Function2<A, A2, R> {
    R apply(A a, A2 a2);
}
