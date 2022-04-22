package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.function.*;
import java.util.*;

public interface IntIterator extends PrimitiveIterator.OfInt
{
    int nextInt();
    
    @Deprecated
    default Integer next() {
        return this.nextInt();
    }
    
    default void forEachRemaining(final IntConsumer intConsumer) {
        this.forEachRemaining((java.util.function.IntConsumer)intConsumer);
    }
    
    @Deprecated
    default void forEachRemaining(final Consumer consumer) {
        java.util.function.IntConsumer intConsumer;
        if (consumer instanceof java.util.function.IntConsumer) {
            intConsumer = (java.util.function.IntConsumer)consumer;
        }
        else {
            Objects.requireNonNull(consumer);
            intConsumer = consumer::accept;
        }
        this.forEachRemaining(intConsumer);
    }
    
    default int skip(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
        }
        int n2 = n;
        while (n2-- != 0 && this.hasNext()) {
            this.nextInt();
        }
        return n - n2 - 1;
    }
    
    @Deprecated
    default Object next() {
        return this.next();
    }
}
