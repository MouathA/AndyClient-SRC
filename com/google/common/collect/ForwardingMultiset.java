package com.google.common.collect;

import javax.annotation.*;
import com.google.common.base.*;
import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible
public abstract class ForwardingMultiset extends ForwardingCollection implements Multiset
{
    protected ForwardingMultiset() {
    }
    
    @Override
    protected abstract Multiset delegate();
    
    @Override
    public int count(final Object o) {
        return this.delegate().count(o);
    }
    
    @Override
    public int add(final Object o, final int n) {
        return this.delegate().add(o, n);
    }
    
    @Override
    public int remove(final Object o, final int n) {
        return this.delegate().remove(o, n);
    }
    
    @Override
    public Set elementSet() {
        return this.delegate().elementSet();
    }
    
    @Override
    public Set entrySet() {
        return this.delegate().entrySet();
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o == this || this.delegate().equals(o);
    }
    
    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }
    
    @Override
    public int setCount(final Object o, final int n) {
        return this.delegate().setCount(o, n);
    }
    
    @Override
    public boolean setCount(final Object o, final int n, final int n2) {
        return this.delegate().setCount(o, n, n2);
    }
    
    @Override
    protected boolean standardContains(@Nullable final Object o) {
        return this.count(o) > 0;
    }
    
    @Override
    protected void standardClear() {
        Iterators.clear(this.entrySet().iterator());
    }
    
    @Beta
    protected int standardCount(@Nullable final Object o) {
        for (final Entry entry : this.entrySet()) {
            if (Objects.equal(entry.getElement(), o)) {
                return entry.getCount();
            }
        }
        return 0;
    }
    
    protected boolean standardAdd(final Object o) {
        this.add(o, 1);
        return true;
    }
    
    @Beta
    @Override
    protected boolean standardAddAll(final Collection collection) {
        return Multisets.addAllImpl(this, collection);
    }
    
    @Override
    protected boolean standardRemove(final Object o) {
        return this.remove(o, 1) > 0;
    }
    
    @Override
    protected boolean standardRemoveAll(final Collection collection) {
        return Multisets.removeAllImpl(this, collection);
    }
    
    @Override
    protected boolean standardRetainAll(final Collection collection) {
        return Multisets.retainAllImpl(this, collection);
    }
    
    protected int standardSetCount(final Object o, final int n) {
        return Multisets.setCountImpl(this, o, n);
    }
    
    protected boolean standardSetCount(final Object o, final int n, final int n2) {
        return Multisets.setCountImpl(this, o, n, n2);
    }
    
    protected Iterator standardIterator() {
        return Multisets.iteratorImpl(this);
    }
    
    protected int standardSize() {
        return Multisets.sizeImpl(this);
    }
    
    protected boolean standardEquals(@Nullable final Object o) {
        return Multisets.equalsImpl(this, o);
    }
    
    protected int standardHashCode() {
        return this.entrySet().hashCode();
    }
    
    @Override
    protected String standardToString() {
        return this.entrySet().toString();
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
    protected class StandardElementSet extends Multisets.ElementSet
    {
        final ForwardingMultiset this$0;
        
        public StandardElementSet(final ForwardingMultiset this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        Multiset multiset() {
            return this.this$0;
        }
    }
}
