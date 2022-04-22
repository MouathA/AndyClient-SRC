package com.viaversion.viaversion.libs.fastutil;

import java.util.*;

public interface Size64
{
    long size64();
    
    @Deprecated
    default int size() {
        return (int)Math.min(2147483647L, this.size64());
    }
    
    default long sizeOf(final Collection collection) {
        return (collection instanceof Size64) ? ((Size64)collection).size64() : collection.size();
    }
    
    default long sizeOf(final Map map) {
        return (map instanceof Size64) ? ((Size64)map).size64() : map.size();
    }
}
