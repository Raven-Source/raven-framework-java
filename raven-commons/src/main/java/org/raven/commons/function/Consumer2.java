package org.raven.commons.function;

@FunctionalInterface
public interface Consumer2<A, A2> {
    void accept(A a, A2 a2);
}
