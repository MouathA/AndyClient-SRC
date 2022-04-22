package org.apache.http.conn.scheme;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.params.*;
import java.io.*;
import org.apache.http.conn.*;
import java.net.*;

@Deprecated
@Immutable
public class PlainSocketFactory implements SocketFactory, SchemeSocketFactory
{
    private final HostNameResolver nameResolver;
    
    public static PlainSocketFactory getSocketFactory() {
        return new PlainSocketFactory();
    }
    
    @Deprecated
    public PlainSocketFactory(final HostNameResolver nameResolver) {
        this.nameResolver = nameResolver;
    }
    
    public PlainSocketFactory() {
        this.nameResolver = null;
    }
    
    public Socket createSocket(final HttpParams httpParams) {
        return new Socket();
    }
    
    public Socket createSocket() {
        return new Socket();
    }
    
    public Socket connectSocket(final Socket socket, final InetSocketAddress inetSocketAddress, final InetSocketAddress inetSocketAddress2, final HttpParams httpParams) throws IOException, ConnectTimeoutException {
        Args.notNull(inetSocketAddress, "Remote address");
        Args.notNull(httpParams, "HTTP parameters");
        Socket socket2 = socket;
        if (socket2 == null) {
            socket2 = this.createSocket();
        }
        if (inetSocketAddress2 != null) {
            socket2.setReuseAddress(HttpConnectionParams.getSoReuseaddr(httpParams));
            socket2.bind(inetSocketAddress2);
        }
        final int connectionTimeout = HttpConnectionParams.getConnectionTimeout(httpParams);
        socket2.setSoTimeout(HttpConnectionParams.getSoTimeout(httpParams));
        socket2.connect(inetSocketAddress, connectionTimeout);
        return socket2;
    }
    
    public final boolean isSecure(final Socket socket) {
        return false;
    }
    
    @Deprecated
    public Socket connectSocket(final Socket socket, final String s, final int n, final InetAddress inetAddress, final int n2, final HttpParams httpParams) throws IOException, UnknownHostException, ConnectTimeoutException {
        InetSocketAddress inetSocketAddress = null;
        if (inetAddress != null || n2 > 0) {
            inetSocketAddress = new InetSocketAddress(inetAddress, (n2 > 0) ? n2 : 0);
        }
        InetAddress inetAddress2;
        if (this.nameResolver != null) {
            inetAddress2 = this.nameResolver.resolve(s);
        }
        else {
            inetAddress2 = InetAddress.getByName(s);
        }
        return this.connectSocket(socket, new InetSocketAddress(inetAddress2, n), inetSocketAddress, httpParams);
    }
}
