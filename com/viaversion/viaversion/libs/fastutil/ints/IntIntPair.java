package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.*;
import java.util.*;

public interface IntIntPair extends Pair
{
    int leftInt();
    
    @Deprecated
    default Integer left() {
        return this.leftInt();
    }
    
    default IntIntPair left(final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    default IntIntPair left(final Integer n) {
        return this.left((int)n);
    }
    
    default int firstInt() {
        return this.leftInt();
    }
    
    @Deprecated
    default Integer first() {
        return this.firstInt();
    }
    
    default IntIntPair first(final int n) {
        return this.left(n);
    }
    
    @Deprecated
    default IntIntPair first(final Integer n) {
        return this.first((int)n);
    }
    
    default int keyInt() {
        return this.firstInt();
    }
    
    @Deprecated
    default Integer key() {
        return this.keyInt();
    }
    
    default IntIntPair key(final int n) {
        return this.left(n);
    }
    
    @Deprecated
    default IntIntPair key(final Integer n) {
        return this.key((int)n);
    }
    
    int rightInt();
    
    @Deprecated
    default Integer right() {
        return this.rightInt();
    }
    
    default IntIntPair right(final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    default IntIntPair right(final Integer n) {
        return this.right((int)n);
    }
    
    default int secondInt() {
        return this.rightInt();
    }
    
    @Deprecated
    default Integer second() {
        return this.secondInt();
    }
    
    default IntIntPair second(final int n) {
        return this.right(n);
    }
    
    @Deprecated
    default IntIntPair second(final Integer n) {
        return this.second((int)n);
    }
    
    default int valueInt() {
        return this.rightInt();
    }
    
    @Deprecated
    default Integer value() {
        return this.valueInt();
    }
    
    default IntIntPair value(final int n) {
        return this.right(n);
    }
    
    @Deprecated
    default IntIntPair value(final Integer n) {
        return this.value((int)n);
    }
    
    default IntIntPair of(final int n, final int n2) {
        return new IntIntImmutablePair(n, n2);
    }
    
    default Comparator lexComparator() {
        return IntIntPair::lambda$lexComparator$0;
    }
    
    @Deprecated
    default Object value() {
        return this.value();
    }
    
    @Deprecated
    default Object key() {
        return this.key();
    }
    
    @Deprecated
    default Pair value(final Object o) {
        return this.value((Integer)o);
    }
    
    @Deprecated
    default Pair key(final Object o) {
        return this.key((Integer)o);
    }
    
    @Deprecated
    default Pair second(final Object o) {
        return this.second((Integer)o);
    }
    
    @Deprecated
    default Pair first(final Object o) {
        return this.first((Integer)o);
    }
    
    @Deprecated
    default Object second() {
        return this.second();
    }
    
    @Deprecated
    default Object first() {
        return this.first();
    }
    
    @Deprecated
    default Pair right(final Object o) {
        return this.right((Integer)o);
    }
    
    @Deprecated
    default Pair left(final Object o) {
        return this.left((Integer)o);
    }
    
    @Deprecated
    default Object right() {
        return this.right();
    }
    
    @Deprecated
    default Object left() {
        return this.left();
    }
    
    default int lambda$lexComparator$0(final IntIntPair intIntPair, final IntIntPair intIntPair2) {
        final int compare = Integer.compare(intIntPair.leftInt(), intIntPair2.leftInt());
        if (compare != 0) {
            return compare;
        }
        return Integer.compare(intIntPair.rightInt(), intIntPair2.rightInt());
    }
}
