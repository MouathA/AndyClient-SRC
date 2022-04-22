package org.lwjgl.openal;

import java.nio.*;
import org.lwjgl.*;

public final class ALCcontext
{
    final long context;
    private boolean valid;
    
    ALCcontext(final long context) {
        this.context = context;
        this.valid = true;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof ALCcontext) {
            return ((ALCcontext)o).context == this.context;
        }
        return super.equals(o);
    }
    
    static IntBuffer createAttributeList(final int n, final int n2, final int n3) {
        final IntBuffer intBuffer = BufferUtils.createIntBuffer(7);
        intBuffer.put(4103);
        intBuffer.put(n);
        intBuffer.put(4104);
        intBuffer.put(n2);
        intBuffer.put(4105);
        intBuffer.put(n3);
        intBuffer.put(0);
        return intBuffer;
    }
    
    void setInvalid() {
        this.valid = false;
    }
    
    public boolean isValid() {
        return this.valid;
    }
}
