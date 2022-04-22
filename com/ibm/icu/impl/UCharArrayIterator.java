package com.ibm.icu.impl;

import com.ibm.icu.text.*;

public final class UCharArrayIterator extends UCharacterIterator
{
    private final char[] text;
    private final int start;
    private final int limit;
    private int pos;
    
    public UCharArrayIterator(final char[] text, final int n, final int limit) {
        if (n < 0 || limit > text.length || n > limit) {
            throw new IllegalArgumentException("start: " + n + " or limit: " + limit + " out of range [0, " + text.length + ")");
        }
        this.text = text;
        this.start = n;
        this.limit = limit;
        this.pos = n;
    }
    
    @Override
    public int current() {
        return (this.pos < this.limit) ? this.text[this.pos] : -1;
    }
    
    @Override
    public int getLength() {
        return this.limit - this.start;
    }
    
    @Override
    public int getIndex() {
        return this.pos - this.start;
    }
    
    @Override
    public int next() {
        return (this.pos < this.limit) ? this.text[this.pos++] : -1;
    }
    
    @Override
    public int previous() {
        int n;
        if (this.pos > this.start) {
            final char[] text = this.text;
            final int pos = this.pos - 1;
            this.pos = pos;
            n = text[pos];
        }
        else {
            n = -1;
        }
        return n;
    }
    
    @Override
    public void setIndex(final int n) {
        if (n < 0 || n > this.limit - this.start) {
            throw new IndexOutOfBoundsException("index: " + n + " out of range [0, " + (this.limit - this.start) + ")");
        }
        this.pos = this.start + n;
    }
    
    @Override
    public int getText(final char[] array, final int n) {
        final int n2 = this.limit - this.start;
        System.arraycopy(this.text, this.start, array, n, n2);
        return n2;
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
}
