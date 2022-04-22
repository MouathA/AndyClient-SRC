package org.apache.http.conn.socket;

import org.apache.http.annotation.*;
import org.apache.http.protocol.*;
import java.io.*;
import org.apache.http.*;
import java.net.*;

@Immutable
public class PlainConnectionSocketFactory implements ConnectionSocketFactory
{
    public static final PlainConnectionSocketFactory INSTANCE;
    
    public static PlainConnectionSocketFactory getSocketFactory() {
        return PlainConnectionSocketFactory.INSTANCE;
    }
    
    public Socket createSocket(final HttpContext httpContext) throws IOException {
        return new Socket();
    }
    
    public Socket connectSocket(final int n, final Socket socket, final HttpHost httpHost, final InetSocketAddress inetSocketAddress, final InetSocketAddress inetSocketAddress2, final HttpContext httpContext) throws IOException {
        final Socket socket2 = (socket != null) ? socket : this.createSocket(httpContext);
        if (inetSocketAddress2 != null) {
            socket2.bind(inetSocketAddress2);
        }
        socket2.connect(inetSocketAddress, n);
        return socket2;
    }
    
    static {
        INSTANCE = new PlainConnectionSocketFactory();
    }
}
