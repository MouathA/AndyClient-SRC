package org.lwjgl.opencl;

import org.lwjgl.*;
import java.nio.*;

public abstract class CLContextCallback extends PointerWrapperAbstract
{
    private final boolean custom;
    
    protected CLContextCallback() {
        super(CallbackUtil.getContextCallback());
        this.custom = false;
    }
    
    protected CLContextCallback(final long n) {
        super(n);
        if (n == 0L) {
            throw new RuntimeException("Invalid callback function pointer specified.");
        }
        this.custom = true;
    }
    
    final boolean isCustom() {
        return this.custom;
    }
    
    protected abstract void handleMessage(final String p0, final ByteBuffer p1);
}
