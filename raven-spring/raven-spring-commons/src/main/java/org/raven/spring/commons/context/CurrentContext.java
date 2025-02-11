package org.raven.spring.commons.context;

import org.raven.commons.context.Context;
import org.raven.commons.context.ContextHolder;
import org.raven.commons.context.impl.IgnoreContext;
import org.raven.spring.commons.utils.ContextHolderProvider;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class CurrentContext {

    public static @Nullable Context get() {
        return doInvoke(ContextHolder::getContext);
    }

    public static boolean put(Context context) {
        return doInvoke(holder -> {
            holder.putContext(context);
        });
    }

    public static boolean setAttribute(String key, Object value) {
        Context context = get();
        if (context != null) {
            context.setAttribute(key, value);
            return true;
        }
        return false;
    }

    public static @Nullable Object getAttribute(String key) {
        Context context = get();
        if (context != null) {
            return context.getAttribute(key);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static @Nullable <T> T getOrSetAttribute(String key, Supplier<T> supplier) {

        Context context = get();
        if (context != null) {
            T value = (T) context.getAttribute(key);

            if (value == null) {
                value = supplier.get();
                setAttribute(key, value);
            }
            return value;
        } else {
            return null;
        }
    }

    public static Map<String, Object> getAttributes() {

        Context context = get();
        if (context != null) {
            return Collections.unmodifiableMap(context.getAttributes());
        } else {
            return Collections.emptyMap();
        }
    }

    public static <R> R invoke(Supplier<R> func) {
        return getContextHolder().invokeWithContext(get(), func);
    }

    public static <R> R invokeWithIgnoreContext(Supplier<R> func) {
        return getContextHolder().invokeWithContext(IgnoreContext.INSTANCE, func);
    }

    public static <R> R invokeWithContext(Context context, Supplier<R> func) {
        return getContextHolder().invokeWithContext(context, func);
    }

    public static void invoke(Runnable func) {
        getContextHolder().invokeWithContext(get(), func);
    }

    public static void invokeWithIgnoreContext(Runnable func) {
        getContextHolder().invokeWithContext(IgnoreContext.INSTANCE, func);
    }

    public static void invokeWithContext(Context context, Runnable func) {
        getContextHolder().invokeWithContext(context, func);
    }

    public static <T, R> R invoke(Function<T, R> func, T t) {
        return getContextHolder().invokeWithContext(get(), func, t);
    }

    public static <T, R> R invokeWithIgnoreContext(Function<T, R> func, T t) {
        return getContextHolder().invokeWithContext(IgnoreContext.INSTANCE, func, t);
    }

    public static <T, R> R invokeWithContext(Context context, Function<T, R> func, T t) {
        return getContextHolder().invokeWithContext(context, func, t);
    }

    public static <T> void invoke(Consumer<T> func, T t) {
        getContextHolder().invokeWithContext(get(), func, t);
    }

    public static <T> void invokeWithIgnoreContext(Consumer<T> func, T t) {
        getContextHolder().invokeWithContext(IgnoreContext.INSTANCE, func, t);
    }

    public static <T> void invokeWithContext(Context context, Consumer<T> func, T t) {
        getContextHolder().invokeWithContext(context, func, t);
    }

    public static ContextHolder getContextHolder() {
        return ContextHolderProvider.getContextHolder();
    }

    private static <R> R doInvoke(Function<ContextHolder, R> func) {
        ContextHolder contextHolder = getContextHolder();
        return contextHolder != null ? func.apply(contextHolder) : null;
    }

    private static boolean doInvoke(Consumer<ContextHolder> func) {
        ContextHolder contextHolder = getContextHolder();
        if (contextHolder != null) {
            func.accept(contextHolder);
            return true;
        }
        return false;
    }
}
