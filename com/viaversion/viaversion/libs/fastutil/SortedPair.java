package com.viaversion.viaversion.libs.fastutil;

import com.viaversion.viaversion.libs.fastutil.objects.*;
import java.util.*;

public interface SortedPair extends Pair
{
    default SortedPair of(final Comparable comparable, final Comparable comparable2) {
        return ObjectObjectImmutableSortedPair.of(comparable, comparable2);
    }
    
    default boolean contains(final Object o) {
        return Objects.equals(o, this.left()) || Objects.equals(o, this.right());
    }
}
