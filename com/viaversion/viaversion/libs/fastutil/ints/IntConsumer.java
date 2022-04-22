package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.function.*;
import java.util.*;

@FunctionalInterface
public interface IntConsumer extends Consumer, java.util.function.IntConsumer
{
    @Deprecated
    default void accept(final Integer n) {
        this.accept(n);
    }
    
    default IntConsumer andThen(final java.util.function.IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        return this::lambda$andThen$0;
    }
    
    default IntConsumer andThen(final IntConsumer intConsumer) {
        return this.andThen((java.util.function.IntConsumer)intConsumer);
    }
    
    @Deprecated
    default Consumer andThen(final Consumer consumer) {
        return super.andThen(consumer);
    }
    
    @Deprecated
    default void accept(final Object o) {
        this.accept((Integer)o);
    }
    
    default java.util.function.IntConsumer andThen(final java.util.function.IntConsumer intConsumer) {
        return this.andThen(intConsumer);
    }
    
    default void lambda$andThen$0(final java.util.function.IntConsumer intConsumer, final int n) {
        this.accept(n);
        intConsumer.accept(n);
    }
}
