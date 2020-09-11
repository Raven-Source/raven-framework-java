package org.raven.web.spring.identity;

import lombok.NonNull;
import org.raven.web.spi.identity.Context;
import org.raven.web.spi.identity.ContextHolder;
import org.raven.web.spi.identity.Identity;
import org.raven.web.spi.identity.IdentityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class IdentityContextDefaultImpl implements IdentityContext {

    @Autowired
    private ContextHolder contextHolder;

    @Override
    public Identity currentIdentity() {
        return (Identity) this.contextHolder.getContext();
    }

    @Override
    public Object getAttribute(@NonNull String key) {
        return this.contextHolder.getContext().getAttributes().get(key);
    }

    @Override
    public void setAttribute(@NonNull String key, Object value) {

        Context context = this.contextHolder.getContext();
        Map<String, Object> attributes = context.getAttributes();
        attributes.put(key, value);
    }
}
