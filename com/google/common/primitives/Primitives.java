package com.google.common.primitives;

import com.google.common.base.*;
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
    
    public static Set allPrimitiveTypes() {
        return Primitives.PRIMITIVE_TO_WRAPPER_TYPE.keySet();
    }
    
    public static Set allWrapperTypes() {
        return Primitives.WRAPPER_TO_PRIMITIVE_TYPE.keySet();
    }
    
    public static boolean isWrapperType(final Class clazz) {
        return Primitives.WRAPPER_TO_PRIMITIVE_TYPE.containsKey(Preconditions.checkNotNull(clazz));
    }
    
    public static Class wrap(final Class clazz) {
        Preconditions.checkNotNull(clazz);
        final Class clazz2 = Primitives.PRIMITIVE_TO_WRAPPER_TYPE.get(clazz);
        return (clazz2 == null) ? clazz : clazz2;
    }
    
    public static Class unwrap(final Class clazz) {
        Preconditions.checkNotNull(clazz);
        final Class clazz2 = Primitives.WRAPPER_TO_PRIMITIVE_TYPE.get(clazz);
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
