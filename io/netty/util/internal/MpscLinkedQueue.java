package io.netty.util.internal;

import java.lang.reflect.*;
import java.util.*;
import java.io.*;

final class MpscLinkedQueue extends MpscLinkedQueueTailRef implements Queue
{
    private static final long serialVersionUID = -1878402552271506449L;
    long p00;
    long p01;
    long p02;
    long p03;
    long p04;
    long p05;
    long p06;
    long p07;
    long p30;
    long p31;
    long p32;
    long p33;
    long p34;
    long p35;
    long p36;
    long p37;
    
    MpscLinkedQueue() {
        final DefaultNode defaultNode = new DefaultNode(null);
        this.setHeadRef(defaultNode);
        this.setTailRef(defaultNode);
    }
    
    private MpscLinkedQueueNode peekNode() {
        while (true) {
            final MpscLinkedQueueNode headRef = this.headRef();
            final MpscLinkedQueueNode next = headRef.next();
            if (next != null) {
                return next;
            }
            if (headRef == this.tailRef()) {
                return null;
            }
        }
    }
    
    @Override
    public boolean offer(final Object o) {
        if (o == null) {
            throw new NullPointerException("value");
        }
        MpscLinkedQueueNode next;
        if (o instanceof MpscLinkedQueueNode) {
            next = (MpscLinkedQueueNode)o;
            next.setNext(null);
        }
        else {
            next = new DefaultNode(o);
        }
        this.getAndSetTailRef(next).setNext(next);
        return true;
    }
    
    @Override
    public Object poll() {
        final MpscLinkedQueueNode peekNode = this.peekNode();
        if (peekNode == null) {
            return null;
        }
        final MpscLinkedQueueNode headRef = this.headRef();
        this.lazySetHeadRef(peekNode);
        headRef.unlink();
        return peekNode.clearMaybe();
    }
    
    @Override
    public Object peek() {
        final MpscLinkedQueueNode peekNode = this.peekNode();
        if (peekNode == null) {
            return null;
        }
        return peekNode.value();
    }
    
    @Override
    public int size() {
        for (MpscLinkedQueueNode mpscLinkedQueueNode = this.peekNode(); mpscLinkedQueueNode != null; mpscLinkedQueueNode = mpscLinkedQueueNode.next()) {
            int n = 0;
            ++n;
        }
        return 0;
    }
    
    @Override
    public boolean isEmpty() {
        return this.peekNode() == null;
    }
    
    @Override
    public boolean contains(final Object o) {
        for (MpscLinkedQueueNode mpscLinkedQueueNode = this.peekNode(); mpscLinkedQueueNode != null; mpscLinkedQueueNode = mpscLinkedQueueNode.next()) {
            if (mpscLinkedQueueNode.value() == o) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Iterator iterator() {
        return new Iterator() {
            private MpscLinkedQueueNode node = MpscLinkedQueue.access$000(this.this$0);
            final MpscLinkedQueue this$0;
            
            @Override
            public boolean hasNext() {
                return this.node != null;
            }
            
            @Override
            public Object next() {
                final MpscLinkedQueueNode node = this.node;
                if (node == null) {
                    throw new NoSuchElementException();
                }
                final Object value = node.value();
                this.node = node.next();
                return value;
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    @Override
    public boolean add(final Object o) {
        if (this.offer(o)) {
            return true;
        }
        throw new IllegalStateException("queue full");
    }
    
    @Override
    public Object remove() {
        final Object poll = this.poll();
        if (poll != null) {
            return poll;
        }
        throw new NoSuchElementException();
    }
    
    @Override
    public Object element() {
        final Object peek = this.peek();
        if (peek != null) {
            return peek;
        }
        throw new NoSuchElementException();
    }
    
    @Override
    public Object[] toArray() {
        final Object[] array = new Object[this.size()];
        final Iterator iterator = this.iterator();
        while (0 < array.length) {
            if (!iterator.hasNext()) {
                return Arrays.copyOf(array, 0);
            }
            array[0] = iterator.next();
            int n = 0;
            ++n;
        }
        return array;
    }
    
    @Override
    public Object[] toArray(final Object[] array) {
        final int size = this.size();
        Object[] array2;
        if (array.length >= size) {
            array2 = array;
        }
        else {
            array2 = (Object[])Array.newInstance(array.getClass().getComponentType(), size);
        }
        final Iterator iterator = this.iterator();
        while (0 < array2.length) {
            if (iterator.hasNext()) {
                array2[0] = iterator.next();
                int n = 0;
                ++n;
            }
            else {
                if (array == array2) {
                    array2[0] = null;
                    return array2;
                }
                if (array.length < 0) {
                    return Arrays.copyOf(array2, 0);
                }
                System.arraycopy(array2, 0, array, 0, 0);
                if (array.length > 0) {
                    array[0] = null;
                }
                return array;
            }
        }
        return array2;
    }
    
    @Override
    public boolean remove(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean containsAll(final Collection collection) {
        final Iterator<Object> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (!this.contains(iterator.next())) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean addAll(final Collection collection) {
        if (collection == null) {
            throw new NullPointerException("c");
        }
        if (collection == this) {
            throw new IllegalArgumentException("c == this");
        }
        final Iterator<Object> iterator = collection.iterator();
        while (iterator.hasNext()) {
            this.add(iterator.next());
        }
        return true;
    }
    
    @Override
    public boolean removeAll(final Collection collection) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean retainAll(final Collection collection) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void clear() {
        while (this.poll() != null) {}
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        final Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            objectOutputStream.writeObject(iterator.next());
        }
        objectOutputStream.writeObject(null);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        final DefaultNode defaultNode = new DefaultNode(null);
        this.setHeadRef(defaultNode);
        this.setTailRef(defaultNode);
        while (true) {
            final Object object = objectInputStream.readObject();
            if (object == null) {
                break;
            }
            this.add(object);
        }
    }
    
    static MpscLinkedQueueNode access$000(final MpscLinkedQueue mpscLinkedQueue) {
        return mpscLinkedQueue.peekNode();
    }
    
    private static final class DefaultNode extends MpscLinkedQueueNode
    {
        private Object value;
        
        DefaultNode(final Object value) {
            this.value = value;
        }
        
        @Override
        public Object value() {
            return this.value;
        }
        
        @Override
        protected Object clearMaybe() {
            final Object value = this.value;
            this.value = null;
            return value;
        }
    }
}
