package org.apache.http.impl.client;

import org.apache.http.params.*;
import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.conn.*;
import org.apache.http.conn.scheme.*;
import org.apache.http.auth.*;
import org.apache.http.impl.auth.*;
import org.apache.http.cookie.*;
import org.apache.http.impl.cookie.*;
import org.apache.http.impl.*;
import org.apache.http.impl.conn.*;
import org.apache.http.client.methods.*;
import org.apache.http.util.*;
import org.apache.http.protocol.*;
import org.apache.http.client.params.*;
import org.apache.http.*;
import org.apache.http.conn.routing.*;
import java.io.*;
import org.apache.http.client.*;

@Deprecated
@ThreadSafe
public abstract class AbstractHttpClient extends CloseableHttpClient
{
    private final Log log;
    @GuardedBy("this")
    private HttpParams defaultParams;
    @GuardedBy("this")
    private HttpRequestExecutor requestExec;
    @GuardedBy("this")
    private ClientConnectionManager connManager;
    @GuardedBy("this")
    private ConnectionReuseStrategy reuseStrategy;
    @GuardedBy("this")
    private ConnectionKeepAliveStrategy keepAliveStrategy;
    @GuardedBy("this")
    private CookieSpecRegistry supportedCookieSpecs;
    @GuardedBy("this")
    private AuthSchemeRegistry supportedAuthSchemes;
    @GuardedBy("this")
    private BasicHttpProcessor mutableProcessor;
    @GuardedBy("this")
    private ImmutableHttpProcessor protocolProcessor;
    @GuardedBy("this")
    private HttpRequestRetryHandler retryHandler;
    @GuardedBy("this")
    private RedirectStrategy redirectStrategy;
    @GuardedBy("this")
    private AuthenticationStrategy targetAuthStrategy;
    @GuardedBy("this")
    private AuthenticationStrategy proxyAuthStrategy;
    @GuardedBy("this")
    private CookieStore cookieStore;
    @GuardedBy("this")
    private CredentialsProvider credsProvider;
    @GuardedBy("this")
    private HttpRoutePlanner routePlanner;
    @GuardedBy("this")
    private UserTokenHandler userTokenHandler;
    @GuardedBy("this")
    private ConnectionBackoffStrategy connectionBackoffStrategy;
    @GuardedBy("this")
    private BackoffManager backoffManager;
    
    protected AbstractHttpClient(final ClientConnectionManager connManager, final HttpParams defaultParams) {
        this.log = LogFactory.getLog(this.getClass());
        this.defaultParams = defaultParams;
        this.connManager = connManager;
    }
    
    protected abstract HttpParams createHttpParams();
    
    protected abstract BasicHttpProcessor createHttpProcessor();
    
    protected HttpContext createHttpContext() {
        final BasicHttpContext basicHttpContext = new BasicHttpContext();
        basicHttpContext.setAttribute("http.scheme-registry", this.getConnectionManager().getSchemeRegistry());
        basicHttpContext.setAttribute("http.authscheme-registry", this.getAuthSchemes());
        basicHttpContext.setAttribute("http.cookiespec-registry", this.getCookieSpecs());
        basicHttpContext.setAttribute("http.cookie-store", this.getCookieStore());
        basicHttpContext.setAttribute("http.auth.credentials-provider", this.getCredentialsProvider());
        return basicHttpContext;
    }
    
    protected ClientConnectionManager createClientConnectionManager() {
        final SchemeRegistry default1 = SchemeRegistryFactory.createDefault();
        final HttpParams params = this.getParams();
        ClientConnectionManagerFactory clientConnectionManagerFactory = null;
        final String s = (String)params.getParameter("http.connection-manager.factory-class-name");
        if (s != null) {
            clientConnectionManagerFactory = (ClientConnectionManagerFactory)Class.forName(s).newInstance();
        }
        ClientConnectionManager instance;
        if (clientConnectionManagerFactory != null) {
            instance = clientConnectionManagerFactory.newInstance(params, default1);
        }
        else {
            instance = new BasicClientConnectionManager(default1);
        }
        return instance;
    }
    
    protected AuthSchemeRegistry createAuthSchemeRegistry() {
        final AuthSchemeRegistry authSchemeRegistry = new AuthSchemeRegistry();
        authSchemeRegistry.register("Basic", new BasicSchemeFactory());
        authSchemeRegistry.register("Digest", new DigestSchemeFactory());
        authSchemeRegistry.register("NTLM", new NTLMSchemeFactory());
        authSchemeRegistry.register("negotiate", new SPNegoSchemeFactory());
        authSchemeRegistry.register("Kerberos", new KerberosSchemeFactory());
        return authSchemeRegistry;
    }
    
