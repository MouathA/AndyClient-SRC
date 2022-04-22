package com.google.common.base;

import java.util.concurrent.*;
import javax.annotation.*;
import java.io.*;
import com.google.common.annotations.*;

@GwtCompatible
public final class Suppliers
{
    private Suppliers() {
    }
    
    public static Supplier compose(final Function function, final Supplier supplier) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(supplier);
        return new SupplierComposition(function, supplier);
    }
    
    public static Supplier memoize(final Supplier supplier) {
        return (supplier instanceof MemoizingSupplier) ? supplier : new MemoizingSupplier((Supplier)Preconditions.checkNotNull(supplier));
    }
    
    public static Supplier memoizeWithExpiration(final Supplier supplier, final long n, final TimeUnit timeUnit) {
        return new ExpiringMemoizingSupplier(supplier, n, timeUnit);
    }
    
    public static Supplier ofInstance(@Nullable final Object o) {
        return new SupplierOfInstance(o);
    }
    
    public static Supplier synchronizedSupplier(final Supplier supplier) {
        return new ThreadSafeSupplier((Supplier)Preconditions.checkNotNull(supplier));
    }
    
    @Beta
    public static Function supplierFunction() {
        return SupplierFunctionImpl.INSTANCE;
    }
    
    private enum SupplierFunctionImpl implements SupplierFunction
    {
        INSTANCE("INSTANCE", 0);
        
        private static final SupplierFunctionImpl[] $VALUES;
        
        private SupplierFunctionImpl(final String s, final int n) {
        }
        
        public Object apply(final Supplier supplier) {
            return supplier.get();
        }
        
        @Override
        public String toString() {
            return "Suppliers.supplierFunction()";
        }
        
        @Override
        public Object apply(final Object o) {
            return this.apply((Supplier)o);
        }
        
        static {
            $VALUES = new SupplierFunctionImpl[] { SupplierFunctionImpl.INSTANCE };
        }
    }
    
    private interface SupplierFunction extends Function
    {
    }
    
    private static class ThreadSafeSupplier implements Supplier, Serializable
    {
        final Supplier delegate;
        private static final long serialVersionUID = 0L;
        
        ThreadSafeSupplier(final Supplier delegate) {
            this.delegate = delegate;
        }
        
        @Override
        public Object get() {
            // monitorenter(delegate = this.delegate)
            // monitorexit(delegate)
            return this.delegate.get();
        }
        
        @Override
        public String toString() {
            return "Suppliers.synchronizedSupplier(" + this.delegate + ")";
        }
    }
    
    private static class SupplierOfInstance implements Supplier, Serializable
    {
        final Object instance;
        private static final long serialVersionUID = 0L;
        
        SupplierOfInstance(@Nullable final Object instance) {
            this.instance = instance;
        }
        
        @Override
        public Object get() {
            return this.instance;
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof SupplierOfInstance && Objects.equal(this.instance, ((SupplierOfInstance)o).instance);
        }
        
        @Override
        public int hashCode() {
            return Objects.hashCode(this.instance);
        }
        
        @Override
        public String toString() {
            return "Suppliers.ofInstance(" + this.instance + ")";
        }
    }
    
    @VisibleForTesting
    static class ExpiringMemoizingSupplier implements Supplier, Serializable
    {
        final Supplier delegate;
        final long durationNanos;
        transient Object value;
        transient long expirationNanos;
        private static final long serialVersionUID = 0L;
        
        ExpiringMemoizingSupplier(final Supplier supplier, final long n, final TimeUnit timeUnit) {
            this.delegate = (Supplier)Preconditions.checkNotNull(supplier);
            this.durationNanos = timeUnit.toNanos(n);
            Preconditions.checkArgument(n > 0L);
        }
        
        @Override
        public Object get() {
            final long expirationNanos = this.expirationNanos;
            final long systemNanoTime = Platform.systemNanoTime();
            if (expirationNanos == 0L || systemNanoTime - expirationNanos >= 0L) {
                // monitorenter(this)
                if (expirationNanos == this.expirationNanos) {
                    final Object value = this.delegate.get();
                    this.value = value;
                    final long n = systemNanoTime + this.durationNanos;
                    this.expirationNanos = ((n == 0L) ? 1L : n);
                    // monitorexit(this)
                    return value;
                }
            }
            // monitorexit(this)
            return this.value;
        }
        
        @Override
        public String toString() {
            return "Suppliers.memoizeWithExpiration(" + this.delegate + ", " + this.durationNanos + ", NANOS)";
        }
    }
    
    @VisibleForTesting
    static class MemoizingSupplier implements Supplier, Serializable
    {
        final Supplier delegate;
        transient boolean initialized;
        transient Object value;
        private static final long serialVersionUID = 0L;
        
        MemoizingSupplier(final Supplier delegate) {
            this.delegate = delegate;
        }
        
        @Override
        public Object get() {
            if (!this.initialized) {
                // monitorenter(this)
                if (!this.initialized) {
                    final Object value = this.delegate.get();
                    this.value = value;
                    this.initialized = true;
                    // monitorexit(this)
                    return value;
                }
            }
            // monitorexit(this)
            return this.value;
        }
        
        @Override
        public String toString() {
            return "Suppliers.memoize(" + this.delegate + ")";
        }
    }
    
    private static class SupplierComposition implements Supplier, Serializable
    {
        final Function function;
        final Supplier supplier;
        private static final long serialVersionUID = 0L;
        
        SupplierComposition(final Function function, final Supplier supplier) {
            this.function = function;
            this.supplier = supplier;
        }
        
        @Override
        public Object get() {
            return this.function.apply(this.supplier.get());
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            if (o instanceof SupplierComposition) {
                final SupplierComposition supplierComposition = (SupplierComposition)o;
                return this.function.equals(supplierComposition.function) && this.supplier.equals(supplierComposition.supplier);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return Objects.hashCode(this.function, this.supplier);
        }
        
        @Override
        public String toString() {
            return "Suppliers.compose(" + this.function + ", " + this.supplier + ")";
        }
    }
}
