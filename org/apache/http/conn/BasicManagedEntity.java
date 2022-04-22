package org.apache.http.conn;

import org.apache.http.entity.*;
import org.apache.http.annotation.*;
import org.apache.http.*;
import org.apache.http.util.*;
import java.io.*;

@Deprecated
@NotThreadSafe
public class BasicManagedEntity extends HttpEntityWrapper implements ConnectionReleaseTrigger, EofSensorWatcher
{
    protected ManagedClientConnection managedConn;
    protected final boolean attemptReuse;
    
    public BasicManagedEntity(final HttpEntity httpEntity, final ManagedClientConnection managedConn, final boolean attemptReuse) {
        super(httpEntity);
        Args.notNull(managedConn, "Connection");
        this.managedConn = managedConn;
        this.attemptReuse = attemptReuse;
    }
    
    @Override
    public boolean isRepeatable() {
        return false;
    }
    
    @Override
    public InputStream getContent() throws IOException {
        return new EofSensorInputStream(this.wrappedEntity.getContent(), this);
    }
    
    private void ensureConsumed() throws IOException {
        if (this.managedConn == null) {
            return;
        }
        if (this.attemptReuse) {
            EntityUtils.consume(this.wrappedEntity);
            this.managedConn.markReusable();
        }
        else {
            this.managedConn.unmarkReusable();
        }
        this.releaseManagedConnection();
    }
    
    @Deprecated
    @Override
    public void consumeContent() throws IOException {
        this.ensureConsumed();
    }
    
    @Override
    public void writeTo(final OutputStream outputStream) throws IOException {
        super.writeTo(outputStream);
        this.ensureConsumed();
    }
    
    public void releaseConnection() throws IOException {
        this.ensureConsumed();
    }
    
    public void abortConnection() throws IOException {
        if (this.managedConn != null) {
            this.managedConn.abortConnection();
            this.managedConn = null;
        }
    }
    
    public boolean eofDetected(final InputStream inputStream) throws IOException {
        if (this.managedConn != null) {
            if (this.attemptReuse) {
                inputStream.close();
                this.managedConn.markReusable();
            }
            else {
                this.managedConn.unmarkReusable();
            }
        }
        this.releaseManagedConnection();
        return false;
    }
    
    public boolean streamClosed(final InputStream inputStream) throws IOException {
        if (this.managedConn != null) {
            if (this.attemptReuse) {
                this.managedConn.isOpen();
                inputStream.close();
                this.managedConn.markReusable();
            }
            else {
                this.managedConn.unmarkReusable();
            }
        }
        this.releaseManagedConnection();
        return false;
    }
    
    public boolean streamAbort(final InputStream inputStream) throws IOException {
        if (this.managedConn != null) {
            this.managedConn.abortConnection();
        }
        return false;
    }
    
    protected void releaseManagedConnection() throws IOException {
        if (this.managedConn != null) {
            this.managedConn.releaseConnection();
            this.managedConn = null;
        }
    }
}
