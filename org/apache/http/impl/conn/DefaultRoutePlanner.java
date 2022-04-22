package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import org.apache.http.conn.*;
import org.apache.http.protocol.*;
import org.apache.http.conn.routing.*;
import org.apache.http.util.*;
import org.apache.http.client.protocol.*;
import org.apache.http.client.config.*;
import java.net.*;
import org.apache.http.*;

@Immutable
public class DefaultRoutePlanner implements HttpRoutePlanner
{
    private final SchemePortResolver schemePortResolver;
    
    public DefaultRoutePlanner(final SchemePortResolver schemePortResolver) {
        this.schemePortResolver = ((schemePortResolver != null) ? schemePortResolver : DefaultSchemePortResolver.INSTANCE);
    }
    
    public HttpRoute determineRoute(final HttpHost httpHost, final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException {
        Args.notNull(httpHost, "Target host");
        Args.notNull(httpRequest, "Request");
        final RequestConfig requestConfig = HttpClientContext.adapt(httpContext).getRequestConfig();
        final InetAddress localAddress = requestConfig.getLocalAddress();
        HttpHost httpHost2 = requestConfig.getProxy();
        if (httpHost2 == null) {
            httpHost2 = this.determineProxy(httpHost, httpRequest, httpContext);
        }
        HttpHost httpHost3;
        if (httpHost.getPort() <= 0) {
            httpHost3 = new HttpHost(httpHost.getHostName(), this.schemePortResolver.resolve(httpHost), httpHost.getSchemeName());
        }
        else {
            httpHost3 = httpHost;
        }
        final boolean equalsIgnoreCase = httpHost3.getSchemeName().equalsIgnoreCase("https");
        if (httpHost2 == null) {
            return new HttpRoute(httpHost3, localAddress, equalsIgnoreCase);
        }
        return new HttpRoute(httpHost3, localAddress, httpHost2, equalsIgnoreCase);
    }
    
    protected HttpHost determineProxy(final HttpHost httpHost, final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException {
        return null;
    }
}
