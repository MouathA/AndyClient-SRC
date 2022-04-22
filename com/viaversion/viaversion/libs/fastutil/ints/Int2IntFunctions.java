package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.*;
import java.util.function.*;
import java.io.*;

public final class Int2IntFunctions
{
    public static final EmptyFunction EMPTY_FUNCTION;
    
    private Int2IntFunctions() {
    }
    
    public static Int2IntFunction singleton(final int n, final int n2) {
        return new Singleton(n, n2);
    }
    
    public static Int2IntFunction singleton(final Integer n, final Integer n2) {
        return new Singleton(n, n2);
    }
    
    public static Int2IntFunction synchronize(final Int2IntFunction int2IntFunction) {
        return (Int2IntFunction)new Int2IntFunctions.SynchronizedFunction(int2IntFunction);
    }
    
    public static Int2IntFunction synchronize(final Int2IntFunction int2IntFunction, final Object o) {
        return (Int2IntFunction)new Int2IntFunctions.SynchronizedFunction(int2IntFunction, o);
    }
    
    public static Int2IntFunction unmodifiable(final Int2IntFunction int2IntFunction) {
        return (Int2IntFunction)new Int2IntFunctions.UnmodifiableFunction(int2IntFunction);
    }
    
    public static Int2IntFunction primitive(final Function function) {
        Objects.requireNonNull(function);
        if (function instanceof Int2IntFunction) {
            return (Int2IntFunction)function;
        }
        if (function instanceof IntUnaryOperator) {
            final Int2IntFunction int2IntFunction = (Int2IntFunction)function;
            Objects.requireNonNull(int2IntFunction);
            return int2IntFunction::applyAsInt;
        }
        return new PrimitiveFunction(function);
    }
    
    static {
        EMPTY_FUNCTION = new EmptyFunction();
    }
    
    public static class Singleton extends AbstractInt2IntFunction implements Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int key;
        protected final int value;
        
        protected Singleton(final int key, final int value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public boolean containsKey(final int n) {
            return this.key == n;
        }
        
        @Override
        public int get(final int n) {
            return (this.key == n) ? this.value : this.defRetValue;
        }
        
        @Override
        public int getOrDefault(final int n, final int n2) {
            return (this.key == n) ? this.value : n2;
        }
        
        @Override
        public int size() {
            return 1;
        }
        
        public Object clone() {
            return this;
        }
    }
    
    public static class PrimitiveFunction implements Int2IntFunction
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
        public int get(final int n) {
            final Integer n2 = this.function.apply(n);
            if (n2 == null) {
                return this.defaultReturnValue();
            }
            return n2;
        }
        
        @Override
        public int getOrDefault(final int n, final int n2) {
            final Integer n3 = this.function.apply(n);
            if (n3 == null) {
                return n2;
            }
            return n3;
        }
        
        @Deprecated
        @Override
        public Integer get(final Object o) {
            if (o == null) {
                return null;
            }
            return this.function.apply(o);
        }
        
        @Deprecated
        @Override
        public Integer getOrDefault(final Object o, final Integer n) {
            if (o == null) {
                return n;
            }
            final Integer n2;
            return ((n2 = this.function.apply(o)) == null) ? n : n2;
        }
        
        @Deprecated
        @Override
        public Integer put(final Integer n, final Integer n2) {
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
            return this.put((Integer)o, (Integer)o2);
        }
    }
    
    public static class EmptyFunction extends AbstractInt2IntFunction implements Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        protected EmptyFunction() {
        }
        
        @Override
        public int get(final int n) {
            return 0;
        }
        
        @Override
        public int getOrDefault(final int n, final int n2) {
            return n2;
        }
        
        @Override
        public boolean containsKey(final int n) {
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
            return Int2IntFunctions.EMPTY_FUNCTION;
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
            return Int2IntFunctions.EMPTY_FUNCTION;
        }
    }
}
