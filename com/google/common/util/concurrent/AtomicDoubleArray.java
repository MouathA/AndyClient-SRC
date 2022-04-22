package com.google.common.util.concurrent;

import java.util.concurrent.atomic.*;
import java.io.*;

public class AtomicDoubleArray implements Serializable
{
    private static final long serialVersionUID = 0L;
    private transient AtomicLongArray longs;
    
    public AtomicDoubleArray(final int n) {
        this.longs = new AtomicLongArray(n);
    }
    
    public AtomicDoubleArray(final double[] array) {
        final int length = array.length;
        final long[] array2 = new long[length];
        while (0 < length) {
            array2[0] = Double.doubleToRawLongBits(array[0]);
            int n = 0;
            ++n;
        }
        this.longs = new AtomicLongArray(array2);
    }
    
    public final int length() {
        return this.longs.length();
    }
    
    public final double get(final int n) {
        return Double.longBitsToDouble(this.longs.get(n));
    }
    
    public final void set(final int n, final double n2) {
        this.longs.set(n, Double.doubleToRawLongBits(n2));
    }
    
    public final void lazySet(final int n, final double n2) {
        this.set(n, n2);
    }
    
    public final double getAndSet(final int n, final double n2) {
        return Double.longBitsToDouble(this.longs.getAndSet(n, Double.doubleToRawLongBits(n2)));
    }
    
    public final boolean compareAndSet(final int n, final double n2, final double n3) {
        return this.longs.compareAndSet(n, Double.doubleToRawLongBits(n2), Double.doubleToRawLongBits(n3));
    }
    
    public final boolean weakCompareAndSet(final int n, final double n2, final double n3) {
        return this.longs.weakCompareAndSet(n, Double.doubleToRawLongBits(n2), Double.doubleToRawLongBits(n3));
    }
    
    public final double getAndAdd(final int n, final double n2) {
        long value;
        double longBitsToDouble;
        do {
            value = this.longs.get(n);
            longBitsToDouble = Double.longBitsToDouble(value);
        } while (!this.longs.compareAndSet(n, value, Double.doubleToRawLongBits(longBitsToDouble + n2)));
        return longBitsToDouble;
    }
    
    public double addAndGet(final int n, final double n2) {
        long value;
        double n3;
        do {
            value = this.longs.get(n);
            n3 = Double.longBitsToDouble(value) + n2;
        } while (!this.longs.compareAndSet(n, value, Double.doubleToRawLongBits(n3)));
        return n3;
    }
    
    @Override
    public String toString() {
        final int n = this.length() - 1;
        if (n == -1) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder(19 * (n + 1));
        sb.append('[');
        while (true) {
            sb.append(Double.longBitsToDouble(this.longs.get(0)));
            if (n == 0) {
                break;
            }
            sb.append(',').append(' ');
            int n2 = 0;
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        final int length = this.length();
        objectOutputStream.writeInt(length);
        while (0 < length) {
            objectOutputStream.writeDouble(this.get(0));
            int n = 0;
            ++n;
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        final int int1 = objectInputStream.readInt();
        this.longs = new AtomicLongArray(int1);
        while (0 < int1) {
            this.set(0, objectInputStream.readDouble());
            int n = 0;
            ++n;
        }
    }
}
