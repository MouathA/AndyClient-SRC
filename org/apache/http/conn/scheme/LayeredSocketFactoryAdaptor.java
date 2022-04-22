package org.apache.http.conn.scheme;

import java.io.*;
import java.net.*;

@Deprecated
class LayeredSocketFactoryAdaptor extends SocketFactoryAdaptor implements LayeredSocketFactory
{
    private final LayeredSchemeSocketFactory factory;
    
    LayeredSocketFactoryAdaptor(final LayeredSchemeSocketFactory factory) {
        super(factory);
        this.factory = factory;
    }
    
    public Socket createSocket(final Socket socket, final String s, final int n, final boolean b) throws IOException, UnknownHostException {
        return this.factory.createLayeredSocket(socket, s, n, b);
    }
}
