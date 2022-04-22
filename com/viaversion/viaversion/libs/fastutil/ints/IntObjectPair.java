package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.*;
import java.util.*;

public interface IntObjectPair extends Pair
{
    int leftInt();
    
    @Deprecated
    default Integer left() {
        return this.leftInt();
    }
    
    default IntObjectPair left(final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    default IntObjectPair left(final Integer n) {
        return this.left((int)n);
    }
    
    default int firstInt() {
        return this.leftInt();
    }
    
    @Deprecated
    default Integer first() {
        return this.firstInt();
    }
    
    default IntObjectPair first(final int n) {
        return this.left(n);
    }
    
    @Deprecated
    default IntObjectPair first(final Integer n) {
        return this.first((int)n);
    }
    
    default int keyInt() {
        return this.firstInt();
    }
    
    @Deprecated
    default Integer key() {
        return this.keyInt();
    }
    
    default IntObjectPair key(final int n) {
        return this.left(n);
    }
    
    @Deprecated
    default IntObjectPair key(final Integer n) {
        return this.key((int)n);
    }
    
    default IntObjectPair of(final int n, final Object o) {
        return new IntObjectImmutablePair(n, o);
    }
    
    default Comparator lexComparator() {
        return IntObjectPair::lambda$lexComparator$0;
    }
    
    @Deprecated
    default Object key() {
        return this.key();
    }
    
    @Deprecated
    default Pair key(final Object o) {
        return this.key((Integer)o);
    }
    
    @Deprecated
    default Pair first(final Object o) {
        return this.first((Integer)o);
    }
    
    @Deprecated
    default Object first() {
        return this.first();
    }
    
    @Deprecated
    default Pair left(final Object o) {
        return this.left((Integer)o);
    }
    
    @Deprecated
    default Object left() {
        return this.left();
    }
    
    default int lambda$lexComparator$0(final IntObjectPair intObjectPair, final IntObjectPair intObjectPair2) {
        final int compare = Integer.compare(intObjectPair.leftInt(), intObjectPair2.leftInt());
        if (compare != 0) {
            return compare;
        }
        return ((Comparable)intObjectPair.right()).compareTo(intObjectPair2.right());
    }
}
