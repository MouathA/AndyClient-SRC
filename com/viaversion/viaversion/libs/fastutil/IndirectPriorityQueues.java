package com.viaversion.viaversion.libs.fastutil;

import java.util.*;

public class IndirectPriorityQueues
{
    public static final EmptyIndirectPriorityQueue EMPTY_QUEUE;
    
    private IndirectPriorityQueues() {
    }
    
    public static IndirectPriorityQueue synchronize(final IndirectPriorityQueue indirectPriorityQueue) {
        return new SynchronizedIndirectPriorityQueue(indirectPriorityQueue);
    }
    
    public static IndirectPriorityQueue synchronize(final IndirectPriorityQueue indirectPriorityQueue, final Object o) {
        return new SynchronizedIndirectPriorityQueue(indirectPriorityQueue, o);
    }
    
    static {
        EMPTY_QUEUE = new EmptyIndirectPriorityQueue();
    }
    
    public static class SynchronizedIndirectPriorityQueue implements IndirectPriorityQueue
    {
        public static final long serialVersionUID = -7046029254386353129L;
        protected final IndirectPriorityQueue q;
        protected final Object sync;
        
        protected SynchronizedIndirectPriorityQueue(final IndirectPriorityQueue q, final Object sync) {
            this.q = q;
            this.sync = sync;
        }
        
        protected SynchronizedIndirectPriorityQueue(final IndirectPriorityQueue q) {
            this.q = q;
            this.sync = this;
        }
        
        @Override
        public void enqueue(final int n) {
            // monitorenter(sync = this.sync)
            this.q.enqueue(n);
        }
        // monitorexit(sync)
        
        @Override
        public int dequeue() {
            // monitorenter(sync = this.sync)
            // monitorexit(sync)
            return this.q.dequeue();
        }
        
        @Override
        public boolean contains(final int n) {
            // monitorenter(sync = this.sync)
            // monitorexit(sync)
            return this.q.contains(n);
        }
        
        @Override
        public int first() {
            // monitorenter(sync = this.sync)
            // monitorexit(sync)
            return this.q.first();
        }
        
        @Override
        public int last() {
            // monitorenter(sync = this.sync)
            // monitorexit(sync)
            return this.q.last();
        }
        
        @Override
        public boolean isEmpty() {
            // monitorenter(sync = this.sync)
            // monitorexit(sync)
            return this.q.isEmpty();
        }
        
        @Override
        public int size() {
            // monitorenter(sync = this.sync)
            // monitorexit(sync)
            return this.q.size();
        }
        
        @Override
        public void clear() {
            // monitorenter(sync = this.sync)
            this.q.clear();
        }
        // monitorexit(sync)
        
        @Override
        public void changed() {
            // monitorenter(sync = this.sync)
            this.q.changed();
        }
        // monitorexit(sync)
        
        @Override
        public void allChanged() {
            // monitorenter(sync = this.sync)
            this.q.allChanged();
        }
        // monitorexit(sync)
        
        @Override
        public void changed(final int n) {
            // monitorenter(sync = this.sync)
            this.q.changed(n);
        }
        // monitorexit(sync)
        
        @Override
        public boolean remove(final int n) {
            // monitorenter(sync = this.sync)
            // monitorexit(sync)
            return this.q.remove(n);
        }
        
        @Override
        public Comparator comparator() {
            // monitorenter(sync = this.sync)
            // monitorexit(sync)
            return this.q.comparator();
        }
        
        @Override
        public int front(final int[] array) {
            return this.q.front(array);
        }
    }
    
    public static class EmptyIndirectPriorityQueue implements IndirectPriorityQueue
    {
        protected EmptyIndirectPriorityQueue() {
        }
        
        @Override
        public void enqueue(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public int dequeue() {
            throw new NoSuchElementException();
        }
        
        @Override
        public boolean isEmpty() {
            return true;
        }
        
        @Override
        public int size() {
            return 0;
        }
        
        @Override
        public boolean contains(final int n) {
            return false;
        }
        
        @Override
        public void clear() {
        }
        
        @Override
        public int first() {
            throw new NoSuchElementException();
        }
        
        @Override
        public int last() {
            throw new NoSuchElementException();
        }
        
        @Override
        public void changed() {
            throw new NoSuchElementException();
        }
        
        @Override
        public void allChanged() {
        }
        
        @Override
        public Comparator comparator() {
            return null;
        }
        
        @Override
        public void changed(final int n) {
            throw new IllegalArgumentException("Index " + n + " is not in the queue");
        }
        
        @Override
        public boolean remove(final int n) {
            return false;
        }
        
        @Override
        public int front(final int[] array) {
            return 0;
        }
    }
}
