package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import org.apache.http.*;
import java.io.*;
import org.apache.http.util.*;
import org.apache.http.io.*;

@Deprecated
@Immutable
public class LoggingSessionInputBuffer implements SessionInputBuffer, EofSensor
{
    private final SessionInputBuffer in;
    private final EofSensor eofSensor;
    private final Wire wire;
    private final String charset;
    
    public LoggingSessionInputBuffer(final SessionInputBuffer in, final Wire wire, final String s) {
        this.in = in;
        this.eofSensor = ((in instanceof EofSensor) ? in : null);
        this.wire = wire;
        this.charset = ((s != null) ? s : Consts.ASCII.name());
    }
    
    public LoggingSessionInputBuffer(final SessionInputBuffer sessionInputBuffer, final Wire wire) {
        this(sessionInputBuffer, wire, null);
    }
    
    public boolean isDataAvailable(final int n) throws IOException {
        return this.in.isDataAvailable(n);
    }
    
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        final int read = this.in.read(array, n, n2);
        if (this.wire.enabled() && read > 0) {
            this.wire.input(array, n, read);
        }
        return read;
    }
    
    public int read() throws IOException {
        final int read = this.in.read();
        if (this.wire.enabled() && read != -1) {
            this.wire.input(read);
        }
        return read;
    }
    
    public int read(final byte[] array) throws IOException {
        final int read = this.in.read(array);
        if (this.wire.enabled() && read > 0) {
            this.wire.input(array, 0, read);
        }
        return read;
    }
    
    public String readLine() throws IOException {
        final String line = this.in.readLine();
        if (this.wire.enabled() && line != null) {
            this.wire.input((line + "\r\n").getBytes(this.charset));
        }
        return line;
    }
    
    public int readLine(final CharArrayBuffer charArrayBuffer) throws IOException {
        final int line = this.in.readLine(charArrayBuffer);
        if (this.wire.enabled() && line >= 0) {
            this.wire.input((new String(charArrayBuffer.buffer(), charArrayBuffer.length() - line, line) + "\r\n").getBytes(this.charset));
        }
        return line;
    }
    
    public HttpTransportMetrics getMetrics() {
        return this.in.getMetrics();
    }
    
    public boolean isEof() {
        return this.eofSensor != null && this.eofSensor.isEof();
    }
}
