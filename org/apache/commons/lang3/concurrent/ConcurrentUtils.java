package org.apache.commons.lang3.concurrent;

import java.util.concurrent.*;

public class ConcurrentUtils
{
    private ConcurrentUtils() {
    }
    
    public static ConcurrentException extractCause(final ExecutionException ex) {
        if (ex == null || ex.getCause() == null) {
            return null;
        }
        throwCause(ex);
        return new ConcurrentException(ex.getMessage(), ex.getCause());
    }
    
    public static ConcurrentRuntimeException extractCauseUnchecked(final ExecutionException ex) {
        if (ex == null || ex.getCause() == null) {
            return null;
        }
        throwCause(ex);
        return new ConcurrentRuntimeException(ex.getMessage(), ex.getCause());
    }
    
    public static void handleCause(final ExecutionException ex) throws ConcurrentException {
        final ConcurrentException cause = extractCause(ex);
        if (cause != null) {
            throw cause;
        }
    }
    
    public static void handleCauseUnchecked(final ExecutionException ex) {
        final ConcurrentRuntimeException causeUnchecked = extractCauseUnchecked(ex);
        if (causeUnchecked != null) {
            throw causeUnchecked;
        }
    }
    
    static Throwable checkedException(final Throwable t) {
        if (t != null && !(t instanceof RuntimeException) && !(t instanceof Error)) {
            return t;
        }
        throw new IllegalArgumentException("Not a checked exception: " + t);
    }
    
    private static void throwCause(final ExecutionException ex) {
        if (ex.getCause() instanceof RuntimeException) {
            throw (RuntimeException)ex.getCause();
        }
        if (ex.getCause() instanceof Error) {
            throw (Error)ex.getCause();
        }
    }
    
    public static Object initialize(final ConcurrentInitializer concurrentInitializer) throws ConcurrentException {
        return (concurrentInitializer != null) ? concurrentInitializer.get() : null;
    }
    
    public static Object initializeUnchecked(final ConcurrentInitializer concurrentInitializer) {
        return initialize(concurrentInitializer);
    }
    
    public static Object putIfAbsent(final ConcurrentMap concurrentMap, final Object o, final Object o2) {
        if (concurrentMap == null) {
            return null;
        }
        final Object putIfAbsent = concurrentMap.putIfAbsent(o, o2);
        return (putIfAbsent != null) ? putIfAbsent : o2;
    }
    
    public static Object createIfAbsent(final ConcurrentMap concurrentMap, final Object o, final ConcurrentInitializer concurrentInitializer) throws ConcurrentException {
        if (concurrentMap == null || concurrentInitializer == null) {
            return null;
        }
        final Object value = concurrentMap.get(o);
        if (value == null) {
            return putIfAbsent(concurrentMap, o, concurrentInitializer.get());
        }
        return value;
    }
    
    public static Object createIfAbsentUnchecked(final ConcurrentMap concurrentMap, final Object o, final ConcurrentInitializer concurrentInitializer) {
        return createIfAbsent(concurrentMap, o, concurrentInitializer);
    }
    
    public static Future constantFuture(final Object o) {
        return new ConstantFuture(o);
    }
    
    static final class ConstantFuture implements Future
    {
        private final Object value;
        
        ConstantFuture(final Object value) {
            this.value = value;
        }
        
        @Override
        public boolean isDone() {
            return true;
        }
        
        @Override
        public Object get() {
            return this.value;
        }
        
        @Override
        public Object get(final long n, final TimeUnit timeUnit) {
            return this.value;
        }
        
        @Override
        public boolean isCancelled() {
            return false;
        }
        
        @Override
        public boolean cancel(final boolean b) {
            return false;
        }
    }
}
