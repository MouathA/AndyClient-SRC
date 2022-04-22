package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.*;
import java.util.function.*;
import java.io.*;

public final class Object2IntFunctions
{
    public static final EmptyFunction EMPTY_FUNCTION;
    
    private Object2IntFunctions() {
    }
    
    public static Object2IntFunction singleton(final Object o, final int n) {
        return new Singleton(o, n);
    }
    
    public static Object2IntFunction singleton(final Object o, final Integer n) {
        return new Singleton(o, n);
    }
    
    public static Object2IntFunction synchronize(final Object2IntFunction object2IntFunction) {
        return (Object2IntFunction)new Object2IntFunctions.SynchronizedFunction(object2IntFunction);
    }
    
    public static Object2IntFunction synchronize(final Object2IntFunction object2IntFunction, final Object o) {
        return (Object2IntFunction)new Object2IntFunctions.SynchronizedFunction(object2IntFunction, o);
    }
    
    public static Object2IntFunction unmodifiable(final Object2IntFunction object2IntFunction) {
        return (Object2IntFunction)new Object2IntFunctions.UnmodifiableFunction(object2IntFunction);
    }
    
    public static Object2IntFunction primitive(final Function function) {
        Objects.requireNonNull(function);
        if (function instanceof Object2IntFunction) {
            return (Object2IntFunction)function;
        }
        if (function instanceof ToIntFunction) {
            return Object2IntFunctions::lambda$primitive$0;
        }
        return new PrimitiveFunction(function);
    }
    
    private static int lambda$primitive$0(final Function function, final Object o) {
        return ((ToIntFunction<Object>)function).applyAsInt(o);
    }
    
    static {
        EMPTY_FUNCTION = new EmptyFunction();
    }
    
    public static class Singleton extends AbstractObject2IntFunction implements Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Object key;
        protected final int value;
        
        protected Singleton(final Object key, final int value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public boolean containsKey(final Object o) {
            return Objects.equals(this.key, o);
        }
        
        @Override
        public int getInt(final Object o) {
            return Objects.equals(this.key, o) ? this.value : this.defRetValue;
        }
        
        @Override
        public int getOrDefault(final Object o, final int n) {
            return Objects.equals(this.key, o) ? this.value : n;
        }
        
        @Override
        public int size() {
            return 1;
        }
        
        public Object clone() {
            return this;
        }
    }
    
    public static class PrimitiveFunction implements Object2IntFunction
    {
        protected final java.util.function.Function function;
        
        protected PrimitiveFunction(final java.util.function.Function function) {
            this.function = function;
        }
        
        @Override
        public boolean containsKey(final Object o) {
            return this.function.apply(o) != null;
        }
        
        @Override
        public int getInt(final Object o) {
            final Integer n = this.function.apply(o);
            if (n == null) {
                return this.defaultReturnValue();
            }
            return n;
        }
        
        @Override
        public int getOrDefault(final Object o, final int n) {
            final Integer n2 = this.function.apply(o);
            if (n2 == null) {
                return n;
            }
            return n2;
        }
        
        @Deprecated
        @Override
        public Integer get(final Object o) {
            return this.function.apply(o);
        }
        
        @Deprecated
        @Override
        public Integer getOrDefault(final Object o, final Integer n) {
            final Integer n2;
            return ((n2 = this.function.apply(o)) == null) ? n : n2;
        }
        
        @Deprecated
        @Override
        public Integer put(final Object o, final Integer n) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public Object getOrDefault(final Object o, final Object o2) {
            return this.getOrDefault(o, (Integer)o2);
        }
        
        @Deprecated
        @Override
        public Object get(final Object o) {
            return this.get(o);
        }
        
        @Deprecated
        @Override
        public Object put(final Object o, final Object o2) {
            return this.put(o, (Integer)o2);
        }
    }
    
    public static class EmptyFunction extends AbstractObject2IntFunction implements Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        protected EmptyFunction() {
        }
        
        @Override
        public int getInt(final Object o) {
            return 0;
        }
        
        @Override
        public int getOrDefault(final Object o, final int n) {
            return n;
        }
        
        @Override
        public boolean containsKey(final Object o) {
            return false;
        }
        
        @Override
        public int defaultReturnValue() {
            return 0;
        }
        
        @Override
        public void defaultReturnValue(final int n) {
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
            return Object2IntFunctions.EMPTY_FUNCTION;
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
            return Object2IntFunctions.EMPTY_FUNCTION;
        }
    }
}
