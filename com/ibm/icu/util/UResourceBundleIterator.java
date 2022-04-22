package com.ibm.icu.util;

import java.util.*;

public class UResourceBundleIterator
{
    private UResourceBundle bundle;
    private int index;
    private int size;
    
    public UResourceBundleIterator(final UResourceBundle bundle) {
        this.index = 0;
        this.size = 0;
        this.bundle = bundle;
        this.size = this.bundle.getSize();
    }
    
    public UResourceBundle next() throws NoSuchElementException {
        if (this.index < this.size) {
            return this.bundle.get(this.index++);
        }
        throw new NoSuchElementException();
    }
    
    public String nextString() throws NoSuchElementException, UResourceTypeMismatchException {
        if (this.index < this.size) {
            return this.bundle.getString(this.index++);
        }
        throw new NoSuchElementException();
    }
    
    public void reset() {
        this.index = 0;
    }
    
    public boolean hasNext() {
        return this.index < this.size;
    }
}
