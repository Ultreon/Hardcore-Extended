package com.zonlykroks.hardcoreex.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public final class SingularMessage {
    private static final Set<Object> executed = new HashSet<>();
    private static final Map<Object, Object> values = new HashMap<>();
    private static final Object lock = new Object();

    public static void exec(Object key, Runnable run) {
        synchronized (lock) {
            if (!executed.contains(key)) {
                executed.add(key);
                run.run();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T exec(Object key, Supplier<T> run) {
        synchronized (lock) {
            T t = (T) values.get(key);
            if (!executed.contains(key)) {
                t = run.get();
                values.put(key, t);
            }
            return t;
        }
    }
}
