package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.http.impl.execchain.*;
import org.apache.http.config.*;
import org.apache.http.client.config.*;
import org.apache.commons.logging.*;
import org.apache.http.conn.routing.*;
import org.apache.http.util.*;
import org.apache.http.*;
import org.apache.http.client.protocol.*;
import org.apache.http.auth.*;
import org.apache.http.protocol.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.params.*;
import org.apache.http.params.*;
import org.apache.http.client.*;
import java.io.*;
import java.util.*;
import org.apache.http.conn.*;
import java.util.concurrent.*;
import org.apache.http.conn.scheme.*;

@ThreadSafe
class InternalHttpClient extends CloseableHttpClient
{
    private final Log log;
    private final ClientExecChain execChain;
    private final HttpClientConnectionManager connManager;
    private final HttpRoutePlanner routePlanner;
    private final Lookup cookieSpecRegistry;
    private final Lookup authSchemeRegistry;
    private final CookieStore cookieStore;
    private final CredentialsProvider credentialsProvider;
    private final RequestConfig defaultConfig;
    private final List closeables;
    
    public InternalHttpClient(final ClientExecChain execChain, final HttpClientConnectionManager connManager, final HttpRoutePlanner routePlanner, final Lookup cookieSpecRegistry, final Lookup authSchemeRegistry, final CookieStore cookieStore, final CredentialsProvider credentialsProvider, final RequestConfig defaultConfig, final List closeables) {
        this.log = LogFactory.getLog(this.getClass());
        Args.notNull(execChain, "HTTP client exec chain");
        Args.notNull(connManager, "HTTP connection manager");
        Args.notNull(routePlanner, "HTTP route planner");
        this.execChain = execChain;
        this.connManager = connManager;
        this.routePlanner = routePlanner;
        this.cookieSpecRegistry = cookieSpecRegistry;
        this.authSchemeRegistry = authSchemeRegistry;
        this.cookieStore = cookieStore;
        this.credentialsProvider = credentialsProvider;
        this.defaultConfig = defaultConfig;
        this.closeables = closeables;
    }
    
    private HttpRoute determineRoute(final HttpHost httpHost, final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException {
        HttpHost httpHost2 = httpHost;
        if (httpHost2 == null) {
            httpHost2 = (HttpHost)httpRequest.getParams().getParameter("http.default-host");
        }
        Asserts.notNull(httpHost2, "Target host");
        return this.routePlanner.determineRoute(httpHost2, httpRequest, httpContext);
    }
    
    private void setupContext(final HttpClientContext httpClientContext) {
        if (httpClientContext.getAttribute("http.auth.target-scope") == null) {
            httpClientContext.setAttribute("http.auth.target-scope", new AuthState());
        }
        if (httpClientContext.getAttribute("http.auth.proxy-scope") == null) {
            httpClientContext.setAttribute("http.auth.proxy-scope", new AuthState());
        }
        if (httpClientContext.getAttribute("http.authscheme-registry") == null) {
            httpClientContext.setAttribute("http.authscheme-registry", this.authSchemeRegistry);
        }
        if (httpClientContext.getAttribute("http.cookiespec-registry") == null) {
            httpClientContext.setAttribute("http.cookiespec-registry", this.cookieSpecRegistry);
        }
        if (httpClientContext.getAttribute("http.cookie-store") == null) {
            httpClientContext.setAttribute("http.cookie-store", this.cookieStore);
        }
        if (httpClientContext.getAttribute("http.auth.credentials-provider") == null) {
            httpClientContext.setAttribute("http.auth.credentials-provider", this.credentialsProvider);
        }
        if (httpClientContext.getAttribute("http.request-config") == null) {
            httpClientContext.setAttribute("http.request-config", this.defaultConfig);
        }
    }
    
    @Override
    protected CloseableHttpResponse doExecute(final HttpHost httpHost, final HttpRequest httpRequest, final HttpContext httpContext) throws IOException, ClientProtocolException {
        Args.notNull(httpRequest, "HTTP request");
        HttpExecutionAware httpExecutionAware = null;
        if (httpRequest instanceof HttpExecutionAware) {
            httpExecutionAware = (HttpExecutionAware)httpRequest;
        }
        final HttpRequestWrapper wrap = HttpRequestWrapper.wrap(httpRequest);
        final HttpClientContext adapt = HttpClientContext.adapt((httpContext != null) ? httpContext : new BasicHttpContext());
        RequestConfig requestConfig = null;
        if (httpRequest instanceof Configurable) {
            requestConfig = ((Configurable)httpRequest).getConfig();
        }
        if (requestConfig == null) {
            final HttpParams params = httpRequest.getParams();
            if (params instanceof HttpParamsNames) {
                if (!((HttpParamsNames)params).getNames().isEmpty()) {
                    requestConfig = HttpClientParamConfig.getRequestConfig(params);
                }
            }
            else {
                requestConfig = HttpClientParamConfig.getRequestConfig(params);
            }
        }
        if (requestConfig != null) {
            adapt.setRequestConfig(requestConfig);
        }
        this.setupContext(adapt);
        return this.execChain.execute(this.determineRoute(httpHost, wrap, adapt), wrap, adapt, httpExecutionAware);
    }
    
    public void close() {
        this.connManager.shutdown();
        if (this.closeables != null) {
            final Iterator<Closeable> iterator = this.closeables.iterator();
            while (iterator.hasNext()) {
                iterator.next().close();
            }
        }
    }
    
    public HttpParams getParams() {
        throw new UnsupportedOperationException();
    }
    
    public ClientConnectionManager getConnectionManager() {
        return new ClientConnectionManager() {
            final InternalHttpClient this$0;
            
            public void shutdown() {
                InternalHttpClient.access$000(this.this$0).shutdown();
            }
            
            public ClientConnectionRequest requestConnection(final HttpRoute httpRoute, final Object o) {
                throw new UnsupportedOperationException();
            }
            
            public void releaseConnection(final ManagedClientConnection managedClientConnection, final long n, final TimeUnit timeUnit) {
                throw new UnsupportedOperationException();
            }
            
            public SchemeRegistry getSchemeRegistry() {
                throw new UnsupportedOperationException();
            }
            
            public void closeIdleConnections(final long n, final TimeUnit timeUnit) {
                InternalHttpClient.access$000(this.this$0).closeIdleConnections(n, timeUnit);
            }
            
            public void closeExpiredConnections() {
                InternalHttpClient.access$000(this.this$0).closeExpiredConnections();
            }
        };
    }
    
    static HttpClientConnectionManager access$000(final InternalHttpClient internalHttpClient) {
        return internalHttpClient.connManager;
    }
}
