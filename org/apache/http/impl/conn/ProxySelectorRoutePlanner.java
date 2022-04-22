package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import org.apache.http.conn.scheme.*;
import org.apache.http.protocol.*;
import org.apache.http.conn.routing.*;
import org.apache.http.conn.params.*;
import org.apache.http.util.*;
import org.apache.http.*;
import java.net.*;
import java.util.*;

@Deprecated
@NotThreadSafe
public class ProxySelectorRoutePlanner implements HttpRoutePlanner
{
    protected final SchemeRegistry schemeRegistry;
    protected ProxySelector proxySelector;
    
    public ProxySelectorRoutePlanner(final SchemeRegistry schemeRegistry, final ProxySelector proxySelector) {
        Args.notNull(schemeRegistry, "SchemeRegistry");
        this.schemeRegistry = schemeRegistry;
        this.proxySelector = proxySelector;
    }
    
    public ProxySelector getProxySelector() {
        return this.proxySelector;
    }
    
    public void setProxySelector(final ProxySelector proxySelector) {
        this.proxySelector = proxySelector;
    }
    
    public HttpRoute determineRoute(final HttpHost httpHost, final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException {
        Args.notNull(httpRequest, "HTTP request");
        final HttpRoute forcedRoute = ConnRouteParams.getForcedRoute(httpRequest.getParams());
        if (forcedRoute != null) {
            return forcedRoute;
        }
        Asserts.notNull(httpHost, "Target host");
        final InetAddress localAddress = ConnRouteParams.getLocalAddress(httpRequest.getParams());
        final HttpHost determineProxy = this.determineProxy(httpHost, httpRequest, httpContext);
        final boolean layered = this.schemeRegistry.getScheme(httpHost.getSchemeName()).isLayered();
        HttpRoute httpRoute;
        if (determineProxy == null) {
            httpRoute = new HttpRoute(httpHost, localAddress, layered);
        }
        else {
            httpRoute = new HttpRoute(httpHost, localAddress, determineProxy, layered);
        }
        return httpRoute;
    }
    
    protected HttpHost determineProxy(final HttpHost httpHost, final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException {
        ProxySelector proxySelector = this.proxySelector;
        if (proxySelector == null) {
            proxySelector = ProxySelector.getDefault();
        }
        if (proxySelector == null) {
            return null;
        }
        final Proxy chooseProxy = this.chooseProxy(proxySelector.select(new URI(httpHost.toURI())), httpHost, httpRequest, httpContext);
        HttpHost httpHost2 = null;
        if (chooseProxy.type() == Proxy.Type.HTTP) {
            if (!(chooseProxy.address() instanceof InetSocketAddress)) {
                throw new HttpException("Unable to handle non-Inet proxy address: " + chooseProxy.address());
            }
            final InetSocketAddress inetSocketAddress = (InetSocketAddress)chooseProxy.address();
            httpHost2 = new HttpHost(this.getHost(inetSocketAddress), inetSocketAddress.getPort());
        }
        return httpHost2;
    }
    
    protected String getHost(final InetSocketAddress inetSocketAddress) {
        return inetSocketAddress.isUnresolved() ? inetSocketAddress.getHostName() : inetSocketAddress.getAddress().getHostAddress();
    }
    
    protected Proxy chooseProxy(final List list, final HttpHost httpHost, final HttpRequest httpRequest, final HttpContext httpContext) {
        Args.notEmpty(list, "List of proxies");
        Proxy no_PROXY = null;
        while (no_PROXY == null && 0 < list.size()) {
            final Proxy proxy = list.get(0);
            switch (proxy.type()) {
                case DIRECT:
                case HTTP: {
                    no_PROXY = proxy;
                    break;
                }
            }
            int n = 0;
            ++n;
        }
        if (no_PROXY == null) {
            no_PROXY = Proxy.NO_PROXY;
        }
        return no_PROXY;
    }
}
