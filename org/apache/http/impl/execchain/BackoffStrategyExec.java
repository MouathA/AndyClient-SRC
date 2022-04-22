package org.apache.http.impl.execchain;

import org.apache.http.annotation.*;
import org.apache.http.client.*;
import org.apache.http.util.*;
import org.apache.http.conn.routing.*;
import org.apache.http.client.protocol.*;
import org.apache.http.client.methods.*;
import java.io.*;
import org.apache.http.*;

@Immutable
public class BackoffStrategyExec implements ClientExecChain
{
    private final ClientExecChain requestExecutor;
    private final ConnectionBackoffStrategy connectionBackoffStrategy;
    private final BackoffManager backoffManager;
    
    public BackoffStrategyExec(final ClientExecChain requestExecutor, final ConnectionBackoffStrategy connectionBackoffStrategy, final BackoffManager backoffManager) {
        Args.notNull(requestExecutor, "HTTP client request executor");
        Args.notNull(connectionBackoffStrategy, "Connection backoff strategy");
        Args.notNull(backoffManager, "Backoff manager");
        this.requestExecutor = requestExecutor;
        this.connectionBackoffStrategy = connectionBackoffStrategy;
        this.backoffManager = backoffManager;
    }
    
    public CloseableHttpResponse execute(final HttpRoute httpRoute, final HttpRequestWrapper httpRequestWrapper, final HttpClientContext httpClientContext, final HttpExecutionAware httpExecutionAware) throws IOException, HttpException {
        Args.notNull(httpRoute, "HTTP route");
        Args.notNull(httpRequestWrapper, "HTTP request");
        Args.notNull(httpClientContext, "HTTP context");
        final CloseableHttpResponse execute = this.requestExecutor.execute(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware);
        if (this.connectionBackoffStrategy.shouldBackoff(execute)) {
            this.backoffManager.backOff(httpRoute);
        }
        else {
            this.backoffManager.probe(httpRoute);
        }
        return execute;
    }
}
