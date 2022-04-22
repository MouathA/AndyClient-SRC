package com.google.common.base;

import com.google.common.annotations.*;
import java.lang.reflect.*;
import java.lang.ref.*;
import java.util.*;
import java.io.*;
import javax.annotation.*;

@GwtCompatible(emulated = true)
@Beta
public final class Enums
{
    @GwtIncompatible("java.lang.ref.WeakReference")
    private static final Map enumConstantCache;
    
    private Enums() {
    }
    
    @GwtIncompatible("reflection")
    public static Field getField(final Enum enum1) {
        return enum1.getDeclaringClass().getDeclaredField(enum1.name());
    }
    
    @Deprecated
    public static Function valueOfFunction(final Class clazz) {
        return new ValueOfFunction(clazz, null);
    }
    
    public static Optional getIfPresent(final Class clazz, final String s) {
        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(s);
        return Platform.getEnumIfPresent(clazz, s);
    }
    
    @GwtIncompatible("java.lang.ref.WeakReference")
    private static Map populateCache(final Class clazz) {
        final HashMap<String, WeakReference<Enum>> hashMap = new HashMap<String, WeakReference<Enum>>();
        for (final Enum enum1 : EnumSet.allOf((Class<Enum>)clazz)) {
            hashMap.put(enum1.name(), new WeakReference<Enum>(enum1));
        }
        Enums.enumConstantCache.put(clazz, hashMap);
        return hashMap;
    }
    
    @GwtIncompatible("java.lang.ref.WeakReference")
    static Map getEnumConstants(final Class clazz) {
        // monitorenter(enumConstantCache = Enums.enumConstantCache)
        Map populateCache = Enums.enumConstantCache.get(clazz);
        if (populateCache == null) {
            populateCache = populateCache(clazz);
        }
        // monitorexit(enumConstantCache)
        return populateCache;
    }
    
    public static Converter stringConverter(final Class clazz) {
        return new StringConverter(clazz);
    }
    
    static {
        enumConstantCache = new WeakHashMap();
    }
    
    private static final class StringConverter extends Converter implements Serializable
    {
        private final Class enumClass;
        private static final long serialVersionUID = 0L;
        
        StringConverter(final Class clazz) {
            this.enumClass = (Class)Preconditions.checkNotNull(clazz);
        }
        
        protected Enum doForward(final String s) {
            return Enum.valueOf((Class<Enum>)this.enumClass, s);
        }
        
        protected String doBackward(final Enum enum1) {
            return enum1.name();
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof StringConverter && this.enumClass.equals(((StringConverter)o).enumClass);
        }
        
        @Override
        public int hashCode() {
            return this.enumClass.hashCode();
        }
        
        @Override
        public String toString() {
            return "Enums.stringConverter(" + this.enumClass.getName() + ".class)";
        }
        
        @Override
        protected Object doBackward(final Object o) {
            return this.doBackward((Enum)o);
        }
        
        @Override
        protected Object doForward(final Object o) {
            return this.doForward((String)o);
        }
    }
    
    private static final class ValueOfFunction implements Function, Serializable
    {
        private final Class enumClass;
        private static final long serialVersionUID = 0L;
        
        private ValueOfFunction(final Class clazz) {
            this.enumClass = (Class)Preconditions.checkNotNull(clazz);
        }
        
        public Enum apply(final String s) {
            return Enum.valueOf((Class<Enum>)this.enumClass, s);
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof ValueOfFunction && this.enumClass.equals(((ValueOfFunction)o).enumClass);
        }
        
        @Override
        public int hashCode() {
            return this.enumClass.hashCode();
        }
        
        @Override
        public String toString() {
            return "Enums.valueOf(" + this.enumClass + ")";
        }
        
        @Override
        public Object apply(final Object o) {
            return this.apply((String)o);
        }
        
        ValueOfFunction(final Class clazz, final Enums$1 object) {
            this(clazz);
        }
    }
}
