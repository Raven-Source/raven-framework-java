package org.raven.commons.function;

@FunctionalInterface
public interface Function5<A, A2, A3, A4, A5, R> {
    R apply(A a, A2 a2, A3 a3, A4 a4, A5 a5);
}
