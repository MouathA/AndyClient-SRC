package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import org.apache.http.conn.*;
import org.apache.http.util.*;
import org.apache.http.protocol.*;
import org.apache.http.*;

@Immutable
public class DefaultProxyRoutePlanner extends DefaultRoutePlanner
{
    private final HttpHost proxy;
    
    public DefaultProxyRoutePlanner(final HttpHost httpHost, final SchemePortResolver schemePortResolver) {
        super(schemePortResolver);
        this.proxy = (HttpHost)Args.notNull(httpHost, "Proxy host");
    }
    
    public DefaultProxyRoutePlanner(final HttpHost httpHost) {
        this(httpHost, null);
    }
    
    @Override
    protected HttpHost determineProxy(final HttpHost httpHost, final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException {
        return this.proxy;
    }
}
