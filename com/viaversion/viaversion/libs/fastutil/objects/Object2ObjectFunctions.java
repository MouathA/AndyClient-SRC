package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.*;

public final class Object2ObjectFunctions
{
    public static final EmptyFunction EMPTY_FUNCTION;
    
    private Object2ObjectFunctions() {
    }
    
    public static Object2ObjectFunction singleton(final Object o, final Object o2) {
        return new Singleton(o, o2);
    }
    
    public static Object2ObjectFunction synchronize(final Object2ObjectFunction object2ObjectFunction) {
        return (Object2ObjectFunction)new Object2ObjectFunctions.SynchronizedFunction(object2ObjectFunction);
    }
    
    public static Object2ObjectFunction synchronize(final Object2ObjectFunction object2ObjectFunction, final Object o) {
        return (Object2ObjectFunction)new Object2ObjectFunctions.SynchronizedFunction(object2ObjectFunction, o);
    }
    
    public static Object2ObjectFunction unmodifiable(final Object2ObjectFunction object2ObjectFunction) {
        return (Object2ObjectFunction)new Object2ObjectFunctions.UnmodifiableFunction(object2ObjectFunction);
    }
    
    static {
        EMPTY_FUNCTION = new EmptyFunction();
    }
    
    public static class Singleton extends AbstractObject2ObjectFunction implements Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Object key;
        protected final Object value;
        
        protected Singleton(final Object key, final Object value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public boolean containsKey(final Object o) {
            return Objects.equals(this.key, o);
        }
        
        @Override
        public Object get(final Object o) {
            return Objects.equals(this.key, o) ? this.value : this.defRetValue;
        }
        
        @Override
        public Object getOrDefault(final Object o, final Object o2) {
            return Objects.equals(this.key, o) ? this.value : o2;
        }
        
        @Override
        public int size() {
            return 1;
        }
        
        public Object clone() {
            return this;
        }
    }
    
    public static class EmptyFunction extends AbstractObject2ObjectFunction implements Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        protected EmptyFunction() {
        }
        
        @Override
        public Object get(final Object o) {
            return null;
        }
        
        @Override
        public Object getOrDefault(final Object o, final Object o2) {
            return o2;
        }
        
        @Override
        public boolean containsKey(final Object o) {
            return false;
        }
        
        @Override
        public Object defaultReturnValue() {
            return null;
        }
        
        @Override
        public void defaultReturnValue(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public int size() {
            return 0;
        }
        
        @Override
        public void clear() {
        }
        
        public Object clone() {
            return Object2ObjectFunctions.EMPTY_FUNCTION;
        }
        
        @Override
        public int hashCode() {
            return 0;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof Function && ((Function)o).size() == 0;
        }
        
        @Override
        public String toString() {
            return "{}";
        }
        
        private Object readResolve() {
            return Object2ObjectFunctions.EMPTY_FUNCTION;
        }
    }
}
