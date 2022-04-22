package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.http.*;
import org.apache.http.util.*;
import java.io.*;
import org.apache.http.client.methods.*;
import java.lang.reflect.*;

@NotThreadSafe
class CloseableHttpResponseProxy implements InvocationHandler
{
    private final HttpResponse original;
    
    CloseableHttpResponseProxy(final HttpResponse original) {
        this.original = original;
    }
    
    public void close() throws IOException {
        EntityUtils.consume(this.original.getEntity());
    }
    
    public Object invoke(final Object o, final Method method, final Object[] array) throws Throwable {
        if (method.getName().equals("close")) {
            this.close();
            return null;
        }
        return method.invoke(this.original, array);
    }
    
    public static CloseableHttpResponse newProxy(final HttpResponse httpResponse) {
        return (CloseableHttpResponse)Proxy.newProxyInstance(CloseableHttpResponseProxy.class.getClassLoader(), new Class[] { CloseableHttpResponse.class }, new CloseableHttpResponseProxy(httpResponse));
    }
}
