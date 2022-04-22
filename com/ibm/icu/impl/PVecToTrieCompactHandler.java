package com.ibm.icu.impl;

public class PVecToTrieCompactHandler implements PropsVectors.CompactHandler
{
    public IntTrieBuilder builder;
    public int initialValue;
    
    public void setRowIndexForErrorValue(final int n) {
    }
    
    public void setRowIndexForInitialValue(final int initialValue) {
        this.initialValue = initialValue;
    }
    
    public void setRowIndexForRange(final int n, final int n2, final int n3) {
        this.builder.setRange(n, n2 + 1, n3, true);
    }
    
    public void startRealValues(final int n) {
        if (n > 65535) {
            throw new IndexOutOfBoundsException();
        }
        this.builder = new IntTrieBuilder(null, 100000, this.initialValue, this.initialValue, false);
    }
}
