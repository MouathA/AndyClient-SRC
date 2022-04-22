package com.sun.jna.ptr;

import com.sun.jna.*;

public class NativeLongByReference extends ByReference
{
    public NativeLongByReference() {
        this(new NativeLong(0L));
    }
    
    public NativeLongByReference(final NativeLong value) {
        super(NativeLong.SIZE);
        this.setValue(value);
    }
    
    public void setValue(final NativeLong nativeLong) {
        this.getPointer().setNativeLong(0L, nativeLong);
    }
    
    public NativeLong getValue() {
        return this.getPointer().getNativeLong(0L);
    }
}
