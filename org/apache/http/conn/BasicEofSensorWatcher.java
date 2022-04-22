package org.apache.http.conn;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.io.*;

@Deprecated
@NotThreadSafe
public class BasicEofSensorWatcher implements EofSensorWatcher
{
    protected final ManagedClientConnection managedConn;
    protected final boolean attemptReuse;
    
    public BasicEofSensorWatcher(final ManagedClientConnection managedConn, final boolean attemptReuse) {
        Args.notNull(managedConn, "Connection");
        this.managedConn = managedConn;
        this.attemptReuse = attemptReuse;
    }
    
    public boolean eofDetected(final InputStream inputStream) throws IOException {
        if (this.attemptReuse) {
            inputStream.close();
            this.managedConn.markReusable();
        }
        this.managedConn.releaseConnection();
        return false;
    }
    
    public boolean streamClosed(final InputStream inputStream) throws IOException {
        if (this.attemptReuse) {
            inputStream.close();
            this.managedConn.markReusable();
        }
        this.managedConn.releaseConnection();
        return false;
    }
    
    public boolean streamAbort(final InputStream inputStream) throws IOException {
        this.managedConn.abortConnection();
        return false;
    }
}
