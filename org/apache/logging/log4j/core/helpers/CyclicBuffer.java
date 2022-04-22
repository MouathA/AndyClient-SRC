package org.apache.logging.log4j.core.helpers;

import java.lang.reflect.*;

public class CyclicBuffer
{
    private final Object[] ring;
    private int first;
    private int last;
    private int numElems;
    private final Class clazz;
    
    public CyclicBuffer(final Class clazz, final int n) throws IllegalArgumentException {
        this.first = 0;
        this.last = 0;
        this.numElems = 0;
        if (n < 1) {
            throw new IllegalArgumentException("The maxSize argument (" + n + ") is not a positive integer.");
        }
        this.ring = this.makeArray(clazz, n);
        this.clazz = clazz;
    }
    
    private Object[] makeArray(final Class clazz, final int n) {
        return (Object[])Array.newInstance(clazz, n);
    }
    
    public synchronized void add(final Object o) {
        this.ring[this.last] = o;
        if (++this.last == this.ring.length) {
            this.last = 0;
        }
        if (this.numElems < this.ring.length) {
            ++this.numElems;
        }
        else if (++this.first == this.ring.length) {
            this.first = 0;
        }
    }
    
    public synchronized Object[] removeAll() {
        final Object[] array = this.makeArray(this.clazz, this.numElems);
        while (this.numElems > 0) {
            --this.numElems;
            final Object[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = this.ring[this.first];
            this.ring[this.first] = null;
            if (++this.first == this.ring.length) {
                this.first = 0;
            }
        }
        return array;
    }
    
    public boolean isEmpty() {
        return 0 == this.numElems;
    }
}
