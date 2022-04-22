package org.apache.http.conn.scheme;

import org.apache.http.annotation.*;
import java.util.*;
import org.apache.http.util.*;

@Deprecated
@Immutable
public final class Scheme
{
    private final String name;
    private final SchemeSocketFactory socketFactory;
    private final int defaultPort;
    private final boolean layered;
    private String stringRep;
    
    public Scheme(final String s, final int defaultPort, final SchemeSocketFactory schemeSocketFactory) {
        Args.notNull(s, "Scheme name");
        Args.check(defaultPort > 0 && defaultPort <= 65535, "Port is invalid");
        Args.notNull(schemeSocketFactory, "Socket factory");
        this.name = s.toLowerCase(Locale.ENGLISH);
        this.defaultPort = defaultPort;
        if (schemeSocketFactory instanceof SchemeLayeredSocketFactory) {
            this.layered = true;
            this.socketFactory = schemeSocketFactory;
        }
        else if (schemeSocketFactory instanceof LayeredSchemeSocketFactory) {
            this.layered = true;
            this.socketFactory = new SchemeLayeredSocketFactoryAdaptor2((LayeredSchemeSocketFactory)schemeSocketFactory);
        }
        else {
            this.layered = false;
            this.socketFactory = schemeSocketFactory;
        }
    }
    
    @Deprecated
    public Scheme(final String s, final SocketFactory socketFactory, final int defaultPort) {
        Args.notNull(s, "Scheme name");
        Args.notNull(socketFactory, "Socket factory");
        Args.check(defaultPort > 0 && defaultPort <= 65535, "Port is invalid");
        this.name = s.toLowerCase(Locale.ENGLISH);
        if (socketFactory instanceof LayeredSocketFactory) {
            this.socketFactory = new SchemeLayeredSocketFactoryAdaptor((LayeredSocketFactory)socketFactory);
            this.layered = true;
        }
        else {
            this.socketFactory = new SchemeSocketFactoryAdaptor(socketFactory);
            this.layered = false;
        }
        this.defaultPort = defaultPort;
    }
    
    public final int getDefaultPort() {
        return this.defaultPort;
    }
    
    @Deprecated
    public final SocketFactory getSocketFactory() {
        if (this.socketFactory instanceof SchemeSocketFactoryAdaptor) {
            return ((SchemeSocketFactoryAdaptor)this.socketFactory).getFactory();
        }
        if (this.layered) {
            return new LayeredSocketFactoryAdaptor((LayeredSchemeSocketFactory)this.socketFactory);
        }
        return new SocketFactoryAdaptor(this.socketFactory);
    }
    
    public final SchemeSocketFactory getSchemeSocketFactory() {
        return this.socketFactory;
    }
    
    public final String getName() {
        return this.name;
    }
    
    public final boolean isLayered() {
        return this.layered;
    }
    
    public final int resolvePort(final int n) {
        return (n <= 0) ? this.defaultPort : n;
    }
    
    @Override
    public final String toString() {
        if (this.stringRep == null) {
            final StringBuilder sb = new StringBuilder();
            sb.append(this.name);
            sb.append(':');
            sb.append(Integer.toString(this.defaultPort));
            this.stringRep = sb.toString();
        }
        return this.stringRep;
    }
    
    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Scheme) {
            final Scheme scheme = (Scheme)o;
            return this.name.equals(scheme.name) && this.defaultPort == scheme.defaultPort && this.layered == scheme.layered;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        LangUtils.hashCode(17, this.defaultPort);
        LangUtils.hashCode(17, this.name);
        LangUtils.hashCode(17, this.layered);
        return 17;
    }
}
