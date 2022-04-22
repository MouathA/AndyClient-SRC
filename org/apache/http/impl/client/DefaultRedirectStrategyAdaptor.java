package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.http.client.*;
import org.apache.http.protocol.*;
import org.apache.http.*;
import org.apache.http.client.methods.*;
import java.net.*;

@Deprecated
@Immutable
class DefaultRedirectStrategyAdaptor implements RedirectStrategy
{
    private final RedirectHandler handler;
    
    public DefaultRedirectStrategyAdaptor(final RedirectHandler handler) {
        this.handler = handler;
    }
    
    public boolean isRedirected(final HttpRequest httpRequest, final HttpResponse httpResponse, final HttpContext httpContext) throws ProtocolException {
        return this.handler.isRedirectRequested(httpResponse, httpContext);
    }
    
    public HttpUriRequest getRedirect(final HttpRequest httpRequest, final HttpResponse httpResponse, final HttpContext httpContext) throws ProtocolException {
        final URI locationURI = this.handler.getLocationURI(httpResponse, httpContext);
        if (httpRequest.getRequestLine().getMethod().equalsIgnoreCase("HEAD")) {
            return new HttpHead(locationURI);
        }
        return new HttpGet(locationURI);
    }
    
    public RedirectHandler getHandler() {
        return this.handler;
    }
}
