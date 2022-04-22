package org.apache.http.conn.routing;

import org.apache.http.annotation.*;
import org.apache.http.*;
import java.net.*;
import org.apache.http.util.*;
import java.util.*;

@Immutable
public final class HttpRoute implements RouteInfo, Cloneable
{
    private final HttpHost targetHost;
    private final InetAddress localAddress;
    private final List proxyChain;
    private final TunnelType tunnelled;
    private final LayerType layered;
    private final boolean secure;
    
    private HttpRoute(final HttpHost targetHost, final InetAddress localAddress, final List list, final boolean secure, final TunnelType tunnelType, final LayerType layerType) {
        Args.notNull(targetHost, "Target host");
        this.targetHost = targetHost;
        this.localAddress = localAddress;
        if (list != null && !list.isEmpty()) {
            this.proxyChain = new ArrayList(list);
        }
        else {
            this.proxyChain = null;
        }
        if (tunnelType == TunnelType.TUNNELLED) {
            Args.check(this.proxyChain != null, "Proxy required if tunnelled");
        }
        this.secure = secure;
        this.tunnelled = ((tunnelType != null) ? tunnelType : TunnelType.PLAIN);
        this.layered = ((layerType != null) ? layerType : LayerType.PLAIN);
    }
    
    public HttpRoute(final HttpHost httpHost, final InetAddress inetAddress, final HttpHost[] array, final boolean b, final TunnelType tunnelType, final LayerType layerType) {
        this(httpHost, inetAddress, (array != null) ? Arrays.asList(array) : null, b, tunnelType, layerType);
    }
    
    public HttpRoute(final HttpHost httpHost, final InetAddress inetAddress, final HttpHost httpHost2, final boolean b, final TunnelType tunnelType, final LayerType layerType) {
        this(httpHost, inetAddress, (httpHost2 != null) ? Collections.singletonList(httpHost2) : null, b, tunnelType, layerType);
    }
    
    public HttpRoute(final HttpHost httpHost, final InetAddress inetAddress, final boolean b) {
        this(httpHost, inetAddress, Collections.emptyList(), b, TunnelType.PLAIN, LayerType.PLAIN);
    }
    
    public HttpRoute(final HttpHost httpHost) {
        this(httpHost, null, Collections.emptyList(), false, TunnelType.PLAIN, LayerType.PLAIN);
    }
    
    public HttpRoute(final HttpHost httpHost, final InetAddress inetAddress, final HttpHost httpHost2, final boolean b) {
        this(httpHost, inetAddress, Collections.singletonList(Args.notNull(httpHost2, "Proxy host")), b, b ? TunnelType.TUNNELLED : TunnelType.PLAIN, b ? LayerType.LAYERED : LayerType.PLAIN);
    }
    
    public HttpRoute(final HttpHost httpHost, final HttpHost httpHost2) {
        this(httpHost, null, httpHost2, false);
    }
    
    public final HttpHost getTargetHost() {
        return this.targetHost;
    }
    
    public final InetAddress getLocalAddress() {
        return this.localAddress;
    }
    
    public final InetSocketAddress getLocalSocketAddress() {
        return (this.localAddress != null) ? new InetSocketAddress(this.localAddress, 0) : null;
    }
    
    public final int getHopCount() {
        return (this.proxyChain != null) ? (this.proxyChain.size() + 1) : 1;
    }
    
    public final HttpHost getHopTarget(final int n) {
        Args.notNegative(n, "Hop index");
        final int hopCount = this.getHopCount();
        Args.check(n < hopCount, "Hop index exceeds tracked route length");
        if (n < hopCount - 1) {
            return this.proxyChain.get(n);
        }
        return this.targetHost;
    }
    
    public final HttpHost getProxyHost() {
        return (this.proxyChain != null && !this.proxyChain.isEmpty()) ? this.proxyChain.get(0) : null;
    }
    
    public final TunnelType getTunnelType() {
        return this.tunnelled;
    }
    
    public final boolean isTunnelled() {
        return this.tunnelled == TunnelType.TUNNELLED;
    }
    
    public final LayerType getLayerType() {
        return this.layered;
    }
    
    public final boolean isLayered() {
        return this.layered == LayerType.LAYERED;
    }
    
    public final boolean isSecure() {
        return this.secure;
    }
    
    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof HttpRoute) {
            final HttpRoute httpRoute = (HttpRoute)o;
            return this.secure == httpRoute.secure && this.tunnelled == httpRoute.tunnelled && this.layered == httpRoute.layered && LangUtils.equals(this.targetHost, httpRoute.targetHost) && LangUtils.equals(this.localAddress, httpRoute.localAddress) && LangUtils.equals(this.proxyChain, httpRoute.proxyChain);
        }
        return false;
    }
    
    @Override
    public final int hashCode() {
        LangUtils.hashCode(17, this.targetHost);
        LangUtils.hashCode(17, this.localAddress);
        if (this.proxyChain != null) {
            final Iterator<HttpHost> iterator = this.proxyChain.iterator();
            while (iterator.hasNext()) {
                LangUtils.hashCode(17, iterator.next());
            }
        }
        LangUtils.hashCode(17, this.secure);
        LangUtils.hashCode(17, this.tunnelled);
        LangUtils.hashCode(17, this.layered);
        return 17;
    }
    
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder(50 + this.getHopCount() * 30);
        if (this.localAddress != null) {
            sb.append(this.localAddress);
            sb.append("->");
        }
        sb.append('{');
        if (this.tunnelled == TunnelType.TUNNELLED) {
            sb.append('t');
        }
        if (this.layered == LayerType.LAYERED) {
            sb.append('l');
        }
        if (this.secure) {
            sb.append('s');
        }
        sb.append("}->");
        if (this.proxyChain != null) {
            final Iterator<HttpHost> iterator = this.proxyChain.iterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next());
                sb.append("->");
            }
        }
        sb.append(this.targetHost);
        return sb.toString();
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
