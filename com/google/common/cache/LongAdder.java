package com.google.common.cache;

import com.google.common.annotations.*;
import java.io.*;

@GwtCompatible(emulated = true)
final class LongAdder extends Striped64 implements Serializable, LongAddable
{
    private static final long serialVersionUID = 7249069246863182397L;
    
    @Override
    final long fn(final long n, final long n2) {
        return n + n2;
    }
    
    public LongAdder() {
    }
    
    @Override
    public void add(final long n) {
        final Cell[] cells;
        if ((cells = this.cells) == null) {
            final long base = this.base;
            if (this.casBase(base, base + n)) {
                return;
            }
        }
        final HashCode hashCode;
        final int code = (hashCode = LongAdder.threadHashCode.get()).code;
        final int length;
        final Cell cell;
        if (cells != null && (length = cells.length) >= 1 && (cell = cells[length - 1 & code]) != null) {
            final Cell cell2 = cell;
            final long value = cell.value;
            final boolean cas;
            if (cas = cell2.cas(value, value + n)) {
                return;
            }
        }
        this.retryUpdate(n, hashCode, true);
    }
    
    @Override
    public void increment() {
        this.add(1L);
    }
    
    public void decrement() {
        this.add(-1L);
    }
    
    @Override
    public long sum() {
        long base = this.base;
        final Cell[] cells = this.cells;
        if (cells != null) {
            while (0 < cells.length) {
                final Cell cell = cells[0];
                if (cell != null) {
                    base += cell.value;
                }
                int n = 0;
                ++n;
            }
        }
        return base;
    }
    
    public void reset() {
        this.internalReset(0L);
    }
    
    public long sumThenReset() {
        long base = this.base;
        final Cell[] cells = this.cells;
        this.base = 0L;
        if (cells != null) {
            while (0 < cells.length) {
                final Cell cell = cells[0];
                if (cell != null) {
                    base += cell.value;
                    cell.value = 0L;
                }
                int n = 0;
                ++n;
            }
        }
        return base;
    }
    
    @Override
    public String toString() {
        return Long.toString(this.sum());
    }
    
    @Override
    public long longValue() {
        return this.sum();
    }
    
    @Override
    public int intValue() {
        return (int)this.sum();
    }
    
    @Override
    public float floatValue() {
        return (float)this.sum();
    }
    
    @Override
    public double doubleValue() {
        return (double)this.sum();
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeLong(this.sum());
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.busy = 0;
        this.cells = null;
        this.base = objectInputStream.readLong();
    }
}
