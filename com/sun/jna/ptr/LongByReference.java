package com.sun.jna.ptr;

public class LongByReference extends ByReference
{
    public LongByReference() {
        this(0L);
    }
    
    public LongByReference(final long value) {
        super(8);
        this.setValue(value);
    }
    
    public void setValue(final long n) {
        this.getPointer().setLong(0L, n);
    }
    
    public long getValue() {
        return this.getPointer().getLong(0L);
    }
}
