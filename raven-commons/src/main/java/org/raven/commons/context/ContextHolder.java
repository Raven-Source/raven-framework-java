package org.raven.commons.context;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author by yanfeng
 * date 2021/10/3 20:19
 */
public interface ContextHolder {

    boolean available();

    void putContext(Context context);

    Context getContext();

    void clearContext();

    <R> R invokeWithContext(Context context, Supplier<R> func);

    void invokeWithContext(Context context, Runnable func);

    <T, R> R invokeWithContext(Context context, Function<T, R> func, T t);

    <T> void invokeWithContext(Context context, Consumer<T> func, T t);
}
