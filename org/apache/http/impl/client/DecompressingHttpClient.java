package org.apache.http.impl.client;

import org.apache.http.client.protocol.*;
import org.apache.http.params.*;
import org.apache.http.conn.*;
import org.apache.http.client.methods.*;
import java.io.*;
import org.apache.http.client.utils.*;
import org.apache.http.protocol.*;
import org.apache.http.client.*;
import org.apache.http.util.*;
import org.apache.http.*;

@Deprecated
public class DecompressingHttpClient implements HttpClient
{
    private final HttpClient backend;
    private final HttpRequestInterceptor acceptEncodingInterceptor;
    private final HttpResponseInterceptor contentEncodingInterceptor;
    
    public DecompressingHttpClient() {
        this(new DefaultHttpClient());
    }
    
    public DecompressingHttpClient(final HttpClient httpClient) {
        this(httpClient, new RequestAcceptEncoding(), new ResponseContentEncoding());
    }
    
    DecompressingHttpClient(final HttpClient backend, final HttpRequestInterceptor acceptEncodingInterceptor, final HttpResponseInterceptor contentEncodingInterceptor) {
        this.backend = backend;
        this.acceptEncodingInterceptor = acceptEncodingInterceptor;
        this.contentEncodingInterceptor = contentEncodingInterceptor;
    }
    
    public HttpParams getParams() {
        return this.backend.getParams();
    }
    
    public ClientConnectionManager getConnectionManager() {
        return this.backend.getConnectionManager();
    }
    
    public HttpResponse execute(final HttpUriRequest httpUriRequest) throws IOException, ClientProtocolException {
        return this.execute(this.getHttpHost(httpUriRequest), httpUriRequest, (HttpContext)null);
    }
    
    public HttpClient getHttpClient() {
        return this.backend;
    }
    
    HttpHost getHttpHost(final HttpUriRequest httpUriRequest) {
        return URIUtils.extractHost(httpUriRequest.getURI());
    }
    
    public HttpResponse execute(final HttpUriRequest httpUriRequest, final HttpContext httpContext) throws IOException, ClientProtocolException {
        return this.execute(this.getHttpHost(httpUriRequest), httpUriRequest, httpContext);
    }
    
    public HttpResponse execute(final HttpHost httpHost, final HttpRequest httpRequest) throws IOException, ClientProtocolException {
        return this.execute(httpHost, httpRequest, (HttpContext)null);
    }
    
    public HttpResponse execute(final HttpHost httpHost, final HttpRequest httpRequest, final HttpContext httpContext) throws IOException, ClientProtocolException {
        final HttpContext httpContext2 = (httpContext != null) ? httpContext : new BasicHttpContext();
        RequestWrapper requestWrapper;
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            requestWrapper = new EntityEnclosingRequestWrapper((HttpEntityEnclosingRequest)httpRequest);
        }
        else {
            requestWrapper = new RequestWrapper(httpRequest);
        }
        this.acceptEncodingInterceptor.process(requestWrapper, httpContext2);
        final HttpResponse execute = this.backend.execute(httpHost, requestWrapper, httpContext2);
        this.contentEncodingInterceptor.process(execute, httpContext2);
        if (Boolean.TRUE.equals(httpContext2.getAttribute("http.client.response.uncompressed"))) {
            execute.removeHeaders("Content-Length");
            execute.removeHeaders("Content-Encoding");
            execute.removeHeaders("Content-MD5");
        }
        return execute;
    }
    
    public Object execute(final HttpUriRequest httpUriRequest, final ResponseHandler responseHandler) throws IOException, ClientProtocolException {
        return this.execute(this.getHttpHost(httpUriRequest), httpUriRequest, responseHandler);
    }
    
    public Object execute(final HttpUriRequest httpUriRequest, final ResponseHandler responseHandler, final HttpContext httpContext) throws IOException, ClientProtocolException {
        return this.execute(this.getHttpHost(httpUriRequest), httpUriRequest, responseHandler, httpContext);
    }
    
    public Object execute(final HttpHost httpHost, final HttpRequest httpRequest, final ResponseHandler responseHandler) throws IOException, ClientProtocolException {
        return this.execute(httpHost, httpRequest, responseHandler, null);
    }
    
    public Object execute(final HttpHost httpHost, final HttpRequest httpRequest, final ResponseHandler responseHandler, final HttpContext httpContext) throws IOException, ClientProtocolException {
        final HttpResponse execute = this.execute(httpHost, httpRequest, httpContext);
        final Object handleResponse = responseHandler.handleResponse(execute);
        final HttpEntity entity = execute.getEntity();
        if (entity != null) {
            EntityUtils.consume(entity);
        }
        return handleResponse;
    }
}
