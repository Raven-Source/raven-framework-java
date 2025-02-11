package org.raven.commons.context.impl;

import org.raven.commons.context.Context;
import org.raven.commons.exception.NotImplementedException;

import java.util.Map;

public class IgnoreContext implements Context {

    public final static IgnoreContext INSTANCE = new IgnoreContext();

    private IgnoreContext() {
    }

    public Map<String, Object> getAttributes() {
        throw new NotImplementedException();
    }

    public Object getAttribute(String name) {
        throw new NotImplementedException();
    }

    public void setAttribute(String name, Object object) {
        throw new NotImplementedException();
    }
}
