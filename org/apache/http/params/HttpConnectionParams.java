package org.apache.http.params;

import org.apache.http.util.*;

@Deprecated
public final class HttpConnectionParams implements CoreConnectionPNames
{
    private HttpConnectionParams() {
    }
    
    public static int getSoTimeout(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getIntParameter("http.socket.timeout", 0);
    }
    
    public static void setSoTimeout(final HttpParams httpParams, final int n) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setIntParameter("http.socket.timeout", n);
    }
    
    public static boolean getSoReuseaddr(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getBooleanParameter("http.socket.reuseaddr", false);
    }
    
    public static void setSoReuseaddr(final HttpParams httpParams, final boolean b) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setBooleanParameter("http.socket.reuseaddr", b);
    }
    
    public static boolean getTcpNoDelay(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getBooleanParameter("http.tcp.nodelay", true);
    }
    
    public static void setTcpNoDelay(final HttpParams httpParams, final boolean b) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setBooleanParameter("http.tcp.nodelay", b);
    }
    
    public static int getSocketBufferSize(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getIntParameter("http.socket.buffer-size", -1);
    }
    
    public static void setSocketBufferSize(final HttpParams httpParams, final int n) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setIntParameter("http.socket.buffer-size", n);
    }
    
    public static int getLinger(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getIntParameter("http.socket.linger", -1);
    }
    
    public static void setLinger(final HttpParams httpParams, final int n) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setIntParameter("http.socket.linger", n);
    }
    
    public static int getConnectionTimeout(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getIntParameter("http.connection.timeout", 0);
    }
    
    public static void setConnectionTimeout(final HttpParams httpParams, final int n) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setIntParameter("http.connection.timeout", n);
    }
    
    public static boolean isStaleCheckingEnabled(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getBooleanParameter("http.connection.stalecheck", true);
    }
    
    public static void setStaleCheckingEnabled(final HttpParams httpParams, final boolean b) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setBooleanParameter("http.connection.stalecheck", b);
    }
    
    public static boolean getSoKeepalive(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getBooleanParameter("http.socket.keepalive", false);
    }
    
    public static void setSoKeepalive(final HttpParams httpParams, final boolean b) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setBooleanParameter("http.socket.keepalive", b);
    }
}
