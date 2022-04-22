package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.protocol.*;
import org.apache.http.*;
import org.apache.http.util.*;
import org.apache.http.conn.*;
import java.io.*;
import org.apache.http.conn.scheme.*;
import org.apache.http.params.*;
import java.net.*;

@Deprecated
@ThreadSafe
public class DefaultClientConnectionOperator implements ClientConnectionOperator
{
    private final Log log;
    protected final SchemeRegistry schemeRegistry;
    protected final DnsResolver dnsResolver;
    
    public DefaultClientConnectionOperator(final SchemeRegistry schemeRegistry) {
        this.log = LogFactory.getLog(this.getClass());
        Args.notNull(schemeRegistry, "Scheme registry");
        this.schemeRegistry = schemeRegistry;
        this.dnsResolver = new SystemDefaultDnsResolver();
    }
    
    public DefaultClientConnectionOperator(final SchemeRegistry schemeRegistry, final DnsResolver dnsResolver) {
        this.log = LogFactory.getLog(this.getClass());
        Args.notNull(schemeRegistry, "Scheme registry");
        Args.notNull(dnsResolver, "DNS resolver");
        this.schemeRegistry = schemeRegistry;
        this.dnsResolver = dnsResolver;
    }
    
    public OperatedClientConnection createConnection() {
        return new DefaultClientConnection();
    }
    
    private SchemeRegistry getSchemeRegistry(final HttpContext httpContext) {
        SchemeRegistry schemeRegistry = (SchemeRegistry)httpContext.getAttribute("http.scheme-registry");
        if (schemeRegistry == null) {
            schemeRegistry = this.schemeRegistry;
        }
        return schemeRegistry;
    }
    
    public void openConnection(final OperatedClientConnection operatedClientConnection, final HttpHost httpHost, final InetAddress inetAddress, final HttpContext httpContext, final HttpParams httpParams) throws IOException {
        Args.notNull(operatedClientConnection, "Connection");
        Args.notNull(httpHost, "Target host");
        Args.notNull(httpParams, "HTTP parameters");
        Asserts.check(!operatedClientConnection.isOpen(), "Connection must not be open");
        final Scheme scheme = this.getSchemeRegistry(httpContext).getScheme(httpHost.getSchemeName());
        final SchemeSocketFactory schemeSocketFactory = scheme.getSchemeSocketFactory();
        final InetAddress[] resolveHostname = this.resolveHostname(httpHost.getHostName());
        final int resolvePort = scheme.resolvePort(httpHost.getPort());
        if (0 < resolveHostname.length) {
            final InetAddress inetAddress2 = resolveHostname[0];
            final boolean b = 0 == resolveHostname.length - 1;
            Socket socket = schemeSocketFactory.createSocket(httpParams);
            operatedClientConnection.opening(socket, httpHost);
            final HttpInetSocketAddress httpInetSocketAddress = new HttpInetSocketAddress(httpHost, inetAddress2, resolvePort);
            InetSocketAddress inetSocketAddress = null;
            if (inetAddress != null) {
                inetSocketAddress = new InetSocketAddress(inetAddress, 0);
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Connecting to " + httpInetSocketAddress);
            }
            final Socket connectSocket = schemeSocketFactory.connectSocket(socket, httpInetSocketAddress, inetSocketAddress, httpParams);
            if (socket != connectSocket) {
                socket = connectSocket;
                operatedClientConnection.opening(socket, httpHost);
            }
            this.prepareSocket(socket, httpContext, httpParams);
            operatedClientConnection.openCompleted(schemeSocketFactory.isSecure(socket), httpParams);
        }
    }
    
    public void updateSecureConnection(final OperatedClientConnection operatedClientConnection, final HttpHost httpHost, final HttpContext httpContext, final HttpParams httpParams) throws IOException {
        Args.notNull(operatedClientConnection, "Connection");
        Args.notNull(httpHost, "Target host");
        Args.notNull(httpParams, "Parameters");
        Asserts.check(operatedClientConnection.isOpen(), "Connection must be open");
        final Scheme scheme = this.getSchemeRegistry(httpContext).getScheme(httpHost.getSchemeName());
        Asserts.check(scheme.getSchemeSocketFactory() instanceof SchemeLayeredSocketFactory, "Socket factory must implement SchemeLayeredSocketFactory");
        final SchemeLayeredSocketFactory schemeLayeredSocketFactory = (SchemeLayeredSocketFactory)scheme.getSchemeSocketFactory();
        final Socket layeredSocket = schemeLayeredSocketFactory.createLayeredSocket(operatedClientConnection.getSocket(), httpHost.getHostName(), scheme.resolvePort(httpHost.getPort()), httpParams);
        this.prepareSocket(layeredSocket, httpContext, httpParams);
        operatedClientConnection.update(layeredSocket, httpHost, schemeLayeredSocketFactory.isSecure(layeredSocket), httpParams);
    }
    
    protected void prepareSocket(final Socket socket, final HttpContext httpContext, final HttpParams httpParams) throws IOException {
        socket.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(httpParams));
        socket.setSoTimeout(HttpConnectionParams.getSoTimeout(httpParams));
        final int linger = HttpConnectionParams.getLinger(httpParams);
        if (linger >= 0) {
            socket.setSoLinger(linger > 0, linger);
        }
    }
    
    protected InetAddress[] resolveHostname(final String s) throws UnknownHostException {
        return this.dnsResolver.resolve(s);
    }
}
