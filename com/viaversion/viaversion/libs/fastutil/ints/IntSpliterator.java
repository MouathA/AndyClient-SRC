package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.function.*;
import java.util.*;

public interface IntSpliterator extends Spliterator.OfInt
{
    @Deprecated
    default boolean tryAdvance(final Consumer consumer) {
        IntConsumer intConsumer;
        if (consumer instanceof IntConsumer) {
            intConsumer = (IntConsumer)consumer;
        }
        else {
            Objects.requireNonNull(consumer);
            intConsumer = consumer::accept;
        }
        return this.tryAdvance(intConsumer);
    }
    
    default boolean tryAdvance(final com.viaversion.viaversion.libs.fastutil.ints.IntConsumer intConsumer) {
        return this.tryAdvance((IntConsumer)intConsumer);
    }
    
    @Deprecated
    default void forEachRemaining(final Consumer consumer) {
        IntConsumer intConsumer;
        if (consumer instanceof IntConsumer) {
            intConsumer = (IntConsumer)consumer;
        }
        else {
            Objects.requireNonNull(consumer);
            intConsumer = consumer::accept;
        }
        this.forEachRemaining(intConsumer);
    }
    
    default void forEachRemaining(final com.viaversion.viaversion.libs.fastutil.ints.IntConsumer intConsumer) {
        this.forEachRemaining((IntConsumer)intConsumer);
    }
    
    default long skip(final long n) {
        if (n < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
        }
        long n2 = n;
        while (n2-- != 0L && this.tryAdvance(IntSpliterator::lambda$skip$0)) {}
        return n - n2 - 1L;
    }
    
    IntSpliterator trySplit();
    
    default IntComparator getComparator() {
        throw new IllegalStateException();
    }
    
    default Spliterator.OfInt trySplit() {
        return this.trySplit();
    }
    
    default Spliterator.OfPrimitive trySplit() {
        return this.trySplit();
    }
    
    default Comparator getComparator() {
        return this.getComparator();
    }
    
    default Spliterator trySplit() {
        return this.trySplit();
    }
    
    default void lambda$skip$0(final int n) {
    }
}
