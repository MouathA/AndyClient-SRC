package org.apache.http.client.protocol;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.protocol.*;
import org.apache.http.util.*;
import org.apache.http.client.*;
import org.apache.http.conn.routing.*;
import org.apache.http.*;
import java.io.*;
import org.apache.http.auth.*;

@Immutable
public class RequestAuthCache implements HttpRequestInterceptor
{
    private final Log log;
    
    public RequestAuthCache() {
        this.log = LogFactory.getLog(this.getClass());
    }
    
    public void process(final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpContext, "HTTP context");
        final HttpClientContext adapt = HttpClientContext.adapt(httpContext);
        final AuthCache authCache = adapt.getAuthCache();
        if (authCache == null) {
            this.log.debug("Auth cache not set in the context");
            return;
        }
        final CredentialsProvider credentialsProvider = adapt.getCredentialsProvider();
        if (credentialsProvider == null) {
            this.log.debug("Credentials provider not set in the context");
            return;
        }
        final RouteInfo httpRoute = adapt.getHttpRoute();
        HttpHost targetHost = adapt.getTargetHost();
        if (targetHost.getPort() < 0) {
            targetHost = new HttpHost(targetHost.getHostName(), httpRoute.getTargetHost().getPort(), targetHost.getSchemeName());
        }
        final AuthState targetAuthState = adapt.getTargetAuthState();
        if (targetAuthState != null && targetAuthState.getState() == AuthProtocolState.UNCHALLENGED) {
            final AuthScheme value = authCache.get(targetHost);
            if (value != null) {
                this.doPreemptiveAuth(targetHost, value, targetAuthState, credentialsProvider);
            }
        }
        final HttpHost proxyHost = httpRoute.getProxyHost();
        final AuthState proxyAuthState = adapt.getProxyAuthState();
        if (proxyHost != null && proxyAuthState != null && proxyAuthState.getState() == AuthProtocolState.UNCHALLENGED) {
            final AuthScheme value2 = authCache.get(proxyHost);
            if (value2 != null) {
                this.doPreemptiveAuth(proxyHost, value2, proxyAuthState, credentialsProvider);
            }
        }
    }
    
    private void doPreemptiveAuth(final HttpHost httpHost, final AuthScheme authScheme, final AuthState authState, final CredentialsProvider credentialsProvider) {
        final String schemeName = authScheme.getSchemeName();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Re-using cached '" + schemeName + "' auth scheme for " + httpHost);
        }
        final Credentials credentials = credentialsProvider.getCredentials(new AuthScope(httpHost, AuthScope.ANY_REALM, schemeName));
        if (credentials != null) {
            if ("BASIC".equalsIgnoreCase(authScheme.getSchemeName())) {
                authState.setState(AuthProtocolState.CHALLENGED);
            }
            else {
                authState.setState(AuthProtocolState.SUCCESS);
            }
            authState.update(authScheme, credentials);
        }
        else {
            this.log.debug("No credentials for preemptive authentication");
        }
    }
}
