package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.util.*;
import org.apache.http.protocol.*;
import org.apache.http.*;
import org.apache.http.config.*;
import org.apache.http.conn.*;
import java.net.*;
import java.io.*;
import org.apache.http.client.protocol.*;
import org.apache.http.conn.socket.*;

@Immutable
class HttpClientConnectionOperator
{
    static final String SOCKET_FACTORY_REGISTRY = "http.socket-factory-registry";
    private final Log log;
    private final Lookup socketFactoryRegistry;
    private final SchemePortResolver schemePortResolver;
    private final DnsResolver dnsResolver;
    
    HttpClientConnectionOperator(final Lookup socketFactoryRegistry, final SchemePortResolver schemePortResolver, final DnsResolver dnsResolver) {
        this.log = LogFactory.getLog(this.getClass());
        Args.notNull(socketFactoryRegistry, "Socket factory registry");
        this.socketFactoryRegistry = socketFactoryRegistry;
        this.schemePortResolver = ((schemePortResolver != null) ? schemePortResolver : DefaultSchemePortResolver.INSTANCE);
        this.dnsResolver = ((dnsResolver != null) ? dnsResolver : SystemDefaultDnsResolver.INSTANCE);
    }
    
    private Lookup getSocketFactoryRegistry(final HttpContext httpContext) {
        Lookup socketFactoryRegistry = (Lookup)httpContext.getAttribute("http.socket-factory-registry");
        if (socketFactoryRegistry == null) {
            socketFactoryRegistry = this.socketFactoryRegistry;
        }
        return socketFactoryRegistry;
    }
    
    public void connect(final ManagedHttpClientConnection managedHttpClientConnection, final HttpHost httpHost, final InetSocketAddress inetSocketAddress, final int n, final SocketConfig socketConfig, final HttpContext httpContext) throws IOException {
        final ConnectionSocketFactory connectionSocketFactory = (ConnectionSocketFactory)this.getSocketFactoryRegistry(httpContext).lookup(httpHost.getSchemeName());
        if (connectionSocketFactory == null) {
            throw new UnsupportedSchemeException(httpHost.getSchemeName() + " protocol is not supported");
        }
        final InetAddress[] resolve = this.dnsResolver.resolve(httpHost.getHostName());
        final int resolve2 = this.schemePortResolver.resolve(httpHost);
        if (0 < resolve.length) {
            final InetAddress inetAddress = resolve[0];
            final boolean b = 0 == resolve.length - 1;
            final Socket socket = connectionSocketFactory.createSocket(httpContext);
            socket.setReuseAddress(socketConfig.isSoReuseAddress());
            managedHttpClientConnection.bind(socket);
            final InetSocketAddress inetSocketAddress2 = new InetSocketAddress(inetAddress, resolve2);
            if (this.log.isDebugEnabled()) {
                this.log.debug("Connecting to " + inetSocketAddress2);
            }
            socket.setSoTimeout(socketConfig.getSoTimeout());
            final Socket connectSocket = connectionSocketFactory.connectSocket(n, socket, httpHost, inetSocketAddress2, inetSocketAddress, httpContext);
            connectSocket.setTcpNoDelay(socketConfig.isTcpNoDelay());
            connectSocket.setKeepAlive(socketConfig.isSoKeepAlive());
            final int soLinger = socketConfig.getSoLinger();
            if (soLinger >= 0) {
                connectSocket.setSoLinger(soLinger > 0, soLinger);
            }
            managedHttpClientConnection.bind(connectSocket);
            if (this.log.isDebugEnabled()) {
                this.log.debug("Connection established " + managedHttpClientConnection);
            }
        }
    }
    
    public void upgrade(final ManagedHttpClientConnection managedHttpClientConnection, final HttpHost httpHost, final HttpContext httpContext) throws IOException {
        final ConnectionSocketFactory connectionSocketFactory = (ConnectionSocketFactory)this.getSocketFactoryRegistry(HttpClientContext.adapt(httpContext)).lookup(httpHost.getSchemeName());
        if (connectionSocketFactory == null) {
            throw new UnsupportedSchemeException(httpHost.getSchemeName() + " protocol is not supported");
        }
        if (!(connectionSocketFactory instanceof LayeredConnectionSocketFactory)) {
            throw new UnsupportedSchemeException(httpHost.getSchemeName() + " protocol does not support connection upgrade");
        }
        managedHttpClientConnection.bind(((LayeredConnectionSocketFactory)connectionSocketFactory).createLayeredSocket(managedHttpClientConnection.getSocket(), httpHost.getHostName(), this.schemePortResolver.resolve(httpHost), httpContext));
    }
}
