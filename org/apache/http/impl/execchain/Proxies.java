package org.apache.http.impl.execchain;

import org.apache.http.annotation.*;
import java.lang.reflect.*;
import org.apache.http.*;
import org.apache.http.client.methods.*;

@NotThreadSafe
class Proxies
{
    static void enhanceEntity(final HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
        final HttpEntity entity = httpEntityEnclosingRequest.getEntity();
        if (entity != null && !entity.isRepeatable() && !isEnhanced(entity)) {
            httpEntityEnclosingRequest.setEntity((HttpEntity)Proxy.newProxyInstance(HttpEntity.class.getClassLoader(), new Class[] { HttpEntity.class }, new RequestEntityExecHandler(entity)));
        }
    }
    
    static boolean isEnhanced(final HttpEntity httpEntity) {
        return httpEntity != null && Proxy.isProxyClass(httpEntity.getClass()) && Proxy.getInvocationHandler(httpEntity) instanceof RequestEntityExecHandler;
    }
    
    static boolean isRepeatable(final HttpRequest httpRequest) {
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            final HttpEntity entity = ((HttpEntityEnclosingRequest)httpRequest).getEntity();
            if (entity != null) {
                return (isEnhanced(entity) && !((RequestEntityExecHandler)Proxy.getInvocationHandler(entity)).isConsumed()) || entity.isRepeatable();
            }
        }
        return true;
    }
    
    public static CloseableHttpResponse enhanceResponse(final HttpResponse httpResponse, final ConnectionHolder connectionHolder) {
        return (CloseableHttpResponse)Proxy.newProxyInstance(ResponseProxyHandler.class.getClassLoader(), new Class[] { CloseableHttpResponse.class }, new ResponseProxyHandler(httpResponse, connectionHolder));
    }
}
