package org.apache.http.impl.auth;

import org.apache.http.annotation.*;
import org.apache.http.params.*;
import org.apache.http.auth.*;
import org.apache.http.protocol.*;

@Immutable
public class NTLMSchemeFactory implements AuthSchemeFactory, AuthSchemeProvider
{
    public AuthScheme newInstance(final HttpParams httpParams) {
        return new NTLMScheme();
    }
    
    public AuthScheme create(final HttpContext httpContext) {
        return new NTLMScheme();
    }
}
