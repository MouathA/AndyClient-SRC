package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.function.*;
import java.util.*;

public interface IntIterable extends Iterable
{
    IntIterator iterator();
    
    default IntIterator intIterator() {
        return this.iterator();
    }
    
    default IntSpliterator spliterator() {
        return IntSpliterators.asSpliteratorUnknownSize(this.iterator(), 0);
    }
    
    default IntSpliterator intSpliterator() {
        return this.spliterator();
    }
    
    default void forEach(final IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        this.iterator().forEachRemaining(intConsumer);
    }
    
    default void forEach(final com.viaversion.viaversion.libs.fastutil.ints.IntConsumer intConsumer) {
        this.forEach((IntConsumer)intConsumer);
    }
    
    @Deprecated
    default void forEach(final Consumer consumer) {
        Objects.requireNonNull(consumer);
        IntConsumer intConsumer;
        if (consumer instanceof IntConsumer) {
            intConsumer = (IntConsumer)consumer;
        }
        else {
            Objects.requireNonNull(consumer);
            intConsumer = consumer::accept;
        }
        this.forEach(intConsumer);
    }
    
    default Spliterator spliterator() {
        return this.spliterator();
    }
    
    default Iterator iterator() {
        return this.iterator();
    }
}
