package com.viaversion.viaversion.libs.fastutil;

import java.util.*;
import java.io.*;

public class PriorityQueues
{
    public static final EmptyPriorityQueue EMPTY_QUEUE;
    
    private PriorityQueues() {
    }
    
    public static PriorityQueue emptyQueue() {
        return PriorityQueues.EMPTY_QUEUE;
    }
    
    public static PriorityQueue synchronize(final PriorityQueue priorityQueue) {
        return new SynchronizedPriorityQueue(priorityQueue);
    }
    
    public static PriorityQueue synchronize(final PriorityQueue priorityQueue, final Object o) {
        return new SynchronizedPriorityQueue(priorityQueue, o);
    }
    
    static {
        EMPTY_QUEUE = new EmptyPriorityQueue();
    }
    
    public static class EmptyPriorityQueue implements PriorityQueue, Serializable
    {
        private static final long serialVersionUID = 0L;
        
        protected EmptyPriorityQueue() {
        }
        
        @Override
        public void enqueue(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Object dequeue() {
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
        public void clear() {
        }
        
        @Override
        public Object first() {
            throw new NoSuchElementException();
        }
        
        @Override
        public Object last() {
            throw new NoSuchElementException();
        }
        
        @Override
        public void changed() {
            throw new NoSuchElementException();
        }
        
        @Override
        public Comparator comparator() {
            return null;
        }
        
        public Object clone() {
            return PriorityQueues.EMPTY_QUEUE;
        }
        
        @Override
        public int hashCode() {
            return 0;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof PriorityQueue && ((PriorityQueue)o).isEmpty();
        }
        
        private Object readResolve() {
            return PriorityQueues.EMPTY_QUEUE;
        }
    }
    
    public static class SynchronizedPriorityQueue implements PriorityQueue, Serializable
    {
        public static final long serialVersionUID = -7046029254386353129L;
        protected final PriorityQueue q;
        protected final Object sync;
        
        protected SynchronizedPriorityQueue(final PriorityQueue q, final Object sync) {
            this.q = q;
            this.sync = sync;
        }
        
        protected SynchronizedPriorityQueue(final PriorityQueue q) {
            this.q = q;
            this.sync = this;
        }
        
        @Override
        public void enqueue(final Object o) {
            // monitorenter(sync = this.sync)
            this.q.enqueue(o);
        }
        // monitorexit(sync)
        
        @Override
        public Object dequeue() {
            // monitorenter(sync = this.sync)
            // monitorexit(sync)
            return this.q.dequeue();
        }
        
        @Override
        public Object first() {
            // monitorenter(sync = this.sync)
            // monitorexit(sync)
            return this.q.first();
        }
        
        @Override
        public Object last() {
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
        public Comparator comparator() {
            // monitorenter(sync = this.sync)
            // monitorexit(sync)
            return this.q.comparator();
        }
        
        @Override
        public String toString() {
            // monitorenter(sync = this.sync)
            // monitorexit(sync)
            return this.q.toString();
        }
        
        @Override
        public int hashCode() {
            // monitorenter(sync = this.sync)
            // monitorexit(sync)
            return this.q.hashCode();
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            // monitorenter(sync = this.sync)
            // monitorexit(sync)
            return this.q.equals(o);
        }
        
        private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
            // monitorenter(sync = this.sync)
            objectOutputStream.defaultWriteObject();
        }
        // monitorexit(sync)
    }
}
