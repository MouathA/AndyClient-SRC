package org.apache.http.conn.routing;

import org.apache.http.annotation.*;
import org.apache.http.*;
import java.net.*;
import org.apache.http.util.*;

@NotThreadSafe
public final class RouteTracker implements RouteInfo, Cloneable
{
    private final HttpHost targetHost;
    private final InetAddress localAddress;
    private boolean connected;
    private HttpHost[] proxyChain;
    private TunnelType tunnelled;
    private LayerType layered;
    private boolean secure;
    
    public RouteTracker(final HttpHost targetHost, final InetAddress localAddress) {
        Args.notNull(targetHost, "Target host");
        this.targetHost = targetHost;
        this.localAddress = localAddress;
        this.tunnelled = TunnelType.PLAIN;
        this.layered = LayerType.PLAIN;
    }
    
    public void reset() {
        this.connected = false;
        this.proxyChain = null;
        this.tunnelled = TunnelType.PLAIN;
        this.layered = LayerType.PLAIN;
        this.secure = false;
    }
    
    public RouteTracker(final HttpRoute httpRoute) {
        this(httpRoute.getTargetHost(), httpRoute.getLocalAddress());
    }
    
    public final void connectTarget(final boolean secure) {
        Asserts.check(!this.connected, "Already connected");
        this.connected = true;
        this.secure = secure;
    }
    
    public final void connectProxy(final HttpHost httpHost, final boolean secure) {
        Args.notNull(httpHost, "Proxy host");
        Asserts.check(!this.connected, "Already connected");
        this.connected = true;
        this.proxyChain = new HttpHost[] { httpHost };
        this.secure = secure;
    }
    
    public final void tunnelTarget(final boolean secure) {
        Asserts.check(this.connected, "No tunnel unless connected");
        Asserts.notNull(this.proxyChain, "No tunnel without proxy");
        this.tunnelled = TunnelType.TUNNELLED;
        this.secure = secure;
    }
    
    public final void tunnelProxy(final HttpHost httpHost, final boolean secure) {
        Args.notNull(httpHost, "Proxy host");
        Asserts.check(this.connected, "No tunnel unless connected");
        Asserts.notNull(this.proxyChain, "No tunnel without proxy");
        final HttpHost[] proxyChain = new HttpHost[this.proxyChain.length + 1];
        System.arraycopy(this.proxyChain, 0, proxyChain, 0, this.proxyChain.length);
        proxyChain[proxyChain.length - 1] = httpHost;
        this.proxyChain = proxyChain;
        this.secure = secure;
    }
    
    public final void layerProtocol(final boolean secure) {
        Asserts.check(this.connected, "No layered protocol unless connected");
        this.layered = LayerType.LAYERED;
        this.secure = secure;
    }
    
    public final HttpHost getTargetHost() {
        return this.targetHost;
    }
    
    public final InetAddress getLocalAddress() {
        return this.localAddress;
    }
    
    public final int getHopCount() {
        if (this.connected) {
            if (this.proxyChain != null) {
                final int n = this.proxyChain.length + 1;
            }
        }
        return 1;
    }
    
    public final HttpHost getHopTarget(final int n) {
        Args.notNegative(n, "Hop index");
        final int hopCount = this.getHopCount();
        Args.check(n < hopCount, "Hop index exceeds tracked route length");
        HttpHost targetHost;
        if (n < hopCount - 1) {
            targetHost = this.proxyChain[n];
        }
        else {
            targetHost = this.targetHost;
        }
        return targetHost;
    }
    
    public final HttpHost getProxyHost() {
        return (this.proxyChain == null) ? null : this.proxyChain[0];
    }
    
    public final boolean isConnected() {
        return this.connected;
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
    
    public final HttpRoute toRoute() {
        return this.connected ? new HttpRoute(this.targetHost, this.localAddress, this.proxyChain, this.secure, this.tunnelled, this.layered) : null;
    }
    
    @Override
    public final boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RouteTracker)) {
            return false;
        }
        final RouteTracker routeTracker = (RouteTracker)o;
        return this.connected == routeTracker.connected && this.secure == routeTracker.secure && this.tunnelled == routeTracker.tunnelled && this.layered == routeTracker.layered && LangUtils.equals(this.targetHost, routeTracker.targetHost) && LangUtils.equals(this.localAddress, routeTracker.localAddress) && LangUtils.equals(this.proxyChain, routeTracker.proxyChain);
    }
    
    @Override
    public final int hashCode() {
        LangUtils.hashCode(17, this.targetHost);
        LangUtils.hashCode(17, this.localAddress);
        if (this.proxyChain != null) {
            final HttpHost[] proxyChain = this.proxyChain;
            while (0 < proxyChain.length) {
                LangUtils.hashCode(17, proxyChain[0]);
                int n = 0;
                ++n;
            }
        }
        LangUtils.hashCode(17, this.connected);
        LangUtils.hashCode(17, this.secure);
        LangUtils.hashCode(17, this.tunnelled);
        LangUtils.hashCode(17, this.layered);
        return 17;
    }
    
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder(50 + this.getHopCount() * 30);
        sb.append("RouteTracker[");
        if (this.localAddress != null) {
            sb.append(this.localAddress);
            sb.append("->");
        }
        sb.append('{');
        if (this.connected) {
            sb.append('c');
        }
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
            final HttpHost[] proxyChain = this.proxyChain;
            while (0 < proxyChain.length) {
                sb.append(proxyChain[0]);
                sb.append("->");
                int n = 0;
                ++n;
            }
        }
        sb.append(this.targetHost);
        sb.append(']');
        return sb.toString();
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
