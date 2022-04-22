package org.apache.http.impl.client;

import org.apache.http.client.methods.*;
import java.util.concurrent.*;

public class HttpRequestFutureTask extends FutureTask
{
    private final HttpUriRequest request;
    private final HttpRequestTaskCallable callable;
    
    public HttpRequestFutureTask(final HttpUriRequest request, final HttpRequestTaskCallable callable) {
        super(callable);
        this.request = request;
        this.callable = callable;
    }
    
    @Override
    public boolean cancel(final boolean b) {
        this.callable.cancel();
        if (b) {
            this.request.abort();
        }
        return super.cancel(b);
    }
    
    public long scheduledTime() {
        return this.callable.getScheduled();
    }
    
    public long startedTime() {
        return this.callable.getStarted();
    }
    
    public long endedTime() {
        if (this.isDone()) {
            return this.callable.getEnded();
        }
        throw new IllegalStateException("Task is not done yet");
    }
    
    public long requestDuration() {
        if (this.isDone()) {
            return this.endedTime() - this.startedTime();
        }
        throw new IllegalStateException("Task is not done yet");
    }
    
    public long taskDuration() {
        if (this.isDone()) {
            return this.endedTime() - this.scheduledTime();
        }
        throw new IllegalStateException("Task is not done yet");
    }
    
    @Override
    public String toString() {
        return this.request.getRequestLine().getUri();
    }
}
