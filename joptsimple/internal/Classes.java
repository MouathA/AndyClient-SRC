package joptsimple.internal;

import java.util.*;

public final class Classes
{
    private static final Map WRAPPERS;
    
    private Classes() {
        throw new UnsupportedOperationException();
    }
    
    public static String shortNameOf(final String s) {
        return s.substring(s.lastIndexOf(46) + 1);
    }
    
    public static Class wrapperOf(final Class clazz) {
        return clazz.isPrimitive() ? Classes.WRAPPERS.get(clazz) : clazz;
    }
    
    static {
        (WRAPPERS = new HashMap(13)).put(Boolean.TYPE, Boolean.class);
        Classes.WRAPPERS.put(Byte.TYPE, Byte.class);
        Classes.WRAPPERS.put(Character.TYPE, Character.class);
        Classes.WRAPPERS.put(Double.TYPE, Double.class);
        Classes.WRAPPERS.put(Float.TYPE, Float.class);
        Classes.WRAPPERS.put(Integer.TYPE, Integer.class);
        Classes.WRAPPERS.put(Long.TYPE, Long.class);
        Classes.WRAPPERS.put(Short.TYPE, Short.class);
        Classes.WRAPPERS.put(Void.TYPE, Void.class);
    }
}
