package com.google.common.collect;

import javax.annotation.*;
import java.util.*;
import com.google.common.annotations.*;

@GwtCompatible
public abstract class ForwardingList extends ForwardingCollection implements List
{
    protected ForwardingList() {
    }
    
    @Override
    protected abstract List delegate();
    
    @Override
    public void add(final int n, final Object o) {
        this.delegate().add(n, o);
    }
    
    @Override
    public boolean addAll(final int n, final Collection collection) {
        return this.delegate().addAll(n, collection);
    }
    
    @Override
    public Object get(final int n) {
        return this.delegate().get(n);
    }
    
    @Override
    public int indexOf(final Object o) {
        return this.delegate().indexOf(o);
    }
    
    @Override
    public int lastIndexOf(final Object o) {
        return this.delegate().lastIndexOf(o);
    }
    
    @Override
    public ListIterator listIterator() {
        return this.delegate().listIterator();
    }
    
    @Override
    public ListIterator listIterator(final int n) {
        return this.delegate().listIterator(n);
    }
    
    @Override
    public Object remove(final int n) {
        return this.delegate().remove(n);
    }
    
    @Override
    public Object set(final int n, final Object o) {
        return this.delegate().set(n, o);
    }
    
    @Override
    public List subList(final int n, final int n2) {
        return this.delegate().subList(n, n2);
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o == this || this.delegate().equals(o);
    }
    
    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }
    
    protected boolean standardAdd(final Object o) {
        this.add(this.size(), o);
        return true;
    }
    
    protected boolean standardAddAll(final int n, final Iterable iterable) {
        return Lists.addAllImpl(this, n, iterable);
    }
    
    protected int standardIndexOf(@Nullable final Object o) {
        return Lists.indexOfImpl(this, o);
    }
    
    protected int standardLastIndexOf(@Nullable final Object o) {
        return Lists.lastIndexOfImpl(this, o);
    }
    
    protected Iterator standardIterator() {
        return this.listIterator();
    }
    
    protected ListIterator standardListIterator() {
        return this.listIterator(0);
    }
    
    @Beta
    protected ListIterator standardListIterator(final int n) {
        return Lists.listIteratorImpl(this, n);
    }
    
    @Beta
    protected List standardSubList(final int n, final int n2) {
        return Lists.subListImpl(this, n, n2);
    }
    
    @Beta
    protected boolean standardEquals(@Nullable final Object o) {
        return Lists.equalsImpl(this, o);
    }
    
    @Beta
    protected int standardHashCode() {
        return Lists.hashCodeImpl(this);
    }
    
    @Override
    protected Collection delegate() {
        return this.delegate();
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
}
