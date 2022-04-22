package com.google.common.collect;

import java.util.*;

public abstract class ForwardingDeque extends ForwardingQueue implements Deque
{
    protected ForwardingDeque() {
    }
    
    @Override
    protected abstract Deque delegate();
    
    @Override
    public void addFirst(final Object o) {
        this.delegate().addFirst(o);
    }
    
    @Override
    public void addLast(final Object o) {
        this.delegate().addLast(o);
    }
    
    @Override
    public Iterator descendingIterator() {
        return this.delegate().descendingIterator();
    }
    
    @Override
    public Object getFirst() {
        return this.delegate().getFirst();
    }
    
    @Override
    public Object getLast() {
        return this.delegate().getLast();
    }
    
    @Override
    public boolean offerFirst(final Object o) {
        return this.delegate().offerFirst(o);
    }
    
    @Override
    public boolean offerLast(final Object o) {
        return this.delegate().offerLast(o);
    }
    
    @Override
    public Object peekFirst() {
        return this.delegate().peekFirst();
    }
    
    @Override
    public Object peekLast() {
        return this.delegate().peekLast();
    }
    
    @Override
    public Object pollFirst() {
        return this.delegate().pollFirst();
    }
    
    @Override
    public Object pollLast() {
        return this.delegate().pollLast();
    }
    
    @Override
    public Object pop() {
        return this.delegate().pop();
    }
    
    @Override
    public void push(final Object o) {
        this.delegate().push(o);
    }
    
    @Override
    public Object removeFirst() {
        return this.delegate().removeFirst();
    }
    
    @Override
    public Object removeLast() {
        return this.delegate().removeLast();
    }
    
    @Override
    public boolean removeFirstOccurrence(final Object o) {
        return this.delegate().removeFirstOccurrence(o);
    }
    
    @Override
    public boolean removeLastOccurrence(final Object o) {
        return this.delegate().removeLastOccurrence(o);
    }
    
    @Override
    protected Queue delegate() {
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
}
