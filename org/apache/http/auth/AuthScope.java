package org.apache.http.auth;

import org.apache.http.annotation.*;
import java.util.*;
import org.apache.http.*;
import org.apache.http.util.*;

@Immutable
public class AuthScope
{
    public static final String ANY_HOST;
    public static final int ANY_PORT = -1;
    public static final String ANY_REALM;
    public static final String ANY_SCHEME;
    public static final AuthScope ANY;
    private final String scheme;
    private final String realm;
    private final String host;
    private final int port;
    
    public AuthScope(final String s, final int n, final String s2, final String s3) {
        this.host = ((s == null) ? AuthScope.ANY_HOST : s.toLowerCase(Locale.ENGLISH));
        this.port = ((n < 0) ? -1 : n);
        this.realm = ((s2 == null) ? AuthScope.ANY_REALM : s2);
        this.scheme = ((s3 == null) ? AuthScope.ANY_SCHEME : s3.toUpperCase(Locale.ENGLISH));
    }
    
    public AuthScope(final HttpHost httpHost, final String s, final String s2) {
        this(httpHost.getHostName(), httpHost.getPort(), s, s2);
    }
    
    public AuthScope(final HttpHost httpHost) {
        this(httpHost, AuthScope.ANY_REALM, AuthScope.ANY_SCHEME);
    }
    
    public AuthScope(final String s, final int n, final String s2) {
        this(s, n, s2, AuthScope.ANY_SCHEME);
    }
    
    public AuthScope(final String s, final int n) {
        this(s, n, AuthScope.ANY_REALM, AuthScope.ANY_SCHEME);
    }
    
    public AuthScope(final AuthScope authScope) {
        Args.notNull(authScope, "Scope");
        this.host = authScope.getHost();
        this.port = authScope.getPort();
        this.realm = authScope.getRealm();
        this.scheme = authScope.getScheme();
    }
    
    public String getHost() {
        return this.host;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public String getRealm() {
        return this.realm;
    }
    
    public String getScheme() {
        return this.scheme;
    }
    
    public int match(final AuthScope authScope) {
        int n = 0;
        if (LangUtils.equals(this.scheme, authScope.scheme)) {
            ++n;
        }
        else if (this.scheme != AuthScope.ANY_SCHEME && authScope.scheme != AuthScope.ANY_SCHEME) {
            return -1;
        }
        if (LangUtils.equals(this.realm, authScope.realm)) {
            n += 2;
        }
        else if (this.realm != AuthScope.ANY_REALM && authScope.realm != AuthScope.ANY_REALM) {
            return -1;
        }
        if (this.port == authScope.port) {
            n += 4;
        }
        else if (this.port != -1 && authScope.port != -1) {
            return -1;
        }
        if (LangUtils.equals(this.host, authScope.host)) {
            n += 8;
        }
        else if (this.host != AuthScope.ANY_HOST && authScope.host != AuthScope.ANY_HOST) {
            return -1;
        }
        return 0;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof AuthScope)) {
            return super.equals(o);
        }
        final AuthScope authScope = (AuthScope)o;
        return LangUtils.equals(this.host, authScope.host) && this.port == authScope.port && LangUtils.equals(this.realm, authScope.realm) && LangUtils.equals(this.scheme, authScope.scheme);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (this.scheme != null) {
            sb.append(this.scheme.toUpperCase(Locale.ENGLISH));
            sb.append(' ');
        }
        if (this.realm != null) {
            sb.append('\'');
            sb.append(this.realm);
            sb.append('\'');
        }
        else {
            sb.append("<any realm>");
        }
        if (this.host != null) {
            sb.append('@');
            sb.append(this.host);
            if (this.port >= 0) {
                sb.append(':');
                sb.append(this.port);
            }
        }
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        LangUtils.hashCode(17, this.host);
        LangUtils.hashCode(17, this.port);
        LangUtils.hashCode(17, this.realm);
        LangUtils.hashCode(17, this.scheme);
        return 17;
    }
    
    static {
        ANY_HOST = null;
        ANY_REALM = null;
        ANY_SCHEME = null;
        ANY = new AuthScope(AuthScope.ANY_HOST, -1, AuthScope.ANY_REALM, AuthScope.ANY_SCHEME);
    }
}
