package org.apache.http.impl.auth;

import org.apache.http.annotation.*;
import org.apache.http.params.*;
import org.apache.http.auth.*;
import org.apache.http.protocol.*;

@Immutable
public class KerberosSchemeFactory implements AuthSchemeFactory, AuthSchemeProvider
{
    private final boolean stripPort;
    
    public KerberosSchemeFactory(final boolean stripPort) {
        this.stripPort = stripPort;
    }
    
    public KerberosSchemeFactory() {
        this(false);
    }
    
    public boolean isStripPort() {
        return this.stripPort;
    }
    
    public AuthScheme newInstance(final HttpParams httpParams) {
        return new KerberosScheme(this.stripPort);
    }
    
    public AuthScheme create(final HttpContext httpContext) {
        return new KerberosScheme(this.stripPort);
    }
}
