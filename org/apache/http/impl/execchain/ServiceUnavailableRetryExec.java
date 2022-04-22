package org.apache.http.impl.execchain;

import org.apache.http.annotation.*;
import org.apache.http.client.*;
import org.apache.commons.logging.*;
import org.apache.http.util.*;
import org.apache.http.conn.routing.*;
import org.apache.http.client.protocol.*;
import org.apache.http.client.methods.*;
import org.apache.http.protocol.*;
import java.io.*;
import org.apache.http.*;

@Immutable
public class ServiceUnavailableRetryExec implements ClientExecChain
{
    private final Log log;
    private final ClientExecChain requestExecutor;
    private final ServiceUnavailableRetryStrategy retryStrategy;
    
    public ServiceUnavailableRetryExec(final ClientExecChain requestExecutor, final ServiceUnavailableRetryStrategy retryStrategy) {
        this.log = LogFactory.getLog(this.getClass());
        Args.notNull(requestExecutor, "HTTP request executor");
        Args.notNull(retryStrategy, "Retry strategy");
        this.requestExecutor = requestExecutor;
        this.retryStrategy = retryStrategy;
    }
    
    public CloseableHttpResponse execute(final HttpRoute httpRoute, final HttpRequestWrapper httpRequestWrapper, final HttpClientContext httpClientContext, final HttpExecutionAware httpExecutionAware) throws IOException, HttpException {
        final Header[] allHeaders = httpRequestWrapper.getAllHeaders();
        CloseableHttpResponse execute;
        while (true) {
            execute = this.requestExecutor.execute(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware);
            if (!this.retryStrategy.retryRequest(execute, 1, httpClientContext)) {
                break;
            }
            execute.close();
            final long retryInterval = this.retryStrategy.getRetryInterval();
            if (retryInterval > 0L) {
                this.log.trace("Wait for " + retryInterval);
                Thread.sleep(retryInterval);
            }
            httpRequestWrapper.setHeaders(allHeaders);
            int n = 0;
            ++n;
        }
        return execute;
    }
}
