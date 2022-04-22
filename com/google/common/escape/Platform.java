package com.google.common.escape;

import com.google.common.annotations.*;

@GwtCompatible(emulated = true)
final class Platform
{
    private static final ThreadLocal DEST_TL;
    
    private Platform() {
    }
    
    static char[] charBufferFromThreadLocal() {
        return Platform.DEST_TL.get();
    }
    
    static {
        DEST_TL = new ThreadLocal() {
            @Override
            protected char[] initialValue() {
                return new char[1024];
            }
            
            @Override
            protected Object initialValue() {
                return this.initialValue();
            }
        };
    }
}
