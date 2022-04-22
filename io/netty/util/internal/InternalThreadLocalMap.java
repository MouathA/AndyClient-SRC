package io.netty.util.internal;

import io.netty.util.concurrent.*;
import java.util.*;

public final class InternalThreadLocalMap extends UnpaddedInternalThreadLocalMap
{
    public static final Object UNSET;
    public long rp1;
    public long rp2;
    public long rp3;
    public long rp4;
    public long rp5;
    public long rp6;
    public long rp7;
    public long rp8;
    public long rp9;
    
    public static InternalThreadLocalMap getIfSet() {
        final Thread currentThread = Thread.currentThread();
        InternalThreadLocalMap threadLocalMap;
        if (currentThread instanceof FastThreadLocalThread) {
            threadLocalMap = ((FastThreadLocalThread)currentThread).threadLocalMap();
        }
        else {
            final ThreadLocal slowThreadLocalMap = UnpaddedInternalThreadLocalMap.slowThreadLocalMap;
            if (slowThreadLocalMap == null) {
                threadLocalMap = null;
            }
            else {
                threadLocalMap = slowThreadLocalMap.get();
            }
        }
        return threadLocalMap;
    }
    
    public static InternalThreadLocalMap get() {
        final Thread currentThread = Thread.currentThread();
        if (currentThread instanceof FastThreadLocalThread) {
            return fastGet((FastThreadLocalThread)currentThread);
        }
        return slowGet();
    }
    
    private static InternalThreadLocalMap fastGet(final FastThreadLocalThread fastThreadLocalThread) {
        InternalThreadLocalMap threadLocalMap = fastThreadLocalThread.threadLocalMap();
        if (threadLocalMap == null) {
            fastThreadLocalThread.setThreadLocalMap(threadLocalMap = new InternalThreadLocalMap());
        }
        return threadLocalMap;
    }
    
    private static InternalThreadLocalMap slowGet() {
        ThreadLocal slowThreadLocalMap = UnpaddedInternalThreadLocalMap.slowThreadLocalMap;
        if (slowThreadLocalMap == null) {
            slowThreadLocalMap = (UnpaddedInternalThreadLocalMap.slowThreadLocalMap = new ThreadLocal());
        }
        InternalThreadLocalMap internalThreadLocalMap = slowThreadLocalMap.get();
        if (internalThreadLocalMap == null) {
            internalThreadLocalMap = new InternalThreadLocalMap();
            slowThreadLocalMap.set(internalThreadLocalMap);
        }
        return internalThreadLocalMap;
    }
    
    public static void remove() {
        final Thread currentThread = Thread.currentThread();
        if (currentThread instanceof FastThreadLocalThread) {
            ((FastThreadLocalThread)currentThread).setThreadLocalMap(null);
        }
        else {
            final ThreadLocal slowThreadLocalMap = UnpaddedInternalThreadLocalMap.slowThreadLocalMap;
            if (slowThreadLocalMap != null) {
                slowThreadLocalMap.remove();
            }
        }
    }
    
    public static void destroy() {
        InternalThreadLocalMap.slowThreadLocalMap = null;
    }
    
    public static int nextVariableIndex() {
        final int andIncrement = InternalThreadLocalMap.nextIndex.getAndIncrement();
        if (andIncrement < 0) {
            InternalThreadLocalMap.nextIndex.decrementAndGet();
            throw new IllegalStateException("too many thread-local indexed variables");
        }
        return andIncrement;
    }
    
    public static int lastVariableIndex() {
        return InternalThreadLocalMap.nextIndex.get() - 1;
    }
    
    private InternalThreadLocalMap() {
        super(newIndexedVariableTable());
    }
    
    private static Object[] newIndexedVariableTable() {
        final Object[] array = new Object[32];
        Arrays.fill(array, InternalThreadLocalMap.UNSET);
        return array;
    }
    