    protected CookieSpecRegistry createCookieSpecRegistry() {
        final CookieSpecRegistry cookieSpecRegistry = new CookieSpecRegistry();
        cookieSpecRegistry.register("best-match", new BestMatchSpecFactory());
        cookieSpecRegistry.register("compatibility", new BrowserCompatSpecFactory());
        cookieSpecRegistry.register("netscape", new NetscapeDraftSpecFactory());
        cookieSpecRegistry.register("rfc2109", new RFC2109SpecFactory());
        cookieSpecRegistry.register("rfc2965", new RFC2965SpecFactory());
        cookieSpecRegistry.register("ignoreCookies", new IgnoreSpecFactory());
        return cookieSpecRegistry;
    }
    
    protected HttpRequestExecutor createRequestExecutor() {
        return new HttpRequestExecutor();
    }
    
    protected ConnectionReuseStrategy createConnectionReuseStrategy() {
        return new DefaultConnectionReuseStrategy();
    }
    
    protected ConnectionKeepAliveStrategy createConnectionKeepAliveStrategy() {
        return new DefaultConnectionKeepAliveStrategy();
    }
    
    protected HttpRequestRetryHandler createHttpRequestRetryHandler() {
        return new DefaultHttpRequestRetryHandler();
    }
    
    @Deprecated
    protected RedirectHandler createRedirectHandler() {
        return new DefaultRedirectHandler();
    }
    
    protected AuthenticationStrategy createTargetAuthenticationStrategy() {
        return new TargetAuthenticationStrategy();
    }
    
    @Deprecated
    protected AuthenticationHandler createTargetAuthenticationHandler() {
        return new DefaultTargetAuthenticationHandler();
    }
    
    protected AuthenticationStrategy createProxyAuthenticationStrategy() {
        return new ProxyAuthenticationStrategy();
    }
    
    @Deprecated
    protected AuthenticationHandler createProxyAuthenticationHandler() {
        return new DefaultProxyAuthenticationHandler();
    }
    
    protected CookieStore createCookieStore() {
        return new BasicCookieStore();
    }
    
    protected CredentialsProvider createCredentialsProvider() {
        return new BasicCredentialsProvider();
    }
    
    protected HttpRoutePlanner createHttpRoutePlanner() {
        return new DefaultHttpRoutePlanner(this.getConnectionManager().getSchemeRegistry());
    }
    
    protected UserTokenHandler createUserTokenHandler() {
        return new DefaultUserTokenHandler();
    }
    
    public final synchronized HttpParams getParams() {
        if (this.defaultParams == null) {
            this.defaultParams = this.createHttpParams();
        }
        return this.defaultParams;
    }
    
    public synchronized void setParams(final HttpParams defaultParams) {
        this.defaultParams = defaultParams;
    }
    
    public final synchronized ClientConnectionManager getConnectionManager() {
        if (this.connManager == null) {
            this.connManager = this.createClientConnectionManager();
        }
        return this.connManager;
    }
    
    public final synchronized HttpRequestExecutor getRequestExecutor() {
        if (this.requestExec == null) {
            this.requestExec = this.createRequestExecutor();
        }
        return this.requestExec;
    }
    
    public final synchronized AuthSchemeRegistry getAuthSchemes() {
        if (this.supportedAuthSchemes == null) {
            this.supportedAuthSchemes = this.createAuthSchemeRegistry();
        }
        return this.supportedAuthSchemes;
    }
    
    public synchronized void setAuthSchemes(final AuthSchemeRegistry supportedAuthSchemes) {
        this.supportedAuthSchemes = supportedAuthSchemes;
    }
    
    public final synchronized ConnectionBackoffStrategy getConnectionBackoffStrategy() {
        return this.connectionBackoffStrategy;
    }
    
    public synchronized void setConnectionBackoffStrategy(final ConnectionBackoffStrategy connectionBackoffStrategy) {
        this.connectionBackoffStrategy = connectionBackoffStrategy;
    }
    
    public final synchronized CookieSpecRegistry getCookieSpecs() {
        if (this.supportedCookieSpecs == null) {
            this.supportedCookieSpecs = this.createCookieSpecRegistry();
        }
        return this.supportedCookieSpecs;
    }
    
    public final synchronized BackoffManager getBackoffManager() {
        return this.backoffManager;
    }
    
    public synchronized void setBackoffManager(final BackoffManager backoffManager) {
        this.backoffManager = backoffManager;
    }
    
