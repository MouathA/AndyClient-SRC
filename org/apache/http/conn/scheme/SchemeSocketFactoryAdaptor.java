package org.apache.http.conn.scheme;

import org.apache.http.params.*;
import java.io.*;
import java.net.*;
import org.apache.http.conn.*;

@Deprecated
class SchemeSocketFactoryAdaptor implements SchemeSocketFactory
{
    private final SocketFactory factory;
    
    SchemeSocketFactoryAdaptor(final SocketFactory factory) {
        this.factory = factory;
    }
    
    public Socket connectSocket(final Socket socket, final InetSocketAddress inetSocketAddress, final InetSocketAddress inetSocketAddress2, final HttpParams httpParams) throws IOException, UnknownHostException, ConnectTimeoutException {
        final String hostName = inetSocketAddress.getHostName();
        final int port = inetSocketAddress.getPort();
        InetAddress address = null;
        if (inetSocketAddress2 != null) {
            address = inetSocketAddress2.getAddress();
            inetSocketAddress2.getPort();
        }
        return this.factory.connectSocket(socket, hostName, port, address, 0, httpParams);
    }
    
    public Socket createSocket(final HttpParams httpParams) throws IOException {
        return this.factory.createSocket();
    }
    
    public boolean isSecure(final Socket socket) throws IllegalArgumentException {
        return this.factory.isSecure(socket);
    }
    
    public SocketFactory getFactory() {
        return this.factory;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (o instanceof SchemeSocketFactoryAdaptor) {
            return this.factory.equals(((SchemeSocketFactoryAdaptor)o).factory);
        }
        return this.factory.equals(o);
    }
    
    @Override
    public int hashCode() {
        return this.factory.hashCode();
    }
}
