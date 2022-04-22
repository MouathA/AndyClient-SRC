package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.*;
import java.util.*;

public interface ObjectIntPair extends Pair
{
    int rightInt();
    
    @Deprecated
    default Integer right() {
        return this.rightInt();
    }
    
    default ObjectIntPair right(final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    default ObjectIntPair right(final Integer n) {
        return this.right((int)n);
    }
    
    default int secondInt() {
        return this.rightInt();
    }
    
    @Deprecated
    default Integer second() {
        return this.secondInt();
    }
    
    default ObjectIntPair second(final int n) {
        return this.right(n);
    }
    
    @Deprecated
    default ObjectIntPair second(final Integer n) {
        return this.second((int)n);
    }
    
    default int valueInt() {
        return this.rightInt();
    }
    
    @Deprecated
    default Integer value() {
        return this.valueInt();
    }
    
    default ObjectIntPair value(final int n) {
        return this.right(n);
    }
    
    @Deprecated
    default ObjectIntPair value(final Integer n) {
        return this.value((int)n);
    }
    
    default ObjectIntPair of(final Object o, final int n) {
        return new ObjectIntImmutablePair(o, n);
    }
    
    default Comparator lexComparator() {
        return ObjectIntPair::lambda$lexComparator$0;
    }
    
    @Deprecated
    default Object value() {
        return this.value();
    }
    
    @Deprecated
    default Pair value(final Object o) {
        return this.value((Integer)o);
    }
    
    @Deprecated
    default Pair second(final Object o) {
        return this.second((Integer)o);
    }
    
    @Deprecated
    default Object second() {
        return this.second();
    }
    
    @Deprecated
    default Pair right(final Object o) {
        return this.right((Integer)o);
    }
    
    @Deprecated
    default Object right() {
        return this.right();
    }
    
    default int lambda$lexComparator$0(final ObjectIntPair objectIntPair, final ObjectIntPair objectIntPair2) {
        final int compareTo = ((Comparable)objectIntPair.left()).compareTo(objectIntPair2.left());
        if (compareTo != 0) {
            return compareTo;
        }
        return Integer.compare(objectIntPair.rightInt(), objectIntPair2.rightInt());
    }
}
