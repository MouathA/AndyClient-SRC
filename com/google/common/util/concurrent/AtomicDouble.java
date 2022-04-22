package com.google.common.util.concurrent;

import java.util.concurrent.atomic.*;
import java.io.*;

public class AtomicDouble extends Number implements Serializable
{
    private static final long serialVersionUID = 0L;
    private transient long value;
    private static final AtomicLongFieldUpdater updater;
    
    public AtomicDouble(final double n) {
        this.value = Double.doubleToRawLongBits(n);
    }
    
    public AtomicDouble() {
    }
    
    public final double get() {
        return Double.longBitsToDouble(this.value);
    }
    
    public final void set(final double n) {
        this.value = Double.doubleToRawLongBits(n);
    }
    
    public final void lazySet(final double n) {
        this.set(n);
    }
    
    public final double getAndSet(final double n) {
        return Double.longBitsToDouble(AtomicDouble.updater.getAndSet(this, Double.doubleToRawLongBits(n)));
    }
    
    public final boolean compareAndSet(final double n, final double n2) {
        return AtomicDouble.updater.compareAndSet(this, Double.doubleToRawLongBits(n), Double.doubleToRawLongBits(n2));
    }
    
    public final boolean weakCompareAndSet(final double n, final double n2) {
        return AtomicDouble.updater.weakCompareAndSet(this, Double.doubleToRawLongBits(n), Double.doubleToRawLongBits(n2));
    }
    
    public final double getAndAdd(final double n) {
        long value;
        double longBitsToDouble;
        do {
            value = this.value;
            longBitsToDouble = Double.longBitsToDouble(value);
        } while (!AtomicDouble.updater.compareAndSet(this, value, Double.doubleToRawLongBits(longBitsToDouble + n)));
        return longBitsToDouble;
    }
    
    public final double addAndGet(final double n) {
        long value;
        double n2;
        do {
            value = this.value;
            n2 = Double.longBitsToDouble(value) + n;
        } while (!AtomicDouble.updater.compareAndSet(this, value, Double.doubleToRawLongBits(n2)));
        return n2;
    }
    
    @Override
    public String toString() {
        return Double.toString(this.get());
    }
    
    @Override
    public int intValue() {
        return (int)this.get();
    }
    
    @Override
    public long longValue() {
        return (long)this.get();
    }
    
    @Override
    public float floatValue() {
        return (float)this.get();
    }
    
    @Override
    public double doubleValue() {
        return this.get();
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeDouble(this.get());
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.set(objectInputStream.readDouble());
    }
    
    static {
        updater = AtomicLongFieldUpdater.newUpdater(AtomicDouble.class, "value");
    }
}
