package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.*;

public final class ObjectIterables
{
    private ObjectIterables() {
    }
    
    public static long size(final Iterable iterable) {
        long n = 0L;
        final Iterator<Object> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            ++n;
        }
        return n;
    }
}
