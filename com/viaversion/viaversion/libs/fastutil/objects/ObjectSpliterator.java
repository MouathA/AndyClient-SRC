package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.*;
import java.util.function.*;

public interface ObjectSpliterator extends Spliterator
{
    default long skip(final long n) {
        if (n < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
        }
        long n2 = n;
        while (n2-- != 0L && this.tryAdvance(ObjectSpliterator::lambda$skip$0)) {}
        return n - n2 - 1L;
    }
    
    ObjectSpliterator trySplit();
    
    default Spliterator trySplit() {
        return this.trySplit();
    }
    
    default void lambda$skip$0(final Object o) {
    }
}
