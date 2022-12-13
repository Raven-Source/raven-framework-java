package org.raven.commons.context.impl;

import org.raven.commons.context.Context;
import org.raven.commons.context.ContextHolder;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author by yanfeng
 * date 2021/10/3 21:28
 */
public class ContextHolderSupport implements ContextHolder {

    private final ThreadLocal<Context> contextThreadLocal = new ThreadLocal<>();

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
        Context ctx = this.contextThreadLocal.get();
        if (ctx == null) {
            ctx = new ContextSupport();
            this.contextThreadLocal.set(ctx);
        }

        return ctx;
    }

    @Override
    public void clearContext() {
        this.contextThreadLocal.remove();
    }

    @Override
    public <R> R invokeWithContext(Context context, Supplier<R> func) {
        Context originalContext = this.contextThreadLocal.get();
        boolean needReplace = context != null && context != originalContext;

        R res;
        try {
            if (needReplace) {
                this.putContext(context);
            }

            res = func.get();
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
    public void invokeWithContext(Context context, Runnable func) {

        Context originalContext = this.contextThreadLocal.get();
        boolean needReplace = context != null && context != originalContext;

        try {
            if (needReplace) {
                this.putContext(context);
            }
            func.run();
        } finally {
            if (needReplace) {
                this.clearContext();
                if (originalContext != null) {
                    this.putContext(originalContext);
                }
            }

        }
    }

    @Override
    public <T, R> R invokeWithContext(Context context, Function<T, R> func, T t) {
        Context originalContext = this.contextThreadLocal.get();
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
    public <T> void invokeWithContext(Context context, Consumer<T> func, T t) {

        Context originalContext = this.contextThreadLocal.get();
        boolean needReplace = context != null && context != originalContext;

        try {
            if (needReplace) {
                this.putContext(context);
            }
            func.accept(t);
        } finally {
            if (needReplace) {
                this.clearContext();
                if (originalContext != null) {
                    this.putContext(originalContext);
                }
            }

        }

    }


}
