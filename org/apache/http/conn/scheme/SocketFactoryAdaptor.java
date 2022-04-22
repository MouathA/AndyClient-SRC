package org.apache.http.conn.scheme;

import org.apache.http.params.*;
import java.io.*;
import java.net.*;
import org.apache.http.conn.*;

@Deprecated
class SocketFactoryAdaptor implements SocketFactory
{
    private final SchemeSocketFactory factory;
    
    SocketFactoryAdaptor(final SchemeSocketFactory factory) {
        this.factory = factory;
    }
    
    public Socket createSocket() throws IOException {
        return this.factory.createSocket(new BasicHttpParams());
    }
    
    public Socket connectSocket(final Socket socket, final String s, final int n, final InetAddress inetAddress, final int n2, final HttpParams httpParams) throws IOException, UnknownHostException, ConnectTimeoutException {
        InetSocketAddress inetSocketAddress = null;
        if (inetAddress != null || n2 > 0) {
            inetSocketAddress = new InetSocketAddress(inetAddress, (n2 > 0) ? n2 : 0);
        }
        return this.factory.connectSocket(socket, new InetSocketAddress(InetAddress.getByName(s), n), inetSocketAddress, httpParams);
    }
    
    public boolean isSecure(final Socket socket) throws IllegalArgumentException {
        return this.factory.isSecure(socket);
    }
    
    public SchemeSocketFactory getFactory() {
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
        if (o instanceof SocketFactoryAdaptor) {
            return this.factory.equals(((SocketFactoryAdaptor)o).factory);
        }
        return this.factory.equals(o);
    }
    
    @Override
    public int hashCode() {
        return this.factory.hashCode();
    }
}
