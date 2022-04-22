package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

public abstract class ForwardingNavigableSet extends ForwardingSortedSet implements NavigableSet
{
    protected ForwardingNavigableSet() {
    }
    
    @Override
    protected abstract NavigableSet delegate();
    
    @Override
    public Object lower(final Object o) {
        return this.delegate().lower(o);
    }
    
    protected Object standardLower(final Object o) {
        return Iterators.getNext(this.headSet(o, false).descendingIterator(), null);
    }
    
    @Override
    public Object floor(final Object o) {
        return this.delegate().floor(o);
    }
    
    protected Object standardFloor(final Object o) {
        return Iterators.getNext(this.headSet(o, true).descendingIterator(), null);
    }
    
    @Override
    public Object ceiling(final Object o) {
        return this.delegate().ceiling(o);
    }
    
    protected Object standardCeiling(final Object o) {
        return Iterators.getNext(this.tailSet(o, true).iterator(), null);
    }
    
    @Override
    public Object higher(final Object o) {
        return this.delegate().higher(o);
    }
    
    protected Object standardHigher(final Object o) {
        return Iterators.getNext(this.tailSet(o, false).iterator(), null);
    }
    
    @Override
    public Object pollFirst() {
        return this.delegate().pollFirst();
    }
    
    protected Object standardPollFirst() {
        return Iterators.pollNext(this.iterator());
    }
    
    @Override
    public Object pollLast() {
        return this.delegate().pollLast();
    }
    
    protected Object standardPollLast() {
        return Iterators.pollNext(this.descendingIterator());
    }
    
    protected Object standardFirst() {
        return this.iterator().next();
    }
    
    protected Object standardLast() {
        return this.descendingIterator().next();
    }
    
    @Override
    public NavigableSet descendingSet() {
        return this.delegate().descendingSet();
    }
    
    @Override
    public Iterator descendingIterator() {
        return this.delegate().descendingIterator();
    }
    
    @Override
    public NavigableSet subSet(final Object o, final boolean b, final Object o2, final boolean b2) {
        return this.delegate().subSet(o, b, o2, b2);
    }
    
    @Beta
    protected NavigableSet standardSubSet(final Object o, final boolean b, final Object o2, final boolean b2) {
        return this.tailSet(o, b).headSet(o2, b2);
    }
    
    @Override
    protected SortedSet standardSubSet(final Object o, final Object o2) {
        return this.subSet(o, true, o2, false);
    }
    
    @Override
    public NavigableSet headSet(final Object o, final boolean b) {
        return this.delegate().headSet(o, b);
    }
    
    protected SortedSet standardHeadSet(final Object o) {
        return this.headSet(o, false);
    }
    
    @Override
    public NavigableSet tailSet(final Object o, final boolean b) {
        return this.delegate().tailSet(o, b);
    }
    
    protected SortedSet standardTailSet(final Object o) {
        return this.tailSet(o, true);
    }
    
    @Override
    protected SortedSet delegate() {
        return this.delegate();
    }
    
    @Override
    protected Set delegate() {
        return this.delegate();
    }
    
    @Override
    protected Collection delegate() {
        return this.delegate();
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
    
    @Beta
    protected class StandardDescendingSet extends Sets.DescendingSet
    {
        final ForwardingNavigableSet this$0;
        
        public StandardDescendingSet(final ForwardingNavigableSet this$0) {
            super(this.this$0 = this$0);
        }
    }
}
