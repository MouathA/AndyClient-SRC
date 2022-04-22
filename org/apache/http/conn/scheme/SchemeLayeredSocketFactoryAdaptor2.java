package org.apache.http.conn.scheme;

import org.apache.http.params.*;
import java.io.*;
import java.net.*;
import org.apache.http.conn.*;

@Deprecated
class SchemeLayeredSocketFactoryAdaptor2 implements SchemeLayeredSocketFactory
{
    private final LayeredSchemeSocketFactory factory;
    
    SchemeLayeredSocketFactoryAdaptor2(final LayeredSchemeSocketFactory factory) {
        this.factory = factory;
    }
    
    public Socket createSocket(final HttpParams httpParams) throws IOException {
        return this.factory.createSocket(httpParams);
    }
    
    public Socket connectSocket(final Socket socket, final InetSocketAddress inetSocketAddress, final InetSocketAddress inetSocketAddress2, final HttpParams httpParams) throws IOException, UnknownHostException, ConnectTimeoutException {
        return this.factory.connectSocket(socket, inetSocketAddress, inetSocketAddress2, httpParams);
    }
    
    public boolean isSecure(final Socket socket) throws IllegalArgumentException {
        return this.factory.isSecure(socket);
    }
    
    public Socket createLayeredSocket(final Socket socket, final String s, final int n, final HttpParams httpParams) throws IOException, UnknownHostException {
        return this.factory.createLayeredSocket(socket, s, n, true);
    }
}
