package org.apache.http.client.methods;

import org.apache.http.annotation.*;
import org.apache.http.*;
import org.apache.http.client.utils.*;

@NotThreadSafe
public abstract class HttpEntityEnclosingRequestBase extends HttpRequestBase implements HttpEntityEnclosingRequest
{
    private HttpEntity entity;
    
    public HttpEntity getEntity() {
        return this.entity;
    }
    
    public void setEntity(final HttpEntity entity) {
        this.entity = entity;
    }
    
    public boolean expectContinue() {
        final Header firstHeader = this.getFirstHeader("Expect");
        return firstHeader != null && "100-continue".equalsIgnoreCase(firstHeader.getValue());
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        final HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase = (HttpEntityEnclosingRequestBase)super.clone();
        if (this.entity != null) {
            httpEntityEnclosingRequestBase.entity = (HttpEntity)CloneUtils.cloneObject(this.entity);
        }
        return httpEntityEnclosingRequestBase;
    }
}
