package org.apache.http.impl.auth;

import org.apache.http.annotation.*;
import org.apache.http.params.*;
import org.apache.http.auth.*;
import org.apache.http.protocol.*;

@Immutable
public class SPNegoSchemeFactory implements AuthSchemeFactory, AuthSchemeProvider
{
    private final boolean stripPort;
    
    public SPNegoSchemeFactory(final boolean stripPort) {
        this.stripPort = stripPort;
    }
    
    public SPNegoSchemeFactory() {
        this(false);
    }
    
    public boolean isStripPort() {
        return this.stripPort;
    }
    
    public AuthScheme newInstance(final HttpParams httpParams) {
        return new SPNegoScheme(this.stripPort);
    }
    
    public AuthScheme create(final HttpContext httpContext) {
        return new SPNegoScheme(this.stripPort);
    }
}
