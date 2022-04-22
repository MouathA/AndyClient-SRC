package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.*;
import org.apache.http.params.*;
import java.io.*;
import java.nio.*;
import org.apache.http.util.*;
import java.nio.charset.*;
import org.apache.http.io.*;

@Deprecated
@NotThreadSafe
public abstract class AbstractSessionOutputBuffer implements SessionOutputBuffer, BufferInfo
{
    private static final byte[] CRLF;
    private OutputStream outstream;
    private ByteArrayBuffer buffer;
    private Charset charset;
    private boolean ascii;
    private int minChunkLimit;
    private HttpTransportMetricsImpl metrics;
    private CodingErrorAction onMalformedCharAction;
    private CodingErrorAction onUnmappableCharAction;
    private CharsetEncoder encoder;
    private ByteBuffer bbuf;
    
    protected AbstractSessionOutputBuffer(final OutputStream outstream, final int n, final Charset charset, final int n2, final CodingErrorAction codingErrorAction, final CodingErrorAction codingErrorAction2) {
        Args.notNull(outstream, "Input stream");
        Args.notNegative(n, "Buffer size");
        this.outstream = outstream;
        this.buffer = new ByteArrayBuffer(n);
        this.charset = ((charset != null) ? charset : Consts.ASCII);
        this.ascii = this.charset.equals(Consts.ASCII);
        this.encoder = null;
        this.minChunkLimit = ((n2 >= 0) ? n2 : 512);
        this.metrics = this.createTransportMetrics();
        this.onMalformedCharAction = ((codingErrorAction != null) ? codingErrorAction : CodingErrorAction.REPORT);
        this.onUnmappableCharAction = ((codingErrorAction2 != null) ? codingErrorAction2 : CodingErrorAction.REPORT);
    }
    
    public AbstractSessionOutputBuffer() {
    }
    
    protected void init(final OutputStream outstream, final int n, final HttpParams httpParams) {
        Args.notNull(outstream, "Input stream");
        Args.notNegative(n, "Buffer size");
        Args.notNull(httpParams, "HTTP parameters");
        this.outstream = outstream;
        this.buffer = new ByteArrayBuffer(n);
        final String s = (String)httpParams.getParameter("http.protocol.element-charset");
        this.charset = ((s != null) ? Charset.forName(s) : Consts.ASCII);
        this.ascii = this.charset.equals(Consts.ASCII);
        this.encoder = null;
        this.minChunkLimit = httpParams.getIntParameter("http.connection.min-chunk-limit", 512);
        this.metrics = this.createTransportMetrics();
        final CodingErrorAction codingErrorAction = (CodingErrorAction)httpParams.getParameter("http.malformed.input.action");
        this.onMalformedCharAction = ((codingErrorAction != null) ? codingErrorAction : CodingErrorAction.REPORT);
        final CodingErrorAction codingErrorAction2 = (CodingErrorAction)httpParams.getParameter("http.unmappable.input.action");
        this.onUnmappableCharAction = ((codingErrorAction2 != null) ? codingErrorAction2 : CodingErrorAction.REPORT);
    }
    
    protected HttpTransportMetricsImpl createTransportMetrics() {
        return new HttpTransportMetricsImpl();
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
    
    protected void flushBuffer() throws IOException {
        final int length = this.buffer.length();
        if (length > 0) {
            this.outstream.write(this.buffer.buffer(), 0, length);
            this.buffer.clear();
            this.metrics.incrementBytesTransferred(length);
        }
    }
    
    public void flush() throws IOException {
        this.flushBuffer();
        this.outstream.flush();
    }
    
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        if (array == null) {
            return;
        }
        if (n2 > this.minChunkLimit || n2 > this.buffer.capacity()) {
            this.flushBuffer();
            this.outstream.write(array, n, n2);
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
        if (this.buffer.isFull()) {
            this.flushBuffer();
        }
        this.buffer.append(n);
    }
    
    public void writeLine(final String s) throws IOException {
        if (s == null) {
            return;
        }
        if (s.length() > 0) {
            if (this.ascii) {
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
        this.write(AbstractSessionOutputBuffer.CRLF);
    }
    
    public void writeLine(final CharArrayBuffer charArrayBuffer) throws IOException {
        if (charArrayBuffer == null) {
            return;
        }
        if (this.ascii) {
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
        this.write(AbstractSessionOutputBuffer.CRLF);
    }
    
    private void writeEncoded(final CharBuffer charBuffer) throws IOException {
        if (!charBuffer.hasRemaining()) {
            return;
        }
        if (this.encoder == null) {
            (this.encoder = this.charset.newEncoder()).onMalformedInput(this.onMalformedCharAction);
            this.encoder.onUnmappableCharacter(this.onUnmappableCharAction);
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
