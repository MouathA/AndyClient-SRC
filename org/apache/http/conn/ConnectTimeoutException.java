package org.apache.http.conn;

import org.apache.http.annotation.*;
import org.apache.http.*;
import java.io.*;
import java.net.*;
import java.util.*;

@Immutable
public class ConnectTimeoutException extends InterruptedIOException
{
    private static final long serialVersionUID = -4816682903149535989L;
    private final HttpHost host;
    
    public ConnectTimeoutException() {
        this.host = null;
    }
    
    public ConnectTimeoutException(final String s) {
        super(s);
        this.host = null;
    }
    
    public ConnectTimeoutException(final IOException ex, final HttpHost host, final InetAddress... array) {
        super("Connect to " + ((host != null) ? host.toHostString() : "remote host") + ((array != null && array.length > 0) ? (" " + Arrays.asList(array)) : "") + ((ex != null && ex.getMessage() != null) ? (" failed: " + ex.getMessage()) : " timed out"));
        this.host = host;
        this.initCause(ex);
    }
    
    public HttpHost getHost() {
        return this.host;
    }
}
