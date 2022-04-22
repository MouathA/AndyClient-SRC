package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.http.conn.routing.*;

@Deprecated
@NotThreadSafe
public class RoutedRequest
{
    protected final RequestWrapper request;
    protected final HttpRoute route;
    
    public RoutedRequest(final RequestWrapper request, final HttpRoute route) {
        this.request = request;
        this.route = route;
    }
    
    public final RequestWrapper getRequest() {
        return this.request;
    }
    
    public final HttpRoute getRoute() {
        return this.route;
    }
}
