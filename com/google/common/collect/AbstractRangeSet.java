package com.google.common.collect;

import java.util.*;
import javax.annotation.*;

abstract class AbstractRangeSet implements RangeSet
{
    @Override
    public boolean contains(final Comparable comparable) {
        return this.rangeContaining(comparable) != null;
    }
    
    @Override
    public abstract Range rangeContaining(final Comparable p0);
    
    @Override
    public boolean isEmpty() {
        return this.asRanges().isEmpty();
    }
    
    @Override
    public void add(final Range range) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void remove(final Range range) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void clear() {
        this.remove(Range.all());
    }
    
    @Override
    public boolean enclosesAll(final RangeSet set) {
        final Iterator<Range> iterator = set.asRanges().iterator();
        while (iterator.hasNext()) {
            if (!this.encloses(iterator.next())) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void addAll(final RangeSet set) {
        final Iterator<Range> iterator = set.asRanges().iterator();
        while (iterator.hasNext()) {
            this.add(iterator.next());
        }
    }
    
    @Override
    public void removeAll(final RangeSet set) {
        final Iterator<Range> iterator = set.asRanges().iterator();
        while (iterator.hasNext()) {
            this.remove(iterator.next());
        }
    }
    
    @Override
    public abstract boolean encloses(final Range p0);
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o == this || (o instanceof RangeSet && this.asRanges().equals(((RangeSet)o).asRanges()));
    }
    
    @Override
    public final int hashCode() {
        return this.asRanges().hashCode();
    }
    
    @Override
    public final String toString() {
        return this.asRanges().toString();
    }
}