    public synchronized void setCookieSpecs(final CookieSpecRegistry supportedCookieSpecs) {
        this.supportedCookieSpecs = supportedCookieSpecs;
    }
    
    public final synchronized ConnectionReuseStrategy getConnectionReuseStrategy() {
        if (this.reuseStrategy == null) {
            this.reuseStrategy = this.createConnectionReuseStrategy();
        }
        return this.reuseStrategy;
    }
    
    public synchronized void setReuseStrategy(final ConnectionReuseStrategy reuseStrategy) {
        this.reuseStrategy = reuseStrategy;
    }
    
    public final synchronized ConnectionKeepAliveStrategy getConnectionKeepAliveStrategy() {
        if (this.keepAliveStrategy == null) {
            this.keepAliveStrategy = this.createConnectionKeepAliveStrategy();
        }
        return this.keepAliveStrategy;
    }
    
    public synchronized void setKeepAliveStrategy(final ConnectionKeepAliveStrategy keepAliveStrategy) {
        this.keepAliveStrategy = keepAliveStrategy;
    }
    
    public final synchronized HttpRequestRetryHandler getHttpRequestRetryHandler() {
        if (this.retryHandler == null) {
            this.retryHandler = this.createHttpRequestRetryHandler();
        }
        return this.retryHandler;
    }
    
    public synchronized void setHttpRequestRetryHandler(final HttpRequestRetryHandler retryHandler) {
        this.retryHandler = retryHandler;
    }
    
    @Deprecated
    public final synchronized RedirectHandler getRedirectHandler() {
        return this.createRedirectHandler();
    }
    
    @Deprecated
    public synchronized void setRedirectHandler(final RedirectHandler redirectHandler) {
        this.redirectStrategy = new DefaultRedirectStrategyAdaptor(redirectHandler);
    }
    
    public final synchronized RedirectStrategy getRedirectStrategy() {
        if (this.redirectStrategy == null) {
            this.redirectStrategy = new DefaultRedirectStrategy();
        }
        return this.redirectStrategy;
    }
    
