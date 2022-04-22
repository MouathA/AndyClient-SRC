package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import org.apache.http.*;
import java.io.*;
import org.apache.http.util.*;
import org.apache.http.io.*;

@Deprecated
@Immutable
public class LoggingSessionOutputBuffer implements SessionOutputBuffer
{
    private final SessionOutputBuffer out;
    private final Wire wire;
    private final String charset;
    
    public LoggingSessionOutputBuffer(final SessionOutputBuffer out, final Wire wire, final String s) {
        this.out = out;
        this.wire = wire;
        this.charset = ((s != null) ? s : Consts.ASCII.name());
    }
    
    public LoggingSessionOutputBuffer(final SessionOutputBuffer sessionOutputBuffer, final Wire wire) {
        this(sessionOutputBuffer, wire, null);
    }
    
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        this.out.write(array, n, n2);
        if (this.wire.enabled()) {
            this.wire.output(array, n, n2);
        }
    }
    
    public void write(final int n) throws IOException {
        this.out.write(n);
        if (this.wire.enabled()) {
            this.wire.output(n);
        }
    }
    
    public void write(final byte[] array) throws IOException {
        this.out.write(array);
        if (this.wire.enabled()) {
            this.wire.output(array);
        }
    }
    
    public void flush() throws IOException {
        this.out.flush();
    }
    
    public void writeLine(final CharArrayBuffer charArrayBuffer) throws IOException {
        this.out.writeLine(charArrayBuffer);
        if (this.wire.enabled()) {
            this.wire.output((new String(charArrayBuffer.buffer(), 0, charArrayBuffer.length()) + "\r\n").getBytes(this.charset));
        }
    }
    
    public void writeLine(final String s) throws IOException {
        this.out.writeLine(s);
        if (this.wire.enabled()) {
            this.wire.output((s + "\r\n").getBytes(this.charset));
        }
    }
    
    public HttpTransportMetrics getMetrics() {
        return this.out.getMetrics();
    }
}
