package org.apache.http.impl.auth;

import org.apache.http.annotation.*;
import java.nio.charset.*;
import org.apache.http.params.*;
import org.apache.http.auth.*;
import org.apache.http.protocol.*;

@Immutable
public class BasicSchemeFactory implements AuthSchemeFactory, AuthSchemeProvider
{
    private final Charset charset;
    
    public BasicSchemeFactory(final Charset charset) {
        this.charset = charset;
    }
    
    public BasicSchemeFactory() {
        this(null);
    }
    
    public AuthScheme newInstance(final HttpParams httpParams) {
        return new BasicScheme();
    }
    
    public AuthScheme create(final HttpContext httpContext) {
        return new BasicScheme(this.charset);
    }
}
