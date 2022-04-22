package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.config.*;
import java.io.*;
import org.apache.http.util.*;
import org.apache.http.*;
import java.nio.*;
import java.nio.charset.*;
import org.apache.http.io.*;

@NotThreadSafe
public class SessionInputBufferImpl implements SessionInputBuffer, BufferInfo
{
    private final HttpTransportMetricsImpl metrics;
    private final byte[] buffer;
    private final ByteArrayBuffer linebuffer;
    private final int minChunkLimit;
    private final MessageConstraints constraints;
    private final CharsetDecoder decoder;
    private InputStream instream;
    private int bufferpos;
    private int bufferlen;
    private CharBuffer cbuf;
    
    public SessionInputBufferImpl(final HttpTransportMetricsImpl metrics, final int n, final int n2, final MessageConstraints messageConstraints, final CharsetDecoder decoder) {
        Args.notNull(metrics, "HTTP transport metrcis");
        Args.positive(n, "Buffer size");
        this.metrics = metrics;
        this.buffer = new byte[n];
        this.bufferpos = 0;
        this.bufferlen = 0;
        this.minChunkLimit = ((n2 >= 0) ? n2 : 512);
        this.constraints = ((messageConstraints != null) ? messageConstraints : MessageConstraints.DEFAULT);
        this.linebuffer = new ByteArrayBuffer(n);
        this.decoder = decoder;
    }
    
    public SessionInputBufferImpl(final HttpTransportMetricsImpl httpTransportMetricsImpl, final int n) {
        this(httpTransportMetricsImpl, n, n, null, null);
    }
    
    public void bind(final InputStream instream) {
        this.instream = instream;
    }
    
    public boolean isBound() {
        return this.instream != null;
    }
    
    public int capacity() {
        return this.buffer.length;
    }
    
    public int length() {
        return this.bufferlen - this.bufferpos;
    }
    
    public int available() {
        return this.capacity() - this.length();
    }
    
    private int streamRead(final byte[] array, final int n, final int n2) throws IOException {
        Asserts.notNull(this.instream, "Input stream");
        return this.instream.read(array, n, n2);
    }
    
    public int fillBuffer() throws IOException {
        if (this.bufferpos > 0) {
            final int bufferlen = this.bufferlen - this.bufferpos;
            if (bufferlen > 0) {
                System.arraycopy(this.buffer, this.bufferpos, this.buffer, 0, bufferlen);
            }
            this.bufferpos = 0;
            this.bufferlen = bufferlen;
        }
        final int bufferlen2 = this.bufferlen;
        final int streamRead = this.streamRead(this.buffer, bufferlen2, this.buffer.length - bufferlen2);
        if (streamRead == -1) {
            return -1;
        }
        this.bufferlen = bufferlen2 + streamRead;
        this.metrics.incrementBytesTransferred(streamRead);
        return streamRead;
    }
    
    public boolean hasBufferedData() {
        return this.bufferpos < this.bufferlen;
    }
    
    public void clear() {
        this.bufferpos = 0;
        this.bufferlen = 0;
    }
    
