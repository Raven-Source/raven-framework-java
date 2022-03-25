package org.raven.commons.function;

@FunctionalInterface
public interface Consumer3<A, A2, A3> {
    void accept(A a, A2 a2, A3 a3);
}
