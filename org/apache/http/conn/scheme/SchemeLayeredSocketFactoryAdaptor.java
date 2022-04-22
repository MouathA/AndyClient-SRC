package org.apache.http.conn.scheme;

import org.apache.http.params.*;
import java.io.*;
import java.net.*;

@Deprecated
class SchemeLayeredSocketFactoryAdaptor extends SchemeSocketFactoryAdaptor implements SchemeLayeredSocketFactory
{
    private final LayeredSocketFactory factory;
    
    SchemeLayeredSocketFactoryAdaptor(final LayeredSocketFactory factory) {
        super(factory);
        this.factory = factory;
    }
    
    public Socket createLayeredSocket(final Socket socket, final String s, final int n, final HttpParams httpParams) throws IOException, UnknownHostException {
        return this.factory.createSocket(socket, s, n, true);
    }
}
