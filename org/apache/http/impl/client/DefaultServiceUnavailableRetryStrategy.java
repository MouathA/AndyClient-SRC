package org.apache.http.impl.client;

import org.apache.http.client.*;
import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;
import org.apache.http.protocol.*;

@Immutable
public class DefaultServiceUnavailableRetryStrategy implements ServiceUnavailableRetryStrategy
{
    private final int maxRetries;
    private final long retryInterval;
    
    public DefaultServiceUnavailableRetryStrategy(final int maxRetries, final int n) {
        Args.positive(maxRetries, "Max retries");
        Args.positive(n, "Retry interval");
        this.maxRetries = maxRetries;
        this.retryInterval = n;
    }
    
    public DefaultServiceUnavailableRetryStrategy() {
        this(1, 1000);
    }
    
    public boolean retryRequest(final HttpResponse httpResponse, final int n, final HttpContext httpContext) {
        return n <= this.maxRetries && httpResponse.getStatusLine().getStatusCode() == 503;
    }
    
    public long getRetryInterval() {
        return this.retryInterval;
    }
}
