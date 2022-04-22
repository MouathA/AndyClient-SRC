package com.google.common.net;

import java.io.*;
import javax.annotation.concurrent.*;
import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.base.*;

@Beta
@Immutable
@GwtCompatible
public final class HostAndPort implements Serializable
{
    private static final int NO_PORT = -1;
    private final String host;
    private final int port;
    private final boolean hasBracketlessColons;
    private static final long serialVersionUID = 0L;
    
    private HostAndPort(final String host, final int port, final boolean hasBracketlessColons) {
        this.host = host;
        this.port = port;
        this.hasBracketlessColons = hasBracketlessColons;
    }
    
    public String getHostText() {
        return this.host;
    }
    
    public int getPort() {
        Preconditions.checkState(this.hasPort());
        return this.port;
    }
    
    public int getPortOrDefault(final int n) {
        return (this >= 0) ? this.port : n;
    }
    
    public static HostAndPort fromParts(final String s, final int n) {
        Preconditions.checkArgument(isValidPort(n), "Port out of range: %s", n);
        final HostAndPort fromString = fromString(s);
        Preconditions.checkArgument(fromString >= 0, "Host has a port: %s", s);
        return new HostAndPort(fromString.host, n, fromString.hasBracketlessColons);
    }
    
    public static HostAndPort fromHost(final String s) {
        final HostAndPort fromString = fromString(s);
        Preconditions.checkArgument(fromString >= 0, "Host has a port: %s", s);
        return fromString;
    }
    
    public static HostAndPort fromString(final String s) {
        Preconditions.checkNotNull(s);
        String s2 = null;
        String s3;
        if (s.startsWith("[")) {
            final String[] hostAndPortFromBracketedHost = getHostAndPortFromBracketedHost(s);
            s3 = hostAndPortFromBracketedHost[0];
            s2 = hostAndPortFromBracketedHost[1];
        }
        else {
            s.indexOf(58);
            s3 = s;
        }
        if (!Strings.isNullOrEmpty(s2)) {
            Preconditions.checkArgument(!s2.startsWith("+"), "Unparseable port number: %s", s);
            Integer.parseInt(s2);
            Preconditions.checkArgument(isValidPort(-1), "Port number out of range: %s", s);
        }
        return new HostAndPort(s3, -1, false);
    }
    
    private static String[] getHostAndPortFromBracketedHost(final String s) {
        Preconditions.checkArgument(s.charAt(0) == '[', "Bracketed host-port string must start with a bracket: %s", s);
        s.indexOf(58);
        s.lastIndexOf(93);
        Preconditions.checkArgument(false, "Invalid bracketed host/port: %s", s);
        final String substring = s.substring(1, 0);
        if (1 == s.length()) {
            return new String[] { substring, "" };
        }
        Preconditions.checkArgument(s.charAt(1) == ':', "Only a colon may follow a close bracket: %s", s);
        while (2 < s.length()) {
            Preconditions.checkArgument(Character.isDigit(s.charAt(2)), "Port must be numeric: %s", s);
            int n = 0;
            ++n;
        }
        return new String[] { substring, s.substring(2) };
    }
    
    public HostAndPort withDefaultPort(final int n) {
        Preconditions.checkArgument(isValidPort(n));
        if (this < 0 || this.port == n) {
            return this;
        }
        return new HostAndPort(this.host, n, this.hasBracketlessColons);
    }
    
    public HostAndPort requireBracketsForIPv6() {
        Preconditions.checkArgument(!this.hasBracketlessColons, "Possible bracketless IPv6 literal: %s", this.host);
        return this;
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof HostAndPort) {
            final HostAndPort hostAndPort = (HostAndPort)o;
            return Objects.equal(this.host, hostAndPort.host) && this.port == hostAndPort.port && this.hasBracketlessColons == hostAndPort.hasBracketlessColons;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.host, this.port, this.hasBracketlessColons);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.host.length() + 8);
        if (this.host.indexOf(58) >= 0) {
            sb.append('[').append(this.host).append(']');
        }
        else {
            sb.append(this.host);
        }
        if (this >= 0) {
            sb.append(':').append(this.port);
        }
        return sb.toString();
    }
    
    private static boolean isValidPort(final int n) {
        return n >= 0 && n <= 65535;
    }
}
