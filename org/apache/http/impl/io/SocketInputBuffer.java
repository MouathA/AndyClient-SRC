package org.apache.http.impl.io;

import org.apache.http.io.*;
import org.apache.http.annotation.*;
import java.net.*;
import org.apache.http.params.*;
import org.apache.http.util.*;
import java.io.*;

@Deprecated
@NotThreadSafe
public class SocketInputBuffer extends AbstractSessionInputBuffer implements EofSensor
{
    private final Socket socket;
    private boolean eof;
    
    public SocketInputBuffer(final Socket socket, final int n, final HttpParams httpParams) throws IOException {
        Args.notNull(socket, "Socket");
        this.socket = socket;
        this.eof = false;
        if (1024 < 0) {
            socket.getReceiveBufferSize();
        }
        if (1024 < 1024) {}
        this.init(socket.getInputStream(), 1024, httpParams);
    }
    
    @Override
    protected int fillBuffer() throws IOException {
        final int fillBuffer = super.fillBuffer();
        this.eof = (fillBuffer == -1);
        return fillBuffer;
    }
    
    public boolean isDataAvailable(final int soTimeout) throws IOException {
        boolean b = this.hasBufferedData();
        if (!b) {
            final int soTimeout2 = this.socket.getSoTimeout();
            this.socket.setSoTimeout(soTimeout);
            this.fillBuffer();
            b = this.hasBufferedData();
            this.socket.setSoTimeout(soTimeout2);
        }
        return b;
    }
    
    public boolean isEof() {
        return this.eof;
    }
}
