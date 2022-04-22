package org.apache.http.conn;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.io.*;

@NotThreadSafe
public class EofSensorInputStream extends InputStream implements ConnectionReleaseTrigger
{
    protected InputStream wrappedStream;
    private boolean selfClosed;
    private final EofSensorWatcher eofWatcher;
    
    public EofSensorInputStream(final InputStream wrappedStream, final EofSensorWatcher eofWatcher) {
        Args.notNull(wrappedStream, "Wrapped stream");
        this.wrappedStream = wrappedStream;
        this.selfClosed = false;
        this.eofWatcher = eofWatcher;
    }
    
    boolean isSelfClosed() {
        return this.selfClosed;
    }
    
    InputStream getWrappedStream() {
        return this.wrappedStream;
    }
    
    protected boolean isReadAllowed() throws IOException {
        if (this.selfClosed) {
            throw new IOException("Attempted read on closed stream.");
        }
        return this.wrappedStream != null;
    }
    
    @Override
    public int read() throws IOException {
        if (this.isReadAllowed()) {
            this.wrappedStream.read();
            this.checkEOF(-1);
        }
        return -1;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (this.isReadAllowed()) {
            this.wrappedStream.read(array, n, n2);
            this.checkEOF(-1);
        }
        return -1;
    }
    
    @Override
    public int read(final byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    @Override
    public int available() throws IOException {
        if (this.isReadAllowed()) {
            this.wrappedStream.available();
        }
        return 0;
    }
    
    @Override
    public void close() throws IOException {
        this.selfClosed = true;
        this.checkClose();
    }
    
    protected void checkEOF(final int n) throws IOException {
        if (this.wrappedStream != null && n < 0) {
            if (this.eofWatcher != null) {
                this.eofWatcher.eofDetected(this.wrappedStream);
            }
            if (true) {
                this.wrappedStream.close();
            }
            this.wrappedStream = null;
        }
    }
    
    protected void checkClose() throws IOException {
        if (this.wrappedStream != null) {
            if (this.eofWatcher != null) {
                this.eofWatcher.streamClosed(this.wrappedStream);
            }
            if (true) {
                this.wrappedStream.close();
            }
            this.wrappedStream = null;
        }
    }
    
    protected void checkAbort() throws IOException {
        if (this.wrappedStream != null) {
            if (this.eofWatcher != null) {
                this.eofWatcher.streamAbort(this.wrappedStream);
            }
            if (true) {
                this.wrappedStream.close();
            }
            this.wrappedStream = null;
        }
    }
    
    public void releaseConnection() throws IOException {
        this.close();
    }
    
    public void abortConnection() throws IOException {
        this.selfClosed = true;
        this.checkAbort();
    }
}
