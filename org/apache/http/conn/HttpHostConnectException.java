package org.apache.http.conn;

import org.apache.http.annotation.*;
import org.apache.http.*;
import java.io.*;
import java.net.*;
import java.util.*;

@Immutable
public class HttpHostConnectException extends ConnectException
{
    private static final long serialVersionUID = -3194482710275220224L;
    private final HttpHost host;
    
    @Deprecated
    public HttpHostConnectException(final HttpHost httpHost, final ConnectException ex) {
        this(ex, httpHost, (InetAddress[])null);
    }
    
    public HttpHostConnectException(final IOException ex, final HttpHost host, final InetAddress... array) {
        super("Connect to " + ((host != null) ? host.toHostString() : "remote host") + ((array != null && array.length > 0) ? (" " + Arrays.asList(array)) : "") + ((ex != null && ex.getMessage() != null) ? (" failed: " + ex.getMessage()) : " refused"));
        this.host = host;
        this.initCause(ex);
    }
    
    public HttpHost getHost() {
        return this.host;
    }
}
