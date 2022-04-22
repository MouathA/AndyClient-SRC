package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import org.apache.http.conn.*;
import org.apache.http.protocol.*;
import java.util.*;
import java.net.*;
import org.apache.http.*;

@Immutable
public class SystemDefaultRoutePlanner extends DefaultRoutePlanner
{
    private final ProxySelector proxySelector;
    
    public SystemDefaultRoutePlanner(final SchemePortResolver schemePortResolver, final ProxySelector proxySelector) {
        super(schemePortResolver);
        this.proxySelector = ((proxySelector != null) ? proxySelector : ProxySelector.getDefault());
    }
    
    public SystemDefaultRoutePlanner(final ProxySelector proxySelector) {
        this(null, proxySelector);
    }
    
    @Override
    protected HttpHost determineProxy(final HttpHost httpHost, final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException {
        final Proxy chooseProxy = this.chooseProxy(this.proxySelector.select(new URI(httpHost.toURI())));
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
    
    private String getHost(final InetSocketAddress inetSocketAddress) {
        return inetSocketAddress.isUnresolved() ? inetSocketAddress.getHostName() : inetSocketAddress.getAddress().getHostAddress();
    }
    
    private Proxy chooseProxy(final List list) {
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
