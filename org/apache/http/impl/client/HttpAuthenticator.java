package org.apache.http.impl.client;

import org.apache.commons.logging.*;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.auth.*;
import org.apache.http.protocol.*;

@Deprecated
public class HttpAuthenticator extends org.apache.http.impl.auth.HttpAuthenticator
{
    public HttpAuthenticator(final Log log) {
        super(log);
    }
    
    public HttpAuthenticator() {
    }
    
    public boolean authenticate(final HttpHost httpHost, final HttpResponse httpResponse, final AuthenticationStrategy authenticationStrategy, final AuthState authState, final HttpContext httpContext) {
        return this.handleAuthChallenge(httpHost, httpResponse, authenticationStrategy, authState, httpContext);
    }
}
