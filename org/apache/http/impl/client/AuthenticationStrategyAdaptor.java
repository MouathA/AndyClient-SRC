package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.protocol.*;
import org.apache.http.util.*;
import org.apache.http.*;
import java.util.*;
import org.apache.http.auth.*;
import org.apache.http.client.*;

@Deprecated
@Immutable
class AuthenticationStrategyAdaptor implements AuthenticationStrategy
{
    private final Log log;
    private final AuthenticationHandler handler;
    
    public AuthenticationStrategyAdaptor(final AuthenticationHandler handler) {
        this.log = LogFactory.getLog(this.getClass());
        this.handler = handler;
    }
    
    public boolean isAuthenticationRequested(final HttpHost httpHost, final HttpResponse httpResponse, final HttpContext httpContext) {
        return this.handler.isAuthenticationRequested(httpResponse, httpContext);
    }
    
    public Map getChallenges(final HttpHost httpHost, final HttpResponse httpResponse, final HttpContext httpContext) throws MalformedChallengeException {
        return this.handler.getChallenges(httpResponse, httpContext);
    }
    
    public Queue select(final Map map, final HttpHost httpHost, final HttpResponse httpResponse, final HttpContext httpContext) throws MalformedChallengeException {
        Args.notNull(map, "Map of auth challenges");
        Args.notNull(httpHost, "Host");
        Args.notNull(httpResponse, "HTTP response");
        Args.notNull(httpContext, "HTTP context");
        final LinkedList<AuthOption> list = new LinkedList<AuthOption>();
        final CredentialsProvider credentialsProvider = (CredentialsProvider)httpContext.getAttribute("http.auth.credentials-provider");
        if (credentialsProvider == null) {
            this.log.debug("Credentials provider not set in the context");
            return list;
        }
        final AuthScheme selectScheme = this.handler.selectScheme(map, httpResponse, httpContext);
        selectScheme.processChallenge(map.get(selectScheme.getSchemeName().toLowerCase(Locale.US)));
        final Credentials credentials = credentialsProvider.getCredentials(new AuthScope(httpHost.getHostName(), httpHost.getPort(), selectScheme.getRealm(), selectScheme.getSchemeName()));
        if (credentials != null) {
            list.add(new AuthOption(selectScheme, credentials));
        }
        return list;
    }
    
    public void authSucceeded(final HttpHost httpHost, final AuthScheme authScheme, final HttpContext httpContext) {
        AuthCache authCache = (AuthCache)httpContext.getAttribute("http.auth.auth-cache");
        if (this.isCachable(authScheme)) {
            if (authCache == null) {
                authCache = new BasicAuthCache();
                httpContext.setAttribute("http.auth.auth-cache", authCache);
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Caching '" + authScheme.getSchemeName() + "' auth scheme for " + httpHost);
            }
            authCache.put(httpHost, authScheme);
        }
    }
    
    public void authFailed(final HttpHost httpHost, final AuthScheme authScheme, final HttpContext httpContext) {
        final AuthCache authCache = (AuthCache)httpContext.getAttribute("http.auth.auth-cache");
        if (authCache == null) {
            return;
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("Removing from cache '" + authScheme.getSchemeName() + "' auth scheme for " + httpHost);
        }
        authCache.remove(httpHost);
    }
    
    private boolean isCachable(final AuthScheme authScheme) {
        if (authScheme == null || !authScheme.isComplete()) {
            return false;
        }
        final String schemeName = authScheme.getSchemeName();
        return schemeName.equalsIgnoreCase("Basic") || schemeName.equalsIgnoreCase("Digest");
    }
    
    public AuthenticationHandler getHandler() {
        return this.handler;
    }
}
