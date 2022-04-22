package org.apache.http.impl.auth;

import org.apache.http.annotation.*;
import java.nio.charset.*;
import org.apache.http.params.*;
import org.apache.http.auth.*;
import org.apache.http.protocol.*;

@Immutable
public class DigestSchemeFactory implements AuthSchemeFactory, AuthSchemeProvider
{
    private final Charset charset;
    
    public DigestSchemeFactory(final Charset charset) {
        this.charset = charset;
    }
    
    public DigestSchemeFactory() {
        this(null);
    }
    
    public AuthScheme newInstance(final HttpParams httpParams) {
        return new DigestScheme();
    }
    
    public AuthScheme create(final HttpContext httpContext) {
        return new DigestScheme(this.charset);
    }
}
