package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.*;
import org.apache.http.protocol.*;
import java.io.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import java.net.*;
import org.apache.http.util.*;
import org.apache.http.conn.*;
import org.apache.http.params.*;

@Deprecated
@ThreadSafe
public class AutoRetryHttpClient implements HttpClient
{
    private final HttpClient backend;
    private final ServiceUnavailableRetryStrategy retryStrategy;
    private final Log log;
    
    public AutoRetryHttpClient(final HttpClient backend, final ServiceUnavailableRetryStrategy retryStrategy) {
        this.log = LogFactory.getLog(this.getClass());
        Args.notNull(backend, "HttpClient");
        Args.notNull(retryStrategy, "ServiceUnavailableRetryStrategy");
        this.backend = backend;
        this.retryStrategy = retryStrategy;
    }
    
    public AutoRetryHttpClient() {
        this(new DefaultHttpClient(), new DefaultServiceUnavailableRetryStrategy());
    }
    
    public AutoRetryHttpClient(final ServiceUnavailableRetryStrategy serviceUnavailableRetryStrategy) {
        this(new DefaultHttpClient(), serviceUnavailableRetryStrategy);
    }
    
    public AutoRetryHttpClient(final HttpClient httpClient) {
        this(httpClient, new DefaultServiceUnavailableRetryStrategy());
    }
    
    public HttpResponse execute(final HttpHost httpHost, final HttpRequest httpRequest) throws IOException {
        return this.execute(httpHost, httpRequest, (HttpContext)null);
    }
    
    public Object execute(final HttpHost httpHost, final HttpRequest httpRequest, final ResponseHandler responseHandler) throws IOException {
        return this.execute(httpHost, httpRequest, responseHandler, null);
    }
    
    public Object execute(final HttpHost httpHost, final HttpRequest httpRequest, final ResponseHandler responseHandler, final HttpContext httpContext) throws IOException {
        return responseHandler.handleResponse(this.execute(httpHost, httpRequest, httpContext));
    }
    
    public HttpResponse execute(final HttpUriRequest httpUriRequest) throws IOException {
        return this.execute(httpUriRequest, (HttpContext)null);
    }
    
    public HttpResponse execute(final HttpUriRequest httpUriRequest, final HttpContext httpContext) throws IOException {
        final URI uri = httpUriRequest.getURI();
        return this.execute(new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme()), httpUriRequest, httpContext);
    }
    
    public Object execute(final HttpUriRequest httpUriRequest, final ResponseHandler responseHandler) throws IOException {
        return this.execute(httpUriRequest, responseHandler, null);
    }
    
    public Object execute(final HttpUriRequest httpUriRequest, final ResponseHandler responseHandler, final HttpContext httpContext) throws IOException {
        return responseHandler.handleResponse(this.execute(httpUriRequest, httpContext));
    }
    
    public HttpResponse execute(final HttpHost httpHost, final HttpRequest httpRequest, final HttpContext httpContext) throws IOException {
        HttpResponse execute;
        while (true) {
            execute = this.backend.execute(httpHost, httpRequest, httpContext);
            if (!this.retryStrategy.retryRequest(execute, 1, httpContext)) {
                break;
            }
            EntityUtils.consume(execute.getEntity());
            final long retryInterval = this.retryStrategy.getRetryInterval();
            this.log.trace("Wait for " + retryInterval);
            Thread.sleep(retryInterval);
            int n = 0;
            ++n;
        }
        return execute;
    }
    
    public ClientConnectionManager getConnectionManager() {
        return this.backend.getConnectionManager();
    }
    
    public HttpParams getParams() {
        return this.backend.getParams();
    }
}
