package com.google.common.cache;

import sun.misc.*;
import java.util.*;

abstract class Striped64 extends Number
{
    static final ThreadHashCode threadHashCode;
    static final int NCPU;
    transient Cell[] cells;
    transient long base;
    transient int busy;
    private static final Unsafe UNSAFE;
    private static final long baseOffset;
    private static final long busyOffset;
    
    final boolean casBase(final long n, final long n2) {
        return Striped64.UNSAFE.compareAndSwapLong(this, Striped64.baseOffset, n, n2);
    }
    
    final boolean casBusy() {
        return Striped64.UNSAFE.compareAndSwapInt(this, Striped64.busyOffset, 0, 1);
    }
    
    abstract long fn(final long p0, final long p1);
    
    final void retryUpdate(final long n, final HashCode hashCode, final boolean b) {
        int code = hashCode.code;
        while (true) {
            final Cell[] cells;
            final int length;
            if ((cells = this.cells) != null && (length = cells.length) > 0) {
                final Cell cell;
                if ((cell = cells[length - 1 & code]) == null) {
                    if (this.busy == 0) {
                        final Cell cell2 = new Cell(n);
                        if (this.busy == 0 && this.casBusy()) {
                            final Cell[] cells2;
                            final int length2;
                            final int n2;
                            if ((cells2 = this.cells) != null && (length2 = cells2.length) > 0 && cells2[n2 = (length2 - 1 & code)] == null) {
                                cells2[n2] = cell2;
                            }
                            this.busy = 0;
                            break;
                        }
                    }
                }
                else {
                    final Cell cell3 = cell;
                    final long value = cell.value;
                    if (cell3.cas(value, this.fn(value, n))) {
                        break;
                    }
                    if (length >= Striped64.NCPU || this.cells != cells) {}
                }
                final int n3 = code ^ code << 13;
                final int n4 = n3 ^ n3 >>> 17;
                code = (n4 ^ n4 << 5);
            }
            else {
                if (this.busy == 0 && this.cells == cells && this.casBusy()) {
                    if (this.cells == cells) {
                        final Cell[] cells3 = new Cell[2];
                        cells3[code & 0x1] = new Cell(n);
                        this.cells = cells3;
                    }
                    this.busy = 0;
                    break;
                }
                final long base = this.base;
                if (this.casBase(base, this.fn(base, n))) {
                    break;
                }
                continue;
            }
        }
        hashCode.code = code;
    }
    
    final void internalReset(final long n) {
        final Cell[] cells = this.cells;
        this.base = n;
        if (cells != null) {
            while (0 < cells.length) {
                final Cell cell = cells[0];
                if (cell != null) {
                    cell.value = n;
                }
                int n2 = 0;
                ++n2;
            }
        }
    }
    
    private static Unsafe getUnsafe() {
        return Unsafe.getUnsafe();
    }
    
    static Unsafe access$000() {
        return getUnsafe();
    }
    
    static {
        threadHashCode = new ThreadHashCode();
        NCPU = Runtime.getRuntime().availableProcessors();
        UNSAFE = getUnsafe();
        final Class<Striped64> clazz = Striped64.class;
        baseOffset = Striped64.UNSAFE.objectFieldOffset(clazz.getDeclaredField("base"));
        busyOffset = Striped64.UNSAFE.objectFieldOffset(clazz.getDeclaredField("busy"));
    }
    
    static final class ThreadHashCode extends ThreadLocal
    {
        public HashCode initialValue() {
            return new HashCode();
        }
        
        public Object initialValue() {
            return this.initialValue();
        }
    }
    
    static final class HashCode
    {
        static final Random rng;
        int code;
        
        HashCode() {
            final int nextInt = HashCode.rng.nextInt();
            this.code = ((nextInt == 0) ? 1 : nextInt);
        }
        
        static {
            rng = new Random();
        }
    }
    
    static final class Cell
    {
        long p0;
        long p1;
        long p2;
        long p3;
        long p4;
        long p5;
        long p6;
        long value;
        long q0;
        long q1;
        long q2;
        long q3;
        long q4;
        long q5;
        long q6;
        private static final Unsafe UNSAFE;
        private static final long valueOffset;
        
        Cell(final long value) {
            this.value = value;
        }
        
        final boolean cas(final long n, final long n2) {
            return Cell.UNSAFE.compareAndSwapLong(this, Cell.valueOffset, n, n2);
        }
        
        static {
            UNSAFE = Striped64.access$000();
            valueOffset = Cell.UNSAFE.objectFieldOffset(Cell.class.getDeclaredField("value"));
        }
    }
}
