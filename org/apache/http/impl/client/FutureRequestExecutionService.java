package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import org.apache.http.client.methods.*;
import org.apache.http.protocol.*;
import org.apache.http.client.*;
import org.apache.http.concurrent.*;
import java.io.*;

@ThreadSafe
public class FutureRequestExecutionService implements Closeable
{
    private final HttpClient httpclient;
    private final ExecutorService executorService;
    private final FutureRequestExecutionMetrics metrics;
    private final AtomicBoolean closed;
    
    public FutureRequestExecutionService(final HttpClient httpclient, final ExecutorService executorService) {
        this.metrics = new FutureRequestExecutionMetrics();
        this.closed = new AtomicBoolean(false);
        this.httpclient = httpclient;
        this.executorService = executorService;
    }
    
    public HttpRequestFutureTask execute(final HttpUriRequest httpUriRequest, final HttpContext httpContext, final ResponseHandler responseHandler) {
        return this.execute(httpUriRequest, httpContext, responseHandler, null);
    }
    
    public HttpRequestFutureTask execute(final HttpUriRequest httpUriRequest, final HttpContext httpContext, final ResponseHandler responseHandler, final FutureCallback futureCallback) {
        if (this.closed.get()) {
            throw new IllegalStateException("Close has been called on this httpclient instance.");
        }
        this.metrics.getScheduledConnections().incrementAndGet();
        final HttpRequestFutureTask httpRequestFutureTask = new HttpRequestFutureTask(httpUriRequest, new HttpRequestTaskCallable(this.httpclient, httpUriRequest, httpContext, responseHandler, futureCallback, this.metrics));
        this.executorService.execute(httpRequestFutureTask);
        return httpRequestFutureTask;
    }
    
    public FutureRequestExecutionMetrics metrics() {
        return this.metrics;
    }
    
    public void close() throws IOException {
        this.closed.set(true);
        this.executorService.shutdownNow();
        if (this.httpclient instanceof Closeable) {
            ((Closeable)this.httpclient).close();
        }
    }
}
