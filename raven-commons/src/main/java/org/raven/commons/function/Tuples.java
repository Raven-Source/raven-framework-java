package org.raven.commons.function;

/**
 * date 2022/8/29 20:43
 */
public abstract class Tuples {
    Tuples() {
    }

    static final Tuples empty = new Tuples() {
    };

    static StringBuilder tupleStringRepresentation(Object... values) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < values.length; ++i) {
            Object t = values[i];
            if (i != 0) {
                sb.append(',');
            }

            if (t != null) {
                sb.append(t);
            }
        }

        return sb;
    }
}
