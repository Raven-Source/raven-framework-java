package org.raven.commons.function;

@FunctionalInterface
public interface Consumer<A> {
    void accept(A a);
}
