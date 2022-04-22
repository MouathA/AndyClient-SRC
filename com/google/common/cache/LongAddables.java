package com.google.common.cache;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.concurrent.atomic.*;

@GwtCompatible(emulated = true)
final class LongAddables
{
    private static final Supplier SUPPLIER;
    
    public static LongAddable create() {
        return (LongAddable)LongAddables.SUPPLIER.get();
    }
    
    static {
        new LongAdder();
        SUPPLIER = new Supplier() {
            @Override
            public LongAddable get() {
                return new LongAdder();
            }
            
            @Override
            public Object get() {
                return this.get();
            }
        };
    }
    
    private static final class PureJavaLongAddable extends AtomicLong implements LongAddable
    {
        private PureJavaLongAddable() {
        }
        
        @Override
        public void increment() {
            this.getAndIncrement();
        }
        
        @Override
        public void add(final long n) {
            this.getAndAdd(n);
        }
        
        @Override
        public long sum() {
            return this.get();
        }
        
        PureJavaLongAddable(final LongAddables$1 supplier) {
            this();
        }
    }
}
