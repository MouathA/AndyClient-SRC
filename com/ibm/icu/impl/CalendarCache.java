package com.ibm.icu.impl;

public class CalendarCache
{
    private static final int[] primes;
    private int pIndex;
    private int size;
    private int arraySize;
    private int threshold;
    private long[] keys;
    private long[] values;
    public static long EMPTY;
    
    public CalendarCache() {
        this.pIndex = 0;
        this.size = 0;
        this.arraySize = CalendarCache.primes[this.pIndex];
        this.threshold = this.arraySize * 3 / 4;
        this.keys = new long[this.arraySize];
        this.values = new long[this.arraySize];
        this.makeArrays(this.arraySize);
    }
    
    private void makeArrays(final int arraySize) {
        this.keys = new long[arraySize];
        this.values = new long[arraySize];
        for (int i = 0; i < arraySize; ++i) {
            this.values[i] = CalendarCache.EMPTY;
        }
        this.arraySize = arraySize;
        this.threshold = (int)(this.arraySize * 0.75);
        this.size = 0;
    }
    
    public synchronized long get(final long n) {
        return this.values[this.findIndex(n)];
    }
    
    public synchronized void put(final long n, final long n2) {
        if (this.size >= this.threshold) {
            this.rehash();
        }
        final int index = this.findIndex(n);
        this.keys[index] = n;
        this.values[index] = n2;
        ++this.size;
    }
    
    private final int findIndex(final long n) {
        int hash = this.hash(n);
        for (int hash2 = 0; this.values[hash] != CalendarCache.EMPTY && this.keys[hash] != n; hash = (hash + hash2) % this.arraySize) {
            if (hash2 == 0) {
                hash2 = this.hash2(n);
            }
        }
        return hash;
    }
    
    private void rehash() {
        final int arraySize = this.arraySize;
        final long[] keys = this.keys;
        final long[] values = this.values;
        if (this.pIndex < CalendarCache.primes.length - 1) {
            this.arraySize = CalendarCache.primes[++this.pIndex];
        }
        else {
            this.arraySize = this.arraySize * 2 + 1;
        }
        this.size = 0;
        this.makeArrays(this.arraySize);
        for (int i = 0; i < arraySize; ++i) {
            if (values[i] != CalendarCache.EMPTY) {
                this.put(keys[i], values[i]);
            }
        }
    }
    
    private final int hash(final long n) {
        int n2 = (int)((n * 15821L + 1L) % this.arraySize);
        if (n2 < 0) {
            n2 += this.arraySize;
        }
        return n2;
    }
    
    private final int hash2(final long n) {
        return this.arraySize - 2 - (int)(n % (this.arraySize - 2));
    }
    
    static {
        primes = new int[] { 61, 127, 509, 1021, 2039, 4093, 8191, 16381, 32749, 65521, 131071, 262139 };
        CalendarCache.EMPTY = Long.MIN_VALUE;
    }
}
