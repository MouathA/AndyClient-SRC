package com.google.common.cache;

import java.util.*;
import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.concurrent.*;
import com.google.common.util.concurrent.*;
import java.io.*;

@GwtCompatible(emulated = true)
public abstract class CacheLoader
{
    protected CacheLoader() {
    }
    
    public abstract Object load(final Object p0) throws Exception;
    
    @GwtIncompatible("Futures")
    public ListenableFuture reload(final Object o, final Object o2) throws Exception {
        Preconditions.checkNotNull(o);
        Preconditions.checkNotNull(o2);
        return Futures.immediateFuture(this.load(o));
    }
    
    public Map loadAll(final Iterable iterable) throws Exception {
        throw new UnsupportedLoadingOperationException();
    }
    
    @Beta
    public static CacheLoader from(final Function function) {
        return new FunctionToCacheLoader(function);
    }
    
    @Beta
    public static CacheLoader from(final Supplier supplier) {
        return new SupplierToCacheLoader(supplier);
    }
    
    @Beta
    @GwtIncompatible("Executor + Futures")
    public static CacheLoader asyncReloading(final CacheLoader cacheLoader, final Executor executor) {
        Preconditions.checkNotNull(cacheLoader);
        Preconditions.checkNotNull(executor);
        return new CacheLoader(cacheLoader, executor) {
            final CacheLoader val$loader;
            final Executor val$executor;
            
            @Override
            public Object load(final Object o) throws Exception {
                return this.val$loader.load(o);
            }
            
            @Override
            public ListenableFuture reload(final Object o, final Object o2) throws Exception {
                final ListenableFutureTask create = ListenableFutureTask.create(new Callable(o, o2) {
                    final Object val$key;
                    final Object val$oldValue;
                    final CacheLoader$1 this$0;
                    
                    @Override
                    public Object call() throws Exception {
                        return this.this$0.val$loader.reload(this.val$key, this.val$oldValue).get();
                    }
                });
                this.val$executor.execute(create);
                return create;
            }
            
            @Override
            public Map loadAll(final Iterable iterable) throws Exception {
                return this.val$loader.loadAll(iterable);
            }
        };
    }
    
    public static final class InvalidCacheLoadException extends RuntimeException
    {
        public InvalidCacheLoadException(final String s) {
            super(s);
        }
    }
    
    static final class UnsupportedLoadingOperationException extends UnsupportedOperationException
    {
    }
    
    private static final class SupplierToCacheLoader extends CacheLoader implements Serializable
    {
        private final Supplier computingSupplier;
        private static final long serialVersionUID = 0L;
        
        public SupplierToCacheLoader(final Supplier supplier) {
            this.computingSupplier = (Supplier)Preconditions.checkNotNull(supplier);
        }
        
        @Override
        public Object load(final Object o) {
            Preconditions.checkNotNull(o);
            return this.computingSupplier.get();
        }
    }
    
    private static final class FunctionToCacheLoader extends CacheLoader implements Serializable
    {
        private final Function computingFunction;
        private static final long serialVersionUID = 0L;
        
        public FunctionToCacheLoader(final Function function) {
            this.computingFunction = (Function)Preconditions.checkNotNull(function);
        }
        
        @Override
        public Object load(final Object o) {
            return this.computingFunction.apply(Preconditions.checkNotNull(o));
        }
    }
}
