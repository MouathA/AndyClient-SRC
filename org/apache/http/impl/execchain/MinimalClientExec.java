package org.apache.http.impl.execchain;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.util.*;
import org.apache.http.conn.routing.*;
import org.apache.http.client.utils.*;
import java.net.*;
import org.apache.http.client.protocol.*;
import org.apache.http.concurrent.*;
import java.util.concurrent.*;
import org.apache.http.protocol.*;
import org.apache.http.client.methods.*;
import org.apache.http.conn.*;
import org.apache.http.client.config.*;
import java.io.*;
import org.apache.http.*;

@Immutable
public class MinimalClientExec implements ClientExecChain
{
    private final Log log;
    private final HttpRequestExecutor requestExecutor;
    private final HttpClientConnectionManager connManager;
    private final ConnectionReuseStrategy reuseStrategy;
    private final ConnectionKeepAliveStrategy keepAliveStrategy;
    private final HttpProcessor httpProcessor;
    
    public MinimalClientExec(final HttpRequestExecutor requestExecutor, final HttpClientConnectionManager connManager, final ConnectionReuseStrategy reuseStrategy, final ConnectionKeepAliveStrategy keepAliveStrategy) {
        this.log = LogFactory.getLog(this.getClass());
        Args.notNull(requestExecutor, "HTTP request executor");
        Args.notNull(connManager, "Client connection manager");
        Args.notNull(reuseStrategy, "Connection reuse strategy");
        Args.notNull(keepAliveStrategy, "Connection keep alive strategy");
        this.httpProcessor = new ImmutableHttpProcessor(new HttpRequestInterceptor[] { new RequestContent(), new RequestTargetHost(), new RequestClientConnControl(), new RequestUserAgent(VersionInfo.getUserAgent("Apache-HttpClient", "org.apache.http.client", this.getClass())) });
        this.requestExecutor = requestExecutor;
        this.connManager = connManager;
        this.reuseStrategy = reuseStrategy;
        this.keepAliveStrategy = keepAliveStrategy;
    }
    
    static void rewriteRequestURI(final HttpRequestWrapper httpRequestWrapper, final HttpRoute httpRoute) throws ProtocolException {
        final URI uri = httpRequestWrapper.getURI();
        if (uri != null) {
            URI uri2;
            if (uri.isAbsolute()) {
                uri2 = URIUtils.rewriteURI(uri, null, true);
            }
            else {
                uri2 = URIUtils.rewriteURI(uri);
            }
            httpRequestWrapper.setURI(uri2);
        }
    }
    
    public CloseableHttpResponse execute(final HttpRoute httpRoute, final HttpRequestWrapper httpRequestWrapper, final HttpClientContext httpClientContext, final HttpExecutionAware httpExecutionAware) throws IOException, HttpException {
        Args.notNull(httpRoute, "HTTP route");
        Args.notNull(httpRequestWrapper, "HTTP request");
        Args.notNull(httpClientContext, "HTTP context");
        rewriteRequestURI(httpRequestWrapper, httpRoute);
        final ConnectionRequest requestConnection = this.connManager.requestConnection(httpRoute, null);
        if (httpExecutionAware != null) {
            if (httpExecutionAware.isAborted()) {
                requestConnection.cancel();
                throw new RequestAbortedException("Request aborted");
            }
            httpExecutionAware.setCancellable(requestConnection);
        }
        final RequestConfig requestConfig = httpClientContext.getRequestConfig();
        final int connectionRequestTimeout = requestConfig.getConnectionRequestTimeout();
        final HttpClientConnection value = requestConnection.get((connectionRequestTimeout > 0) ? ((long)connectionRequestTimeout) : 0L, TimeUnit.MILLISECONDS);
        final ConnectionHolder cancellable = new ConnectionHolder(this.log, this.connManager, value);
        if (httpExecutionAware != null) {
            if (httpExecutionAware.isAborted()) {
                cancellable.close();
                throw new RequestAbortedException("Request aborted");
            }
            httpExecutionAware.setCancellable(cancellable);
        }
        if (!value.isOpen()) {
            final int connectTimeout = requestConfig.getConnectTimeout();
            this.connManager.connect(value, httpRoute, (connectTimeout > 0) ? connectTimeout : false, httpClientContext);
            this.connManager.routeComplete(value, httpRoute, httpClientContext);
        }
        final int socketTimeout = requestConfig.getSocketTimeout();
        if (socketTimeout >= 0) {
            value.setSocketTimeout(socketTimeout);
        }
        HttpHost targetHost = null;
        final HttpRequest original = httpRequestWrapper.getOriginal();
        if (original instanceof HttpUriRequest) {
            final URI uri = ((HttpUriRequest)original).getURI();
            if (uri.isAbsolute()) {
                targetHost = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
            }
        }
        if (targetHost == null) {
            targetHost = httpRoute.getTargetHost();
        }
        httpClientContext.setAttribute("http.target_host", targetHost);
        httpClientContext.setAttribute("http.request", httpRequestWrapper);
        httpClientContext.setAttribute("http.connection", value);
        httpClientContext.setAttribute("http.route", httpRoute);
        this.httpProcessor.process(httpRequestWrapper, httpClientContext);
        final HttpResponse execute = this.requestExecutor.execute(httpRequestWrapper, value, httpClientContext);
        this.httpProcessor.process(execute, httpClientContext);
        if (this.reuseStrategy.keepAlive(execute, httpClientContext)) {
            cancellable.setValidFor(this.keepAliveStrategy.getKeepAliveDuration(execute, httpClientContext), TimeUnit.MILLISECONDS);
            cancellable.markReusable();
        }
        else {
            cancellable.markNonReusable();
        }
        final HttpEntity entity = execute.getEntity();
        if (entity == null || !entity.isStreaming()) {
            cancellable.releaseConnection();
            return Proxies.enhanceResponse(execute, null);
        }
        return Proxies.enhanceResponse(execute, cancellable);
    }
}
