package io.netty.util;

import java.util.concurrent.*;
import io.netty.util.internal.*;

public final class AttributeKey extends UniqueName
{
    private static final ConcurrentMap names;
    
    public static AttributeKey valueOf(final String s) {
        return new AttributeKey(s);
    }
    
    @Deprecated
    public AttributeKey(final String s) {
        super(AttributeKey.names, s, new Object[0]);
    }
    
    static {
        names = PlatformDependent.newConcurrentHashMap();
    }
}
