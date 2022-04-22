package com.sun.jna.ptr;

public class ShortByReference extends ByReference
{
    public ShortByReference() {
        this((short)0);
    }
    
    public ShortByReference(final short value) {
        super(2);
        this.setValue(value);
    }
    
    public void setValue(final short n) {
        this.getPointer().setShort(0L, n);
    }
    
    public short getValue() {
        return this.getPointer().getShort(0L);
    }
}
