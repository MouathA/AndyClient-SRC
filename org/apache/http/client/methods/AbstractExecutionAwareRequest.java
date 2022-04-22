package org.apache.http.client.methods;

import org.apache.http.*;
import org.apache.http.concurrent.*;
import java.util.concurrent.locks.*;
import org.apache.http.conn.*;
import org.apache.http.message.*;
import org.apache.http.client.utils.*;
import org.apache.http.params.*;

public abstract class AbstractExecutionAwareRequest extends AbstractHttpMessage implements HttpExecutionAware, AbortableHttpRequest, Cloneable, HttpRequest
{
    private Lock abortLock;
    private boolean aborted;
    private Cancellable cancellable;
    
    protected AbstractExecutionAwareRequest() {
        this.abortLock = new ReentrantLock();
    }
    
    @Deprecated
    public void setConnectionRequest(final ClientConnectionRequest clientConnectionRequest) {
        if (this.aborted) {
            return;
        }
        this.abortLock.lock();
        this.cancellable = new Cancellable(clientConnectionRequest) {
            final ClientConnectionRequest val$connRequest;
            final AbstractExecutionAwareRequest this$0;
            
            public boolean cancel() {
                this.val$connRequest.abortRequest();
                return true;
            }
        };
        this.abortLock.unlock();
    }
    
    @Deprecated
    public void setReleaseTrigger(final ConnectionReleaseTrigger connectionReleaseTrigger) {
        if (this.aborted) {
            return;
        }
        this.abortLock.lock();
        this.cancellable = new Cancellable(connectionReleaseTrigger) {
            final ConnectionReleaseTrigger val$releaseTrigger;
            final AbstractExecutionAwareRequest this$0;
            
            public boolean cancel() {
                this.val$releaseTrigger.abortConnection();
                return true;
            }
        };
        this.abortLock.unlock();
    }
    
    private void cancelExecution() {
        if (this.cancellable != null) {
            this.cancellable.cancel();
            this.cancellable = null;
        }
    }
    
    public void abort() {
        if (this.aborted) {
            return;
        }
        this.abortLock.lock();
        this.aborted = true;
        this.cancelExecution();
        this.abortLock.unlock();
    }
    
    public boolean isAborted() {
        return this.aborted;
    }
    
    public void setCancellable(final Cancellable cancellable) {
        if (this.aborted) {
            return;
        }
        this.abortLock.lock();
        this.cancellable = cancellable;
        this.abortLock.unlock();
    }
    
    public Object clone() throws CloneNotSupportedException {
        final AbstractExecutionAwareRequest abstractExecutionAwareRequest = (AbstractExecutionAwareRequest)super.clone();
        abstractExecutionAwareRequest.headergroup = (HeaderGroup)CloneUtils.cloneObject(this.headergroup);
        abstractExecutionAwareRequest.params = (HttpParams)CloneUtils.cloneObject(this.params);
        abstractExecutionAwareRequest.abortLock = new ReentrantLock();
        abstractExecutionAwareRequest.cancellable = null;
        abstractExecutionAwareRequest.aborted = false;
        return abstractExecutionAwareRequest;
    }
    
    public void completed() {
        this.abortLock.lock();
        this.cancellable = null;
        this.abortLock.unlock();
    }
    
    public void reset() {
        this.abortLock.lock();
        this.cancelExecution();
        this.aborted = false;
        this.abortLock.unlock();
    }
}
