package org.raven.web.spring.identity;

import org.raven.web.spi.identity.Context;
import org.raven.web.spi.identity.ContextHolder;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;
import java.util.function.Function;

@Component
public class ContextHolderDefaultImpl implements ContextHolder {

    private ThreadLocal<Context> contextThreadLocal = new ThreadLocal();

    @Override
    public boolean available() {
        return this.contextThreadLocal.get() != null;
    }

    @Override
    public void putContext(Context context) {
        this.contextThreadLocal.set(context);
    }

    @Override
    public Context getContext() {
        Context ctx = (Context) this.contextThreadLocal.get();
        if (ctx == null) {
            ctx = new Context();
            this.contextThreadLocal.set(ctx);
        }

        return ctx;
    }

    @Override
    public void clearContext() {
        this.contextThreadLocal.remove();
    }

    @Override
    public <T, R> R invokeWithContext(Context context, Function<T, R> func, T t) {

        Context originalContext = (Context) this.contextThreadLocal.get();
        boolean needReplace = context != null && context != originalContext;

        R res;
        try {
            if (needReplace) {
                this.putContext(context);
            }

            res = func.apply(t);
        } finally {
            if (needReplace) {
                this.clearContext();
                if (originalContext != null) {
                    this.putContext(originalContext);
                }
            }

        }

        return res;
    }

    @Override
    public <T, U, R> R invokeWithContext(Context context, BiFunction<T, U, R> func, T t, U u) {

        Context originalContext = (Context) this.contextThreadLocal.get();
        boolean needReplace = context != null && context != originalContext;

        R res;
        try {
            if (needReplace) {
                this.putContext(context);
            }

            res = func.apply(t, u);
        } finally {
            if (needReplace) {
                this.clearContext();
                if (originalContext != null) {
                    this.putContext(originalContext);
                }
            }

        }

        return res;
    }


}
