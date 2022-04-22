package com.google.common.collect;

import java.io.*;
import com.google.common.annotations.*;
import java.util.*;
import javax.annotation.*;

@GwtCompatible(serializable = true)
final class ExplicitOrdering extends Ordering implements Serializable
{
    final ImmutableMap rankMap;
    private static final long serialVersionUID = 0L;
    
    ExplicitOrdering(final List list) {
        this(buildRankMap(list));
    }
    
    ExplicitOrdering(final ImmutableMap rankMap) {
        this.rankMap = rankMap;
    }
    
    @Override
    public int compare(final Object o, final Object o2) {
        return this.rank(o) - this.rank(o2);
    }
    
    private int rank(final Object o) {
        final Integer n = (Integer)this.rankMap.get(o);
        if (n == null) {
            throw new IncomparableValueException(o);
        }
        return n;
    }
    
    private static ImmutableMap buildRankMap(final List list) {
        final ImmutableMap.Builder builder = ImmutableMap.builder();
        for (final Object next : list) {
            final ImmutableMap.Builder builder2 = builder;
            final Object o = next;
            final int n = 0;
            int n2 = 0;
            ++n2;
            builder2.put(o, n);
        }
        return builder.build();
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o instanceof ExplicitOrdering && this.rankMap.equals(((ExplicitOrdering)o).rankMap);
    }
    
    @Override
    public int hashCode() {
        return this.rankMap.hashCode();
    }
    
    @Override
    public String toString() {
        return "Ordering.explicit(" + this.rankMap.keySet() + ")";
    }
}