    public int read() throws IOException {
        while (!this.hasBufferedData()) {
            if (this.fillBuffer() == -1) {
                return -1;
            }
        }
        return this.buffer[this.bufferpos++] & 0xFF;
    }
    
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (array == null) {
            return 0;
        }
        if (this.hasBufferedData()) {
            final int min = Math.min(n2, this.bufferlen - this.bufferpos);
            System.arraycopy(this.buffer, this.bufferpos, array, n, min);
            this.bufferpos += min;
            return min;
        }
        if (n2 > this.minChunkLimit) {
            final int streamRead = this.streamRead(array, n, n2);
            if (streamRead > 0) {
                this.metrics.incrementBytesTransferred(streamRead);
            }
            return streamRead;
        }
        while (!this.hasBufferedData()) {
            if (this.fillBuffer() == -1) {
                return -1;
            }
        }
        final int min2 = Math.min(n2, this.bufferlen - this.bufferpos);
        System.arraycopy(this.buffer, this.bufferpos, array, n, min2);
        this.bufferpos += min2;
        return min2;
    }
    
    public int read(final byte[] array) throws IOException {
        if (array == null) {
            return 0;
        }
        return this.read(array, 0, array.length);
    }
    
    private int locateLF() {
        for (int i = this.bufferpos; i < this.bufferlen; ++i) {
            if (this.buffer[i] == 10) {
                return i;
            }
        }
        return -1;
    }
    
    public int readLine(final CharArrayBuffer charArrayBuffer) throws IOException {
        Args.notNull(charArrayBuffer, "Char array buffer");
        while (false) {
            final int locateLF = this.locateLF();
            if (locateLF != -1) {
                if (this.linebuffer.isEmpty()) {
                    return this.lineFromReadBuffer(charArrayBuffer, locateLF);
                }
                this.linebuffer.append(this.buffer, this.bufferpos, locateLF + 1 - this.bufferpos);
                this.bufferpos = locateLF + 1;
            }
            else {
                if (this.hasBufferedData()) {
                    this.linebuffer.append(this.buffer, this.bufferpos, this.bufferlen - this.bufferpos);
                    this.bufferpos = this.bufferlen;
                }
                this.fillBuffer();
                if (0 == -1) {}
            }
            final int maxLineLength = this.constraints.getMaxLineLength();
            if (maxLineLength > 0 && this.linebuffer.length() >= maxLineLength) {
                throw new MessageConstraintException("Maximum line length limit exceeded");
            }
        }
        if (0 == -1 && this.linebuffer.isEmpty()) {
            return -1;
        }
        return this.lineFromLineBuffer(charArrayBuffer);
    }
    
    private int lineFromLineBuffer(final CharArrayBuffer charArrayBuffer) throws IOException {
        int n = this.linebuffer.length();
        if (n > 0) {
            if (this.linebuffer.byteAt(n - 1) == 10) {
                --n;
            }
            if (n > 0 && this.linebuffer.byteAt(n - 1) == 13) {
                --n;
            }
        }
        if (this.decoder == null) {
            charArrayBuffer.append(this.linebuffer, 0, n);
        }
        else {
            n = this.appendDecoded(charArrayBuffer, ByteBuffer.wrap(this.linebuffer.buffer(), 0, n));
        }
        this.linebuffer.clear();
        return n;
    }
    
    private int lineFromReadBuffer(final CharArrayBuffer charArrayBuffer, final int n) throws IOException {
        int n2 = n;
        final int bufferpos = this.bufferpos;
        this.bufferpos = n2 + 1;
        if (n2 > bufferpos && this.buffer[n2 - 1] == 13) {
            --n2;
        }
        int appendDecoded = n2 - bufferpos;
        if (this.decoder == null) {
            charArrayBuffer.append(this.buffer, bufferpos, appendDecoded);
        }
        else {
            appendDecoded = this.appendDecoded(charArrayBuffer, ByteBuffer.wrap(this.buffer, bufferpos, appendDecoded));
        }
        return appendDecoded;
    }
    
    private int appendDecoded(final CharArrayBuffer charArrayBuffer, final ByteBuffer byteBuffer) throws IOException {
        if (!byteBuffer.hasRemaining()) {
            return 0;
        }
        if (this.cbuf == null) {
            this.cbuf = CharBuffer.allocate(1024);
        }
        this.decoder.reset();
        while (byteBuffer.hasRemaining()) {
            final int n = 0 + this.handleDecodingResult(this.decoder.decode(byteBuffer, this.cbuf, true), charArrayBuffer, byteBuffer);
        }
        final int n2 = 0 + this.handleDecodingResult(this.decoder.flush(this.cbuf), charArrayBuffer, byteBuffer);
        this.cbuf.clear();
        return 0;
    }
    
    private int handleDecodingResult(final CoderResult coderResult, final CharArrayBuffer charArrayBuffer, final ByteBuffer byteBuffer) throws IOException {
        if (coderResult.isError()) {
            coderResult.throwException();
        }
        this.cbuf.flip();
        final int remaining = this.cbuf.remaining();
        while (this.cbuf.hasRemaining()) {
            charArrayBuffer.append(this.cbuf.get());
        }
        this.cbuf.compact();
        return remaining;
    }
    
    public String readLine() throws IOException {
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(64);
        if (this.readLine(charArrayBuffer) != -1) {
            return charArrayBuffer.toString();
        }
        return null;
    }
    
    public boolean isDataAvailable(final int n) throws IOException {
        return this.hasBufferedData();
    }
    
    public HttpTransportMetrics getMetrics() {
        return this.metrics;
    }
}
