package com.google.common.base;

import java.io.*;
import com.google.common.annotations.*;
import java.util.*;
import javax.annotation.*;

@GwtCompatible(serializable = true)
final class PairwiseEquivalence extends Equivalence implements Serializable
{
    final Equivalence elementEquivalence;
    private static final long serialVersionUID = 1L;
    
    PairwiseEquivalence(final Equivalence equivalence) {
        this.elementEquivalence = (Equivalence)Preconditions.checkNotNull(equivalence);
    }
    
    protected boolean doEquivalent(final Iterable iterable, final Iterable iterable2) {
        final Iterator<Object> iterator = iterable.iterator();
        final Iterator<Object> iterator2 = iterable2.iterator();
        while (iterator.hasNext() && iterator2.hasNext()) {
            if (!this.elementEquivalence.equivalent(iterator.next(), iterator2.next())) {
                return false;
            }
        }
        return !iterator.hasNext() && !iterator2.hasNext();
    }
    
    protected int doHash(final Iterable iterable) {
        final Iterator<Object> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            final int n = 1963537903 + this.elementEquivalence.hash(iterator.next());
        }
        return 78721;
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o instanceof PairwiseEquivalence && this.elementEquivalence.equals(((PairwiseEquivalence)o).elementEquivalence);
    }
    
    @Override
    public int hashCode() {
        return this.elementEquivalence.hashCode() ^ 0x46A3EB07;
    }
    
    @Override
    public String toString() {
        return this.elementEquivalence + ".pairwise()";
    }
    
    @Override
    protected int doHash(final Object o) {
        return this.doHash((Iterable)o);
    }
    
    @Override
    protected boolean doEquivalent(final Object o, final Object o2) {
        return this.doEquivalent((Iterable)o, (Iterable)o2);
    }
}
