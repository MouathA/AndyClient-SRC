package org.apache.http.client.protocol;

import org.apache.commons.logging.*;
import org.apache.http.protocol.*;
import java.util.*;
import org.apache.http.util.*;
import org.apache.http.*;
import org.apache.http.auth.*;

@Deprecated
abstract class RequestAuthenticationBase implements HttpRequestInterceptor
{
    final Log log;
    
    public RequestAuthenticationBase() {
        this.log = LogFactory.getLog(this.getClass());
    }
    
    void process(final AuthState authState, final HttpRequest httpRequest, final HttpContext httpContext) {
        final AuthScheme authScheme = authState.getAuthScheme();
        final Credentials credentials = authState.getCredentials();
        switch (authState.getState()) {
            case FAILURE: {
                return;
            }
            case SUCCESS: {
                this.ensureAuthScheme(authScheme);
                if (authScheme.isConnectionBased()) {
                    return;
                }
                break;
            }
            case CHALLENGED: {
                final Queue authOptions = authState.getAuthOptions();
                if (authOptions != null) {
                    if (!authOptions.isEmpty()) {
                        final AuthOption authOption = authOptions.remove();
                        final AuthScheme authScheme2 = authOption.getAuthScheme();
                        final Credentials credentials2 = authOption.getCredentials();
                        authState.update(authScheme2, credentials2);
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Generating response to an authentication challenge using " + authScheme2.getSchemeName() + " scheme");
                        }
                        httpRequest.addHeader(this.authenticate(authScheme2, credentials2, httpRequest, httpContext));
                    }
                    return;
                }
                this.ensureAuthScheme(authScheme);
                break;
            }
        }
        if (authScheme != null) {
            httpRequest.addHeader(this.authenticate(authScheme, credentials, httpRequest, httpContext));
        }
    }
    
    private void ensureAuthScheme(final AuthScheme authScheme) {
        Asserts.notNull(authScheme, "Auth scheme");
    }
    
    private Header authenticate(final AuthScheme authScheme, final Credentials credentials, final HttpRequest httpRequest, final HttpContext httpContext) throws AuthenticationException {
        Asserts.notNull(authScheme, "Auth scheme");
        if (authScheme instanceof ContextAwareAuthScheme) {
            return ((ContextAwareAuthScheme)authScheme).authenticate(credentials, httpRequest, httpContext);
        }
        return authScheme.authenticate(credentials, httpRequest);
    }
}