    public int size() {
        int n = 0;
        if (this.futureListenerStackDepth != 0) {
            ++n;
        }
        if (this.localChannelReaderStackDepth != 0) {
            ++n;
        }
        if (this.handlerSharableCache != null) {
            ++n;
        }
        if (this.counterHashCode != null) {
            ++n;
        }
        if (this.random != null) {
            ++n;
        }
        if (this.typeParameterMatcherGetCache != null) {
            ++n;
        }
        if (this.typeParameterMatcherFindCache != null) {
            ++n;
        }
        if (this.stringBuilder != null) {
            ++n;
        }
        if (this.charsetEncoderCache != null) {
            ++n;
        }
        if (this.charsetDecoderCache != null) {
            ++n;
        }
        final Object[] indexedVariables = this.indexedVariables;
        while (0 < indexedVariables.length) {
            if (indexedVariables[0] != InternalThreadLocalMap.UNSET) {
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return -1;
    }
    
    public StringBuilder stringBuilder() {
        StringBuilder stringBuilder = this.stringBuilder;
        if (stringBuilder == null) {
            stringBuilder = (this.stringBuilder = new StringBuilder(512));
        }
        else {
            stringBuilder.setLength(0);
        }
        return stringBuilder;
    }
    
    public Map charsetEncoderCache() {
        Map charsetEncoderCache = this.charsetEncoderCache;
        if (charsetEncoderCache == null) {
            charsetEncoderCache = (this.charsetEncoderCache = new IdentityHashMap());
        }
        return charsetEncoderCache;
    }
    
    public Map charsetDecoderCache() {
        Map charsetDecoderCache = this.charsetDecoderCache;
        if (charsetDecoderCache == null) {
            charsetDecoderCache = (this.charsetDecoderCache = new IdentityHashMap());
        }
        return charsetDecoderCache;
    }
    
    public int futureListenerStackDepth() {
        return this.futureListenerStackDepth;
    }
    
    public void setFutureListenerStackDepth(final int futureListenerStackDepth) {
        this.futureListenerStackDepth = futureListenerStackDepth;
    }
    
    public ThreadLocalRandom random() {
        ThreadLocalRandom random = this.random;
        if (random == null) {
            random = (this.random = new ThreadLocalRandom());
        }
        return random;
    }
    
    public Map typeParameterMatcherGetCache() {
        Map typeParameterMatcherGetCache = this.typeParameterMatcherGetCache;
        if (typeParameterMatcherGetCache == null) {
            typeParameterMatcherGetCache = (this.typeParameterMatcherGetCache = new IdentityHashMap());
        }
        return typeParameterMatcherGetCache;
    }
    
    public Map typeParameterMatcherFindCache() {
        Map typeParameterMatcherFindCache = this.typeParameterMatcherFindCache;
        if (typeParameterMatcherFindCache == null) {
            typeParameterMatcherFindCache = (this.typeParameterMatcherFindCache = new IdentityHashMap());
        }
        return typeParameterMatcherFindCache;
    }
    
    public IntegerHolder counterHashCode() {
        return this.counterHashCode;
    }
    
    public void setCounterHashCode(final IntegerHolder counterHashCode) {
        this.counterHashCode = counterHashCode;
    }
    
    public Map handlerSharableCache() {
        Map handlerSharableCache = this.handlerSharableCache;
        if (handlerSharableCache == null) {
            handlerSharableCache = (this.handlerSharableCache = new WeakHashMap(4));
        }
        return handlerSharableCache;
    }
    
    public int localChannelReaderStackDepth() {
        return this.localChannelReaderStackDepth;
    }
    
    public void setLocalChannelReaderStackDepth(final int localChannelReaderStackDepth) {
        this.localChannelReaderStackDepth = localChannelReaderStackDepth;
    }
    
    public Object indexedVariable(final int n) {
        final Object[] indexedVariables = this.indexedVariables;
        return (n < indexedVariables.length) ? indexedVariables[n] : InternalThreadLocalMap.UNSET;
    }
    
    public boolean setIndexedVariable(final int n, final Object o) {
        final Object[] indexedVariables = this.indexedVariables;
        if (n < indexedVariables.length) {
            final Object o2 = indexedVariables[n];
            indexedVariables[n] = o;
            return o2 == InternalThreadLocalMap.UNSET;
        }
        this.expandIndexedVariableTableAndSet(n, o);
        return true;
    }
    
    private void expandIndexedVariableTableAndSet(final int n, final Object o) {
        final Object[] indexedVariables = this.indexedVariables;
        final int length = indexedVariables.length;
        final int n2 = n | n >>> 1;
        final int n3 = n2 | n2 >>> 2;
        final int n4 = n3 | n3 >>> 4;
        final int n5 = n4 | n4 >>> 8;
        int n6 = n5 | n5 >>> 16;
        ++n6;
        final Object[] copy = Arrays.copyOf(indexedVariables, n6);
        Arrays.fill(copy, length, copy.length, InternalThreadLocalMap.UNSET);
        copy[n] = o;
        this.indexedVariables = copy;
    }
    
    public Object removeIndexedVariable(final int n) {
        final Object[] indexedVariables = this.indexedVariables;
        if (n < indexedVariables.length) {
            final Object o = indexedVariables[n];
            indexedVariables[n] = InternalThreadLocalMap.UNSET;
            return o;
        }
        return InternalThreadLocalMap.UNSET;
    }
    
    public boolean isIndexedVariableSet(final int n) {
        final Object[] indexedVariables = this.indexedVariables;
        return n < indexedVariables.length && indexedVariables[n] != InternalThreadLocalMap.UNSET;
    }
    
    static {
        UNSET = new Object();
    }
}
