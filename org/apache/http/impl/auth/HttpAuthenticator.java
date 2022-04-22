package org.apache.http.impl.auth;

import org.apache.commons.logging.*;
import org.apache.http.client.*;
import org.apache.http.protocol.*;
import java.util.*;
import org.apache.http.*;
import java.io.*;
import org.apache.http.util.*;
import org.apache.http.auth.*;

public class HttpAuthenticator
{
    private final Log log;
    
    public HttpAuthenticator(final Log log) {
        this.log = ((log != null) ? log : LogFactory.getLog(this.getClass()));
    }
    
    public HttpAuthenticator() {
        this(null);
    }
    
    public boolean isAuthenticationRequested(final HttpHost httpHost, final HttpResponse httpResponse, final AuthenticationStrategy authenticationStrategy, final AuthState authState, final HttpContext httpContext) {
        if (authenticationStrategy.isAuthenticationRequested(httpHost, httpResponse, httpContext)) {
            this.log.debug("Authentication required");
            if (authState.getState() == AuthProtocolState.SUCCESS) {
                authenticationStrategy.authFailed(httpHost, authState.getAuthScheme(), httpContext);
            }
            return true;
        }
        switch (authState.getState()) {
            case CHALLENGED:
            case HANDSHAKE: {
                this.log.debug("Authentication succeeded");
                authState.setState(AuthProtocolState.SUCCESS);
                authenticationStrategy.authSucceeded(httpHost, authState.getAuthScheme(), httpContext);
                break;
            }
            case SUCCESS: {
                break;
            }
            default: {
                authState.setState(AuthProtocolState.UNCHALLENGED);
                break;
            }
        }
        return false;
    }
    
    public boolean handleAuthChallenge(final HttpHost httpHost, final HttpResponse httpResponse, final AuthenticationStrategy authenticationStrategy, final AuthState authState, final HttpContext httpContext) {
        if (this.log.isDebugEnabled()) {
            this.log.debug(httpHost.toHostString() + " requested authentication");
        }
        final Map challenges = authenticationStrategy.getChallenges(httpHost, httpResponse, httpContext);
        if (challenges.isEmpty()) {
            this.log.debug("Response contains no authentication challenges");
            return false;
        }
        final AuthScheme authScheme = authState.getAuthScheme();
        switch (authState.getState()) {
            case FAILURE: {
                return false;
            }
            case SUCCESS: {
                authState.reset();
                break;
            }
            case CHALLENGED:
            case HANDSHAKE: {
                if (authScheme == null) {
                    this.log.debug("Auth scheme is null");
                    authenticationStrategy.authFailed(httpHost, null, httpContext);
                    authState.reset();
                    authState.setState(AuthProtocolState.FAILURE);
                    return false;
                }
            }
            case UNCHALLENGED: {
                if (authScheme == null) {
                    break;
                }
                final Header header = challenges.get(authScheme.getSchemeName().toLowerCase(Locale.US));
                if (header == null) {
                    authState.reset();
                    break;
                }
                this.log.debug("Authorization challenge processed");
                authScheme.processChallenge(header);
                if (authScheme.isComplete()) {
                    this.log.debug("Authentication failed");
                    authenticationStrategy.authFailed(httpHost, authState.getAuthScheme(), httpContext);
                    authState.reset();
                    authState.setState(AuthProtocolState.FAILURE);
                    return false;
                }
                authState.setState(AuthProtocolState.HANDSHAKE);
                return true;
            }
        }
        final Queue select = authenticationStrategy.select(challenges, httpHost, httpResponse, httpContext);
        if (select != null && !select.isEmpty()) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Selected authentication options: " + select);
            }
            authState.setState(AuthProtocolState.CHALLENGED);
            authState.update(select);
            return true;
        }
        return false;
    }
    
    public void generateAuthResponse(final HttpRequest httpRequest, final AuthState authState, final HttpContext httpContext) throws HttpException, IOException {
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
                        httpRequest.addHeader(this.doAuth(authScheme2, credentials2, httpRequest, httpContext));
                    }
                    return;
                }
                this.ensureAuthScheme(authScheme);
                break;
            }
        }
        if (authScheme != null) {
            httpRequest.addHeader(this.doAuth(authScheme, credentials, httpRequest, httpContext));
        }
    }
    
    private void ensureAuthScheme(final AuthScheme authScheme) {
        Asserts.notNull(authScheme, "Auth scheme");
    }
    
    private Header doAuth(final AuthScheme authScheme, final Credentials credentials, final HttpRequest httpRequest, final HttpContext httpContext) throws AuthenticationException {
        if (authScheme instanceof ContextAwareAuthScheme) {
            return ((ContextAwareAuthScheme)authScheme).authenticate(credentials, httpRequest, httpContext);
        }
        return authScheme.authenticate(credentials, httpRequest);
    }
}
