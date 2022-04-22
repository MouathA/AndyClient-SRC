package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.function.*;

@FunctionalInterface
public interface IntBinaryOperator extends BinaryOperator, java.util.function.IntBinaryOperator
{
    int apply(final int p0, final int p1);
    
    @Deprecated
    default int applyAsInt(final int n, final int n2) {
        return this.apply(n, n2);
    }
    
    @Deprecated
    default Integer apply(final Integer n, final Integer n2) {
        return this.apply((int)n, (int)n2);
    }
    
    @Deprecated
    default Object apply(final Object o, final Object o2) {
        return this.apply((Integer)o, (Integer)o2);
    }
}
