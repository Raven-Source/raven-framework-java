package org.raven.api.codegen;

public abstract class AbstractFileCodeGenerator {

    protected final String outputRoot;

    public AbstractFileCodeGenerator(String outputRoot) {
        this.outputRoot = outputRoot;
    }
}
