package com.sun.jna;

import java.lang.reflect.*;

public class CallbackResultContext extends ToNativeContext
{
    private Method method;
    
    CallbackResultContext(final Method method) {
        this.method = method;
    }
    
    public Method getMethod() {
        return this.method;
    }
}
