package org.apache.http.impl.execchain;

import org.apache.http.annotation.*;
import org.apache.http.client.*;
import org.apache.commons.logging.*;
import org.apache.http.util.*;
import org.apache.http.conn.routing.*;
import org.apache.http.client.protocol.*;
import org.apache.http.client.methods.*;
import java.io.*;
import org.apache.http.*;

@Immutable
public class RetryExec implements ClientExecChain
{
    private final Log log;
    private final ClientExecChain requestExecutor;
    private final HttpRequestRetryHandler retryHandler;
    
    public RetryExec(final ClientExecChain requestExecutor, final HttpRequestRetryHandler retryHandler) {
        this.log = LogFactory.getLog(this.getClass());
        Args.notNull(requestExecutor, "HTTP request executor");
        Args.notNull(retryHandler, "HTTP request retry handler");
        this.requestExecutor = requestExecutor;
        this.retryHandler = retryHandler;
    }
    
    public CloseableHttpResponse execute(final HttpRoute httpRoute, final HttpRequestWrapper httpRequestWrapper, final HttpClientContext httpClientContext, final HttpExecutionAware httpExecutionAware) throws IOException, HttpException {
        Args.notNull(httpRoute, "HTTP route");
        Args.notNull(httpRequestWrapper, "HTTP request");
        Args.notNull(httpClientContext, "HTTP context");
        httpRequestWrapper.getAllHeaders();
        return this.requestExecutor.execute(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware);
    }
}
