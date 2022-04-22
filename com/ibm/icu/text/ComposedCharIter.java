package com.ibm.icu.text;

import com.ibm.icu.impl.*;

public final class ComposedCharIter
{
    @Deprecated
    public static final char DONE = '\uffff';
    private final Normalizer2Impl n2impl;
    private String decompBuf;
    private int curChar;
    private int nextChar;
    
    @Deprecated
    public ComposedCharIter() {
        this(false, 0);
    }
    
    @Deprecated
    public ComposedCharIter(final boolean b, final int n) {
        this.curChar = 0;
        this.nextChar = -1;
        if (b) {
            this.n2impl = Norm2AllModes.getNFKCInstance().impl;
        }
        else {
            this.n2impl = Norm2AllModes.getNFCInstance().impl;
        }
    }
    
    @Deprecated
    public boolean hasNext() {
        if (this.nextChar == -1) {
            this.findNextChar();
        }
        return this.nextChar != -1;
    }
    
    @Deprecated
    public char next() {
        if (this.nextChar == -1) {
            this.findNextChar();
        }
        this.curChar = this.nextChar;
        this.nextChar = -1;
        return (char)this.curChar;
    }
    
    @Deprecated
    public String decomposition() {
        if (this.decompBuf != null) {
            return this.decompBuf;
        }
        return "";
    }
    
    private void findNextChar() {
        int n = this.curChar + 1;
        this.decompBuf = null;
        while (true) {
            this.decompBuf = this.n2impl.getDecomposition(-1);
            if (this.decompBuf != null) {
                break;
            }
            ++n;
        }
        this.nextChar = -1;
    }
}
