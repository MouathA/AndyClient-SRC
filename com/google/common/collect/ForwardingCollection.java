package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;
import javax.annotation.*;
import com.google.common.base.*;

@GwtCompatible
public abstract class ForwardingCollection extends ForwardingObject implements Collection
{
    protected ForwardingCollection() {
    }
    
    @Override
    protected abstract Collection delegate();
    
    @Override
    public Iterator iterator() {
        return this.delegate().iterator();
    }
    
    @Override
    public int size() {
        return this.delegate().size();
    }
    
    @Override
    public boolean removeAll(final Collection collection) {
        return this.delegate().removeAll(collection);
    }
    
    @Override
    public boolean isEmpty() {
        return this.delegate().isEmpty();
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.delegate().contains(o);
    }
    
    @Override
    public boolean add(final Object o) {
        return this.delegate().add(o);
    }
    
    @Override
    public boolean remove(final Object o) {
        return this.delegate().remove(o);
    }
    
    @Override
    public boolean containsAll(final Collection collection) {
        return this.delegate().containsAll(collection);
    }
    
    @Override
    public boolean addAll(final Collection collection) {
        return this.delegate().addAll(collection);
    }
    
    @Override
    public boolean retainAll(final Collection collection) {
        return this.delegate().retainAll(collection);
    }
    
    @Override
    public void clear() {
        this.delegate().clear();
    }
    
    @Override
    public Object[] toArray() {
        return this.delegate().toArray();
    }
    
    @Override
    public Object[] toArray(final Object[] array) {
        return this.delegate().toArray(array);
    }
    
    protected boolean standardContains(@Nullable final Object o) {
        return Iterators.contains(this.iterator(), o);
    }
    
    protected boolean standardContainsAll(final Collection collection) {
        return Collections2.containsAllImpl(this, collection);
    }
    
    protected boolean standardAddAll(final Collection collection) {
        return Iterators.addAll(this, collection.iterator());
    }
    
    protected boolean standardRemove(@Nullable final Object o) {
        final Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (Objects.equal(iterator.next(), o)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    
    protected boolean standardRemoveAll(final Collection collection) {
        return Iterators.removeAll(this.iterator(), collection);
    }
    
    protected boolean standardRetainAll(final Collection collection) {
        return Iterators.retainAll(this.iterator(), collection);
    }
    
    protected void standardClear() {
        Iterators.clear(this.iterator());
    }
    
    protected boolean standardIsEmpty() {
        return !this.iterator().hasNext();
    }
    
    protected String standardToString() {
        return Collections2.toStringImpl(this);
    }
    
    protected Object[] standardToArray() {
        return this.toArray(new Object[this.size()]);
    }
    
    protected Object[] standardToArray(final Object[] array) {
        return ObjectArrays.toArrayImpl(this, array);
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
}
