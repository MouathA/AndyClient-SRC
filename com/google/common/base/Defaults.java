package com.google.common.base;

import java.util.*;

public final class Defaults
{
    private static final Map DEFAULTS;
    
    private Defaults() {
    }
    
    private static void put(final Map map, final Class clazz, final Object o) {
        map.put(clazz, o);
    }
    
    public static Object defaultValue(final Class clazz) {
        return Defaults.DEFAULTS.get(Preconditions.checkNotNull(clazz));
    }
    
    static {
        final HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        put(hashMap, Boolean.TYPE, false);
        put(hashMap, Character.TYPE, '\0');
        put(hashMap, Byte.TYPE, 0);
        put(hashMap, Short.TYPE, 0);
        put(hashMap, Integer.TYPE, 0);
        put(hashMap, Long.TYPE, 0L);
        put(hashMap, Float.TYPE, 0.0f);
        put(hashMap, Double.TYPE, 0.0);
        DEFAULTS = Collections.unmodifiableMap((Map<?, ?>)hashMap);
    }
}
