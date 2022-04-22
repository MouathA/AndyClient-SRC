package com.sun.jna.ptr;

import com.sun.jna.*;

public abstract class ByReference extends PointerType
{
    protected ByReference(final int n) {
        this.setPointer(new Memory(n));
    }
}
