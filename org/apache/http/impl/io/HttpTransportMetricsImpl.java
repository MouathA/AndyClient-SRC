package org.apache.http.impl.io;

import org.apache.http.io.*;
import org.apache.http.annotation.*;

@NotThreadSafe
public class HttpTransportMetricsImpl implements HttpTransportMetrics
{
    private long bytesTransferred;
    
    public HttpTransportMetricsImpl() {
        this.bytesTransferred = 0L;
    }
    
    public long getBytesTransferred() {
        return this.bytesTransferred;
    }
    
    public void setBytesTransferred(final long bytesTransferred) {
        this.bytesTransferred = bytesTransferred;
    }
    
    public void incrementBytesTransferred(final long n) {
        this.bytesTransferred += n;
    }
    
    public void reset() {
        this.bytesTransferred = 0L;
    }
}
