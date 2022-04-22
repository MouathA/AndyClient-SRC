package org.apache.http.impl.execchain;

import org.apache.http.entity.*;
import org.apache.http.annotation.*;
import org.apache.http.*;
import org.apache.http.conn.*;
import java.io.*;

@NotThreadSafe
class ResponseEntityWrapper extends HttpEntityWrapper implements EofSensorWatcher
{
    private final ConnectionHolder connReleaseTrigger;
    
    public ResponseEntityWrapper(final HttpEntity httpEntity, final ConnectionHolder connReleaseTrigger) {
        super(httpEntity);
        this.connReleaseTrigger = connReleaseTrigger;
    }
    
    private void cleanup() {
        if (this.connReleaseTrigger != null) {
            this.connReleaseTrigger.abortConnection();
        }
    }
    
    public void releaseConnection() throws IOException {
        if (this.connReleaseTrigger != null) {
            if (this.connReleaseTrigger.isReusable()) {
                this.connReleaseTrigger.releaseConnection();
            }
            this.cleanup();
        }
    }
    
    @Override
    public boolean isRepeatable() {
        return false;
    }
    
    @Override
    public InputStream getContent() throws IOException {
        return new EofSensorInputStream(this.wrappedEntity.getContent(), this);
    }
    
    @Deprecated
    @Override
    public void consumeContent() throws IOException {
        this.releaseConnection();
    }
    
    @Override
    public void writeTo(final OutputStream outputStream) throws IOException {
        this.wrappedEntity.writeTo(outputStream);
        this.releaseConnection();
        this.cleanup();
    }
    
    public boolean eofDetected(final InputStream inputStream) throws IOException {
        inputStream.close();
        this.releaseConnection();
        this.cleanup();
        return false;
    }
    
    public boolean streamClosed(final InputStream inputStream) throws IOException {
        final boolean b = this.connReleaseTrigger != null && !this.connReleaseTrigger.isReleased();
        inputStream.close();
        this.releaseConnection();
        this.cleanup();
        return false;
    }
    
    public boolean streamAbort(final InputStream inputStream) throws IOException {
        this.cleanup();
        return false;
    }
}
