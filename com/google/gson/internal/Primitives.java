package com.google.gson.internal;

import java.lang.reflect.*;
import java.util.*;

public final class Primitives
{
    private static final Map PRIMITIVE_TO_WRAPPER_TYPE;
    private static final Map WRAPPER_TO_PRIMITIVE_TYPE;
    
    private Primitives() {
    }
    
    private static void add(final Map map, final Map map2, final Class clazz, final Class clazz2) {
        map.put(clazz, clazz2);
        map2.put(clazz2, clazz);
    }
    
    public static boolean isPrimitive(final Type type) {
        return Primitives.PRIMITIVE_TO_WRAPPER_TYPE.containsKey(type);
    }
    
    public static boolean isWrapperType(final Type type) {
        return Primitives.WRAPPER_TO_PRIMITIVE_TYPE.containsKey($Gson$Preconditions.checkNotNull(type));
    }
    
    public static Class wrap(final Class clazz) {
        final Class clazz2 = Primitives.PRIMITIVE_TO_WRAPPER_TYPE.get($Gson$Preconditions.checkNotNull(clazz));
        return (clazz2 == null) ? clazz : clazz2;
    }
    
    public static Class unwrap(final Class clazz) {
        final Class clazz2 = Primitives.WRAPPER_TO_PRIMITIVE_TYPE.get($Gson$Preconditions.checkNotNull(clazz));
        return (clazz2 == null) ? clazz : clazz2;
    }
    
    static {
        final HashMap<Object, Object> hashMap = new HashMap<Object, Object>(16);
        final HashMap<Object, Object> hashMap2 = new HashMap<Object, Object>(16);
        add(hashMap, hashMap2, Boolean.TYPE, Boolean.class);
        add(hashMap, hashMap2, Byte.TYPE, Byte.class);
        add(hashMap, hashMap2, Character.TYPE, Character.class);
        add(hashMap, hashMap2, Double.TYPE, Double.class);
        add(hashMap, hashMap2, Float.TYPE, Float.class);
        add(hashMap, hashMap2, Integer.TYPE, Integer.class);
        add(hashMap, hashMap2, Long.TYPE, Long.class);
        add(hashMap, hashMap2, Short.TYPE, Short.class);
        add(hashMap, hashMap2, Void.TYPE, Void.class);
        PRIMITIVE_TO_WRAPPER_TYPE = Collections.unmodifiableMap((Map<?, ?>)hashMap);
        WRAPPER_TO_PRIMITIVE_TYPE = Collections.unmodifiableMap((Map<?, ?>)hashMap2);
    }
}
