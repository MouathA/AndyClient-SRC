package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import java.io.*;
import java.nio.*;
import org.apache.http.util.*;
import java.nio.charset.*;
import org.apache.http.io.*;

@NotThreadSafe
public class SessionOutputBufferImpl implements SessionOutputBuffer, BufferInfo
{
    private static final byte[] CRLF;
    private final HttpTransportMetricsImpl metrics;
    private final ByteArrayBuffer buffer;
    private final int fragementSizeHint;
    private final CharsetEncoder encoder;
    private OutputStream outstream;
    private ByteBuffer bbuf;
    
    public SessionOutputBufferImpl(final HttpTransportMetricsImpl metrics, final int n, final int n2, final CharsetEncoder encoder) {
        Args.positive(n, "Buffer size");
        Args.notNull(metrics, "HTTP transport metrcis");
        this.metrics = metrics;
        this.buffer = new ByteArrayBuffer(n);
        this.fragementSizeHint = ((n2 >= 0) ? n2 : 0);
        this.encoder = encoder;
    }
    
    public SessionOutputBufferImpl(final HttpTransportMetricsImpl httpTransportMetricsImpl, final int n) {
        this(httpTransportMetricsImpl, n, n, null);
    }
    
    public void bind(final OutputStream outstream) {
        this.outstream = outstream;
    }
    
    public boolean isBound() {
        return this.outstream != null;
    }
    
    public int capacity() {
        return this.buffer.capacity();
    }
    
    public int length() {
        return this.buffer.length();
    }
    
    public int available() {
        return this.capacity() - this.length();
    }
    
    private void streamWrite(final byte[] array, final int n, final int n2) throws IOException {
        Asserts.notNull(this.outstream, "Output stream");
        this.outstream.write(array, n, n2);
    }
    
    private void flushStream() throws IOException {
        if (this.outstream != null) {
            this.outstream.flush();
        }
    }
    
    private void flushBuffer() throws IOException {
        final int length = this.buffer.length();
        if (length > 0) {
            this.streamWrite(this.buffer.buffer(), 0, length);
            this.buffer.clear();
            this.metrics.incrementBytesTransferred(length);
        }
    }
    
    public void flush() throws IOException {
        this.flushBuffer();
        this.flushStream();
    }
    
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        if (array == null) {
            return;
        }
        if (n2 > this.fragementSizeHint || n2 > this.buffer.capacity()) {
            this.flushBuffer();
            this.streamWrite(array, n, n2);
            this.metrics.incrementBytesTransferred(n2);
        }
        else {
            if (n2 > this.buffer.capacity() - this.buffer.length()) {
                this.flushBuffer();
            }
            this.buffer.append(array, n, n2);
        }
    }
    
    public void write(final byte[] array) throws IOException {
        if (array == null) {
            return;
        }
        this.write(array, 0, array.length);
    }
    
    public void write(final int n) throws IOException {
        if (this.fragementSizeHint > 0) {
            if (this.buffer.isFull()) {
                this.flushBuffer();
            }
            this.buffer.append(n);
        }
        else {
            this.flushBuffer();
            this.outstream.write(n);
        }
    }
    
    public void writeLine(final String s) throws IOException {
        if (s == null) {
            return;
        }
        if (s.length() > 0) {
            if (this.encoder == null) {
                while (0 < s.length()) {
                    this.write(s.charAt(0));
                    int n = 0;
                    ++n;
                }
            }
            else {
                this.writeEncoded(CharBuffer.wrap(s));
            }
        }
        this.write(SessionOutputBufferImpl.CRLF);
    }
    
    public void writeLine(final CharArrayBuffer charArrayBuffer) throws IOException {
        if (charArrayBuffer == null) {
            return;
        }
        if (this.encoder == null) {
            int min;
            for (int i = charArrayBuffer.length(); i > 0; i -= min) {
                min = Math.min(this.buffer.capacity() - this.buffer.length(), i);
                if (min > 0) {
                    this.buffer.append(charArrayBuffer, 0, min);
                }
                if (this.buffer.isFull()) {
                    this.flushBuffer();
                }
            }
        }
        else {
            this.writeEncoded(CharBuffer.wrap(charArrayBuffer.buffer(), 0, charArrayBuffer.length()));
        }
        this.write(SessionOutputBufferImpl.CRLF);
    }
    
    private void writeEncoded(final CharBuffer charBuffer) throws IOException {
        if (!charBuffer.hasRemaining()) {
            return;
        }
        if (this.bbuf == null) {
            this.bbuf = ByteBuffer.allocate(1024);
        }
        this.encoder.reset();
        while (charBuffer.hasRemaining()) {
            this.handleEncodingResult(this.encoder.encode(charBuffer, this.bbuf, true));
        }
        this.handleEncodingResult(this.encoder.flush(this.bbuf));
        this.bbuf.clear();
    }
    
    private void handleEncodingResult(final CoderResult coderResult) throws IOException {
        if (coderResult.isError()) {
            coderResult.throwException();
        }
        this.bbuf.flip();
        while (this.bbuf.hasRemaining()) {
            this.write(this.bbuf.get());
        }
        this.bbuf.compact();
    }
    
    public HttpTransportMetrics getMetrics() {
        return this.metrics;
    }
    
    static {
        CRLF = new byte[] { 13, 10 };
    }
}
