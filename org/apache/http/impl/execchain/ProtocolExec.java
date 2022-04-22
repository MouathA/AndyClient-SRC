package org.apache.http.impl.execchain;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.util.*;
import org.apache.http.conn.routing.*;
import org.apache.http.client.utils.*;
import java.net.*;
import org.apache.http.client.protocol.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.auth.*;
import org.apache.http.protocol.*;
import org.apache.http.client.*;
import java.io.*;
import org.apache.http.*;

@Immutable
public class ProtocolExec implements ClientExecChain
{
    private final Log log;
    private final ClientExecChain requestExecutor;
    private final HttpProcessor httpProcessor;
    
    public ProtocolExec(final ClientExecChain requestExecutor, final HttpProcessor httpProcessor) {
        this.log = LogFactory.getLog(this.getClass());
        Args.notNull(requestExecutor, "HTTP client request executor");
        Args.notNull(httpProcessor, "HTTP protocol processor");
        this.requestExecutor = requestExecutor;
        this.httpProcessor = httpProcessor;
    }
    
    void rewriteRequestURI(final HttpRequestWrapper httpRequestWrapper, final HttpRoute httpRoute) throws ProtocolException {
        final URI uri = httpRequestWrapper.getURI();
        if (uri != null) {
            URI uri2;
            if (httpRoute.getProxyHost() != null && !httpRoute.isTunnelled()) {
                if (!uri.isAbsolute()) {
                    uri2 = URIUtils.rewriteURI(uri, httpRoute.getTargetHost(), true);
                }
                else {
                    uri2 = URIUtils.rewriteURI(uri);
                }
            }
            else if (uri.isAbsolute()) {
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
        final HttpRequest original = httpRequestWrapper.getOriginal();
        URI uri;
        if (original instanceof HttpUriRequest) {
            uri = ((HttpUriRequest)original).getURI();
        }
        else {
            uri = URI.create(original.getRequestLine().getUri());
        }
        httpRequestWrapper.setURI(uri);
        this.rewriteRequestURI(httpRequestWrapper, httpRoute);
        HttpHost httpHost = (HttpHost)httpRequestWrapper.getParams().getParameter("http.virtual-host");
        if (httpHost != null && httpHost.getPort() == -1) {
            final int port = httpRoute.getTargetHost().getPort();
            if (port != -1) {
                httpHost = new HttpHost(httpHost.getHostName(), port, httpHost.getSchemeName());
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Using virtual host" + httpHost);
            }
        }
        HttpHost targetHost = null;
        if (httpHost != null) {
            targetHost = httpHost;
        }
        else if (uri != null && uri.isAbsolute() && uri.getHost() != null) {
            targetHost = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
        }
        if (targetHost == null) {
            targetHost = httpRoute.getTargetHost();
        }
        if (uri != null) {
            final String userInfo = uri.getUserInfo();
            if (userInfo != null) {
                CredentialsProvider credentialsProvider = httpClientContext.getCredentialsProvider();
                if (credentialsProvider == null) {
                    credentialsProvider = new BasicCredentialsProvider();
                    httpClientContext.setCredentialsProvider(credentialsProvider);
                }
                credentialsProvider.setCredentials(new AuthScope(targetHost), new UsernamePasswordCredentials(userInfo));
            }
        }
        httpClientContext.setAttribute("http.target_host", targetHost);
        httpClientContext.setAttribute("http.route", httpRoute);
        httpClientContext.setAttribute("http.request", httpRequestWrapper);
        this.httpProcessor.process(httpRequestWrapper, httpClientContext);
        final CloseableHttpResponse execute = this.requestExecutor.execute(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware);
        httpClientContext.setAttribute("http.response", execute);
        this.httpProcessor.process(execute, httpClientContext);
        return execute;
    }
}
