package io.netty.util.internal;

import java.util.concurrent.atomic.*;
import java.util.*;

class UnpaddedInternalThreadLocalMap
{
    static ThreadLocal slowThreadLocalMap;
    static final AtomicInteger nextIndex;
    Object[] indexedVariables;
    int futureListenerStackDepth;
    int localChannelReaderStackDepth;
    Map handlerSharableCache;
    IntegerHolder counterHashCode;
    ThreadLocalRandom random;
    Map typeParameterMatcherGetCache;
    Map typeParameterMatcherFindCache;
    StringBuilder stringBuilder;
    Map charsetEncoderCache;
    Map charsetDecoderCache;
    
    UnpaddedInternalThreadLocalMap(final Object[] indexedVariables) {
        this.indexedVariables = indexedVariables;
    }
    
    static {
        nextIndex = new AtomicInteger();
    }
}
