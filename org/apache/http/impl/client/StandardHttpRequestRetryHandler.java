package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import java.util.concurrent.*;
import org.apache.http.*;
import java.util.*;

@Immutable
public class StandardHttpRequestRetryHandler extends DefaultHttpRequestRetryHandler
{
    private final Map idempotentMethods;
    
    public StandardHttpRequestRetryHandler(final int n, final boolean b) {
        super(n, b);
        (this.idempotentMethods = new ConcurrentHashMap()).put("GET", Boolean.TRUE);
        this.idempotentMethods.put("HEAD", Boolean.TRUE);
        this.idempotentMethods.put("PUT", Boolean.TRUE);
        this.idempotentMethods.put("DELETE", Boolean.TRUE);
        this.idempotentMethods.put("OPTIONS", Boolean.TRUE);
        this.idempotentMethods.put("TRACE", Boolean.TRUE);
    }
    
    public StandardHttpRequestRetryHandler() {
        this(3, false);
    }
    
    protected boolean handleAsIdempotent(final HttpRequest httpRequest) {
        final Boolean b = this.idempotentMethods.get(httpRequest.getRequestLine().getMethod().toUpperCase(Locale.US));
        return b != null && b;
    }
}