    public synchronized void setRedirectStrategy(final RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    
    @Deprecated
    public final synchronized AuthenticationHandler getTargetAuthenticationHandler() {
        return this.createTargetAuthenticationHandler();
    }
    
    @Deprecated
    public synchronized void setTargetAuthenticationHandler(final AuthenticationHandler authenticationHandler) {
        this.targetAuthStrategy = new AuthenticationStrategyAdaptor(authenticationHandler);
    }
    
    public final synchronized AuthenticationStrategy getTargetAuthenticationStrategy() {
        if (this.targetAuthStrategy == null) {
            this.targetAuthStrategy = this.createTargetAuthenticationStrategy();
        }
        return this.targetAuthStrategy;
    }
    
    public synchronized void setTargetAuthenticationStrategy(final AuthenticationStrategy targetAuthStrategy) {
        this.targetAuthStrategy = targetAuthStrategy;
    }
    
    @Deprecated
    public final synchronized AuthenticationHandler getProxyAuthenticationHandler() {
        return this.createProxyAuthenticationHandler();
    }
    
    @Deprecated
    public synchronized void setProxyAuthenticationHandler(final AuthenticationHandler authenticationHandler) {
        this.proxyAuthStrategy = new AuthenticationStrategyAdaptor(authenticationHandler);
    }
    
    public final synchronized AuthenticationStrategy getProxyAuthenticationStrategy() {
        if (this.proxyAuthStrategy == null) {
            this.proxyAuthStrategy = this.createProxyAuthenticationStrategy();
        }
        return this.proxyAuthStrategy;
    }
    
    public synchronized void setProxyAuthenticationStrategy(final AuthenticationStrategy proxyAuthStrategy) {
        this.proxyAuthStrategy = proxyAuthStrategy;
    }
    
    public final synchronized CookieStore getCookieStore() {
        if (this.cookieStore == null) {
            this.cookieStore = this.createCookieStore();
        }
        return this.cookieStore;
    }
    
    public synchronized void setCookieStore(final CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }
    
    public final synchronized CredentialsProvider getCredentialsProvider() {
        if (this.credsProvider == null) {
            this.credsProvider = this.createCredentialsProvider();
        }
        return this.credsProvider;
    }
    
    public synchronized void setCredentialsProvider(final CredentialsProvider credsProvider) {
        this.credsProvider = credsProvider;
    }
    
    public final synchronized HttpRoutePlanner getRoutePlanner() {
        if (this.routePlanner == null) {
            this.routePlanner = this.createHttpRoutePlanner();
        }
        return this.routePlanner;
    }
    
    public synchronized void setRoutePlanner(final HttpRoutePlanner routePlanner) {
        this.routePlanner = routePlanner;
    }
    
    public final synchronized UserTokenHandler getUserTokenHandler() {
        if (this.userTokenHandler == null) {
            this.userTokenHandler = this.createUserTokenHandler();
        }
        return this.userTokenHandler;
    }
    
    public synchronized void setUserTokenHandler(final UserTokenHandler userTokenHandler) {
        this.userTokenHandler = userTokenHandler;
    }
    
    protected final synchronized BasicHttpProcessor getHttpProcessor() {
        if (this.mutableProcessor == null) {
            this.mutableProcessor = this.createHttpProcessor();
        }
        return this.mutableProcessor;
    }
    
    private synchronized HttpProcessor getProtocolProcessor() {
        if (this.protocolProcessor == null) {
            final BasicHttpProcessor httpProcessor = this.getHttpProcessor();
            final int requestInterceptorCount = httpProcessor.getRequestInterceptorCount();
            final HttpRequestInterceptor[] array = new HttpRequestInterceptor[requestInterceptorCount];
            while (0 < requestInterceptorCount) {
                array[0] = httpProcessor.getRequestInterceptor(0);
                int responseInterceptorCount = 0;
                ++responseInterceptorCount;
            }
            int responseInterceptorCount = httpProcessor.getResponseInterceptorCount();
            final HttpResponseInterceptor[] array2 = new HttpResponseInterceptor[0];
            while (0 < 0) {
                array2[0] = httpProcessor.getResponseInterceptor(0);
                int n = 0;
                ++n;
            }
            this.protocolProcessor = new ImmutableHttpProcessor(array, array2);
        }
        return this.protocolProcessor;
    }
    
    public synchronized int getResponseInterceptorCount() {
        return this.getHttpProcessor().getResponseInterceptorCount();
    }
    
    public synchronized HttpResponseInterceptor getResponseInterceptor(final int n) {
        return this.getHttpProcessor().getResponseInterceptor(n);
    }
    
    public synchronized HttpRequestInterceptor getRequestInterceptor(final int n) {
        return this.getHttpProcessor().getRequestInterceptor(n);
    }
    
    public synchronized int getRequestInterceptorCount() {
        return this.getHttpProcessor().getRequestInterceptorCount();
    }
    
    public synchronized void addResponseInterceptor(final HttpResponseInterceptor httpResponseInterceptor) {
        this.getHttpProcessor().addInterceptor(httpResponseInterceptor);
        this.protocolProcessor = null;
    }
    
    public synchronized void addResponseInterceptor(final HttpResponseInterceptor httpResponseInterceptor, final int n) {
        this.getHttpProcessor().addInterceptor(httpResponseInterceptor, n);
        this.protocolProcessor = null;
    }
    
    public synchronized void clearResponseInterceptors() {
        this.getHttpProcessor().clearResponseInterceptors();
        this.protocolProcessor = null;
    }
    
    public synchronized void removeResponseInterceptorByClass(final Class clazz) {
        this.getHttpProcessor().removeResponseInterceptorByClass(clazz);
        this.protocolProcessor = null;
    }
    
    public synchronized void addRequestInterceptor(final HttpRequestInterceptor httpRequestInterceptor) {
        this.getHttpProcessor().addInterceptor(httpRequestInterceptor);
        this.protocolProcessor = null;
    }
    
    public synchronized void addRequestInterceptor(final HttpRequestInterceptor httpRequestInterceptor, final int n) {
        this.getHttpProcessor().addInterceptor(httpRequestInterceptor, n);
        this.protocolProcessor = null;
    }
    
    public synchronized void clearRequestInterceptors() {
        this.getHttpProcessor().clearRequestInterceptors();
        this.protocolProcessor = null;
    }
    
    public synchronized void removeRequestInterceptorByClass(final Class clazz) {
        this.getHttpProcessor().removeRequestInterceptorByClass(clazz);
        this.protocolProcessor = null;
    }
    
    @Override
    protected final CloseableHttpResponse doExecute(final HttpHost httpHost, final HttpRequest httpRequest, final HttpContext httpContext) throws IOException, ClientProtocolException {
        Args.notNull(httpRequest, "HTTP request");
        // monitorenter(this)
        final HttpContext httpContext2 = this.createHttpContext();
        HttpContext httpContext3;
        if (httpContext == null) {
            httpContext3 = httpContext2;
        }
        else {
            httpContext3 = new DefaultedHttpContext(httpContext, httpContext2);
        }
        final HttpParams determineParams = this.determineParams(httpRequest);
        httpContext3.setAttribute("http.request-config", HttpClientParamConfig.getRequestConfig(determineParams));
        final RequestDirector clientRequestDirector = this.createClientRequestDirector(this.getRequestExecutor(), this.getConnectionManager(), this.getConnectionReuseStrategy(), this.getConnectionKeepAliveStrategy(), this.getRoutePlanner(), this.getProtocolProcessor(), this.getHttpRequestRetryHandler(), this.getRedirectStrategy(), this.getTargetAuthenticationStrategy(), this.getProxyAuthenticationStrategy(), this.getUserTokenHandler(), determineParams);
        final HttpRoutePlanner routePlanner = this.getRoutePlanner();
        final ConnectionBackoffStrategy connectionBackoffStrategy = this.getConnectionBackoffStrategy();
        final BackoffManager backoffManager = this.getBackoffManager();
        // monitorexit(this)
        if (connectionBackoffStrategy != null && backoffManager != null) {
            final HttpRoute determineRoute = routePlanner.determineRoute((httpHost != null) ? httpHost : ((HttpHost)this.determineParams(httpRequest).getParameter("http.default-host")), httpRequest, httpContext3);
            final CloseableHttpResponse proxy = CloseableHttpResponseProxy.newProxy(clientRequestDirector.execute(httpHost, httpRequest, httpContext3));
            if (connectionBackoffStrategy.shouldBackoff(proxy)) {
                backoffManager.backOff(determineRoute);
            }
            else {
                backoffManager.probe(determineRoute);
            }
            return proxy;
        }
        return CloseableHttpResponseProxy.newProxy(clientRequestDirector.execute(httpHost, httpRequest, httpContext3));
    }
    
    @Deprecated
    protected RequestDirector createClientRequestDirector(final HttpRequestExecutor httpRequestExecutor, final ClientConnectionManager clientConnectionManager, final ConnectionReuseStrategy connectionReuseStrategy, final ConnectionKeepAliveStrategy connectionKeepAliveStrategy, final HttpRoutePlanner httpRoutePlanner, final HttpProcessor httpProcessor, final HttpRequestRetryHandler httpRequestRetryHandler, final RedirectHandler redirectHandler, final AuthenticationHandler authenticationHandler, final AuthenticationHandler authenticationHandler2, final UserTokenHandler userTokenHandler, final HttpParams httpParams) {
        return new DefaultRequestDirector(httpRequestExecutor, clientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpRoutePlanner, httpProcessor, httpRequestRetryHandler, redirectHandler, authenticationHandler, authenticationHandler2, userTokenHandler, httpParams);
    }
    
    @Deprecated
    protected RequestDirector createClientRequestDirector(final HttpRequestExecutor httpRequestExecutor, final ClientConnectionManager clientConnectionManager, final ConnectionReuseStrategy connectionReuseStrategy, final ConnectionKeepAliveStrategy connectionKeepAliveStrategy, final HttpRoutePlanner httpRoutePlanner, final HttpProcessor httpProcessor, final HttpRequestRetryHandler httpRequestRetryHandler, final RedirectStrategy redirectStrategy, final AuthenticationHandler authenticationHandler, final AuthenticationHandler authenticationHandler2, final UserTokenHandler userTokenHandler, final HttpParams httpParams) {
        return new DefaultRequestDirector(this.log, httpRequestExecutor, clientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpRoutePlanner, httpProcessor, httpRequestRetryHandler, redirectStrategy, authenticationHandler, authenticationHandler2, userTokenHandler, httpParams);
    }
    
    protected RequestDirector createClientRequestDirector(final HttpRequestExecutor httpRequestExecutor, final ClientConnectionManager clientConnectionManager, final ConnectionReuseStrategy connectionReuseStrategy, final ConnectionKeepAliveStrategy connectionKeepAliveStrategy, final HttpRoutePlanner httpRoutePlanner, final HttpProcessor httpProcessor, final HttpRequestRetryHandler httpRequestRetryHandler, final RedirectStrategy redirectStrategy, final AuthenticationStrategy authenticationStrategy, final AuthenticationStrategy authenticationStrategy2, final UserTokenHandler userTokenHandler, final HttpParams httpParams) {
        return new DefaultRequestDirector(this.log, httpRequestExecutor, clientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpRoutePlanner, httpProcessor, httpRequestRetryHandler, redirectStrategy, authenticationStrategy, authenticationStrategy2, userTokenHandler, httpParams);
    }
    
    protected HttpParams determineParams(final HttpRequest httpRequest) {
        return new ClientParamsStack(null, this.getParams(), httpRequest.getParams(), null);
    }
    
    public void close() {
        this.getConnectionManager().shutdown();
    }
}
