package org.apache.http.client.protocol;

import org.apache.http.annotation.*;
import org.apache.http.protocol.*;
import org.apache.http.conn.routing.*;
import java.util.*;
import org.apache.http.config.*;
import org.apache.http.cookie.*;
import org.apache.http.client.*;
import org.apache.http.auth.*;
import org.apache.http.client.config.*;

@NotThreadSafe
public class HttpClientContext extends HttpCoreContext
{
    public static final String HTTP_ROUTE = "http.route";
    public static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";
    public static final String COOKIESPEC_REGISTRY = "http.cookiespec-registry";
    public static final String COOKIE_SPEC = "http.cookie-spec";
    public static final String COOKIE_ORIGIN = "http.cookie-origin";
    public static final String COOKIE_STORE = "http.cookie-store";
    public static final String CREDS_PROVIDER = "http.auth.credentials-provider";
    public static final String AUTH_CACHE = "http.auth.auth-cache";
    public static final String TARGET_AUTH_STATE = "http.auth.target-scope";
    public static final String PROXY_AUTH_STATE = "http.auth.proxy-scope";
    public static final String USER_TOKEN = "http.user-token";
    public static final String AUTHSCHEME_REGISTRY = "http.authscheme-registry";
    public static final String REQUEST_CONFIG = "http.request-config";
    
    public static HttpClientContext adapt(final HttpContext httpContext) {
        if (httpContext instanceof HttpClientContext) {
            return (HttpClientContext)httpContext;
        }
        return new HttpClientContext(httpContext);
    }
    
    public static HttpClientContext create() {
        return new HttpClientContext(new BasicHttpContext());
    }
    
    public HttpClientContext(final HttpContext httpContext) {
        super(httpContext);
    }
    
    public HttpClientContext() {
    }
    
    public RouteInfo getHttpRoute() {
        return (RouteInfo)this.getAttribute("http.route", HttpRoute.class);
    }
    
    public List getRedirectLocations() {
        return (List)this.getAttribute("http.protocol.redirect-locations", List.class);
    }
    
    public CookieStore getCookieStore() {
        return (CookieStore)this.getAttribute("http.cookie-store", CookieStore.class);
    }
    
    public void setCookieStore(final CookieStore cookieStore) {
        this.setAttribute("http.cookie-store", cookieStore);
    }
    
    public CookieSpec getCookieSpec() {
        return (CookieSpec)this.getAttribute("http.cookie-spec", CookieSpec.class);
    }
    
    public CookieOrigin getCookieOrigin() {
        return (CookieOrigin)this.getAttribute("http.cookie-origin", CookieOrigin.class);
    }
    
    private Lookup getLookup(final String s, final Class clazz) {
        return (Lookup)this.getAttribute(s, Lookup.class);
    }
    
    public Lookup getCookieSpecRegistry() {
        return this.getLookup("http.cookiespec-registry", CookieSpecProvider.class);
    }
    
    public void setCookieSpecRegistry(final Lookup lookup) {
        this.setAttribute("http.cookiespec-registry", lookup);
    }
    
    public Lookup getAuthSchemeRegistry() {
        return this.getLookup("http.authscheme-registry", AuthSchemeProvider.class);
    }
    
    public void setAuthSchemeRegistry(final Lookup lookup) {
        this.setAttribute("http.authscheme-registry", lookup);
    }
    
    public CredentialsProvider getCredentialsProvider() {
        return (CredentialsProvider)this.getAttribute("http.auth.credentials-provider", CredentialsProvider.class);
    }
    
    public void setCredentialsProvider(final CredentialsProvider credentialsProvider) {
        this.setAttribute("http.auth.credentials-provider", credentialsProvider);
    }
    
    public AuthCache getAuthCache() {
        return (AuthCache)this.getAttribute("http.auth.auth-cache", AuthCache.class);
    }
    
    public void setAuthCache(final AuthCache authCache) {
        this.setAttribute("http.auth.auth-cache", authCache);
    }
    
    public AuthState getTargetAuthState() {
        return (AuthState)this.getAttribute("http.auth.target-scope", AuthState.class);
    }
    
    public AuthState getProxyAuthState() {
        return (AuthState)this.getAttribute("http.auth.proxy-scope", AuthState.class);
    }
    
    public Object getUserToken(final Class clazz) {
        return this.getAttribute("http.user-token", clazz);
    }
    
    public Object getUserToken() {
        return this.getAttribute("http.user-token");
    }
    
    public void setUserToken(final Object o) {
        this.setAttribute("http.user-token", o);
    }
    
    public RequestConfig getRequestConfig() {
        final RequestConfig requestConfig = (RequestConfig)this.getAttribute("http.request-config", RequestConfig.class);
        return (requestConfig != null) ? requestConfig : RequestConfig.DEFAULT;
    }
    
    public void setRequestConfig(final RequestConfig requestConfig) {
        this.setAttribute("http.request-config", requestConfig);
    }
}
