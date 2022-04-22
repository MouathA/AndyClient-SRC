package org.apache.http;

import java.io.*;
import org.apache.http.annotation.*;
import java.net.*;
import java.util.*;
import org.apache.http.util.*;

@Immutable
public final class HttpHost implements Cloneable, Serializable
{
    private static final long serialVersionUID = -7529410654042457626L;
    public static final String DEFAULT_SCHEME_NAME = "http";
    protected final String hostname;
    protected final String lcHostname;
    protected final int port;
    protected final String schemeName;
    protected final InetAddress address;
    
    public HttpHost(final String s, final int port, final String s2) {
        this.hostname = (String)Args.notBlank(s, "Host name");
        this.lcHostname = s.toLowerCase(Locale.ENGLISH);
        if (s2 != null) {
            this.schemeName = s2.toLowerCase(Locale.ENGLISH);
        }
        else {
            this.schemeName = "http";
        }
        this.port = port;
        this.address = null;
    }
    
    public HttpHost(final String s, final int n) {
        this(s, n, null);
    }
    
    public HttpHost(final String s) {
        this(s, -1, null);
    }
    
    public HttpHost(final InetAddress inetAddress, final int port, final String s) {
        this.address = (InetAddress)Args.notNull(inetAddress, "Inet address");
        this.hostname = inetAddress.getHostAddress();
        this.lcHostname = this.hostname.toLowerCase(Locale.ENGLISH);
        if (s != null) {
            this.schemeName = s.toLowerCase(Locale.ENGLISH);
        }
        else {
            this.schemeName = "http";
        }
        this.port = port;
    }
    
    public HttpHost(final InetAddress inetAddress, final int n) {
        this(inetAddress, n, null);
    }
    
    public HttpHost(final InetAddress inetAddress) {
        this(inetAddress, -1, null);
    }
    
    public HttpHost(final HttpHost httpHost) {
        Args.notNull(httpHost, "HTTP host");
        this.hostname = httpHost.hostname;
        this.lcHostname = httpHost.lcHostname;
        this.schemeName = httpHost.schemeName;
        this.port = httpHost.port;
        this.address = httpHost.address;
    }
    
    public String getHostName() {
        return this.hostname;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public String getSchemeName() {
        return this.schemeName;
    }
    
    public InetAddress getAddress() {
        return this.address;
    }
    
    public String toURI() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.schemeName);
        sb.append("://");
        sb.append(this.hostname);
        if (this.port != -1) {
            sb.append(':');
            sb.append(Integer.toString(this.port));
        }
        return sb.toString();
    }
    
    public String toHostString() {
        if (this.port != -1) {
            final StringBuilder sb = new StringBuilder(this.hostname.length() + 6);
            sb.append(this.hostname);
            sb.append(":");
            sb.append(Integer.toString(this.port));
            return sb.toString();
        }
        return this.hostname;
    }
    
    @Override
    public String toString() {
        return this.toURI();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof HttpHost) {
            final HttpHost httpHost = (HttpHost)o;
            return this.lcHostname.equals(httpHost.lcHostname) && this.port == httpHost.port && this.schemeName.equals(httpHost.schemeName);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        LangUtils.hashCode(17, this.lcHostname);
        LangUtils.hashCode(17, this.port);
        LangUtils.hashCode(17, this.schemeName);
        return 17;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
