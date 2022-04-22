package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.*;
import java.io.*;

public interface IntIntSortedPair extends IntIntPair, SortedPair, Serializable
{
    default IntIntSortedPair of(final int n, final int n2) {
        return IntIntImmutableSortedPair.of(n, n2);
    }
    
    default boolean contains(final int n) {
        return n == this.leftInt() || n == this.rightInt();
    }
    
    @Deprecated
    default boolean contains(final Object o) {
        return o != null && this.contains((int)o);
    }
}
