package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.*;
import java.util.function.*;
import java.io.*;

public final class Int2ObjectFunctions
{
    public static final EmptyFunction EMPTY_FUNCTION;
    
    private Int2ObjectFunctions() {
    }
    
    public static Int2ObjectFunction singleton(final int n, final Object o) {
        return new Singleton(n, o);
    }
    
    public static Int2ObjectFunction singleton(final Integer n, final Object o) {
        return new Singleton(n, o);
    }
    
    public static Int2ObjectFunction synchronize(final Int2ObjectFunction int2ObjectFunction) {
        return (Int2ObjectFunction)new Int2ObjectFunctions.SynchronizedFunction(int2ObjectFunction);
    }
    
    public static Int2ObjectFunction synchronize(final Int2ObjectFunction int2ObjectFunction, final Object o) {
        return (Int2ObjectFunction)new Int2ObjectFunctions.SynchronizedFunction(int2ObjectFunction, o);
    }
    
    public static Int2ObjectFunction unmodifiable(final Int2ObjectFunction int2ObjectFunction) {
        return (Int2ObjectFunction)new Int2ObjectFunctions.UnmodifiableFunction(int2ObjectFunction);
    }
    
    public static Int2ObjectFunction primitive(final Function function) {
        Objects.requireNonNull(function);
        if (function instanceof Int2ObjectFunction) {
            return (Int2ObjectFunction)function;
        }
        if (function instanceof IntFunction) {
            final Int2ObjectFunction int2ObjectFunction = (Int2ObjectFunction)function;
            Objects.requireNonNull(int2ObjectFunction);
            return int2ObjectFunction::apply;
        }
        return new PrimitiveFunction(function);
    }
    
    static {
        EMPTY_FUNCTION = new EmptyFunction();
    }
    
    public static class Singleton extends AbstractInt2ObjectFunction implements Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int key;
        protected final Object value;
        
        protected Singleton(final int key, final Object value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public boolean containsKey(final int n) {
            return this.key == n;
        }
        
        @Override
        public Object get(final int n) {
            return (this.key == n) ? this.value : this.defRetValue;
        }
        
        @Override
        public Object getOrDefault(final int n, final Object o) {
            return (this.key == n) ? this.value : o;
        }
        
        @Override
        public int size() {
            return 1;
        }
        
        public Object clone() {
            return this;
        }
    }
    
    public static class PrimitiveFunction implements Int2ObjectFunction
    {
        protected final java.util.function.Function function;
        
        protected PrimitiveFunction(final java.util.function.Function function) {
            this.function = function;
        }
        
        @Override
        public boolean containsKey(final int n) {
            return this.function.apply(n) != null;
        }
        
        @Deprecated
        @Override
        public boolean containsKey(final Object o) {
            return o != null && this.function.apply(o) != null;
        }
        
        @Override
        public Object get(final int n) {
            final Object apply = this.function.apply(n);
            if (apply == null) {
                return null;
            }
            return apply;
        }
        
        @Override
        public Object getOrDefault(final int n, final Object o) {
            final Object apply = this.function.apply(n);
            if (apply == null) {
                return o;
            }
            return apply;
        }
        
        @Deprecated
        @Override
        public Object get(final Object o) {
            if (o == null) {
                return null;
            }
            return this.function.apply(o);
        }
        
        @Deprecated
        @Override
        public Object getOrDefault(final Object o, final Object o2) {
            if (o == null) {
                return o2;
            }
            final Object apply;
            return ((apply = this.function.apply(o)) == null) ? o2 : apply;
        }
        
        @Deprecated
        @Override
        public Object put(final Integer n, final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public Object put(final Object o, final Object o2) {
            return this.put((Integer)o, o2);
        }
    }
    
    public static class EmptyFunction extends AbstractInt2ObjectFunction implements Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        protected EmptyFunction() {
        }
        
        @Override
        public Object get(final int n) {
            return null;
        }
        
        @Override
        public Object getOrDefault(final int n, final Object o) {
            return o;
        }
        
        @Override
        public boolean containsKey(final int n) {
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
            return Int2ObjectFunctions.EMPTY_FUNCTION;
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
            return Int2ObjectFunctions.EMPTY_FUNCTION;
        }
    }
}
