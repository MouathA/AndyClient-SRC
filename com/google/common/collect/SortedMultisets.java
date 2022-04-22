package com.google.common.collect;

import javax.annotation.*;
import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible(emulated = true)
final class SortedMultisets
{
    private SortedMultisets() {
    }
    
    private static Object getElementOrThrow(final Multiset.Entry entry) {
        if (entry == null) {
            throw new NoSuchElementException();
        }
        return entry.getElement();
    }
    
    private static Object getElementOrNull(@Nullable final Multiset.Entry entry) {
        return (entry == null) ? null : entry.getElement();
    }
    
    static Object access$000(final Multiset.Entry entry) {
        return getElementOrThrow(entry);
    }
    
    static Object access$100(final Multiset.Entry entry) {
        return getElementOrNull(entry);
    }
    
    @GwtIncompatible("Navigable")
    static class NavigableElementSet extends ElementSet implements NavigableSet
    {
        NavigableElementSet(final SortedMultiset sortedMultiset) {
            super(sortedMultiset);
        }
        
        @Override
        public Object lower(final Object o) {
            return SortedMultisets.access$100(this.multiset().headMultiset(o, BoundType.OPEN).lastEntry());
        }
        
        @Override
        public Object floor(final Object o) {
            return SortedMultisets.access$100(this.multiset().headMultiset(o, BoundType.CLOSED).lastEntry());
        }
        
        @Override
        public Object ceiling(final Object o) {
            return SortedMultisets.access$100(this.multiset().tailMultiset(o, BoundType.CLOSED).firstEntry());
        }
        
        @Override
        public Object higher(final Object o) {
            return SortedMultisets.access$100(this.multiset().tailMultiset(o, BoundType.OPEN).firstEntry());
        }
        
        @Override
        public NavigableSet descendingSet() {
            return new NavigableElementSet(this.multiset().descendingMultiset());
        }
        
        @Override
        public Iterator descendingIterator() {
            return this.descendingSet().iterator();
        }
        
        @Override
        public Object pollFirst() {
            return SortedMultisets.access$100(this.multiset().pollFirstEntry());
        }
        
        @Override
        public Object pollLast() {
            return SortedMultisets.access$100(this.multiset().pollLastEntry());
        }
        
        @Override
        public NavigableSet subSet(final Object o, final boolean b, final Object o2, final boolean b2) {
            return new NavigableElementSet(this.multiset().subMultiset(o, BoundType.forBoolean(b), o2, BoundType.forBoolean(b2)));
        }
        
        @Override
        public NavigableSet headSet(final Object o, final boolean b) {
            return new NavigableElementSet(this.multiset().headMultiset(o, BoundType.forBoolean(b)));
        }
        
        @Override
        public NavigableSet tailSet(final Object o, final boolean b) {
            return new NavigableElementSet(this.multiset().tailMultiset(o, BoundType.forBoolean(b)));
        }
    }
    
    static class ElementSet extends Multisets.ElementSet implements SortedSet
    {
        private final SortedMultiset multiset;
        
        ElementSet(final SortedMultiset multiset) {
            this.multiset = multiset;
        }
        
        @Override
        final SortedMultiset multiset() {
            return this.multiset;
        }
        
        @Override
        public Comparator comparator() {
            return this.multiset().comparator();
        }
        
        @Override
        public SortedSet subSet(final Object o, final Object o2) {
            return this.multiset().subMultiset(o, BoundType.CLOSED, o2, BoundType.OPEN).elementSet();
        }
        
        @Override
        public SortedSet headSet(final Object o) {
            return this.multiset().headMultiset(o, BoundType.OPEN).elementSet();
        }
        
        @Override
        public SortedSet tailSet(final Object o) {
            return this.multiset().tailMultiset(o, BoundType.CLOSED).elementSet();
        }
        
        @Override
        public Object first() {
            return SortedMultisets.access$000(this.multiset().firstEntry());
        }
        
        @Override
        public Object last() {
            return SortedMultisets.access$000(this.multiset().lastEntry());
        }
        
        @Override
        Multiset multiset() {
            return this.multiset();
        }
    }
}
