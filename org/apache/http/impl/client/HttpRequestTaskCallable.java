package org.apache.http.impl.client;

import java.util.concurrent.*;
import org.apache.http.client.methods.*;
import java.util.concurrent.atomic.*;
import org.apache.http.protocol.*;
import org.apache.http.client.*;
import org.apache.http.concurrent.*;

class HttpRequestTaskCallable implements Callable
{
    private final HttpUriRequest request;
    private final HttpClient httpclient;
    private final AtomicBoolean cancelled;
    private final long scheduled;
    private long started;
    private long ended;
    private final HttpContext context;
    private final ResponseHandler responseHandler;
    private final FutureCallback callback;
    private final FutureRequestExecutionMetrics metrics;
    
    HttpRequestTaskCallable(final HttpClient httpclient, final HttpUriRequest request, final HttpContext context, final ResponseHandler responseHandler, final FutureCallback callback, final FutureRequestExecutionMetrics metrics) {
        this.cancelled = new AtomicBoolean(false);
        this.scheduled = System.currentTimeMillis();
        this.started = -1L;
        this.ended = -1L;
        this.httpclient = httpclient;
        this.responseHandler = responseHandler;
        this.request = request;
        this.context = context;
        this.callback = callback;
        this.metrics = metrics;
    }
    
    public long getScheduled() {
        return this.scheduled;
    }
    
    public long getStarted() {
        return this.started;
    }
    
    public long getEnded() {
        return this.ended;
    }
    
    public Object call() throws Exception {
        if (!this.cancelled.get()) {
            this.metrics.getActiveConnections().incrementAndGet();
            this.started = System.currentTimeMillis();
            this.metrics.getScheduledConnections().decrementAndGet();
            final Object execute = this.httpclient.execute(this.request, this.responseHandler, this.context);
            this.ended = System.currentTimeMillis();
            this.metrics.getSuccessfulConnections().increment(this.started);
            if (this.callback != null) {
                this.callback.completed(execute);
            }
            final Object o = execute;
            this.metrics.getRequests().increment(this.started);
            this.metrics.getTasks().increment(this.started);
            this.metrics.getActiveConnections().decrementAndGet();
            return o;
        }
        throw new IllegalStateException("call has been cancelled for request " + this.request.getURI());
    }
    
    public void cancel() {
        this.cancelled.set(true);
        if (this.callback != null) {
            this.callback.cancelled();
        }
    }
}
