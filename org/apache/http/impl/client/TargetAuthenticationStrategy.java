package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.http.client.config.*;
import org.apache.http.protocol.*;
import org.apache.http.*;
import java.util.*;
import org.apache.http.auth.*;

@Immutable
public class TargetAuthenticationStrategy extends AuthenticationStrategyImpl
{
    public static final TargetAuthenticationStrategy INSTANCE;
    
    public TargetAuthenticationStrategy() {
        super(401, "WWW-Authenticate");
    }
    
    @Override
    Collection getPreferredAuthSchemes(final RequestConfig requestConfig) {
        return requestConfig.getTargetPreferredAuthSchemes();
    }
    
    @Override
    public void authFailed(final HttpHost httpHost, final AuthScheme authScheme, final HttpContext httpContext) {
        super.authFailed(httpHost, authScheme, httpContext);
    }
    
    @Override
    public void authSucceeded(final HttpHost httpHost, final AuthScheme authScheme, final HttpContext httpContext) {
        super.authSucceeded(httpHost, authScheme, httpContext);
    }
    
    @Override
    public Queue select(final Map map, final HttpHost httpHost, final HttpResponse httpResponse, final HttpContext httpContext) throws MalformedChallengeException {
        return super.select(map, httpHost, httpResponse, httpContext);
    }
    
    @Override
    public Map getChallenges(final HttpHost httpHost, final HttpResponse httpResponse, final HttpContext httpContext) throws MalformedChallengeException {
        return super.getChallenges(httpHost, httpResponse, httpContext);
    }
    
    @Override
    public boolean isAuthenticationRequested(final HttpHost httpHost, final HttpResponse httpResponse, final HttpContext httpContext) {
        return super.isAuthenticationRequested(httpHost, httpResponse, httpContext);
    }
    
    static {
        INSTANCE = new TargetAuthenticationStrategy();
    }
}
