package com.google.common.collect;

import java.util.concurrent.*;
import java.util.*;

public abstract class ForwardingBlockingDeque extends ForwardingDeque implements BlockingDeque
{
    protected ForwardingBlockingDeque() {
    }
    
    @Override
    protected abstract BlockingDeque delegate();
    
    @Override
    public int remainingCapacity() {
        return this.delegate().remainingCapacity();
    }
    
    @Override
    public void putFirst(final Object o) throws InterruptedException {
        this.delegate().putFirst(o);
    }
    
    @Override
    public void putLast(final Object o) throws InterruptedException {
        this.delegate().putLast(o);
    }
    
    @Override
    public boolean offerFirst(final Object o, final long n, final TimeUnit timeUnit) throws InterruptedException {
        return this.delegate().offerFirst(o, n, timeUnit);
    }
    
    @Override
    public boolean offerLast(final Object o, final long n, final TimeUnit timeUnit) throws InterruptedException {
        return this.delegate().offerLast(o, n, timeUnit);
    }
    
    @Override
    public Object takeFirst() throws InterruptedException {
        return this.delegate().takeFirst();
    }
    
    @Override
    public Object takeLast() throws InterruptedException {
        return this.delegate().takeLast();
    }
    
    @Override
    public Object pollFirst(final long n, final TimeUnit timeUnit) throws InterruptedException {
        return this.delegate().pollFirst(n, timeUnit);
    }
    
    @Override
    public Object pollLast(final long n, final TimeUnit timeUnit) throws InterruptedException {
        return this.delegate().pollLast(n, timeUnit);
    }
    
    @Override
    public void put(final Object o) throws InterruptedException {
        this.delegate().put(o);
    }
    
    @Override
    public boolean offer(final Object o, final long n, final TimeUnit timeUnit) throws InterruptedException {
        return this.delegate().offer(o, n, timeUnit);
    }
    
    @Override
    public Object take() throws InterruptedException {
        return this.delegate().take();
    }
    
    @Override
    public Object poll(final long n, final TimeUnit timeUnit) throws InterruptedException {
        return this.delegate().poll(n, timeUnit);
    }
    
    @Override
    public int drainTo(final Collection collection) {
        return this.delegate().drainTo(collection);
    }
    
    @Override
    public int drainTo(final Collection collection, final int n) {
        return this.delegate().drainTo(collection, n);
    }
    
    @Override
    protected Deque delegate() {
        return this.delegate();
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
