package org.apache.http.impl.execchain;

import org.apache.http.annotation.*;
import java.lang.reflect.*;
import org.apache.http.*;
import java.io.*;

@NotThreadSafe
class RequestEntityExecHandler implements InvocationHandler
{
    private static final Method WRITE_TO_METHOD;
    private final HttpEntity original;
    private boolean consumed;
    
    RequestEntityExecHandler(final HttpEntity original) {
        this.consumed = false;
        this.original = original;
    }
    
    public HttpEntity getOriginal() {
        return this.original;
    }
    
    public boolean isConsumed() {
        return this.consumed;
    }
    
    public Object invoke(final Object o, final Method method, final Object[] array) throws Throwable {
        if (method.equals(RequestEntityExecHandler.WRITE_TO_METHOD)) {
            this.consumed = true;
        }
        return method.invoke(this.original, array);
    }
    
    static {
        WRITE_TO_METHOD = HttpEntity.class.getMethod("writeTo", OutputStream.class);
    }
}
