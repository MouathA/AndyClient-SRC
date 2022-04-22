package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.protocol.*;
import java.io.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.*;
import java.net.*;
import org.apache.http.client.*;
import org.apache.http.*;
import org.apache.http.util.*;

@ThreadSafe
public abstract class CloseableHttpClient implements HttpClient, Closeable
{
    private final Log log;
    
    public CloseableHttpClient() {
        this.log = LogFactory.getLog(this.getClass());
    }
    
    protected abstract CloseableHttpResponse doExecute(final HttpHost p0, final HttpRequest p1, final HttpContext p2) throws IOException, ClientProtocolException;
    
    public CloseableHttpResponse execute(final HttpHost httpHost, final HttpRequest httpRequest, final HttpContext httpContext) throws IOException, ClientProtocolException {
        return this.doExecute(httpHost, httpRequest, httpContext);
    }
    
    public CloseableHttpResponse execute(final HttpUriRequest httpUriRequest, final HttpContext httpContext) throws IOException, ClientProtocolException {
        Args.notNull(httpUriRequest, "HTTP request");
        return this.doExecute(determineTarget(httpUriRequest), httpUriRequest, httpContext);
    }
    
    private static HttpHost determineTarget(final HttpUriRequest httpUriRequest) throws ClientProtocolException {
        HttpHost host = null;
        final URI uri = httpUriRequest.getURI();
        if (uri.isAbsolute()) {
            host = URIUtils.extractHost(uri);
            if (host == null) {
                throw new ClientProtocolException("URI does not specify a valid host name: " + uri);
            }
        }
        return host;
    }
    
    public CloseableHttpResponse execute(final HttpUriRequest httpUriRequest) throws IOException, ClientProtocolException {
        return this.execute(httpUriRequest, (HttpContext)null);
    }
    
    public CloseableHttpResponse execute(final HttpHost httpHost, final HttpRequest httpRequest) throws IOException, ClientProtocolException {
        return this.doExecute(httpHost, httpRequest, null);
    }
    
    public Object execute(final HttpUriRequest httpUriRequest, final ResponseHandler responseHandler) throws IOException, ClientProtocolException {
        return this.execute(httpUriRequest, responseHandler, null);
    }
    
    public Object execute(final HttpUriRequest httpUriRequest, final ResponseHandler responseHandler, final HttpContext httpContext) throws IOException, ClientProtocolException {
        return this.execute(determineTarget(httpUriRequest), httpUriRequest, responseHandler, httpContext);
    }
    
    public Object execute(final HttpHost httpHost, final HttpRequest httpRequest, final ResponseHandler responseHandler) throws IOException, ClientProtocolException {
        return this.execute(httpHost, httpRequest, responseHandler, null);
    }
    
    public Object execute(final HttpHost httpHost, final HttpRequest httpRequest, final ResponseHandler responseHandler, final HttpContext httpContext) throws IOException, ClientProtocolException {
        Args.notNull(responseHandler, "Response handler");
        final CloseableHttpResponse execute = this.execute(httpHost, httpRequest, httpContext);
        final Object handleResponse = responseHandler.handleResponse(execute);
        EntityUtils.consume(execute.getEntity());
        return handleResponse;
    }
    
    public HttpResponse execute(final HttpHost httpHost, final HttpRequest httpRequest, final HttpContext httpContext) throws IOException, ClientProtocolException {
        return this.execute(httpHost, httpRequest, httpContext);
    }
    
    public HttpResponse execute(final HttpHost httpHost, final HttpRequest httpRequest) throws IOException, ClientProtocolException {
        return this.execute(httpHost, httpRequest);
    }
    
    public HttpResponse execute(final HttpUriRequest httpUriRequest, final HttpContext httpContext) throws IOException, ClientProtocolException {
        return this.execute(httpUriRequest, httpContext);
    }
    
    public HttpResponse execute(final HttpUriRequest httpUriRequest) throws IOException, ClientProtocolException {
        return this.execute(httpUriRequest);
    }
}
