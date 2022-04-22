package org.lwjgl.opencl;

import org.lwjgl.*;

abstract class CLObject extends PointerWrapperAbstract
{
    protected CLObject(final long n) {
        super(n);
    }
    
    final long getPointerUnsafe() {
        return this.pointer;
    }
}
