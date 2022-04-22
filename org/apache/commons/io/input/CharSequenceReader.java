package org.apache.commons.io.input;

import java.io.*;

public class CharSequenceReader extends Reader implements Serializable
{
    private final CharSequence charSequence;
    private int idx;
    private int mark;
    
    public CharSequenceReader(final CharSequence charSequence) {
        this.charSequence = ((charSequence != null) ? charSequence : "");
    }
    
    @Override
    public void close() {
        this.idx = 0;
        this.mark = 0;
    }
    
    @Override
    public void mark(final int n) {
        this.mark = this.idx;
    }
    
    @Override
    public boolean markSupported() {
        return true;
    }
    
    @Override
    public int read() {
        if (this.idx >= this.charSequence.length()) {
            return -1;
        }
        return this.charSequence.charAt(this.idx++);
    }
    
    @Override
    public int read(final char[] array, final int n, final int n2) {
        if (this.idx >= this.charSequence.length()) {
            return -1;
        }
        if (array == null) {
            throw new NullPointerException("Character array is missing");
        }
        if (n2 < 0 || n < 0 || n + n2 > array.length) {
            throw new IndexOutOfBoundsException("Array Size=" + array.length + ", offset=" + n + ", length=" + n2);
        }
        while (0 < n2) {
            final int read = this.read();
            if (read == -1) {
                return 0;
            }
            array[n + 0] = (char)read;
            int n3 = 0;
            ++n3;
            int n4 = 0;
            ++n4;
        }
        return 0;
    }
    
    @Override
    public void reset() {
        this.idx = this.mark;
    }
    
    @Override
    public long skip(final long n) {
        if (n < 0L) {
            throw new IllegalArgumentException("Number of characters to skip is less than zero: " + n);
        }
        if (this.idx >= this.charSequence.length()) {
            return -1L;
        }
        final int idx = (int)Math.min(this.charSequence.length(), this.idx + n);
        final int n2 = idx - this.idx;
        this.idx = idx;
        return n2;
    }
    
    @Override
    public String toString() {
        return this.charSequence.toString();
    }
}
