package com.viaversion.viaversion.libs.fastutil;

import com.viaversion.viaversion.libs.fastutil.objects.*;
import java.util.*;

public interface Pair
{
    Object left();
    
    Object right();
    
    default Pair left(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    default Pair right(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    default Object first() {
        return this.left();
    }
    
    default Object second() {
        return this.right();
    }
    
    default Pair first(final Object o) {
        return this.left(o);
    }
    
    default Pair second(final Object o) {
        return this.right(o);
    }
    
    default Pair key(final Object o) {
        return this.left(o);
    }
    
    default Pair value(final Object o) {
        return this.right(o);
    }
    
    default Object key() {
        return this.left();
    }
    
    default Object value() {
        return this.right();
    }
    
    default Pair of(final Object o, final Object o2) {
        return new ObjectObjectImmutablePair(o, o2);
    }
    
    default Comparator lexComparator() {
        return Pair::lambda$lexComparator$0;
    }
    
    default int lambda$lexComparator$0(final Pair pair, final Pair pair2) {
        final int compareTo = ((Comparable)pair.left()).compareTo(pair2.left());
        if (compareTo != 0) {
            return compareTo;
        }
        return ((Comparable)pair.right()).compareTo(pair2.right());
    }
}
