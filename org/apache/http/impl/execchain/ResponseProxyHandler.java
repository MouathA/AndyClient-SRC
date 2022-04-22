package org.apache.http.impl.execchain;

import org.apache.http.annotation.*;
import java.lang.reflect.*;
import org.apache.http.*;
import java.io.*;

@NotThreadSafe
class ResponseProxyHandler implements InvocationHandler
{
    private static final Method CLOSE_METHOD;
    private final HttpResponse original;
    private final ConnectionHolder connHolder;
    
    ResponseProxyHandler(final HttpResponse original, final ConnectionHolder connHolder) {
        this.original = original;
        this.connHolder = connHolder;
        final HttpEntity entity = original.getEntity();
        if (entity != null && entity.isStreaming() && connHolder != null) {
            this.original.setEntity(new ResponseEntityWrapper(entity, connHolder));
        }
    }
    
    public void close() throws IOException {
        if (this.connHolder != null) {
            this.connHolder.abortConnection();
        }
    }
    
    public Object invoke(final Object o, final Method method, final Object[] array) throws Throwable {
        if (method.equals(ResponseProxyHandler.CLOSE_METHOD)) {
            this.close();
            return null;
        }
        return method.invoke(this.original, array);
    }
    
    static {
        CLOSE_METHOD = Closeable.class.getMethod("close", (Class<?>[])new Class[0]);
    }
}
