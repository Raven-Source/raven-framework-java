package org.raven.web.spi.identity;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface ContextHolder {

    boolean available();

    void putContext(Context var1);

    Context getContext();

    void clearContext();

    <T, R> R invokeWithContext(Context context, Function<T, R> func, T t);

    <T, U, R> R invokeWithContext(Context context, BiFunction<T, U, R> func, T t, U u);


}
