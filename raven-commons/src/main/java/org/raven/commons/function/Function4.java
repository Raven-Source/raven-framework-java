package org.raven.commons.function;

@FunctionalInterface
public interface Function4<A, A2, A3, A4, R> {
    R apply(A a, A2 a2, A3 a3, A4 a4);
}
