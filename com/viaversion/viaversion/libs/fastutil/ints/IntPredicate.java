package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.function.*;
import java.util.*;

@FunctionalInterface
public interface IntPredicate extends Predicate, java.util.function.IntPredicate
{
    @Deprecated
    default boolean test(final Integer n) {
        return this.test(n);
    }
    
    default IntPredicate and(final java.util.function.IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        return this::lambda$and$0;
    }
    
    default IntPredicate and(final IntPredicate intPredicate) {
        return this.and((java.util.function.IntPredicate)intPredicate);
    }
    
    @Deprecated
    default Predicate and(final Predicate predicate) {
        return super.and(predicate);
    }
    
    default IntPredicate negate() {
        return this::lambda$negate$1;
    }
    
    default IntPredicate or(final java.util.function.IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        return this::lambda$or$2;
    }
    
    default IntPredicate or(final IntPredicate intPredicate) {
        return this.or((java.util.function.IntPredicate)intPredicate);
    }
    
    @Deprecated
    default Predicate or(final Predicate predicate) {
        return super.or(predicate);
    }
    
    default Predicate negate() {
        return this.negate();
    }
    
    @Deprecated
    default boolean test(final Object o) {
        return this.test((Integer)o);
    }
    
    default java.util.function.IntPredicate or(final java.util.function.IntPredicate intPredicate) {
        return this.or(intPredicate);
    }
    
    default java.util.function.IntPredicate negate() {
        return this.negate();
    }
    
    default java.util.function.IntPredicate and(final java.util.function.IntPredicate intPredicate) {
        return this.and(intPredicate);
    }
    
    default boolean lambda$or$2(final java.util.function.IntPredicate intPredicate, final int n) {
        return this.test(n) || intPredicate.test(n);
    }
    
    default boolean lambda$negate$1(final int n) {
        return !this.test(n);
    }
    
    default boolean lambda$and$0(final java.util.function.IntPredicate intPredicate, final int n) {
        return this.test(n) && intPredicate.test(n);
    }
}
