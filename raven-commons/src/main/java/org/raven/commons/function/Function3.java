package org.raven.commons.function;

@FunctionalInterface
public interface Function3<A, A2, A3, R> {
    R apply(A a, A2 a2, A3 a3);
}
