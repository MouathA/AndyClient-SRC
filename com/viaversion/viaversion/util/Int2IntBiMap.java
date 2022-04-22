package com.viaversion.viaversion.util;

import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.*;

public interface Int2IntBiMap extends Int2IntMap
{
    Int2IntBiMap inverse();
    
    int put(final int p0, final int p1);
    
    @Deprecated
    default void putAll(final Map map) {
        throw new UnsupportedOperationException();
    }
}
