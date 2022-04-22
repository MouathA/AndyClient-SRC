package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.http.impl.execchain.*;
import org.apache.http.util.*;
import org.apache.http.impl.*;
import org.apache.http.params.*;
import org.apache.http.*;
import org.apache.http.protocol.*;
import org.apache.http.client.protocol.*;
import org.apache.http.conn.routing.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.config.*;
import java.io.*;
import org.apache.http.client.*;
import org.apache.http.conn.*;
import java.util.concurrent.*;
import org.apache.http.conn.scheme.*;

@ThreadSafe
class MinimalHttpClient extends CloseableHttpClient
{
    private final HttpClientConnectionManager connManager;
    private final MinimalClientExec requestExecutor;
    private final HttpParams params;
    
    public MinimalHttpClient(final HttpClientConnectionManager httpClientConnectionManager) {
        this.connManager = (HttpClientConnectionManager)Args.notNull(httpClientConnectionManager, "HTTP connection manager");
        this.requestExecutor = new MinimalClientExec(new HttpRequestExecutor(), httpClientConnectionManager, DefaultConnectionReuseStrategy.INSTANCE, DefaultConnectionKeepAliveStrategy.INSTANCE);
        this.params = new BasicHttpParams();
    }
    
    @Override
    protected CloseableHttpResponse doExecute(final HttpHost httpHost, final HttpRequest httpRequest, final HttpContext httpContext) throws IOException, ClientProtocolException {
        Args.notNull(httpHost, "Target host");
        Args.notNull(httpRequest, "HTTP request");
        HttpExecutionAware httpExecutionAware = null;
        if (httpRequest instanceof HttpExecutionAware) {
            httpExecutionAware = (HttpExecutionAware)httpRequest;
        }
        final HttpRequestWrapper wrap = HttpRequestWrapper.wrap(httpRequest);
        final HttpClientContext adapt = HttpClientContext.adapt((httpContext != null) ? httpContext : new BasicHttpContext());
        final HttpRoute httpRoute = new HttpRoute(httpHost);
        RequestConfig config = null;
        if (httpRequest instanceof Configurable) {
            config = ((Configurable)httpRequest).getConfig();
        }
        if (config != null) {
            adapt.setRequestConfig(config);
        }
        return this.requestExecutor.execute(httpRoute, wrap, adapt, httpExecutionAware);
    }
    
    public HttpParams getParams() {
        return this.params;
    }
    
    public void close() {
        this.connManager.shutdown();
    }
    
    public ClientConnectionManager getConnectionManager() {
        return new ClientConnectionManager() {
            final MinimalHttpClient this$0;
            
            public void shutdown() {
                MinimalHttpClient.access$000(this.this$0).shutdown();
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
                MinimalHttpClient.access$000(this.this$0).closeIdleConnections(n, timeUnit);
            }
            
            public void closeExpiredConnections() {
                MinimalHttpClient.access$000(this.this$0).closeExpiredConnections();
            }
        };
    }
    
    static HttpClientConnectionManager access$000(final MinimalHttpClient minimalHttpClient) {
        return minimalHttpClient.connManager;
    }
}
