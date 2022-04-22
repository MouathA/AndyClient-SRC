package io.netty.handler.codec.serialization;

import java.util.*;

class CachingClassResolver implements ClassResolver
{
    private final Map classCache;
    private final ClassResolver delegate;
    
    CachingClassResolver(final ClassResolver delegate, final Map classCache) {
        this.delegate = delegate;
        this.classCache = classCache;
    }
    
    @Override
    public Class resolve(final String s) throws ClassNotFoundException {
        final Class clazz = this.classCache.get(s);
        if (clazz != null) {
            return clazz;
        }
        final Class resolve = this.delegate.resolve(s);
        this.classCache.put(s, resolve);
        return resolve;
    }
}
