package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.function.*;

@FunctionalInterface
public interface IntUnaryOperator extends UnaryOperator, java.util.function.IntUnaryOperator
{
    int apply(final int p0);
    
    default IntUnaryOperator identity() {
        return IntUnaryOperator::lambda$identity$0;
    }
    
    default IntUnaryOperator negation() {
        return IntUnaryOperator::lambda$negation$1;
    }
    
    @Deprecated
    default int applyAsInt(final int n) {
        return this.apply(n);
    }
    
    @Deprecated
    default Integer apply(final Integer n) {
        return this.apply((int)n);
    }
    
    @Deprecated
    default Object apply(final Object o) {
        return this.apply((Integer)o);
    }
    
    default int lambda$negation$1(final int n) {
        return -n;
    }
    
    default int lambda$identity$0(final int n) {
        return n;
    }
}
